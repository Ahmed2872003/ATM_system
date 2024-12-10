
package atmsystem.interfaces;

import atmsystem.models.Admin;
import atmsystem.models.User;



public interface IAdmin {
    
    void addAccount(User user) throws Exception;
    void resetPin(User user, String newPin) throws Exception;
    void login(Admin admin);
}
