

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
		boolean start_new = false;  
		if (start_new) {
			lib.init_library(); // initialize state
			lib.save_library(); // save for future use
		}
		else {
			lib.load_library();
		}
		
		Admin medialab_admin = lib.get_admins().get(0);
		Admin my_admin = lib.get_admins().get(1);
		
		
		//System.out.println(medialab_admin.get_username());
	
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
        	Label book_label = new Label("Title:"+book_title+", "+author+", ISBN:"+isbn+", Rating:"+Double.toString(rating)+ " out of "+num_ratings+" ratings");
        	top_books_box.getChildren().add(book_label);
        }
        
        window.getChildren().add(top_books_box);
        
        //user login button
        Button user_login = new Button("Log in as User");
        user_login.setOnAction(e->openUserLoginPage(primaryStage));
        window.getChildren().add(user_login);
        
        //user creation button
        Button user_create = new Button("Create an user account");
        user_create.setOnAction(e->openCreateUserPage(primaryStage));
        window.getChildren().add(user_create);
        
        //admin login button
        Button admin_login = new Button("Log in as Admin");
        admin_login.setOnAction(e->openAdminLoginPage(primaryStage));
        window.getChildren().add(admin_login);
        
        Scene scene = new Scene(window,400,500);
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
	
	private void openUserLoginPage(Stage primaryStage) {
		UserLoginPage user_login_page = new UserLoginPage(lib);
		user_login_page.start(new Stage());
		
		primaryStage.close(); // close this window when opening new one
	}
	
	private void openAdminLoginPage(Stage primaryStage) {
		AdminLoginPage admin_login_page = new AdminLoginPage(lib);
		admin_login_page.start(new Stage());
		primaryStage.close();
	}

	private void openCreateUserPage(Stage primaryStage) {
		UserCreationPage user_create = new UserCreationPage(lib);
		user_create.start(new Stage());
		primaryStage.close();
	}

}
