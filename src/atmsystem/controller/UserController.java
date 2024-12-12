package atmsystem.controller;

import atmsystem.DB.Query;
import atmsystem.interfaces.IUser;

import atmsystem.models.Account;
import atmsystem.models.User;
import atmsystem.models.Transaction;
import atmsystem.models.UserModel;

import atmsystem.utils.Password;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class UserController implements IUser {

//    Properties
    private static UserController instance;

    HashMap<Integer, ReentrantLock> locks = new HashMap<Integer, ReentrantLock>();

//    Constructor
    private UserController() {
    }

//    Methods
    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }

        return instance;
    }

    @Override
    public void withdrawl_funds(User user, int withdrawlAmount) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void withdrawl_funds_shared(User user, int withdrawlAmount) throws Exception {
    }

    @Override
    public void deposite_funds(User user, int depositeAmount) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void deposite_funds_shared(User user, int depositeAmount) throws Exception {

    }

    @Override
    public int check_balance(User user) throws Exception {
        UserModel um = new UserModel();

        try {
            um.open_connection();

            ResultSet rs = um.join_with_account("WHERE user.id = ? AND account.id = ?", new Object[]{user.get_id(), user.get_account().get_id()});

            if (!rs.next()) throw new Exception("User_id or account_id not found in user_account model");
            
            user.get_account().set_balance(rs.getInt("balance"));

            return user.get_account().get_balance();

        } catch (Exception exp) {

            throw exp;
        } finally {
            um.close_connection();
        }
    }

    @Override
    public void change_pin(User user, String newPin) throws Exception {
        Account acc = user.get_account();

        Query query = new Query();

        try {
            query.open_connection();

            ResultSet rs = query.get("SELECT * FROM user_account WHERE user_id = ? AND account_id = ?", new Object[]{user.get_id(), acc.get_id()});

            if (!rs.next()) {
                throw new Exception("User_id or account_id not found in user_account model");
            }

            if (!Password.decrypt(rs.getString("pin")).equals(acc.get_pin())) { // check if the old Pin provided by the user matches the one in the database
                throw new Exception("Wrong old passowrd value");
            }

            acc.set_pin(newPin);

            String encryptedPin = Password.encrypt(newPin);

            query.update("UPDATE user_account SET pin = ? WHERE user_id = ? AND account_id = ?", new Object[]{encryptedPin, user.get_id(), acc.get_id()});

        } catch (Exception exp) {

            throw exp;
        } finally {
            query.close_connection();
        }

    }

    @Override
    public ArrayList<Transaction> view_transactions_history(User user) throws Exception {

        Query query = new Query();

        ArrayList<Transaction> transactions = new ArrayList();

        try {
            query.open_connection();

            ResultSet rs = query.get("SELECT * FROM transaction_log WHERE user_id = ? AND account_id = ?", new Object[]{user.get_id(), user.get_account().get_id()});

            while (rs.next()) {
                transactions.add(Transaction.convert_to_transaction(rs));
            }

            return transactions;

        } catch (Exception exp) {

            throw exp;
        } finally {
            query.close_connection();
        }

    }

    @Override
    public void login(User user) throws Exception {

        UserModel um = new UserModel();
        try {
            Account acc = user.get_account();

            um.open_connection();

            ResultSet rs = um.join_with_account("WHERE user.id = ? AND account.card_number = ?", new Object[]{user.get_id(), acc.get_card_number()});

            if (!rs.next()) {
                throw new Exception("Wrong id or card_number");
            }

            if (!Password.decrypt(rs.getString("pin")).equals(acc.get_pin())) {
                throw new Exception("Wrong password");
            }

            user.set_name(rs.getString("name"));
            acc.set_balance(rs.getInt("balance"));
            acc.set_id(rs.getInt("account_id"));
            acc.set_shared(rs.getBoolean("shared"));

        } catch (Exception exception) {

            throw exception;
        } finally {
            um.close_connection();
        }

    }

}
