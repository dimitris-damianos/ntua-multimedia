import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserCreationPage extends Application{
	
	private Library lib;
	
	public UserCreationPage(Library lib) {
		this.lib = lib;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override 
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Create user account");
		
		VBox root = new VBox(10);
		
		Label heade1 = new Label("Create new account");
		root.getChildren().add(heade1);
		
		TextField Username  = new TextField();
		Username.setPromptText("Username");
		root.getChildren().add(Username);
		
		PasswordField Password  = new PasswordField();
		Password.setPromptText("Password");
		root.getChildren().add(Password);
		
		TextField Name  = new TextField();
		Name.setPromptText("Name");
		root.getChildren().add(Name);
		
		TextField Surname  = new TextField();
		Surname.setPromptText("Surname");
		root.getChildren().add(Surname);
		
		TextField IDnum  = new TextField();
		IDnum.setPromptText("ID number");
		root.getChildren().add(IDnum);
		
		TextField Email  = new TextField();
		Email.setPromptText("Email");
		root.getChildren().add(Email);
		
		Label message = new Label(); //error messaging
        root.getChildren().add(message);
		
		Button createUser = new Button("Create account");
		createUser.setOnAction(e->{
			String username = Username.getText();
			String password = Password.getText();
			String name = Name.getText();
			String surname = Surname.getText();
			String id_num = IDnum.getText();
			String email = Email.getText();
			
			User user = lib.find_user(username);
			if (user != null) {
				message.setText("Username already exists");
			}
			else if(!lib.unique_email(email)) {
				message.setText("Email already exists");
			}
			else if(!lib.unique_id(id_num)) {
				message.setText("ID number already exists");
			}
			else {
				User new_user = new User(username,password,
									name,surname,
									id_num,email); //create new user object
				lib.add_user(new_user); //save new user 
				lib.save_library(); // save library state
				message.setText("Account created");
				openUserActionPage(new_user,primaryStage);
			}
			
		});
		root.getChildren().add(createUser);
	
		Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	private void openUserActionPage(User user, Stage primaryStage) {
		UserActionPage user_action_page = new UserActionPage(user, this.lib);
		user_action_page.start(new Stage());
		primaryStage.close();
	}
}