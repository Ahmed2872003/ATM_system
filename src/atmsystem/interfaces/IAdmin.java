
package atmsystem.interfaces;

import atmsystem.models.Account;
import atmsystem.models.Admin;
import atmsystem.models.User;



public interface IAdmin {
    
    void add_user_Account(User user) throws Exception;
    void add_user_to_existent_account(User user, Account acc) throws Exception;
    void login(Admin admin) throws Exception;
}
