package atmsystem.models;

import java.sql.Connection;

import atmsystem.DB;

import atmsystem.interfaces.IModel;

import atmsystem.utils.CardNumber;
import atmsystem.utils.Password;

import java.util.ArrayList;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UserModel implements IModel {

    @Override
    public ArrayList<Object> get(String filter, Object[] params) throws Exception {

        ArrayList<Object> users = new ArrayList<Object>();

        Connection con = null;

        try {
            con = DB.get_instance().get_connection();

            String sql = "SELECT user.id, user.name, account_id, card_number, pin, balance, shared FROM user_account\n"
                    + "INNER JOIN user\n"
                    + "on user_account.user_id = user.id\n"
                    + "INNER JOIN account\n"
                    + "on user_account.account_id = account.id\n";

            PreparedStatement pstm = con.prepareStatement(filter);

            if (params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    pstm.setObject(i + 1, params[i]);
                }
            }
            sql += pstm.toString().split(": ")[1];

            ResultSet rs = pstm.executeQuery(sql);

            while (rs.next()) {
                Account acc = new Account(rs.getInt("account_id"), rs.getString("card_number"), Password.decrypt(rs.getString("pin")), rs.getInt("balance"), rs.getBoolean("shared"));

                User user = new User(rs.getInt("id"), rs.getString("name"), acc);

                users.add(user);
            }

            con.close();
        } catch (Exception exp) {

            if (con != null) {
                con.close();
            }
            throw exp;
        }

        return users;
    }

    @Override
    public boolean insert(Object obj) throws Exception {

        Connection con = null;

        try {
            con = DB.get_instance().get_connection();

            User user = (User) obj;

            int user_id = hadnle_user_insertion(user, con);

            user.set_id(user_id);

            int account_id = handle_acc_insertion(user, con);

            user.get_account().set_id(account_id);
            
            
            handle_user_acc_insertion(user, con);
            
            con.close();
        } catch (Exception exp) {
            if (con != null) {
                con.close();
            }
            throw exp;
        }
        return true;

    }

    private int hadnle_user_insertion(User user, Connection con) throws SQLException, Exception {
        String user_SQL = "INSERT INTO user (name)\n"
                + "VALUES(?)";

        PreparedStatement pstm = con.prepareStatement(user_SQL, Statement.RETURN_GENERATED_KEYS);

        pstm.setString(1, user.get_name());

        pstm.executeUpdate();

        ResultSet rs = pstm.getGeneratedKeys();

        rs.next();

        return rs.getInt(1);

    }

    private int handle_acc_insertion(User user, Connection con) throws SQLException, Exception {
        String acc_SQL = "INSERT INTO account (card_number, balance, shared)\n"
                + "VALUES (?, ?, ?)";

        String card_number = CardNumber.generate(Account.card_number_length);

        PreparedStatement pstm = con.prepareStatement(acc_SQL, Statement.RETURN_GENERATED_KEYS);

        pstm.setString(1, card_number);
        pstm.setInt(2, user.get_account().get_balance());
        pstm.setBoolean(3, user.get_account().get_shared());

        user.get_account().set_card_number(card_number);

        pstm.executeUpdate();

        ResultSet rs = pstm.getGeneratedKeys();

        rs.next();

        return rs.getInt(1);
    }

    private void handle_user_acc_insertion(User user, Connection con) throws SQLException, Exception {
        String user_account_SQL = "INSERT INTO user_account (user_id, account_id, pin)\n"
                + "VALUES (?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(user_account_SQL);

        pstm.setInt(1, user.get_id());
        pstm.setInt(2, user.get_account().get_id());
        pstm.setString(3, Password.encrypt(user.get_account().get_pin()));

        pstm.executeUpdate();
    }
}
