package application;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
//import java.util.Map;

public class Library implements Serializable{
    public List<Book> books; 
    public List<BorrowedBook> all_borrowed_books;
    public List<User> users;
    public List<Admin> admins;
    public List<String> categories;

    public Library(){
       this.books = new ArrayList<>();
       this.all_borrowed_books = new ArrayList<>();
       this.users = new ArrayList<>();
       this.admins = new ArrayList<>();
       this.categories = new ArrayList<>();
    }

    //get
    public List<Book> get_allBooks(){
        return this.books;
    }
    public List<BorrowedBook> get_borrowedBooks(){
        return this.all_borrowed_books;
    }
    public List<User> get_users(){
        return this.users;
    }
    public List<Admin> get_admins(){
        return this.admins;
    }
    public List<String> get_categories(){
        return this.categories;
    }
  
    //set
    public void set_books(List<Book> books){
        this.books = books;
    }
    public void set_borrowedBooks(List<BorrowedBook> borrowed){
        this.all_borrowed_books = borrowed;
    }
    public void set_users(List<User> users){
        this.users = users;
    }
    public void set_admins(List<Admin> admins){
        this.admins = admins;
    }
    public void set_categories(List<String> categories){
        this.categories = categories;
    }
    
    // The following methods are used for serielization/deserialization of library
    // save data methods
    public void save_users() {
    	LibrarySer.save_users(users);
    }
    public void save_admins() {
    	LibrarySer.save_admins(admins);
    }
    public void save_books() {
    	LibrarySer.save_books(books);
    }
    public void save_borrowed() {
    	LibrarySer.save_borrowed(all_borrowed_books);
    }
    public void save_library() {
    	save_users();
    	save_admins();
    	save_books() ;
    	save_borrowed();
    }
    
    // load data methods
    public void load_users(){
    	List<User> loaded = LibrarySer.load_users();
    	if (users!=null) {
    		users.clear();
    		users.addAll(loaded);
    		System.out.println("Users loaded.");
    	}
    }
    public void load_admins(){
    	List<Admin> loaded =  LibrarySer.load_admins();
    	if (loaded != null) {
    		admins.clear();
    		admins.addAll(loaded);
    		System.out.println("Admins loaded.");
    	}
    }
    public void load_books(){
    	List<Book> loaded = LibrarySer.load_books();
    	if (loaded!=null) {
    		books.clear();
    		books.addAll(loaded);
    		System.out.println("Books loaded");
    	}
    }
    public void load_borrowed(){
    	List<BorrowedBook> loaded = LibrarySer.load_borrowed();
    	if (loaded!=null) {
    		all_borrowed_books.clear();
    		all_borrowed_books.addAll(loaded);
    		System.out.println("Borrowed books loaded");
    	}
    }
    public void load_library() {
    	load_users();
    	load_admins();
    	load_books() ;
    	load_borrowed();
    }
     
    
    //Utility functions
    // retrieve top-rated books
    public List<Book> get_top_books(int limit){
    	return books.stream()
                .sorted((b1, b2) -> Double.compare(b2.get_rating(), b1.get_rating()))
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    
    //Update borrowed book with information from the updated book
    //given they share the same ISBN
    public void update_borrowed_info(Book book) {
    	for(BorrowedBook borrowed: all_borrowed_books) {
    		if (borrowed.get_book().get_ISBN().equals(book.get_ISBN())) {
    			borrowed.get_book().set_author(book.get_author());
    			borrowed.get_book().set_category(book.get_category());
    			borrowed.get_book().set_publisher(book.get_publisher());
    			borrowed.get_book().set_releaseYear(book.get_releaseYear());
    			borrowed.get_book().set_title(book.get_title());
    		}
    	}
    }
    
    // find borrowing of a user
    public BorrowedBook find_borrowed(User user, Book book) {
    	for (BorrowedBook borrowed: all_borrowed_books) {
    		if(borrowed.get_user().equals(user) && borrowed.get_book().equals(book)) {
    			return borrowed;
    		}
    	}
    	return null;
    }
    
    //find book by ISBN
    public Book find_book(String ISBN) {
    	for (Book book: books) {
    		if (book.get_ISBN().equals(ISBN)) {
    			return book;
    		}
    	}
    	return null;
    }
    
    //find user by username
    public User find_user(String username) {
    	for(User user: users){
    		if(user.get_username().equals(username)) {
    			return user;
    		}
    	}
    	return null;
    }

    //ADMIN utilities
    // books management
    public void admin_add_book(Admin admin, String title, String author, String publisher, int release_year, String ISBN, String category,int copies){
        Book book = new Book(title,author,publisher,release_year,ISBN,category,copies);
        books.add(book);
        System.out.println("Success");
    }
    
    public void admin_delete_book(Admin admin,String ISBN){ // delete book , as well borrowing
        Book deleted = null;
        for (Book book: books) {
        	if(book.get_ISBN().equals(ISBN)) {
        		deleted = book;
        		break;
        	}
        }
        // delete book from books and borrowed_books
        if (deleted!=null) {
        	String isbn = deleted.get_ISBN();
        	// find if this book is borrowed and  remove it
        	all_borrowed_books.removeIf(borrowed -> {
        		if(borrowed.get_book().get_ISBN().equals(isbn)) {
        			return true;
        		}
        		return false;
        	});
        	books.remove(deleted);
        	System.out.println("Successful delete.");
        }
        else {
        	System.out.println("Book not found.");
        }
    }
    public void admin_edit_book(Admin admin,String ISBN,String new_title, String new_author,
    							String new_publisher, int new_releaseDate, int new_copies, String new_category) {
    	Book edited = null;
    	for(Book book: books) {
    		if(book.get_ISBN().equals(ISBN)) {
    			edited = book;
    			break;
    		}
    	}
    	// edit book if it is found
    	if (edited != null) {
    		edited.set_author(new_author);
    		edited.set_category(new_category);
    		edited.set_copies(new_copies);
    		edited.set_publisher(new_publisher);
    		edited.set_releaseYear(new_releaseDate);
    		edited.set_title(new_title);
    		
    		//update borrowed books with the same ISBN
    		update_borrowed_info(edited);
    		
    		System.out.println("Booked and borrowings edited.");
    	}
    	else {
    		System.out.println("Book not found.");
    	}
    }
    
    //category managements
    public void admin_add_category(Admin admin, String category) {
    	boolean exists = false;
    	for (String categ:categories) {
    		if(categ.equals(category)){
    			exists = true;
    			break;
    		}
    	}
    	if(exists) {
    		System.out.println("Category already exists.");
    	}
    	else {
    		categories.add(category);
    		System.out.println("Category added.");
    	}
    }
    
    public void admin_remove_category(Admin admin, String category) { //removes category and associated book
    	boolean exists = false;
    	for (String categ:categories) {
    		if(categ.equals(category)){
    			exists = true;
    			break;
    		}
    	}
    	if(exists) {// delete books from books and borrowed
    		all_borrowed_books.removeIf(borrowed -> {
    			if(borrowed.get_book().get_category().equals(category)) {
    				return true;
    			}
    			return false;
    		});
    		books.removeIf(book -> { // remove associated books
    			if(book.get_category().equals(category)) {
    				return true;
    			}
    			return false;
    		}
    		);
    		
    		categories.remove(category);
    		System.out.println("Category and associated books deleted.");
    	}
    	else {
    		System.out.println("Category doens't exist.");
    	}
    }
    
    public void admin_edit_category(Admin admin, String old_category,String new_category) {
    	// search for existing category 
    	boolean exists = false;
    	for (String categ:categories) {
    		if(categ.equals(old_category)){
    			exists = true;
    			break;
    		}
    	}
    	if(exists) {// delete old category, add the new
    		categories.remove(old_category);
    		categories.add(new_category);
    		
    		//update books and borrowing
    		for(Book book:books) {
    			if (book.get_category().equals(old_category)){
    				book.set_category(new_category);
    				update_borrowed_info(book);
    			}
    		}
    		System.out.println("Category and associated books updated.");
    	}
    	else {
    		System.out.println("Category doens't exist.");
    	}
    }

    //users managements
    public void add_admin(Admin new_admin) {
    	admins.add(new_admin);
    }
    
    public void add_user(User user) {
    	users.add(user);
    }
    
    public void admin_delete_user(Admin admin, String user_id_num){
    	// check if user exists
    	User user = null;
    	for(User u:users) {
    		if(u.get_idNum().equals(user_id_num)) {
    			user = u;
    		}
    	}
    	if(user!=null) { // user exists
    		// update number of book copies from user borrowings
    		BorrowedBook borrowed = null;
    		for (Book book:books) {
    			borrowed = find_borrowed(user,book);
    			if (borrowed != null) { // book is borrowed
    				//System.out.println("not null borrowed");
    				book.set_copies(book.get_copies()+1); // add one copy
    			}
    		}
    		// delete borrowings
    		all_borrowed_books.removeIf(borrowed_book-> {
    			if(borrowed_book.get_user().get_idNum().equals(user_id_num)) {
    				return true;
    			}
    			return false;
    		});
    		users.remove(user);
    		System.out.println("User deleted.");
    	}
    	else {
    		System.out.println("User not found.");
    	}
    }
    
    // find user by username and update its credentials
    public void admin_edit_user_byUsername(Admin admin, String target, 
    										String new_username, String new_pass, String new_name, String new_sur, String new_idNum, String new_email) {
    	User user = null;
    	for (User u:users) {
    		if (u.get_username().equals(target)) {
    			user = u;
    		}
    	}
    	if (user != null) {
    		user.set_email(new_email);
    		user.set_idNum(new_idNum);
    		user.set_name(new_name);
    		user.set_password(new_pass);
    		user.set_surname(new_sur);
    		user.set_username(new_username);
    		System.out.println("User edited.");
    	}
    	else {
    		System.out.println("User not found.");
    	}
    }
    
 // find user by ID and update its credentials
    public void admin_edit_user_byID(Admin admin, String target, 
    										String new_username, String new_pass, String new_name, String new_sur, String new_idNum, String new_email) {
    	User user = null;
    	for (User u:users) {
    		if (u.get_idNum().equals(target)) {
    			user = u;
    		}
    	}
    	if (user != null) {
    		user.set_email(new_email);
    		user.set_idNum(new_idNum);
    		user.set_name(new_name);
    		user.set_password(new_pass);
    		user.set_surname(new_sur);
    		user.set_username(new_username);
    		System.out.println("User edited.");
    	}
    	else {
    		System.out.println("User not found.");
    	}
    }
    

    //BORROWINGS managements
    
    // terminate borrowing
    public void admin_terminate_borrowing(Admin admin,String username, String ISBN) {
    	User user = find_user(username);
    	Book book = find_book(ISBN);
    	if (user == null) {
    		System.out.println("User not found.");
    	}
    	else if(book == null){
    		System.out.println("Book not found.");
    	}
    	else {
    		// check if user has borrowed book
    		BorrowedBook borrowed = find_borrowed(user,book);
    		if (borrowed == null) {
    			System.out.println("User hasnt borrowed this book.");
    		}
    		else {
    			// update book copies
    			book.set_copies(book.get_copies()+1);
    			//delete borrowing
    			all_borrowed_books.remove(borrowed);
    			System.out.println("Borrowing terminated");
    		}
    	}
    }
    
    // view all borrowings
    /////////
    
    /// USER UTILITIES
    // find all books given args. If any arg is empty, still return book
    public List<Book> search_books(String title, String author, String release_year){
    	List<Book> res = new ArrayList<>();
    	for (Book book: books) {
    		// check if given args match to book
    		// if args are empty, return book also
    		boolean title_found = title.isEmpty() || book.get_title().toLowerCase().contains(title.toLowerCase());
            boolean author_found = author.isEmpty() || book.get_author().toLowerCase().contains(author.toLowerCase());
            boolean releaseYear_found = release_year.isEmpty() || String.valueOf(book.get_releaseYear()).equals(release_year);
    		
            if (title_found && author_found && releaseYear_found) {
            	res.add(book);
            }
    	}
    	
    	return res;
    }
    
    public void user_borrow_book(User user, Book book) {
    	// use already implemented func
    	if (user != null && book !=null) {
    		user.borrow(book);
    		BorrowedBook borrowed =  user.get_borrowed_books().getLast();
    		this.all_borrowed_books.add(borrowed);
    	}
    }
    
    
    /// METHODS FOR LIBRARY INIT
    public void init_library() {
    	this.init_categories();
    	this.init_book();
    	this.init_admins();
    	this.init_users();
    	this.init_borrowings();
    }
    private void init_borrowings() {
    	/// get users
    	User user1 = this.users.get(0);
    	User user2 = this.users.get(1);
    	User user3 = this.users.get(2);
    	User user4 = this.users.get(3);
    	
    	//get books
    	Book  book1 = this.books.get(0);
    	Book  book2 = this.books.get(1);
    	Book  book3 = this.books.get(2);
    	Book  book4 = this.books.get(3);
    	Book  book5 = this.books.get(4);
    	Book  book6 = this.books.get(5);
    	
    	// set borrowing
    	user_borrow_book(user1,book1);
    	user_borrow_book(user1,book2);
    	user_borrow_book(user1,book3);
    	user_borrow_book(user1,book4);
    	
    	user_borrow_book(user2,book1);
    	user_borrow_book(user2,book2);
    	user_borrow_book(user2,book3);
    	
    	user_borrow_book(user3,book1);
    	user_borrow_book(user3,book4);
    	user_borrow_book(user3,book5);
    	user_borrow_book(user3,book6);
    	
    	
    	user_borrow_book(user4,book1);
    	user_borrow_book(user4,book5);

    		
    }
    
    private void init_users() {
    	User user1 = new User("user1","pass1","name1","sur1","id1","mail1");
    	User user2 = new User("user2","pass2","name2","sur2","id2","mail2");
    	User user3 = new User("user3","pass3","name3","sur3","id3","mail3");
    	User user4 = new User("user4","pass4","name4","sur4","id4","mail4");
    	User user5 = new User("user5","pass5","name5","sur5","id5","mail5");
    	User user6 = new User("user6","pass6","name6","sur6","id6","mail6");
    	
    	this.users.add(user1);
    	this.users.add(user2);
    	this.users.add(user3);
    	this.users.add(user4);
    	this.users.add(user5);
    	this.users.add(user6);
    }
    
    private void init_admins() {
    	Admin admin1 = new Admin("medialab","medialab_2024");
    	Admin admin2 = new Admin("dimitris","damianos");
    	
    	this.admins.add(admin1);
    	this.admins.add(admin2);		
    }
    
    private void init_categories(){
    	this.categories.add("cat1");
    	this.categories.add("cat2");
    	this.categories.add("cat3");
    	this.categories.add("cat4");
    	this.categories.add("cat5");
    	this.categories.add("cat6");
    	this.categories.add("cat7");
    }
    
    private void init_book(){
    	String comm1 = "Good";
    	String comm2 = "Great";
    	String comm3 = "Bad";
    	String comm4 = "Terrible";
    	String comm5 = "Recommended";
    	String comm6 = "Not Recommended";
    	
    	// good book
        Book book1 = new Book("Title1","author1","publ1",1950,"isbn1","cat1",5);
        book1.add_comment(comm1);
        book1.add_comment(comm2);
        book1.add_comment(comm5);
        book1.add_rating(3.5);
        book1.add_rating(4.5);
        book1.add_rating(5.0);
        this.books.add(book1);
        
        // great book
        Book book2 = new Book("Title2","author2","publ2",2000,"isbn2","cat2",3);
        book2.add_comment(comm1);
        book2.add_comment(comm1);
        book2.add_comment(comm1);
        book2.add_rating(5.0);
        book2.add_rating(4.5);
        book2.add_rating(5.0);
        this.books.add(book2);
        
        // bad book
        Book book3 = new Book("Title3","author3","publ3",1999,"isbn3","cat1",10);
        book3.add_comment(comm6);
        book3.add_comment(comm3);
        book3.add_comment(comm4);
        book3.add_rating(2.5);
        book3.add_rating(2.0);
        book3.add_rating(1.5);
        this.books.add(book3);
        
        // terrible book
        Book book4 = new Book("Title4","author4","publ4",2013,"isbn4","cat6",5);
        book4.add_comment(comm6);
        book4.add_comment(comm3);
        book4.add_comment(comm4);
        book4.add_rating(1.5);
        book4.add_rating(2.0);
        book4.add_rating(1.5);
        this.books.add(book4);
        
        // best book
        Book book5 = new Book("Title5","author5","publ5",1955,"isbn5","cat4",13);
        book5.add_comment(comm1);
        book5.add_comment(comm2);
        book5.add_comment(comm5);
        book5.add_rating(5.0);
        book5.add_rating(5.0);
        book5.add_rating(5.0);
        this.books.add(book5);
        
        
        // worst book
        Book book6 = new Book("Title6","author6","publ6",1987,"isbn6","cat6",5);
        book6.add_comment(comm6);
        book6.add_comment(comm4);
        book6.add_comment(comm6);
        book6.add_rating(1.0);
        book6.add_rating(0.5);
        book6.add_rating(0.5);
        this.books.add(book6);
        
        // pk book
        Book book7 = new Book("Title7","author7","publ7",1987,"isbn7","cat7",8);
        book7.add_comment(comm1);
        book7.add_comment(comm5);
        book7.add_comment(comm1);
        book7.add_rating(3.0);
        book7.add_rating(4.0);
        book7.add_rating(3.5);
        this.books.add(book7);
        
    }
    
    
 

}