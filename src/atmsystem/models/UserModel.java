package atmsystem.models;

import atmsystem.DB.Query;
import java.io.IOException;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel extends Query {

    public ResultSet join_with_account(String filter, Object[] params) throws SQLException, IOException {

        String sql = "SELECT user.id, user.name, account_id, card_number, pin, balance, shared FROM user_account\n"
                + "INNER JOIN user\n"
                + "on user_account.user_id = user.id\n"
                + "INNER JOIN account\n"
                + "on user_account.account_id = account.id\n";
        
        sql += filter;

        PreparedStatement pstm = conn.prepareStatement(sql);

        fill_params(pstm, params);

        ResultSet rs = pstm.executeQuery();
        
        return rs;

    }
}
