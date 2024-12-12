
package atmsystem.interfaces;

import atmsystem.models.Admin;
import atmsystem.models.User;



public interface IAdmin {
    
    void add_user_Account(User user) throws Exception;
    void add_user_to_existent_account(User user , String cardNumber) throws Exception;
    void login(Admin admin);
}
