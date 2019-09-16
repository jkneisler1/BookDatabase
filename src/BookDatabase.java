import java.util.*;
import java.text.NumberFormat;

/**
 * Author: John Kneisler
 * Date: 9/16/2019
 * --------
 * First part of the program:
 * Create a book class to hold the following information
 *      Title -- String
 *      Author -- String
 *      Price -- double
 *      Description -- String
 *      isInStock -- boolean
 * Also create a method to print out the information of the book.
 * --------
 * Second part of the program:
 * Entering and printing multiple books
 * Enter 5 books.  After the fifth book is entered, the program will output the books in the
 * order that they were entered.
 * --------
 * Third part of the program:
 * Created a new project of the book program.
 * There are three parts of the Book Database Application
 * 1) Entering a book into the database
 * 2) Displaying a single book or multiple books from the database
 * 3) Querying a book in the database using a SKU number.
 *
 * Used a HashMap to emulate a database (bookDB)
 * There are a set of choices provided for the user to choose from.  The choices to enter are in parenthesis.
 */

public class BookDatabase {
    private HashMap<String, Book> bookDB = new HashMap<>();

    public static void main(String[] args) {

        String choice;
        boolean cont = true;
        Scanner key = new Scanner(System.in);
        BookDatabase bDB = new BookDatabase();

        while (cont) {
            System.out.println("Do you want to do:");
            System.out.println("\tEnter books into the database (E)");
            System.out.println("\tFind a book (F)");
            System.out.println("\tAdd all books (A)");
            System.out.println("\tSearch for a book (S)");
            System.out.println("\tCreate an empty book (M)");
            System.out.println("\tQuit (Q)");
            choice = key.nextLine();

            if (choice.equalsIgnoreCase("E")) {
                System.out.println("Entering books into the database");
                bDB.enterBook();
            } else if (choice.equalsIgnoreCase("F")) {
                System.out.println("Searching for a book");
                System.out.println("A list of SKU numbers and book titles are listed below.");
                System.out.println("SKU Number\tBook Titles");
                System.out.println("==================================================");

                Set<String> allKeysSet = bDB.getKeys();
                for (String mkey : allKeysSet) {
                    System.out.println(mkey + "\t" + bDB.getVal(mkey));
                }
            }
            else if (choice.equalsIgnoreCase("A")) {
                bDB.enterAll();
            }
            else if (choice.equalsIgnoreCase("S")) {
                System.out.println("Enter a sku number: ");
                String sku = key.nextLine();
                System.out.println();
                Book tmpBook = bDB.getBook(sku);
                tmpBook.displayBook();
            }
            else if (choice.equalsIgnoreCase("M")) {
                System.out.println("Enter an empty book with a SKU number (to be filled in later).");
                System.out.println("Enter a new SKU number: ");
                String sku = key.nextLine();
                System.out.println();
                bDB.enterEmptyBook(sku);
            }
            else if (choice.equalsIgnoreCase("Q")) {
                System.out.println("Quiting the system.");
                cont = false;
            }
            System.out.println();
        }
    }

    private Set<String> getKeys() {                     // returns the SKU numbers for choice "F"
        return bookDB.keySet();
    }

    private String getVal(String ky) {                  // returns the book titles from the hash map for choice "F"
        return bookDB.get(ky).getTitle();
    }

    private void enterEmptyBook(String sku) {           // used to enter an empty book
        Book book = new Book();
        bookDB.put(sku, book);
        return;
    }

    private void enterBook() {                          // Method used to enter and populate a book
        String sku;
        String author;
        String description;
        double price;
        String title;
        boolean isInStock;
        String priceTmp;
        String inStock;
        Scanner keyb = new Scanner(System.in);

        System.out.println("Enter the sku number.");            // Enter the SKU number
        sku = keyb.nextLine();

        System.out.println("Enter the title of the book.");     // Enter the title
        title = keyb.nextLine();

        System.out.println("Enter the author of the book.");    // Enter the author
        author = keyb.nextLine();

        System.out.println("Enter the price of the book.");     // Enter the price of the book
        priceTmp = keyb.nextLine();
        price = Double.parseDouble(priceTmp);

        System.out.println("Enter a description of the book."); // Enter the description of the book
        description = keyb.nextLine();

        System.out.println("Enter whether the book is in stock (\"Y\"|\"N\").");    // in stock?
        inStock = keyb.nextLine();
        if (inStock.equalsIgnoreCase("Y")) {
            isInStock = true;
        } else if (inStock.equalsIgnoreCase("N")) {
            isInStock = false;
        } else {
            System.out.println("Not a valid entry.  In stock status is unknown; therefore, it will be listed as no.");
            isInStock = false;
        }

        Book book = new Book(title, author, description, price, isInStock);
        bookDB.put(sku, book);
    }

    private void enterAll() {                    // Populates a standard set of books into the database

        String sku = "Java1001";
        Book book1 = new Book("Head First Java", "Kathy Sierra and Bert Bates", "Easy to read Java workbook", 47.50, true);
        bookDB.put(sku, book1);

        sku = "Java1002";
        Book book2 = new Book("Thinking in Java", "Bruce Eckel", "Details about Java under the hood.", 20.00, true);
        bookDB.put(sku, book2);

        sku = "Orcl1003";
        Book book3 = new Book("OCP: Oracle Certified Professional Jave SE", "Jeanne Boyarsky", "Everything you need to know in one place", 45.00, true);
        bookDB.put(sku, book3);

        sku = "Python1004";
        Book book4 = new Book("Automate the Boring Stuff with Python", "Al Sweigart", "Fun with Python", 10.50, true);
        bookDB.put(sku, book4);

        sku = "Zombie1005";
        Book book5 = new Book("The Maker's Guide to the Zombie Apocalypse", "Simon Monk", "Defend Your Base with Simple Circuits, Arduino and Raspberry Pi", 16.50, false);
        bookDB.put(sku, book5);

        sku = "Rasp1006";
        Book book6 = new Book("Raspberry Pi Projects for the Evil Genius", "Donald Norris", "A dozen friendly fun projects for the Raspberry Pi!", 14.75, true);
        bookDB.put(sku, book6);
    }

    private Book getBook(String sku) {                       // Will return the Object Book given a SKU number
        return bookDB.get(sku);
    }

    private class Book {                                    // The Book class with helper methods
        private String title;
        private String author;
        private String description;
        private double price;
        private boolean isInStock;

        // Default Constructors
        private Book() {
            title = "NO TITLE";
            author = "NO AUTHOR";
            description = "NO DESCPTION";
            price = 0.0;
            isInStock = false;
        }

        // Overloaded Constructor
        private Book(String title, String author, String description) {
            this.title = title;
            this.author = author;
            this.description = description;
            this.price = 0.0;
            this.isInStock = false;
        }

        // Overloaded Constructor
        private Book(String title, String author, String description, double price, boolean isInStock ) {
            this.title = title;
            this.author = author;
            this.description = description;
            this.price = price;
            this.isInStock = isInStock;
        }

        // Method: displayBook
        // This method is used to display the information for one book
        private void displayBook() {
            NumberFormat formatter = NumberFormat.getCurrencyInstance();

            System.out.println("Book title: " + getTitle());
            System.out.println("Book author: " + getAuthor());
            System.out.println("Book description: " + getDescription());
            System.out.println(("The price is: " + formatter.format(getPrice())));
            if (isInStock()) {
                System.out.println("Currently the book is in stock.");
            }
            else {
                System.out.println("Currently the book is not in stock.");
            }
            System.out.println();
        }

        private String displayText() {
            String newLine = "\n";
            StringBuilder sb = new StringBuilder();
            sb.append("Author: ");
            sb.append(getAuthor());
            sb.append(newLine);
            sb.append("Title: ");
            sb.append(getTitle());
            sb.append(newLine);
            sb.append("Description: ");
            sb.append(getDescription());
            sb.append(newLine);

            return sb.toString();
        }

        /**
         * Getter methods for the book variables
         */
        private String getTitle() {
            return title;
        }
        private String getAuthor() {
            return author;
        }
        private String getDescription() {
            return description;
        }
        private double getPrice() {
            return price;
        }
        private boolean isInStock() {
            return isInStock;
        }

        /**
         * Setter methods for the book variables
         */
        public void setTitle(String title) {
            this.title = title;
        }
        public void setAuthor(String author) {
            this.author = author;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public void setPrice(double price) {
            this.price = price;
        }
        public void setInStock(boolean inStock) {
            isInStock = inStock;
        }
    }
}
