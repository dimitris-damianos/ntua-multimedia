import java.util.ArrayList;
import java.util.List;
//import java.util.Map;

public class Library{
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

    //


}