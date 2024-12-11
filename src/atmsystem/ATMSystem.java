package atmsystem;



import atmsystem.models.User;
import java.sql.ResultSet;
import java.sql.Connection;

import java.sql.PreparedStatement;


import atmsystem.models.UserModel;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import atmsystem.models.Account;
import atmsystem.models.TransactionModel;
import atmsystem.models.User;

public class ATMSystem {
    public static void main(String[] args) throws Exception {
        
        TransactionModel tm = new TransactionModel();
        
        ArrayList<Object> transactions = tm.get("WHERE user.id = ? AND account.id = ?", new Object[]{ 10, 12 });
        
        
        for(Object transaction: transactions){
            System.out.println(transaction.toString());
        }
        
    }
}
