package atmsystem.models;

import atmsystem.DB;
import atmsystem.interfaces.IModel;
import atmsystem.utils.Password;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TransactionModel implements IModel {

    @Override
    public ArrayList<Object> get(String filter, Object[] params) throws Exception {

        ArrayList<Object> transactions = new ArrayList<Object>();

        Connection con = null;

        try {
            con = DB.get_instance().get_connection();

            String sql = "SELECT transaction_log.id, transaction_type.id as type_id,transaction_type.type,transaction_log.amount   FROM transaction_log\n"
                    + "INNER JOIN user\n"
                    + "ON transaction_log.user_id = user.id\n"
                    + "INNER JOIN account\n"
                    + "ON transaction_log.account_id = account.id\n"
                    + "INNER JOIN transaction_type\n"
                    + "ON transaction_log.transaction_type_id = transaction_type.id\n";

            PreparedStatement pstm = con.prepareStatement(filter);

            if (params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    pstm.setObject(i + 1, params[i]);
                }
            }
            sql += pstm.toString().split(": ")[1];

            ResultSet rs = pstm.executeQuery(sql);

            while (rs.next()) {
                Transaction.Type t = rs.getInt("type_id") == Transaction.Type.Deposit.getId()? Transaction.Type.Deposit : Transaction.Type.Withdrawal;
                
                Transaction transaction = new Transaction(rs.getInt("id"),t , null, rs.getInt("amount"));
                
                transactions.add(transaction);
            }

            con.close();
        } catch (Exception exp) {

            if (con != null) {
                con.close();
            }
            throw exp;
        }

        return transactions;
    }

    @Override
    public boolean insert(Object obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
