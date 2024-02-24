import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminLoginPage extends Application{
	
	private Library lib;
	
	public AdminLoginPage(Library lib) {
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

        Label title = new Label("Admin Login");
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
        		// get admin and move to next page
        		Admin admin = lib.find_admin(username);
        		openAdminActionPage(admin,primaryStage);
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
		for (Admin admin: lib.get_admins()) {
			if (admin.get_username().equals(username) && admin.get_password().equals(password)) {
				// credentials match
				return true; 
			}
		}
		// credentials don't match
		return false;
	}
	
	// used to open UserActionPage
	private void openAdminActionPage(Admin admin, Stage primaryStage) {
		AdminActionPage admin_action_page = new AdminActionPage(admin, this.lib);
		admin_action_page.start(new Stage());
		primaryStage.close();
	}
	
}