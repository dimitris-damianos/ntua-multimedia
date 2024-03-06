import java.io.Serializable;

/** 
 * Implements an administrator of the library.
 */
public class Admin implements Serializable{
    private String username;
    private String password;

    /**
     * Constructor of Admin objects
     * 
     * @param username Admin's login username
     * @param password Admin's login password
     */
    public Admin(String username, String password){
        this.username = username;
        this.password = password;
    }
    
    /**
     * Gets Admin's password
     * 
     * @return admin's password
     */
    public String get_password(){
        return this.password;
    }
    /**
     * Gets Admin's username 
     * 
     * @return admin's username
     */
    public String get_username(){
        return this.username;
    } 
    
    /**
     * Sets Admin's password
     * 
     * @param password String, admin's password
     */
    public void set_password(String password){
        this.password = password;
    }
    /**
     * Sets Admin's username
     * 
     * @param username String, admin;s username
     */
    public void set_username(String username){
        this.username = username;
    }


}
