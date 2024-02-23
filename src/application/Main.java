package application;

import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class Main extends Application {
	
	private static Library lib;
	
	
	public static void main(String[] args) {
		lib = new Library();  // create library instance
		
		// used to determine library state
		// if true, creates default library state (used the first time)
		// if false, load library state
		boolean start_new = true;  
		if (start_new) {
			lib.init_library(); // initialize state
			//lib.save_library(); // save for future use
		}
		else {
			lib.load_library();
		}
		
		Admin medialab_admin = lib.get_admins().get(0);
		Admin my_admin = lib.get_admins().get(1);
	
		// launch javaFX application
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("e-Library");

        // create a VBox for the layout
        VBox window = new VBox(10);
        window.setPadding(new Insets(10));
        window.setAlignment(Pos.CENTER);
        
        // window label/message
        Label title = new Label("Welcome to e-Library");
        
        //HBox for title alignment
        HBox title_box = new HBox(title);
        title_box.setAlignment(Pos.CENTER);
        
        // add title to window
        window.getChildren().add(title_box);
        
        //get top-5 books
        List<Book> top_five_books = lib.get_top_books(5);
        // display books in VBox
        VBox top_books_box = new VBox(5);
        Label top_books_label = new Label("Top rated books:");
        top_books_box.getChildren().add(top_books_label);
        
        for (Book book: top_five_books) {
        	// retrieve useful information
        	double rating = Math.round(book.get_rating());
        	String num_ratings = ""+book.get_allRatings().size();
        	String book_title = book.get_title();
        	String author = book.get_author();
        	String isbn = book.get_ISBN();
        	
        	//display information 
        	//Label book_label = new Label(title+" ,"+author+" ,"+isbn+" ,Rating:"+String.format("%.2f", rating)+" in "+String.format("%.2i", num_ratings));
        	Label book_label = new Label(book_title+", "+author+", ISBN:"+isbn+", Rating:"+Double.toString(rating)+ " out of "+num_ratings+" ratings");
        	top_books_box.getChildren().add(book_label);
        }
        
        window.getChildren().add(top_books_box);
        
        Scene scene = new Scene(window,700,800);
        primaryStage.setScene(scene);
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
