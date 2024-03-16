package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;



public class Main extends Application {
	public static void main(String[] args) {
		//launch(args);
		Library lib = new Library();
		lib.init_library();  // initiliaze library
		// testing
		Admin admin1 = lib.admins.get(0);
		//Admin admin2 = lib.admins.get(1);
		//System.out.println("Admin1: name:"+admin1.get_username()+" pass:"+admin1.get_password());
		//System.out.println("Admin2: name:"+admin2.get_username()+" pass:"+admin2.get_password());
		for (Book book: lib.books) {
			System.out.println("Title: "+book.get_title()+" copies remain:"+book.get_copies());
			}
		for (User user: lib.users) {
			System.out.println("Name: "+user.get_username());
			}
		lib.admin_remove_category(admin1,"cat1");;
		for (Book book: lib.books) {
			System.out.println("Title: "+book.get_title()+" copies remain:"+book.get_copies());
			}
		for (User user: lib.users) {
			System.out.println("Name: "+user.get_username());
			}
		
		
	}
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Multi-Login Example");

        // Create a GridPane for the layout
        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Create UI components
        Button userLoginButton = new Button("User Login");
        Button adminLoginButton = new Button("Admin Login");

        // Add components to the grid
        grid.add(userLoginButton, 0, 0);
        grid.add(adminLoginButton, 1, 0);

        // Set up event handling for the buttons
        userLoginButton.setOnAction(e -> openUserLoginPage());
        adminLoginButton.setOnAction(e -> openAdminLoginPage());

        // Create a scene and set it on the stage
        Scene scene = new Scene(grid, 300, 100);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }
	
	// Method to open the User Login page
    private void openUserLoginPage() {
        Stage userLoginStage = new Stage();
        userLoginStage.setTitle("User Login Page");

        // Create UI components for the User Login page (similar to the previous example)
        // ...

        // Set up the layout and scene for the User Login page
        // ...

        // Show the User Login stage
        userLoginStage.show();
    }
    
 // Method to open the Admin Login page
    private void openAdminLoginPage() {
        Stage adminLoginStage = new Stage();
        adminLoginStage.setTitle("Admin Login Page");
        
        adminLoginStage.show();
    }

}
