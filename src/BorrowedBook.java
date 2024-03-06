import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class BorrowedBook implements Serializable{
    private User user;
    private Book book;
    private LocalDate borrow_date;
    private LocalDate return_date;

    /**
     * Constructor 
     */
    public BorrowedBook(User user,Book book){
        this.user = user;
        this.book = book;
        this.borrow_date = LocalDate.now(); //set borrowing date
        this.return_date = borrow_date.plusDays(5); // return date is 5 days after
    }

    //get
    public User get_user(){
        return this.user;
    }
    public Book get_book(){
        return this.book;
    }
    public LocalDate get_borrow_date(){
        return this.borrow_date;
    }
    public LocalDate get_return_date() {
    	return this.return_date;
    }

    //set
    public void set_user(User user){
        this.user = user;
    }
    public void set_book(Book book){
        this.book = book;
    }
    public void set_borrow_date(LocalDate date){
        this.borrow_date = date;
    }    
    public void set_return_date() {
    	this.return_date = this.borrow_date.plusDays(5);
    }
    
    // check if book is due 
    public boolean is_due() {
    	LocalDate today = LocalDate.now();
    	if (today.isEqual(this.return_date)) {
    		return true;
    	}
    	return false;
    }
}
