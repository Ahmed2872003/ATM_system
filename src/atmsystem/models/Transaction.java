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

    public Transaction(int id, Type type, User user) throws Exception {
        this.id = id;
        set_type(type);
        set_user(user);
    }

    public void set_type(Type type) throws Exception {
        if (type == null) {
            throw new Exception("Should provide transaction type");
        }

        this.type = type;
    }

    public void set_user(User user) throws Exception {
        if (user == null) {
            throw new Exception("Should provide user");
        }
        
        this.user = user;

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

}
