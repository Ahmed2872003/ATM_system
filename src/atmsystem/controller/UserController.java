package atmsystem.controller;


import atmsystem.interfaces.IUser;
import atmsystem.models.User;
import atmsystem.models.Transaction;

public class UserController implements IUser{
    
    private static UserController instance;
    
    private UserController(){}
    
    public static UserController getInstance(){
        if(instance == null) instance = new UserController();
        
        return instance;
    }

    @Override
    public void withdrawlFunds(User user, int withdrawlAmount) throws Exception{
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    @Override
    public void depositeFunds(User user, int depositeAmount) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }



    @Override
    public int checkBalance(User user) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void changePin(User user, String newPin) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Transaction[] viewTransactionsHistory(User user) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    
}
