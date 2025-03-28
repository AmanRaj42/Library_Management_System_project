package LMSproject;

import java.util.*;

class Book {
    private int bookId;
    private String title;
    private String author;
    private boolean isBorrowed;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }

    public int getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isBorrowed() { return isBorrowed; }

    public void borrowBook() { this.isBorrowed = true; }
    public void returnBook() { this.isBorrowed = false; }

    @Override
    public String toString() {
        return "[ID: " + bookId + "] " + title + " by " + author + " " + (isBorrowed ? "(Borrowed)" : "(Available)");
    }
}

class User {
    private int userId;
    private String name;
    private List<Book> borrowedBooks;

    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public int getUserId() { return userId; }
    public String getName() { return name; }
    public List<Book> getBorrowedBooks() { return borrowedBooks; }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
        book.borrowBook();
    }

    public boolean returnBook(int bookId) {
        for (Book book : borrowedBooks) {
            if (book.getBookId() == bookId) {
                book.returnBook();
                borrowedBooks.remove(book);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "User[ID: " + userId + ", Name: " + name + "]";
    }
}

class Library {
    private List<Book> books;
    private Map<Integer, User> users;
    private int bookCounter = 1;
    private int userCounter = 1;

    public Library() {
        books = new ArrayList<>();
        users = new HashMap<>();
    }

    public void addBook(String title, String author) {
        books.add(new Book(bookCounter++, title, author));
        System.out.println("Book added successfully!");
    }

    public void registerUser(String name) {
        users.put(userCounter, new User(userCounter, name));
        System.out.println("User registered with ID: " + userCounter);
        userCounter++;
    }

    public void borrowBook(int userId, int bookId) {
        User user = users.get(userId);
        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        for (Book book : books) {
            if (book.getBookId() == bookId) {
                if (book.isBorrowed()) {
                    System.out.println("Book is already borrowed!");
                } else {
                    user.borrowBook(book);
                    System.out.println("Book borrowed successfully!");
                }
                return;
            }
        }
        System.out.println("Book not found!");
    }

    public void returnBook(int userId, int bookId) {
        User user = users.get(userId);
        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        if (user.returnBook(bookId)) {
            System.out.println("Book returned successfully!");
        } else {
            System.out.println("User did not borrow this book!");
        }
    }

    public void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available!");
        } else {
            books.forEach(System.out::println);
        }
    }

    public void viewBorrowedBooks(int userId) {
        User user = users.get(userId);
        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        List<Book> borrowedBooks = user.getBorrowedBooks();
        if (borrowedBooks.isEmpty()) {
            System.out.println("No borrowed books.");
        } else {
            borrowedBooks.forEach(System.out::println);
        }
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        while (true) {
            System.out.println("\n=== Library Management System ===");
            System.out.println("1. Add Book");
            System.out.println("2. Register User");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. View Books");
            System.out.println("6. View Borrowed Books");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Book Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Book Author: ");
                    String author = scanner.nextLine();
                    library.addBook(title, author);
                    break;
                case 2:
                    System.out.print("Enter User Name: ");
                    String name = scanner.nextLine();
                    library.registerUser(name);
                    break;
                case 3:
                    System.out.print("Enter User ID: ");
                    int userId = scanner.nextInt();
                    System.out.print("Enter Book ID: ");
                    int bookId = scanner.nextInt();
                    library.borrowBook(userId, bookId);
                    break;
                case 4:
                    System.out.print("Enter User ID: ");
                    int returnUserId = scanner.nextInt();
                    System.out.print("Enter Book ID: ");
                    int returnBookId = scanner.nextInt();
                    library.returnBook(returnUserId, returnBookId);
                    break;
                case 5:
                    library.viewBooks();
                    break;
                case 6:
                    System.out.print("Enter User ID: ");
                    int viewUserId = scanner.nextInt();
                    library.viewBorrowedBooks(viewUserId);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }
}
