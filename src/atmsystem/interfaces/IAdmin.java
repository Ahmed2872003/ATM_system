
package atmsystem.interfaces;

import atmsystem.models.User;



public interface IAdmin {
    
    void addAccount(User user) throws Exception;
    void resetPin(User user, String newPin) throws Exception;
}
