package project.wallet.models;

import lombok.Getter;

@Getter
public class TransactionTag {
  private Long TagId;
  private String TagName;

  public TransactionTag setCategoryId(Long categoryId){
    this.TagId = categoryId;
    return this;
  }

  public TransactionTag setCategoryName(String categoryName){
    this.TagName = categoryName;
    return this;
  }
}