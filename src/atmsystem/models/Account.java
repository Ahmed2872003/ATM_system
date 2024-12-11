package atmsystem.models;

public class Account {

    private int id;
    private String card_number;
    private String pin;
    private int balance;
    private boolean shared;

    final public static int card_number_length = 16;
    final public static int pin_length = 4;
    final public static int inital_balance = 50;
    
    public Account(int id, String card_number, String pin, int balance, boolean shared) throws Exception {
        set_id(id);
        set_card_number(card_number);
        set_pin(pin);
        set_balance(balance);
        set_shared(shared);
    }
    
    public Account(String card_number, String pin) throws Exception{
        set_card_number(card_number);
        set_pin(pin);
    }
    
    public Account(String pin, int balance, boolean shared) throws Exception{
        set_pin(pin);
        set_balance(balance);
        set_shared(shared);
    }
    
    
    
    public void set_id(int id){
        this.id = id;
    }
    public void set_card_number(String card_number) throws Exception{        
        if(!card_number.matches("^\\d{"+ card_number_length+"}$")) throw new Exception("Card number must be (" + card_number_length + ") digits.");
        
        this.card_number = card_number;
    }
    public void set_pin(String pin) throws Exception{
        if(!pin.matches("^\\d{" + pin_length + "}$")) throw new Exception("Pin must be (" + pin_length + ") digits.");
        
        this.pin = pin;
    }
    
    public void set_balance(int balance)throws Exception{
        
        if(balance < 0) throw new Exception("Balance must be positive.");
        
        if(balance < inital_balance) throw new Exception("Initial balance should be >= " + inital_balance);
        
        if(balance % Transaction.multiple_of_transactoin != 0) throw new Exception("Balance must be multiples of " + Transaction.multiple_of_transactoin);
        
        this.balance = balance;
        
    }
    public void update_balance(int newBalance) throws Exception{
        
        if(newBalance < 0) throw new Exception("No sufficient funds");
                
        this.balance = balance;
    }
    
    
    public void set_shared(boolean shared){
        this.shared = shared;
    }
    
    
    
    
    public int get_id(){
        return id;
    }
    public String get_card_number(){
        return card_number;
    }
    public String get_pin(){
        return pin;
    }
    public int get_balance(){
        return balance;
    }
    public boolean get_shared(){
        return shared;
    }
    
    @Override
    public String toString(){
        return "{id: " + id + ", card_number: " + card_number + ", pin: " + pin + ", balance: " + balance + ", shared: " + shared + "}";
    }
    

}
