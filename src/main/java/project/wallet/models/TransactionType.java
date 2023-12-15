package project.wallet.models;

import project.wallet.annotations.SqlTyping;

@SqlTyping(name="transaction_type")
public enum TransactionType {
  CLAIM,
  SPEND
}
