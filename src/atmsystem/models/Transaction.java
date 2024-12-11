package atmsystem.models;

public class Transaction {

    public static enum Type {
        Deposit(1), Withdrawal(2);
        private final int id;

        Type(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    };

    private int id;
    private Type type;
    private User user;
    private int amount;

    public static int multiple_of_transactoin = 50;
    
    
    public Transaction(int id, Type type, User user, int amount) throws Exception {
        this.id = id;
        set_type(type);
        set_user(user);
        set_amount(amount);
    }
    

    public void set_id(int id) {
        this.id = id;
    }

    public void set_type(Type type) throws Exception {
        if (type == null) {
            throw new Exception("Should provide transaction type");
        }

        this.type = type;
    }

    public void set_user(User user){

        this.user = user;

    }

    public void set_amount(int amount) throws Exception{
        if(amount < 0) throw new Exception("Transation amount should be positive");
        
        if(amount % Transaction.multiple_of_transactoin != 0) throw new Exception("Transation amount should be multiples of " + Transaction.multiple_of_transactoin);
        
        this.amount = amount;
    }
    
    public int get_id() {
        return id;
    }

    public Type get_type() {
        return type;
    }

    public User get_user() {
        return user;
    }
    
    @Override
    public String toString(){
        return "{id: " + id + ", type: " + type.toString() + ", amount: " + amount + "}";
    }

}
