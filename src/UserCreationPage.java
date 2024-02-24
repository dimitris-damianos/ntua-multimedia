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
	
	@Override 
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Create user account");
		
		VBox root = new VBox(10);
		
		
		Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
}