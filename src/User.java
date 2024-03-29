
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String id_num;
    private String email;
    private List<BorrowedBook> borrowed_books;
    private int book_limit;

    public User(String username, String password, String name, String surname,
                String id_num, String email){
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.id_num = id_num;
        this.email = email;
        this.borrowed_books = new ArrayList();
        this.book_limit=2; // limit of 2 borrowed books 

    }
    //get 
    public String get_username(){
        return this.username;
    }
    public String get_password(){
        return this.password;
    }
    public String get_name(){
        return this.name;
    }
    public String get_surname(){
        return this.surname;
    }
    public String get_idNum(){
        return this.id_num;
    }
    public String get_email(){
        return this.email;
    }
    public List<BorrowedBook> get_borrowed_books(){
    	return this.borrowed_books;
    }
    public int get_book_limit() {
    	return this.book_limit;
    }

    //set
    public void set_username(String val){
        this.username = val;
    }
    public void set_password(String val){
        this.password = val;
    }
    public void set_name(String val){
        this.name = val;
    }
    public void set_surname(String val){
        this.surname = val;
    }
    public void set_idNum(String val){
        this.id_num = val;
    }
    public void set_email(String val){
        this.email = val;
    }
    public void set_borrowed_books(List<BorrowedBook> val){
    	this.borrowed_books = val;
    }
    public void set_book_limit(int limit) {
    	this.book_limit = limit;
    }
    

    // check if has admin privilages
    public boolean is_admin(){
        return false;
    }

    //add review for specific book
    public void add_review(Book book,Double rating, String comment){
        if(book == null){
            System.out.println("Book not found.");
        }
        else{
            book.add_comment(comment);
            book.add_rating(rating);
            System.out.println("Review uploaded.");
        }
    }

    // borrow specific book if possible
    public void borrow(Book book){
        if(book != null){
            if(book.get_copies() > 0){
            	if (this.book_limit == 0) {
            		System.out.println("Book limit reached");
            	}
            	else {
            		this.book_limit--; 
            		BorrowedBook borrowed = new BorrowedBook(this, book);
	                book.set_copies(book.get_copies()-1); //update available copies
	                this.borrowed_books.add(borrowed);          // update borrowing list
	                //System.out.println("Succesfull borrowing.");
            	}
                
            }
            else{
                System.out.println("No more copies available.");
            }
        }
        else{
            System.out.println("Book not found.");
        }
    }

    // view user's borrowed books
    public void view_borrowedBooks(){
        for(BorrowedBook book: borrowed_books){
            System.out.println("Borrowed books:"+book.get_book().get_title()+book.get_borrow_date());
        }
    }

    // check if user has borrowed given book
    public boolean has_borrowed(Book book) {
    	for (BorrowedBook borrowed: borrowed_books) {
    		if (borrowed.get_book().equals(book)) {
    			return true;
    		}
    	}
    	return false;
    }
}
