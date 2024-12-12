package atmsystem.models;


import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int id;
    private String name;
    private Account account;
    
    final public static int name_length = 3;
    
//    Constructors
    public User(int id, String name, Account account) throws Exception{
        set_id(id);
        set_name(name);
        set_account(account);
    }
    
    public User(String name, Account account) throws Exception{
        set_name(name);
        set_account(account);
    }
    
    public User(int id, Account account) throws Exception{
        set_id(id);
        set_account(account);
    }
    
    public User(){}
    
//    Setters
    
    public void set_id(int id){
        this.id = id;
    }
    
    public void set_name(String name) throws Exception{
        
        if(name == null || name.isEmpty()) throw new Exception("User should have a name");
        
        if(name.length() < 3) throw new Exception("Name length should be >= " + name_length);
        
        this.name = name;
    }
    
    
    public void set_account(Account account) throws Exception{
        if(account == null) throw new Exception("User must have account");
            
        this.account = account;
    }
    
//    Getters
    
    public int get_id(){
        return id;
    }
    
    public String get_name(){
        return name;
    }
    
    public Account get_account(){
        return account;
    }
    
//    Methods
    
    public static User convert_to_user(ResultSet rs) throws SQLException, Exception{
        if(rs == null) return null;
        
        User u = new User();
        
        u.set_id(rs.getInt("id"));
        u.set_name(rs.getString("name"));
        
        return u;
    }
    
    
    @Override
    public String toString(){
        return "{id: " + id + ", name: " + name + "}";
    }

}
