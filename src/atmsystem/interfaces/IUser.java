package atmsystem.interfaces;


public interface IUser {
    
    void withdrawFunds();
    void depositeFunds();
    int checkBalance();
    void changePin();
    Object[] viewTransactionsHistory();
}
