

public class Main{
    public static void main(String[] args){
        Book book = new Book("1984","WIlson","CPU",2022,"11-11","book",10);
        book.add_rating(2.0);
        book.add_rating(3.0);
        book.add_rating(5.0);
        book.add_rating(4.0);
        System.out.println(book.get_rating());
        book.add_comment("Boring");
        book.add_comment("UNTRUEEE");
        book.add_comment("TRUMP IS INNOCCENT");
        System.out.println(book.get_comments());

    }


}