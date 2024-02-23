package application;
import java.util.Date;

public class BorrowedBook{
    private User user;
    private Book book;
    private Date date;

    /**
     * Constructor 
     */
    public BorrowedBook(User user,Book book){
        this.user = user;
        this.book = book;
        this.date = new Date(); //set borrowing date
    }

    //get
    public User get_user(){
        return this.user;
    }
    public Book get_book(){
        return this.book;
    }
    public Date get_date(){
        return this.date;
    }

    //set
    public void set_user(User user){
        this.user = user;
    }
    public void set_book(Book book){
        this.book = book;
    }
    public void set_date(Date date){
        this.date = date;
    }    
}
