import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserLoginPage extends Application{
	
	private Library lib;
	
	public UserLoginPage(Library lib) {
		// get access to main lib object
		this.lib = lib; 
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override 
	public void start(Stage primaryStage) {
		primaryStage.setTitle("User Login");
		
		VBox window = new VBox(10);
		window.setPadding(new Insets(10));

        Label title = new Label("User Login");
        window.getChildren().add(title);

        TextField username_field= new TextField();
        username_field.setPromptText("Username");
        window.getChildren().add(username_field);

        PasswordField password_field = new PasswordField();
        password_field.setPromptText("Password");
        window.getChildren().add(password_field);
        
        // used for messaging user for incorrect credentials
        Label message = new Label();
        window.getChildren().add(message);
        
        
        Button login_button = new Button("Log in");
        login_button.setOnAction(e->{
        	String username = username_field.getText();
        	String password = password_field.getText();
        	
        	if (check_credentials(username, password)) {
        		message.setText("Successful login");
        		// get user and move to next page
        		User user = lib.find_user(username);
        		openUserActionPage(user,primaryStage);
        	}
        	else {
        		message.setText("Invalid username or password, try again");
        	}
        	
        });
        
        window.getChildren().add(login_button);
        
        // required to show window 
        Scene scene = new Scene(window,300,200);
        primaryStage.setScene(scene);
        primaryStage.show();
        
	}
	
	// used to check input credentials
	private boolean check_credentials(String username, String password) {
		for (User user: lib.get_users()) {
			if (user.get_username().equals(username) && user.get_password().equals(password)) {
				// credentials match
				return true; 
			}
		}
		// credentials don't match
		return false;
	}
	
	// used to open UserActionPage
	private void openUserActionPage(User user, Stage primaryStage) {
		UserActionPage user_action_page = new UserActionPage(user, this.lib);
		user_action_page.start(new Stage());
		primaryStage.close();
	}
	
}