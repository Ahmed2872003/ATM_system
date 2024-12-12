package atmsystem.DB;

import atmsystem.DB.DB;
import java.io.IOException;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Query {

    protected Connection conn;

    public void open_connection() throws IOException, SQLException {
        if (conn == null) {
            conn = DB.get_instance().get_connection();
        }
    }

    public void close_connection() throws IOException, SQLException {
        if (conn != null) {
            conn.close();
            conn = null;
        }

    }

    public ResultSet get(String query, Object[] params) throws Exception {

        ResultSet rs = null;

        PreparedStatement pstm = conn.prepareStatement(query);

        fill_params(pstm, params);

        rs = pstm.executeQuery();

        return rs;
    }

    public int insert(String query, Object[] params) throws Exception {

        int id = -1;

        PreparedStatement pstm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        fill_params(pstm, params);

        pstm.executeUpdate();

        ResultSet rs = pstm.getGeneratedKeys();

        if (rs.next()) {
            id = rs.getInt(1);
        }

        return id;
    }

    public int update(String query, Object[] params) throws Exception {

        int rowCount = 0;

        PreparedStatement pstmt = conn.prepareStatement(query);

        fill_params(pstmt, params);

        rowCount = pstmt.executeUpdate();

        return rowCount;

    }

    protected static void fill_params(PreparedStatement pstm, Object[] params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                pstm.setObject(i + 1, params[i]);
            }
        }
    }

}
