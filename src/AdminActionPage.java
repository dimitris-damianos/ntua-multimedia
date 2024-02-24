import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AdminActionPage extends Application{
	
	private Admin admin;
	private Library lib;
	
	private ListView<String> borrowed_books;
	private TextArea search_results;
	private VBox window;
	
	public AdminActionPage(Admin admin, Library lib) {
		this.admin = admin;
		this.lib = lib;
	}
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Admin page");
		window = new VBox(10);
		
		
		Scene scene = new Scene(window, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

		
	}
}