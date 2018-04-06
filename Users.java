package systemlogin;

/**
 *
 * @author mitchellgriffith
 */
public class Users {
  
  private String username;
  private String password;
  
  Users(String name, String pass){
    
    this.username = name;
    this.password = pass;
    
  }
  
  public String getUsername(){
    return username;
  }
  public String getPassword(){
    return password;
  }
  
  public void setPassword(String pass){
    this.password = pass;
  }
  
}
