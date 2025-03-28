
import java.util.*;

class Book {
    int id;
    String title, author;
    boolean isBorrowed;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Title: " + title + ", Author: " + author + ", Available: " + !isBorrowed;
    }
}

class User {
    int userId;
    String name;
    List<Integer> borrowedBooks;

    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }
}

public class LibraryManagementSystem {
    static Scanner scanner = new Scanner(System.in);
    static List<Book> books = new ArrayList<>();
    static Map<Integer, User> users = new HashMap<>();
    static int bookIdCounter = 1, userIdCounter = 1;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Register User");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. View Books");
            System.out.println("6. View Borrowed Books");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: addBook(); break;
                case 2: registerUser(); break;
                case 3: borrowBook(); break;
                case 4: returnBook(); break;
                case 5: viewBooks(); break;
                case 6: viewBorrowedBooks(); break;
                case 7: System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice! Try again.");
            }
        }
    }

    static void addBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author name: ");
        String author = scanner.nextLine();
        books.add(new Book(bookIdCounter++, title, author));
        System.out.println("Book added successfully!");
    }

    static void registerUser() {
        System.out.print("Enter user name: ");
        String name = scanner.nextLine();
        users.put(userIdCounter, new User(userIdCounter, name));
        System.out.println("User registered with ID: " + userIdCounter++);
    }

    static void borrowBook() {
        System.out.print("Enter user ID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();

        if (!users.containsKey(userId)) {
            System.out.println("User not found!");
            return;
        }
        for (Book book : books) {
            if (book.id == bookId && !book.isBorrowed) {
                book.isBorrowed = true;
                users.get(userId).borrowedBooks.add(bookId);
                System.out.println("Book borrowed successfully!");
                return;
            }
        }
        System.out.println("Book not available!");
    }

    static void returnBook() {
        System.out.print("Enter user ID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();

        if (!users.containsKey(userId) || !users.get(userId).borrowedBooks.contains(bookId)) {
            System.out.println("Invalid return request!");
            return;
        }
        for (Book book : books) {
            if (book.id == bookId) {
                book.isBorrowed = false;
                users.get(userId).borrowedBooks.remove(Integer.valueOf(bookId));
                System.out.println("Book returned successfully!");
                return;
            }
        }
    }

    static void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            books.forEach(System.out::println);
        }
    }

    static void viewBorrowedBooks() {
        boolean found = false;
        for (Book book : books) {
            if (book.isBorrowed) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) System.out.println("No books are currently borrowed.");
    }
}

