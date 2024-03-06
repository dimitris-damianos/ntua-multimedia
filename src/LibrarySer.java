
import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibrarySer{
	// path files
    private static final String BOOK_PATH = "medialab/books.ser";
    private static final String USERS_PATH = "medialab/users.ser";
    private static final String ADMIN_PATH = "medialab/admins.ser";
    private static final String BORROWED_PATH = "medialab/borrowed.ser";
    private static final String CATEGORIES_PATH = "medialab/categories.ser";
    private static final Logger LOGGER = Logger.getLogger(LibrarySer.class.getName());
    
    // user load and save methods
    public static List<User> load_users(){
    	try (ObjectInputStream output = new ObjectInputStream(new FileInputStream(USERS_PATH))) {
            return (List<User>) output.readObject();
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.INFO, "No previous USERS found.Returning NULL list.");
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error loading USERS: " + e.getMessage(), e);
        }
        return null;
    }
    
    public static void save_users(List<User> users) {
        try (ObjectOutputStream input = new ObjectOutputStream(new FileOutputStream(USERS_PATH))) {
            input.writeObject(users);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving USERS: " + e.getMessage(), e);
        }
    }
    
    // admin load and save methods
    public static List<Admin> load_admins(){
    	try (ObjectInputStream output = new ObjectInputStream(new FileInputStream(ADMIN_PATH))) {
            return (List<Admin>) output.readObject();
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.INFO, "No previous ADMINS found.Returning NULL list.");
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error loading ADMINS: " + e.getMessage(), e);
        }
        return null;
    }
    
    public static void save_admins(List<Admin> admins) {
        try (ObjectOutputStream input = new ObjectOutputStream(new FileOutputStream(ADMIN_PATH))) {
            input.writeObject(admins);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving ADMINS: " + e.getMessage(), e);
        }
    }
    
    // books save and load method
    public static List<Book> load_books(){
    	try (ObjectInputStream output = new ObjectInputStream(new FileInputStream(BOOK_PATH))) {
            return (List<Book>) output.readObject();
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.INFO, "No previous BOOKS found.Returning NULL list.");
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error loading BOOKS: " + e.getMessage(), e);
        }
        return null;
    }
    
    public static void save_books(List<Book> books) {
        try (ObjectOutputStream input = new ObjectOutputStream(new FileOutputStream(BOOK_PATH))) {
            input.writeObject(books);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving BOOKS: " + e.getMessage(), e);
        }
    }
    
    // borrowings save and load methods
    public static List<BorrowedBook> load_borrowed(){
    	try (ObjectInputStream output = new ObjectInputStream(new FileInputStream(BORROWED_PATH))) {
            return (List<BorrowedBook>) output.readObject();
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.INFO, "No previous BORROWED found.Returning NULL list.");
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error loading BORROWED: " + e.getMessage(), e);
        }
        return null;
    }
    
    public static void save_borrowed(List<BorrowedBook> borrowed) {
        try (ObjectOutputStream input = new ObjectOutputStream(new FileOutputStream(BORROWED_PATH))) {
            input.writeObject(borrowed);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving BORROWED: " + e.getMessage(), e);
        }
    }
    
    // borrowings save and load methods
    public static List<String> load_categories(){
    	try (ObjectInputStream output = new ObjectInputStream(new FileInputStream(CATEGORIES_PATH))) {
            return (List<String>) output.readObject();
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.INFO, "No previous CATEGORIES found.Returning NULL list.");
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error loading CATEGORIES: " + e.getMessage(), e);
        }
        return null;
    }
    
    public static void save_categories(List<String> categories) {
        try (ObjectOutputStream input = new ObjectOutputStream(new FileOutputStream(CATEGORIES_PATH))) {
            input.writeObject(categories);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving CATEGORIES: " + e.getMessage(), e);
        }
    }
    
    
    
}