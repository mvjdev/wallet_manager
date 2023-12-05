package project.wallet.models;

import lombok.Getter;

@Getter
public class TransactionTag {
  private Long id;
  private String name;

  public TransactionTag setId(Long id) {
    this.id = id;
    return this;
  }

  public TransactionTag setName(String name) {
    this.name = name;
    return this;
  }
}
