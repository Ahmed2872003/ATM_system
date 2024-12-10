package atmsystem.interfaces;

import atmsystem.models.User;


public interface IUser {
    
    void withdrawlFunds(User user, int withdrawlAmount) throws Exception;
    void depositeFunds(User user, int depositeAmount) throws Exception;
    int checkBalance(User user) throws Exception;
    void changePin(User user, String newPin) throws Exception;
    Object[] viewTransactionsHistory(User user) throws Exception;
}
