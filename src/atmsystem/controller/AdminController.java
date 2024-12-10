package atmsystem.controller;


import atmsystem.interfaces.IAdmin;

import atmsystem.models.User;

public class AdminController implements IAdmin {
    
    private static AdminController instance;
    
    private AdminController(){}
    
    public static AdminController getInstance(){
        if(instance == null) instance = new AdminController();
        
        return instance;
    }
    

    @Override
    public void addAccount(User user) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void resetPin(User user, String newPin) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
