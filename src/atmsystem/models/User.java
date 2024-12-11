package atmsystem.models;


public class User {
    private int id;
    private String name;
    private Account account;
    
    final public static int name_length = 3;
    
    
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
    
    public int get_id(){
        return id;
    }
    
    public String get_name(){
        return name;
    }
    
    public Account get_account(){
        return account;
    }
    
    @Override
    public String toString(){
        return "{user: {id: " + id + ", name: " + name + "}, account: " + account.toString();
    }

}
