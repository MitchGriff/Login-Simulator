package systemlogin;

import Views.MyFrame;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author Mitchell Griffith
 */
public class Controller {
  
  private final MyFrame frame = new MyFrame();
  
  public ArrayList<Users> users = new ArrayList<Users>();
  
  public Controller(){
    frame.setTitle( getClass().getSimpleName() );
    frame.setLocationRelativeTo(null);
    
    //Creating the array list of Users
    //ArrayList<Users> users = new ArrayList<Users>();
    
    Users aa = new Users("aa111111@wcupa.edu", "a2Foobar");
    Users bb = new Users("bb222222@wcupa.edu", "BAr.FFOO");
    Users cc = new Users("cc333333@wcupa.edu", "a123.456");
    
    users.add(aa);
    users.add(bb);
    users.add(cc);
 
    JTextField loginTextField = frame.getLogin();
    JTextField currentPassTextField = frame.getCurrentPass();
    JTextField newPassTextField = frame.getNewPass();
    JTextArea messageBox = frame.getTextArea();
    JButton changePassButton = frame.getChangePass();
    JButton dumpButton = frame.getDumpData();
    
    //setting the text area to read only
    messageBox.setEditable(false);
    
    /**
     * Validates all text fields and gives the user feedback on their entry
     */
    
    changePassButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
       
        String username = loginTextField.getText().trim();
        
        if(validateLogin(username)){ //checks to see if the username is in the correct format
          
          messageBox.setText("Validation: Login format is correct.");
          
          if(userExists(username)){ //checks to see if the user exists
            
            int loginIdx = getIndex(username);
            
            String pass = currentPassTextField.getText().trim();
            
            if(checkCurPass(loginIdx, pass)){ //checks if current password is correct
              
              messageBox.setText("Validation: OK");
              String newPass = newPassTextField.getText().trim();
              
              if(newPassLength(newPass)){
              
                if(checkCurPass(loginIdx, newPass)){//checks if the new password is the same as the original one
                 
                  messageBox.setText("Validation: OK \nNew Password: Connot be the current password");

                }
                else if(!(checkCurPass(loginIdx, newPass))){ //if the password is not the current or too short
                  
                  messageBox.setText("Validation: OK ");
                  
                  if(validateNewPass(newPass)){
                    
                    messageBox.setText("Validation: OK \nNew Password: OK");
                    users.get(loginIdx).setPassword(newPass);
                    
                  }
                  else if(!(validateNewPass(newPass))){
                    
                    messageBox.setText("Validation: OK \nNew Password: Too Weak");
                  
                  }
                  
                }
              }
              else if(!(newPassLength(newPass))){
                messageBox.setText("Validation: OK \nNew Password: Too short");

              }
            }
            else if(!(checkCurPass(loginIdx, pass))){
              messageBox.setText("Validation: Current password is incorrect.");
            }
            
          }
          else if(!(userExists(username))){
            messageBox.setText("Validation: No such user.");
          }
          
        }
        else if(!(validateLogin(username))){
          messageBox.setText("Validation: Login format is incorrect.");
        }
      
      }
    });
    
    /**
     * Prints data in users ArrayList to the console
     */
    dumpButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        
        for(int i = 0; i < users.size(); i++){
          System.out.println("Login: " + users.get(i).getUsername() + "\nPassword: " + users.get(i).getPassword());
          System.out.println("");
        }
        
      }
    });
    
  }
  
  /**
   * Validates the login format using a regular expression
   * @param login
   * @return 
   */
  public boolean validateLogin(String login){
    if(login.matches("[a-z]{2}[0-9]{6}@wcupa.edu"))
      return true;
    else
      return false;
  }
  
  /**
   * Checks to make sure the user exists in the list
   * @param login
   * @return 
   */
  public boolean userExists(String login){
   String tempLogin = "";
    
    for(int i=0; i < users.size();i++){
     tempLogin = users.get(i).getUsername();
      if(login.equals(tempLogin)){
        return true;
      }
    }
    return false;
  }
  
  /**
   * Finds the index the login exists at for future reference 
   * @param login
   * @return 
   */
  public int getIndex(String login){
    int idx = 0;
    String tempLogin = "";
    
    for(int i=0; i < users.size();i++){
     tempLogin = users.get(i).getUsername();
      if(login.equals(tempLogin)){
        idx = i;
      }
    }
    return idx;
  }
  
  /**
   * Checks to see if the password entered is the one in the database
   * Also used to make sure the new password is not the same as the current one
   * @param idx
   * @param pass
   * @return 
   */
  public boolean checkCurPass(int idx, String pass){
    String realPass = users.get(idx).getPassword();
    if(pass.equals(realPass))
      return true;
    else
      return false; 
  }

  /**
   * Validates the new passwords length is long enough to be strong
   * @param pass
   * @return 
   */
  public boolean newPassLength(String pass){
    if(pass.length() >= 8)
      return true;
    else
      return false;
  }
  
  /**
   * Ensures the new password is strong enough
   * @param pass
   * @return 
   */
  public boolean validateNewPass(String pass){
    int required = 0;
    int upperCase = 0;
    int lowerCase = 0;
    int digit = 0;
    int special = 0;
    char[] chars;
    chars = pass.toCharArray();
    
    for(int i = 0; i < chars.length; i ++){
      if(Character.isUpperCase(chars[i]))
        upperCase++;
      else if(Character.isLowerCase(chars[i]))
        lowerCase++;
      else if(Character.isDigit(chars[i]))
        digit++;
      else
        special++;
    }
    if(upperCase > 0)
      required++;
    if(lowerCase > 0)
      required++;
    if(digit > 0)
      required++;
    if(special > 0)
      required++;
     
    if(required >= 3)
      return true;
    else if(required < 3)
      return false;
    return false;
  }
  
  public static void main(String[] args){
    Controller app = new Controller(); 
    app.frame.setVisible(true); 
  }
  
}
