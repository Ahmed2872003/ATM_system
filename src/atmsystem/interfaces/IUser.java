package atmsystem.interfaces;

import atmsystem.models.Transaction;
import atmsystem.models.User;
import java.util.ArrayList;


public interface IUser {
    
    void make_transaction(User user, int amount, Transaction.Type transType) throws Exception;
    int check_balance(User user) throws Exception;
    void change_pin(User user,String oldPin, String newPin) throws Exception;
    ArrayList<Transaction> view_transactions_history(User user) throws Exception;
    void login(User user) throws Exception;
    
}
