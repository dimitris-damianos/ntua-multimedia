import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/*
 * Implements user action page
 */
public class UserActionPage extends Application{
	
	private User user;
	private Library lib;
	
	private ListView<StringBuilder> borrowed_books;
	private ListView<StringBuilder> search_results;
	
	/**
	 * Constructor of action page
	 * @param user	User object instance, defined by Login page
	 * @param lib	Library object instance, defined by Login page
	 */
	public UserActionPage(User user, Library lib) {
		this.user = user;
		this.lib = lib;
		this.borrowed_books = new ListView<>();
		this.search_results = new ListView<>();
	}
	
	
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("User page - Welcome "+user.get_username());
		HBox root = new HBox(10);
		
		//columns
		//buttons
		VBox buttons = new VBox(10);
		buttons.setAlignment(Pos.CENTER);
		
		// borrowed books list
		VBox borrowings = new VBox(10);
		borrowings.setAlignment(Pos.CENTER);
		
		//buttons for user's available actions
		Label buttons_label = new Label("Available actions:");
		
		Button search_button = new Button("Search books");
		search_button.setOnAction(e->search_books());
			
		Button borrow_button = new Button("Borrow new book");
		borrow_button.setOnAction(e->borrow_book());
				
		Button add_comment_rating_button = new Button("Add review");
		add_comment_rating_button.setOnAction(e->add_review());
		
		buttons.getChildren().addAll(buttons_label, search_button,
									borrow_button, add_comment_rating_button);
	
		
		//borrowings
		update_borrowed_books();
		Label header = new Label("Borrowed books:");
		borrowings.getChildren().addAll(header,borrowed_books);
		
		
		root.getChildren().add(buttons);
		root.getChildren().add(borrowings);
		
		Scene scene = new Scene(root, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

		
	}
	
	// update borrowed book list after any action
	private void update_borrowed_books() {
		ObservableList<StringBuilder> borrowings = FXCollections.observableArrayList();
		StringBuilder section = new StringBuilder();
		if(user.get_borrowed_books().isEmpty()) {
			section.append("No active borrowings");
		}else {
			for (BorrowedBook borrowed: user.get_borrowed_books()) {;
				section.append("Title: "+borrowed.get_book().get_title()+" ,ISBN: "+borrowed.get_book().get_ISBN()+"\n");
				section.append("Borrowed: "+borrowed.get_borrow_date()+" ,return: "+borrowed.get_return_date()+"\n");
				if (borrowed.is_due()) {
					section.append("NOTICE: This book is due \n");
				}
				section.append("\n");
			}
		}
		borrowings.add(section);
		
		borrowed_books.setItems(borrowings);
	}
	
	private void update_search_list(String title, String author, String year) {
		ObservableList<StringBuilder> books = FXCollections.observableArrayList();
		List<Book> results = lib.search_books(title, author, year);
		if (results.isEmpty()) {
			StringBuilder section = new StringBuilder().append("None\n");
			books.add(section);
		}
		else {
			for(Book book: results) {
				StringBuilder section = new StringBuilder();
				section.append("Title: "+book.get_title()+" ,author: "+book.get_author()+" ,ISBN: "+book.get_ISBN()+"\n");
				section.append("Publisher:"+ book.get_publisher()+" ,year: "+book.get_releaseYear()+"\n");
				section.append("Rating: "+String.format("%.2f",book.get_rating())+" in "+book.get_allRatings().size()+" ratings\n");
				
				if(book.get_comments().isEmpty()) {
					section.append("Comments: None\n");
				}
				else {
					section.append("Comments: ").append(String.join(", ",book.get_comments())).append("\n");
				}
				if(book.get_allRatings().isEmpty()) {
					section.append("All ratings: None\n");
				}
				else {
					section.append("All ratings: ").append(book.get_allRatings()).append("\n");
				}
				section.append("\n");
				
				books.add(section);
			}
		}
		search_results.setItems(books);
	}
	
	
	private void search_books() {
		Stage search_stage = new Stage();
		search_stage.setTitle("Search books");
		
		VBox root = new VBox(10);
		
		Label header = new Label("Search book by title, author and release year:");
		Label title_field = new Label("Title:");
		
		TextField Title = new TextField();
		Title.setPromptText("Title");
		
		Label author_field = new Label("Author:");
		TextField Author = new TextField();
		Author.setPromptText("Author");
		
		Label year_field = new Label("Release year:");
		TextField Year = new TextField();
		Year.setPromptText("Year");
		
		Label results = new Label("Results:");
		
		Button search_button = new Button("Search book");
		search_button.setOnAction(e->{
			String title = Title.getText();
			String author = Author.getText();
			String year = Year.getText();
			
			update_search_list(title,author,year);
		});
		
		root.getChildren().addAll(header,title_field,Title,
									author_field,Author, year_field, Year,
								 search_button, results, search_results);
		

		Scene scene = new Scene(root,300,400);
		search_stage.setScene(scene);;
		search_stage.show();
		
	}
	
	private void borrow_book() {
		Stage borrow_stage = new Stage();
		borrow_stage.setTitle("Borrow book");
		
		VBox root = new VBox(10);
		
		Label header = new Label("Borrow book using its ISBN:");
		TextField ISBN = new TextField();
		ISBN.setPromptText("ISBN");
		
		Label message = new Label(); //error messaging

		Button borrow_button = new Button("Borrow book");
		borrow_button.setOnAction(e->{
			String isbn = ISBN.getText();
			Book book = lib.find_book(isbn);
			
			if (book == null) { //check if book exists
				message.setText("Book with ISBN: "+isbn+" doesn't exist");
			}else {
				BorrowedBook borrowed =  lib.find_borrowed(this.user, book);
				if(borrowed != null) { // check if user has already borrowed this book
					message.setText("Book already borrowed");
				}
				else if(user.get_book_limit()==0) {
					message.setText("Borrowed book number limit reached");
				}
				else {
					if (book.get_copies()>0) {
						lib.user_borrow_book(user, book);
						update_borrowed_books(); // update borrowed view 
						lib.save_library();  // save lib state
						message.setText("Book borrowed");
						}
					else {
						message.setText("No available copies");
					}
					
				}
			}
		});
		
		Label notice = new Label("Notice: you can borrow each book once");
		
		root.getChildren().addAll(header, ISBN, message, borrow_button, notice);

		Scene scene = new Scene(root,300,200);
		borrow_stage.setScene(scene);;
		borrow_stage.show();
	}
	
	private void add_review() {
		Stage comment_stage = new Stage();
		comment_stage.setTitle("Add review");
		
		VBox root = new VBox(10);
		Label title = new Label("Add review to currently borrowed book");
		
		Label header = new Label("Book's ISBN:");
		TextField ISBN = new TextField();
		ISBN.setPromptText("ISBN");
		
		Label rating_label = new Label("Rating");
		TextField Rating = new TextField();
		Rating.setPromptText("Rating");
		
		Label comment_label = new Label("Comments:");
		TextField Comments = new TextField();
		Comments.setPromptText("Comments");
		
		Label message = new Label(); //error messaging

		Button review_button = new Button("Add review");
		review_button.setOnAction(e->{
			Double rating = Double.valueOf(Rating.getText());
			String comments = Comments.getText();
			String isbn = ISBN.getText();
			
			Book book = lib.find_book(isbn);
			
			if (book == null) { //check if book exists
				message.setText("Book with ISBN: "+isbn+" doesn't exist");
			}
			else if(!user.has_borrowed(book)) {
				message.setText("No open borrowings with this book");
			}
			else if (rating > 5 || rating <1){
				message.setText("Invalid rating. Must be in range 1-5");
			}else {
				book.add_rating(rating);
				book.add_comment(comments);
				lib.save_library(); // save library
				message.setText("Review added");
			}
		});
		
		
		root.getChildren().addAll(title,header, ISBN, rating_label, Rating,
								comment_label, Comments, message, review_button);

		Scene scene = new Scene(root,300,400);
		comment_stage.setScene(scene);;
		comment_stage.show();
	}
}