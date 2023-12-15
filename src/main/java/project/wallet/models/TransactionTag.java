package project.wallet.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionTag {
  private Long transactionTagId;
  private String name;
}