package atmsystem.controller;


import atmsystem.interfaces.IAdmin;
import atmsystem.models.Admin;

import atmsystem.models.User;

public class AdminController implements IAdmin {
    
    private static AdminController instance;
    
    private AdminController(){}
    
    public static AdminController getInstance(){
        if(instance == null) instance = new AdminController();
        
        return instance;
    }
    

    @Override
    public void add_user_Account(User user) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    @Override
    public void add_user_to_existent_account(User user, String cardNumber) throws Exception{
    
    }
    
    @Override
    public void login(Admin admin){
    }

}
