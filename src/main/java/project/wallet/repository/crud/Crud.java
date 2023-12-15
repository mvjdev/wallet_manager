package project.wallet.repository.crud;

public class Crud {
  public static AccountCrudOperations ACCOUNT = new AccountCrudOperations();
  public static AccountAmountCrudOperations ACCOUNT_AMOUNT = new AccountAmountCrudOperations();
  public static CurrencyCrudOperations CURRENCY = new CurrencyCrudOperations();
  public static CurrencyValueCrudOperations CURRENCY_VALUE = new CurrencyValueCrudOperations();
  public static TransactionTagCrudOperations TRANSACTION_TAG = new TransactionTagCrudOperations();
  public static TransactionCrudOperations TRANSACTION = new TransactionCrudOperations();
  public static TransferHistoryCrudOperations TRANSFER_HISTORY = new TransferHistoryCrudOperations();
}
