package atmsystem.interfaces;

import atmsystem.models.Transaction;
import atmsystem.models.User;
import java.util.ArrayList;


public interface IUser {
    
    void withdrawl_funds(User user, int withdrawlAmount) throws Exception;
    void deposite_funds(User user, int depositeAmount) throws Exception;
    int check_balance(User user) throws Exception;
    void change_pin(User user, String newPin) throws Exception;
    ArrayList<Transaction> view_transactions_history(User user) throws Exception;
    void login(User user) throws Exception;
    
}
