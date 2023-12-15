package project.wallet.configs;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;

@Getter
@Setter
public class PooledConnection {
  private Connection temp1;
  private Connection temp2;
  private Connection temp3;
  private Connection temp4;
  private Connection temp5;
  private Connection temp6;
  private Connection temp7;
}
