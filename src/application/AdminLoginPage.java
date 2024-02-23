package application;
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
	private Library library;
	
	public void set_library(Library library) {
		this.library = library;
	}
	
	@Override
	public void start(Stage page) {
		page.setTitle("Admin login");
		
		VBox window = new VBox(10);
		window.setPadding(new Insets(10));
		
	}
}




