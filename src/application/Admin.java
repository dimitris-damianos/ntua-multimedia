package application;

/** 
 * Implements an administrator of the library.
 */
public class Admin implements Person{
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
     * Used for seperating User and Admin Persons privilages.
     */
    public boolean is_admin(){
        return true;
    }
    
    // Getters
    public String get_password(){
        return this.password;
    }
    public String get_username(){
        return this.username;
    } 
    
    //Setters
    public void set_password(String password){
        this.password = password;
    }
    public void set_username(String username){
        this.username = username;
    }


}
