import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * 
 * Implements an Implements an administrator action page.
 */

public class AdminActionPage extends Application{
	
	private Admin admin;
	private Library lib;
	
	private ListView<StringBuilder> book_list;
	private ListView<String> categories_list;
	private ListView<String> user_list;

	/**
	 * Class constructor
	 * @param admin	Admin object
	 * @param lib	Library object
	 */
	public AdminActionPage(Admin admin, Library lib) {
		this.admin = admin;
		this.lib = lib;
		this.book_list = new ListView<>();
		this.categories_list = new ListView<>();
		this.user_list = new ListView<>();
	}
	/**
	 * Application GUI components
	 */
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Admin page - Welcome "+admin.get_username());
		HBox root = new HBox(10);
		root.setAlignment(Pos.TOP_CENTER);
		
		// collumns
		VBox buttons = new VBox(10);
		buttons.setAlignment(Pos.CENTER_LEFT);
		
		VBox titles = new VBox(5);
		titles.setAlignment(Pos.CENTER);
		
		VBox categories = new VBox(5);
		categories.setAlignment(Pos.CENTER);
		
		VBox users = new VBox(10);
		users.setAlignment(Pos.CENTER);
		
		// buttons for all avalaible admin's actions
		Label buttons_label = new Label("Available actions:");
		
		Button view_books_button = new Button("View books information");
		view_books_button.setOnAction(e->view_books_info());
		
		Button add_book_button = new Button("Add new book");
		add_book_button.setOnAction(e->add_book());
		
		Button delete_book_button = new Button("Delete book");
		delete_book_button.setOnAction(e->delete_book());
		
		Button edit_book_button = new Button("Edit book info");
		edit_book_button.setOnAction(e->edit_book());
		
		Button add_category_button = new Button("Add new category");
		add_category_button.setOnAction(e->add_category());
		
		Button edit_category_button = new Button("Edit a category");
		edit_category_button.setOnAction(e->edit_category());
		
		Button delete_category_button = new Button("Delete a category");
		delete_category_button.setOnAction(e->delete_category());
		
		Button view_borrowings_button = new Button("View borrowed books");
		view_borrowings_button.setOnAction(e->view_borrowings());
		
		Button terminate_borrowing_button = new Button("Terminate a borrowing");
		terminate_borrowing_button.setOnAction(e->terminate_borrowing());
		
		Button view_users_button = new Button("View users information");
		view_users_button.setOnAction(e->view_users());
		
		Button edit_user_button = new Button("Edit user");
		edit_user_button.setOnAction(e->edit_user());
		
		Button delete_user_button = new Button("Delete user");
		delete_user_button.setOnAction(e->delete_user());
		
		buttons.getChildren().addAll(
				buttons_label,view_books_button,
				add_book_button, delete_book_button,edit_book_button,
				add_category_button, edit_category_button, delete_category_button,
				view_borrowings_button, terminate_borrowing_button,
				view_users_button,edit_user_button, delete_user_button
				);
		
		//titles
		update_all_lists();
		Label titles_label = new Label("Books overview:");
		titles.getChildren().addAll(titles_label,book_list);
		
		//categories
		update_all_lists();
		Label categories_label = new Label("Categories:");
		categories.getChildren().addAll(categories_label,categories_list);
		
		//users
		update_all_lists();
		Label users_label = new Label("Users overview:");
		users.getChildren().addAll(users_label,user_list);
		
		//buttons
		root.getChildren().addAll(buttons, titles, categories, users);
		
		Scene scene = new Scene(root, 1000, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}
	
	private void update_book_list() {
		ObservableList<StringBuilder> books = FXCollections.observableArrayList();
		StringBuilder section = new StringBuilder();
		for (String categ: lib.get_categories()) {;
			section.append("In category "+categ+", available books:\n");
			for (Book book: lib.get_allBooks()) {
				if(book.in_category(categ)) {
					section.append("Title :"+ book.get_title()+" ,ISBN: "+book.get_ISBN()+"\n");
				}
			}
			section.append("\n");
		}
		books.add(section);
		book_list.setItems(books);
	}
	
	private void update_categories_list() {
		ObservableList<String> categ = FXCollections.observableArrayList();
		for (String cat: lib.get_categories()) {;
			categ.add(cat);
		}
		categories_list.setItems(categ);
	}
	
	private void update_user_list() {
		ObservableList<String> u = FXCollections.observableArrayList();
		for (User user: lib.get_users()) {;
			u.add(user.get_username()+", ID:"+user.get_idNum());
		}
		user_list.setItems(u);
	}
	
	private void update_all_lists() {
		update_book_list();
		update_categories_list();
		update_user_list();
	}
	
	// button action handling methods
	private void view_books_info() {
		StringBuilder books_info = new StringBuilder();
		
		for(Book book: lib.get_allBooks()) {
			books_info.append("Title: "+book.get_title()+"\n");
			books_info.append("Authos: "+book.get_author()+"\n");
			books_info.append("Release year: "+Integer.toString(book.get_releaseYear())+"\n");
			books_info.append("ISBN: "+book.get_ISBN()+"\n");
			books_info.append("Category: "+book.get_category()+"\n");
			books_info.append("Available copies: "+Integer.toString(book.get_copies())+"\n");
			books_info.append("Rating: "+String.format("%.2f",book.get_rating())+"\n");
			
			if(book.get_comments().isEmpty()) {
				books_info.append("Comments: None\n");
			}
			else {
				books_info.append("Comments: ").append(String.join(", ",book.get_comments())).append("\n");
			}
			if(book.get_allRatings().isEmpty()) {
				books_info.append("All ratings: None\n");
			}
			else {
				books_info.append("All ratings: ").append(book.get_allRatings()).append("\n");
			}
			
			books_info.append("\n");
			
		}
		
		// display books info
		TextArea info = new TextArea(books_info.toString());
		info.setEditable(false);
		
		Stage info_stage = new Stage();
		info_stage.setTitle("Books information");
		info_stage.setScene(new Scene(new VBox(new Label("Books information"),info),400,200));
		info_stage.show();
		
	}
	
	private void add_book() {
        Stage add_book_stage = new Stage();
        add_book_stage.setTitle("Add new book");
        
        VBox root = new VBox(10);
        Label header = new Label("Provide book's information (title, author etc):");
        root.getChildren().add(header);
        
        TextField newTitle = new TextField();
        newTitle.setPromptText("Set title");
        root.getChildren().add(newTitle);
        
        TextField newAuthor = new TextField();
        newAuthor.setPromptText("Set author");
        root.getChildren().add(newAuthor);
        
        TextField newPubl = new TextField();
        newPubl.setPromptText("Set publisher");
        root.getChildren().add(newPubl);
        
        TextField newReleaseYear = new TextField();
        newReleaseYear.setPromptText("Set release year");
        root.getChildren().add(newReleaseYear);
        
        TextField newISBN = new TextField();
        newISBN.setPromptText("Set ISBN");
        root.getChildren().add(newISBN);
        
        TextField newCategory = new TextField();
        newCategory.setPromptText("Set category");
        root.getChildren().add(newCategory);
        
        TextField newCopies = new TextField();
        newCopies.setPromptText("Set available copies number");
        root.getChildren().add(newCopies);
        
        Label message = new Label(); //error messaging
        root.getChildren().add(message);
        
        Button addBook = new Button("Add book");
        addBook.setOnAction(e->{
        	String title = newTitle.getText();
        	String author = newAuthor.getText();
        	String publisher = newPubl.getText();
        	String release_year = newReleaseYear.getText();
        	String ISBN = newISBN.getText();
        	String category = newCategory.getText();
        	String copies = newCopies.getText();
        	
        	if(!lib.unique_isbn(ISBN)) {
        		message.setText("Unique ISBN required");
        	}
        	else if(!lib.category_exists(category)) {
        		message.setText("Given category doesn't exist");
        	}
        	else {
        		lib.admin_add_book(admin, title, author, publisher, Integer.parseInt(release_year), ISBN, category,Integer.parseInt(copies));
                lib.save_library();
                update_all_lists();
                message.setText("Book added");
        	}
        });
        
        root.getChildren().add(addBook);
        
        Scene scene = new Scene(root, 300,400);
        add_book_stage.setScene(scene);
        add_book_stage.show();
	}
	
	private void edit_book() {
		Stage edit_book_stage = new Stage();
        edit_book_stage.setTitle("Edit book");
        
        // provide ISBN to find book for editing
        VBox root = new VBox(10);
        Label header1 = new Label("Provide book's ISBN to edit:");
        root.getChildren().add(header1);
        
        TextField bookISBN = new TextField();
        bookISBN.setPromptText("Book's ISBN");
        root.getChildren().add(bookISBN);
        
        // provide new info (except ISBN)
        Label header2 = new Label("Provide book's new information:");
        root.getChildren().add(header2);
        
        TextField newTitle = new TextField();
        newTitle.setPromptText("Set title");
        root.getChildren().add(newTitle);
        
        TextField newAuthor = new TextField();
        newAuthor.setPromptText("Set author");
        root.getChildren().add(newAuthor);
        
        TextField newPubl = new TextField();
        newPubl.setPromptText("Set publisher");
        root.getChildren().add(newPubl);
        
        TextField newReleaseYear = new TextField();
        newReleaseYear.setPromptText("Set release year");
        root.getChildren().add(newReleaseYear);
        
        TextField newCategory = new TextField();
        newCategory.setPromptText("Set category");
        root.getChildren().add(newCategory);
        
        TextField newCopies = new TextField();
        newCopies.setPromptText("Set available copies number");
        root.getChildren().add(newCopies);
        
        Label message = new Label(); //error messaging
        root.getChildren().add(message);
        
        Button addBook = new Button("Edit book");
        addBook.setOnAction(e->{
        	String title = newTitle.getText();
        	String author = newAuthor.getText();
        	String publisher = newPubl.getText();
        	String release_year = newReleaseYear.getText();
        	String category = newCategory.getText().toLowerCase();
        	String copies = newCopies.getText();
        	
        	String ISBN = bookISBN.getText();
        	
        	if(!lib.isbn_exists(ISBN)) {
        		message.setText("Book with ISBN: "+ISBN+" doesn't exist");
        	}
        	else if(!lib.category_exists(category)) {
        		message.setText("Given category doesn't exist");
        	}
        	else {
        		lib.admin_edit_book(admin, ISBN, title, author,publisher,
        							Integer.parseInt(release_year),Integer.parseInt(copies),category);
        		lib.save_library();
                update_all_lists();
                message.setText("Book edited");
        	}
        });
        
        root.getChildren().add(addBook);
        
        Scene scene = new Scene(root, 300,400);
        edit_book_stage.setScene(scene);
        edit_book_stage.show();
	}
	
	private void delete_book() {
		Stage delete_book_stage = new Stage();
		delete_book_stage.setTitle("Delete book");
        
        // provide ISBN to find book for editing
        VBox root = new VBox(10);
        Label header1 = new Label("Provide book's ISBN to delete:");
        root.getChildren().add(header1);
        
        TextField bookISBN = new TextField();
        bookISBN.setPromptText("Book's ISBN");
        root.getChildren().add(bookISBN);
        
        Label message = new Label(); //error messaging
        root.getChildren().add(message);
        
        Button delete_book = new Button("Delete book");
        delete_book.setOnAction(e->{
        	String isbn = bookISBN.getText();
        	if(lib.isbn_exists(isbn)) {
        		lib.admin_delete_book(admin, isbn); // perform delete
                lib.save_library(); // save library state
                update_all_lists();
                message.setText("Book deleted");
        	}
        	else {
        		message.setText("Book with ISBN: "+isbn+"dosen't exist");
        	}
        });
        root.getChildren().add(delete_book);
        
        Scene scene = new Scene(root, 300,200);
        delete_book_stage.setScene(scene);
        delete_book_stage.show();
        
	}
	
	private void add_category() {
		Stage add_categ_stage = new Stage();
        add_categ_stage.setTitle("Add new category");
        
        VBox root = new VBox(10);
        Label header = new Label("New category:");
        root.getChildren().add(header);
        
        TextField newCategory = new TextField();
        newCategory.setPromptText("Set category");
        root.getChildren().add(newCategory);
        
        Label message = new Label(); //error messaging
        root.getChildren().add(message);
        
        Button add_category = new Button("Add category");
        add_category.setOnAction(e->{
        	String category = newCategory.getText();
        	if(lib.category_exists(category)) {
        		message.setText("Category already exists");
        	}
        	else {
        		lib.admin_add_category(admin, category);
        		lib.save_library(); //save state
        		update_all_lists();
        		message.setText("Category added");
        	}
        });
        
        root.getChildren().add(add_category);
        
        Scene scene = new Scene(root, 300,400);
        add_categ_stage.setScene(scene);
        add_categ_stage.show();
        
        
	}
	
	private void edit_category() {
		Stage edit_categ_stage = new Stage();
        edit_categ_stage.setTitle("Edit category");
        
        VBox root = new VBox(10);
        Label header1 = new Label("Old category:");
        root.getChildren().add(header1);
        
        TextField oldCategory = new TextField();
        oldCategory.setPromptText("Old category name");
        root.getChildren().add(oldCategory);
        
        Label header2 = new Label("New category:");
        root.getChildren().add(header2);
        
        TextField newCategory = new TextField();
        newCategory.setPromptText("Set new category");
        root.getChildren().add(newCategory);
        
        Label message = new Label(); //error messaging
        root.getChildren().add(message);
        
        Button edit_category = new Button("Edit category");
        edit_category.setOnAction(e->{
        	String old_category = oldCategory.getText().toLowerCase();
        	String new_category = newCategory.getText().toLowerCase();
        	
        	if(!lib.category_exists(old_category)) {
        		message.setText("Category doesn't exists");
        	}
        	else if(lib.category_exists(new_category.toLowerCase())) {
        		message.setText("Category already exists");
        	}
        	else {
        		lib.admin_edit_category(admin, old_category,new_category);
        		lib.save_library(); //save state
        		update_all_lists();
        		message.setText("Category and asscociated books updated");
        	}
        });
        
        root.getChildren().add(edit_category);
        
        Scene scene = new Scene(root, 300,400);
        edit_categ_stage.setScene(scene);
        edit_categ_stage.show();
	}
	
	private void delete_category() {
		Stage del_categ_stage = new Stage();
        del_categ_stage.setTitle("Delete category");
        
        VBox root = new VBox(10);
        Label header1 = new Label("Category name:");
        root.getChildren().add(header1);
        
        TextField Category = new TextField();
        Category.setPromptText("Category name");
        root.getChildren().add(Category);
       
        Label message = new Label(); //error messaging
        root.getChildren().add(message);
        
        Button edit_category = new Button("Edit category");
        edit_category.setOnAction(e->{
        	String category = Category.getText();
        	
        	if(!lib.category_exists(category)) {
        		message.setText("Category doesn't exists");
        	}
        	else {
        		lib.admin_remove_category(admin, category);
        		lib.save_library(); //save state
        		update_all_lists();
        		message.setText("Category and associated books deleted");
        	}
        });
        
        root.getChildren().add(edit_category);
        
        Scene scene = new Scene(root, 300,400);
        del_categ_stage.setScene(scene);
        del_categ_stage.show();
	}
	
	private void view_borrowings() {
		StringBuilder borrowed_info = new StringBuilder();
		int index = 0;
		for (User user: lib.users) {
			index++;
			borrowed_info.append(Integer.toString(index)+") User: "+user.get_username()+" ,ID:"+user.get_idNum()+"\n");
			borrowed_info.append("Active borrowings: \n");
			if (user.get_borrowed_books().isEmpty()) {
				borrowed_info.append("	 None\n");
			}
			else {
				for (BorrowedBook borrowed: user.get_borrowed_books()) {
					borrowed_info.append("	 Title: "+borrowed.get_book().get_title()+" ,ISBN:"+borrowed.get_book().get_ISBN());
					borrowed_info.append("	 Borrow date:"+borrowed.get_borrow_date()+"\n");
					if (borrowed.is_due()) {
						borrowed_info.append("	 Status: is due\n");
					}
					else {
						LocalDate today = LocalDate.now();
						long remaining_days = ChronoUnit.DAYS.between(today, borrowed.get_return_date());
						borrowed_info.append("	 Status: "+remaining_days+" days remaining\n");
					}
					
				}
			}
			borrowed_info.append("\n");
		}
		
		// display books info
		TextArea info = new TextArea(borrowed_info.toString());
		info.setEditable(false);
		
		Stage info_stage = new Stage();
		info_stage.setTitle("Borrowed books information");
		info_stage.setScene(new Scene(new VBox(new Label("Borrowed books per user"),info),400,200));
		info_stage.show();
	}
	
	private void terminate_borrowing() {
		Stage del_borr_stage = new Stage();
        del_borr_stage.setTitle("Terminate borrowings");
        
        VBox root = new VBox(10);
        Label header1 = new Label("User's username");
        root.getChildren().add(header1);
        
        TextField Username = new TextField();
        Username.setPromptText("username");
        root.getChildren().add(Username);
        
        Label header2 = new Label("Book's ISBN");
        root.getChildren().add(header2);
        
        TextField ISBN = new TextField();
        ISBN.setPromptText("ISBN");
        root.getChildren().add(ISBN);
       
        Label message = new Label(); //error messaging
        root.getChildren().add(message);
        
        Button terminate_borrowing = new Button("Terminate borrowing");
        terminate_borrowing.setOnAction(e->{
        	String username = Username.getText();
        	String isbn = ISBN.getText();
        	
        	User user = lib.find_user(username);
        	Book book = lib.find_book(isbn);
        	
        	if(user == null) {
        		message.setText("User doesn't exist");
        	}
        	else if(book == null){
        		message.setText("Book doesn't exist");
        	}
        	else {
        		BorrowedBook borrowed = lib.find_borrowed(user, book);
        		if (borrowed == null) {
        			message.setText("User hasn't borrowed this book");
        		}
        		else {
        			lib.admin_terminate_borrowing(admin, username, isbn);
	        		lib.save_library(); //save state
	        		update_all_lists(); //update view lists
	        		message.setText("Borrowing terminated");
        		}
        		
        	}
        });
        
        root.getChildren().add(terminate_borrowing);
        
        Scene scene = new Scene(root, 300,400);
        del_borr_stage.setScene(scene);
        del_borr_stage.show();
	}
	
	private void view_users() {
		StringBuilder user_info = new StringBuilder();
		
		for(User user:lib.get_users()) {
			user_info.append("Username: "+user.get_username()+"\n");
			user_info.append("Full name: "+user.get_name()+" "+user.get_username()+"\n");
			user_info.append("ID number:"+user.get_idNum()+"\n");
			user_info.append("Email:"+user.get_email()+"\n");
			user_info.append("\n");
			}
			
			
		TextArea info = new TextArea(user_info.toString());
		info.setEditable(false);
		
		Stage info_stage = new Stage();
		info_stage.setTitle("Users information");
		info_stage.setScene(new Scene(new VBox(new Label("Users information"),info),400,200));
		info_stage.show();
	}
	
	private void edit_user() {
		Stage edit_user_stage = new Stage();
        edit_user_stage.setTitle("Edit user information");
        
        VBox root = new VBox(10);
        Label header1 = new Label("Insert username:");
        root.getChildren().add(header1);
        
        TextField oldUsername = new TextField();
        oldUsername.setPromptText("Username");
        root.getChildren().add(oldUsername);
        
        Label header2 = new Label("New information:");
        root.getChildren().add(header2);
        
        TextField newUsername = new TextField();
        newUsername.setPromptText("New username");
        root.getChildren().add(newUsername);
        
        TextField newPassword= new TextField();
        newPassword.setPromptText("New password");
        root.getChildren().add(newPassword);
        
        TextField newName= new TextField();
        newName.setPromptText("New name");
        root.getChildren().add(newName);
        
        TextField newSurname = new TextField();
        newSurname.setPromptText("New surname");
        root.getChildren().add(newSurname);
        
        TextField newID = new TextField();
        newID.setPromptText("New ID number");
        root.getChildren().add(newID);
        
        TextField newEmail= new TextField();
        newEmail.setPromptText("New email");
        root.getChildren().add(newEmail);
        
        Label message = new Label(); //error messaging
        root.getChildren().add(message);
        
        Button edit_user = new Button("Edit user");
        edit_user.setOnAction(e->{
        	String old_username = oldUsername.getText();
        	
        	String new_username = newUsername.getText();
        	String new_pass = newPassword.getText();
        	String new_name = newName.getText();
        	String new_sur = newSurname.getText();
        	String new_id = newID.getText();
        	String new_email = newEmail.getText();

                	
        	User user = lib.find_user(old_username);
        	if(user == null) {
        		message.setText("User doesn't exists");
        	}
        	else {
        		User existing_username = lib.find_user(new_username);
        		if(existing_username != null) {
        			message.setText("New username already exists");
        		}
        		else if(!lib.unique_id(new_id)){
        			message.setText("ID number already exists");
        		}
        		else if(!lib.unique_email(new_email)) {
        			message.setText("Email address already exists");
        		}
        		else {
            		lib.admin_edit_user_byUsername(admin, old_username,
            										new_username,new_pass,new_name,new_sur,
            										new_id, new_email);
            		lib.save_library(); //save state
            		update_all_lists();
            		message.setText("User updated");
            	}
        	}
        });
        
        root.getChildren().add(edit_user);
        
        Scene scene = new Scene(root, 300,400);
        edit_user_stage.setScene(scene);
        edit_user_stage.show();
	}
	
	private void delete_user() {
		Stage delete_user_stage = new Stage();
		delete_user_stage.setTitle("Delete user");
        
        // provide ISBN to find book for editing
        VBox root = new VBox(10);
        Label header1 = new Label("Provide user's username to delete:");
        root.getChildren().add(header1);
        
        TextField Username = new TextField();
        Username.setPromptText("User's username");
        root.getChildren().add(Username);
        
        Label message = new Label(); //error messaging
        root.getChildren().add(message);
        
        Button delete_user = new Button("Delete user");
        delete_user.setOnAction(e->{
        	String username = Username.getText();
        	User user = lib.find_user(username);
        	
        	if(user != null) {
        		lib.admin_delete_user(admin, username); // perform delete
                lib.save_library(); // save library state
                update_all_lists();
                message.setText("User deleted");
        	}
        	else {
        		message.setText("User dosen't exist");
        	}
        });
        root.getChildren().add(delete_user);
        
        Scene scene = new Scene(root, 300,200);
        delete_user_stage.setScene(scene);
        delete_user_stage.show();
	}
}