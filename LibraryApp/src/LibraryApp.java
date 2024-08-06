import java.util.List;
import java.util.Scanner;

public class LibraryApp {
    private static Library library = new Library();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("Library Management App");
            System.out.println("1. Add a book");
            System.out.println("2. View books");
            System.out.println("3. Remove a book");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    viewBooks();
                    break;
                case 3:
                    removeBook();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        Book book = new Book(title, author);
        library.addBook(book);
        System.out.println("Book added successfully.");
    }

    private static void viewBooks() {
        List<Book> books = library.getBooks();
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
        } else {
            for (int i = 0; i < books.size(); i++) {
                System.out.println((i + 1) + ". " + books.get(i));
            }
        }
    }

    private static void removeBook() {
        viewBooks();
        System.out.print("Enter the number of the book to remove: ");
        int index = scanner.nextInt() - 1;
        library.removeBook(index);
        System.out.println("Book removed successfully.");
    }
}
