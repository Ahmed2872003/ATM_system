package atmsystem.controller;


import atmsystem.interfaces.IAdmin;

public class AdminController implements IAdmin {
    
    private static AdminController instance;
    
    private AdminController(){}
    
    public static AdminController getInstance(){
        if(instance == null) instance = new AdminController();
        
        return instance;
    }
    

    @Override
    public void addAccount() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void resetPin() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
