package atmsystem.models;


public class Admin {
    private int id;
    private String password;
    
    final private static int password_length = 8;

    public Admin(int id, String password) throws Exception{
        set_id(id);
        set_password(password);
    }

    public void set_id(int id) throws Exception{
        if(id < 0) throw new Exception("Id must be positive");

        this.id = id;
    }

    public void set_password(String password) throws Exception{
        if(password == null || password.isEmpty()) throw new Exception("Should provide a password");
        
        if(password.length() < 8) throw new Exception("Password length shold be >= " + password_length);
        
        this.password = password;
    }
    
    public int get_id(){
        return id;
    }
    
    public String get_password(){
        return password;
    }
    
    @Override
    public String toString(){
        return "{id: " + id + ", password: " + password + "}";
    }


}
