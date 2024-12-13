package atmsystem.controller;

import atmsystem.DB.Query;
import atmsystem.interfaces.IAdmin;
import atmsystem.models.Account;
import atmsystem.models.Admin;

import atmsystem.models.User;
import atmsystem.utils.RandomNumber;
import atmsystem.utils.EncryptorDecryptor;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileWriter;

import java.sql.ResultSet;

public class AdminController implements IAdmin {

    private static AdminController instance;

    private AdminController() {
    }

    public static AdminController getInstance() {
        if (instance == null) {
            instance = new AdminController();
        }

        return instance;
    }

    @Override
    public void add_user_Account(User user) throws Exception {

        Query query = new Query();

        try {
            query.open_connection();

            Account acc = user.get_account();

            int userId = query.insert("INSERT INTO user (name) VALUES (?)", new Object[]{user.get_name()});

            int accountId = query.insert("INSERT INTO account (balance, shared) VALUES (?, ?)", new Object[]{acc.get_balance(), acc.get_shared()});

            String generatedCardNumber = RandomNumber.generate(Account.card_number_length);

            String generatedPin = RandomNumber.generate(Account.pin_length);

            String encryptedPin = EncryptorDecryptor.encrypt(generatedPin);

            query.insert("INSERT INTO user_account (user_id, account_id, card_number, pin) VALUES (?, ?, ?, ?)", new Object[]{userId, accountId, generatedCardNumber, encryptedPin});

            user.set_id(userId);
            acc.set_id(accountId);
            acc.set_card_number(generatedCardNumber);
            acc.set_pin(generatedPin);

            FileWriter fw = new FileWriter("user_log.txt", true);

            fw.append(user.toString() + "=>" + acc.toString() + "\n");

            fw.close();
        } finally {
            query.close_connection();
        }

    }

    @Override
    public void add_user_to_existent_account(User user, Account acc) throws Exception {
        Query query = new Query();


        try {
            query.open_connection();

            ResultSet rs = query.get("SELECT * FROM account WHERE id = ?", new Object[]{acc.get_id()});

            if (!rs.next()) {
                throw new Exception("NO account with that id");
            }

            if (!rs.getBoolean("shared")) {
                throw new Exception("This account is not a shared account");
            }

            int userId = query.insert("INSERT INTO user (name) VALUES (?)", new Object[]{user.get_name()});

            String generatedCardNumber = RandomNumber.generate(Account.card_number_length);

            String generatedPin = RandomNumber.generate(Account.pin_length);

            String encryptedPin = EncryptorDecryptor.encrypt(generatedPin);

            query.insert("INSERT INTO user_account (user_id, account_id, card_number, pin) VALUES (?, ?, ?, ?)", new Object[]{userId, acc.get_id(), generatedCardNumber, encryptedPin});

            user.set_id(userId);
            acc.set_card_number(generatedCardNumber);
            acc.set_pin(generatedPin);
            acc.set_shared(true);
            acc.set_balance(rs.getInt("balance"));
            
            
            FileWriter fw = new FileWriter("user_log.txt", true);

            fw.append(user.toString() + "=>" + acc.toString() + "\n");

            fw.close();
        } finally {
            query.close_connection();
        }
    }

    @Override
    public void login(Admin admin) throws Exception {
        Query query = new Query();

        try {
            query.open_connection();

            ResultSet rs = query.get("SELECT * FROM admin WHERE id = ?", new Object[]{admin.get_id()});

            if (!rs.next()) {
                throw new Exception("No admin with that id");
            }

            if (!admin.get_password().equals(EncryptorDecryptor.decrypt(rs.getString("password")))) {
                throw new Exception("Wrong password");
            }

        } finally {
            query.close_connection();
        }

    }

}
