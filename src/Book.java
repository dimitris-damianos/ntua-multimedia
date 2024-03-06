
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
/** 
 * Represents a Book item of the Library
 */

public class Book implements Serializable{
    /**
     * Attributes 
     */
    private String title;
    private String author;
    private String publisher;
    private int release_year;
    private String ISBN;
    private String category;
    private int copies; 
    private List<Double> all_ratings;
    private double rating;
    private List<String> comments;

    /**
     * Constructor
     * @param title Book's title
     * @param author Book's author
     * @param publisher Book's publisher
     * @param release_year Book's release year
     * @param ISBN Book's ISBN
     * @param category Book's category
     * @param copies Book's available copies
     * @param all_ratings Book's all ratings from readers
     * @param rating Book's average rating
     * @param comments Book's comments from readers
     */
    public Book(String title, String author, String publisher,int release_year, String ISBN, String category,
                int copies){
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.release_year = release_year;
        this.category = category;
        this.ISBN = ISBN;
        this.copies = copies;
        this.rating = 0;
        this.all_ratings = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    /**
     * Methods for info retrieval
     */
    public String get_title(){
        return this.title;
    }
    public String get_author(){
        return this.author;
    }
    public String get_publisher(){
        return this.publisher;
    }
    public int get_releaseYear(){
        return this.release_year;
    }
    public String get_ISBN(){
        return this.ISBN;
    }
    public String get_category(){
        return this.category;
    }
    public int get_copies(){
        return this.copies;
    }
    public double get_rating(){
        return this.rating;
    }
    public List<Double> get_allRatings(){
        return this.all_ratings;
    }
    public List<String> get_comments(){
        return this.comments;
    }

    /**
     * Methods for info setup 
     */
    public void set_title(String title){
        this.title = title;
    }
    public void set_author(String author){
        this.author = author;
    }
    public void set_releaseYear(int release_year){
        this.release_year = release_year;
    }
    public void set_publisher(String publisher){
        this.publisher = publisher;
    }
    public void set_copies(int copies){
        this.copies = copies;
    }
    public void set_ISBN(String ISBN){
        this.ISBN = ISBN;
    }
    public void set_category(String category){
        this.category = category;
    }
    public void add_rating(Double rating){
        this.all_ratings.add(rating);
        this.rating = update_rating(); // each new rating updates books rating
    }
    public void add_comment(String comment){
        this.comments.add(comment);
    }

    /**
     * Checks if book belongs in given category
     * 
     * @param categ	String, given category
     * @return	Boolean
     */
    public boolean in_category(String categ) {
    	if(this.category.equals(categ)) {
    		return true;
    	}
    	return false;
    }
    
    private double update_rating(){
        if(this.all_ratings.isEmpty()){
            return 0.0;
        }
        double sum = 0.0;
        for(double num : this.all_ratings){
            sum += num;
        }
        return (double) sum/this.all_ratings.size();
    } 


}
