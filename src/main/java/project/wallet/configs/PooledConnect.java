package project.wallet.configs;

import java.util.HashMap;
import java.util.Map;

public class PooledConnect {
  private static final long expTime = 6000;
  public static HashMap<PooledConnection, Long> available = new HashMap<>();
  public static HashMap<PooledConnection, Long> inUse = new HashMap<>();

  public synchronized static PooledConnection getConnection() {
    long now = System.currentTimeMillis();
    if (!available.isEmpty()) {
      for (Map.Entry<PooledConnection, Long> entry : available.entrySet()) {
        if (now - entry.getValue() > expTime) {
          popElement(available);
        } else {
          PooledConnection po = popElement(available, entry.getKey());
          push(inUse, po, now);
          return po;
        }
      }
    }

    return createPooledObject(now);
  }

  private synchronized static PooledConnection createPooledObject(long now) {
    PooledConnection po = new PooledConnection();
    push(inUse, po, now);
    return po;
  }

  private synchronized static void push(
      HashMap<PooledConnection, Long> map,
      PooledConnection po,
      long now
  ) {
    map.put(po, now);
  }

  public static void releaseObject(PooledConnection po) {
    cleanUp(po);
    available.put(po, System.currentTimeMillis());
    inUse.remove(po);
  }

  private static void popElement(HashMap<PooledConnection, Long> map) {
    Map.Entry<PooledConnection, Long> entry = map.entrySet().iterator().next();
    map.remove(entry.getKey());
  }

  private static PooledConnection popElement(HashMap<PooledConnection, Long> map, PooledConnection key) {
    map.remove(key);
    return key;
  }

  public static void cleanUp(PooledConnection po) {
    po.setTemp1(null);
    po.setTemp2(null);
    po.setTemp3(null);
    po.setTemp4(null);
    po.setTemp5(null);
    po.setTemp6(null);
    po.setTemp7(null);
  }
}
