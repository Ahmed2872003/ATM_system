package atmsystem.interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;

import atmsystem.DB;
import java.util.ArrayList;


public interface IModel {

    ArrayList<Object> get(String filter, Object[] params) throws Exception;

    boolean insert(Object obj) throws Exception;

    default int update(String query, Object[] params) throws Exception {
        Connection connection = null;
        
        int rowCount = 0;
        
        try {
            connection = DB.get_instance().get_connection();

            PreparedStatement stmt = connection.prepareStatement(query);

            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            

            rowCount = stmt.executeUpdate();
            
            connection.close();

        } catch (Exception exp) {
            if(connection != null) connection.close();
            throw exp;
        }
        return rowCount;

    }
}
