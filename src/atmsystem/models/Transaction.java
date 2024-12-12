package atmsystem.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Transaction {

//    Properties
    public static enum Type {
        Deposit(1), Withdrawal(2);
        private final int id;

        Type(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Type fromId(int id) {
            if (id == Deposit.id) {
                return Deposit;
            } else if (id == Withdrawal.id) {
                return Withdrawal;
            }

            return null;
        }
    };

    private int id;
    private Type type;
    private User user;
    private int amount;
    private String created_at;

    public static int multiple_of_transactoin = 50;

//    Constructors
    public Transaction(int id, Type type, User user, int amount, String created_at) throws Exception {
        this.id = id;
        set_type(type);
        set_user(user);
        set_amount(amount);
        set_created_at(created_at);
    }

    public Transaction(Type type, User user, int amount) throws Exception {
        set_type(type);
        set_user(user);
        set_amount(amount);
    }

    public Transaction() {
    }

    ;
    
//    Setters

    public void set_id(int id) {
        this.id = id;
    }

    public void set_type(Type type) throws Exception {
        if (type == null) {
            throw new Exception("Should provide transaction type");
        }

        this.type = type;
    }

    public void set_user(User user) throws Exception {

        if (user == null) {
            throw new Exception("Transactoin should contain a user");
        }

        this.user = user;

    }

    public void set_amount(int amount) throws Exception {
        if (amount < 0) {
            throw new Exception("Transation amount should be positive");
        }

        if (amount % Transaction.multiple_of_transactoin != 0) {
            throw new Exception("Transation amount should be multiples of " + Transaction.multiple_of_transactoin);
        }

        this.amount = amount;
    }

    public void set_created_at(String created_at) {
        this.created_at = created_at;
    }

//    Getters
    public int get_id() {
        return id;
    }

    public Type get_type() {
        return type;
    }

    public User get_user() {
        return user;
    }

    public int get_amount() {
        return amount;
    }

    public String get_created_at() {
        return this.created_at;
    }

//    Methods
    public static Transaction convert_to_transaction(ResultSet rs) throws SQLException, Exception {
        if (rs == null) {
            return null;
        }

        Transaction tran = new Transaction();

        tran.set_id(rs.getInt("id"));
        tran.set_type(Type.fromId(rs.getInt("transaction_type_id")));
        tran.set_amount(rs.getInt("amount"));
        tran.set_created_at(rs.getString("created_at"));
        
        return tran;
    }

    @Override
    public String toString() {
        return "{id: " + id + ", type: " + type.toString() + ", amount: " + amount + ", created_at: " + created_at + "}";
    }

}
