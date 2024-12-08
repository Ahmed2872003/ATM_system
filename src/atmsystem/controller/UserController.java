package atmsystem.controller;


import atmsystem.interfaces.IUser;

public class UserController implements IUser{
    
    private static UserController instance;
    
    private UserController(){}
    
    public static UserController getInstance(){
        if(instance == null) instance = new UserController();
        
        return instance;
    }

    @Override
    public void withdrawFunds() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }


    @Override
    public int checkBalance() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void changePin() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Object[] viewTransactionsHistory() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void depositeFunds() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
