package atmsystem.controller;

import atmsystem.DB.Query;
import atmsystem.interfaces.IUser;

import atmsystem.models.Account;
import atmsystem.models.User;
import atmsystem.models.Transaction;
import atmsystem.models.UserModel;

import atmsystem.utils.EncryptorDecryptor;

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
    public void make_transaction(User user, int amount, Transaction.Type transType) throws Exception {

        if (amount == 0) {
            throw new Exception("Invalid transaction amount");
        }

        Account acc = user.get_account();

        ReentrantLock locker = null;

        Query query = new Query();

        try {
            if (acc.get_shared()) {
                synchronized (locks) {
                    locks.putIfAbsent(acc.get_id(), new ReentrantLock());

                    locker = locks.get(acc.get_id());

                }
                locker.lock();
            }

            query.open_connection();

            int currentBalance = check_balance(user);

            if (transType == Transaction.Type.Withdrawal) { // updates the balance but in the current object to apply validatoin on the new balance
                acc.update_balance(currentBalance - amount);
            } else if (transType == Transaction.Type.Deposit) {
                acc.update_balance(currentBalance + amount);
            }

            query.update("UPDATE account SET balance = ? WHERE id = ?", new Object[]{acc.get_balance(), acc.get_id()}); // update the balance in the DB

            if (acc.get_shared()) {
                locker.unlock();
            }

            Object[] transaction_log_data = new Object[]{user.get_id(), acc.get_id(), transType.getId(), amount};

            query.update("INSERT INTO transaction_log (user_id, account_id, transaction_type_id, amount) VALUES (?, ?, ?, ?)", transaction_log_data);

        } finally {
            query.close_connection();

            if (acc.get_shared() && locker.isHeldByCurrentThread()) {
                locker.unlock();
            }
        }
    }

    @Override
    public int check_balance(User user) throws Exception {
        UserModel um = new UserModel();

        try {
            um.open_connection();

            ResultSet rs = um.join_with_account("WHERE user.id = ? AND account.id = ?", new Object[]{user.get_id(), user.get_account().get_id()});

            if (!rs.next()) {
                throw new Exception("User_id or account_id not found in user_account model");
            }

            user.get_account().update_balance(rs.getInt("balance"));

            return user.get_account().get_balance();

        } finally {
            um.close_connection();
        }

    }

    @Override
    public void change_pin(User user, String oldPin, String newPin) throws Exception {
        Account acc = user.get_account();

        Query query = new Query();

        try {
            query.open_connection();

            ResultSet rs = query.get("SELECT * FROM user_account WHERE user_id = ? AND account_id = ?", new Object[]{user.get_id(), acc.get_id()});

            if (!rs.next()) {
                throw new Exception("User_id or account_id not found in user_account model");
            }

            if (!EncryptorDecryptor.decrypt(rs.getString("pin")).equals(oldPin)) { // check if the old Pin provided by the user matches the one in the database
                throw new Exception("Wrong old pin value");
            }

            acc.set_pin(newPin);

            String encryptedPin = EncryptorDecryptor.encrypt(newPin);

            query.update("UPDATE user_account SET pin = ? WHERE user_id = ? AND account_id = ?", new Object[]{encryptedPin, user.get_id(), acc.get_id()});

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

            ResultSet rs = um.join_with_account("WHERE user_account.card_number = ?", new Object[]{acc.get_card_number()});

            if (!rs.next()) {
                throw new Exception("Wrong card_number");
            }

            if (!EncryptorDecryptor.decrypt(rs.getString("pin")).equals(acc.get_pin())) {
                throw new Exception("Wrong password");
            }

            user.set_id(rs.getInt("id"));

            user.set_name(rs.getString("name"));
            acc.update_balance(rs.getInt("balance"));
            acc.set_id(rs.getInt("account_id"));
            acc.set_shared(rs.getBoolean("shared"));

        } finally {
            um.close_connection();
        }

    }

}
