import java.util.ArrayList;

public abstract class User {
	
	private String name;
	private ArrayList<Book> borrowedBooks = new ArrayList<Book>();
	
	public abstract boolean addBook(Book book);
	public abstract boolean removeBook(int BookID);
	public abstract boolean checkOut(ArrayList<Integer> bookIDs, String userName);
	public abstract boolean returnBook(int bookID);
	public abstract void findBorrowedBooks(String userName);
	public abstract boolean findBorrower(int bookID);
	public abstract boolean isExceedLimit(int count);
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setBorrowedBooks(Book newBook) {
		borrowedBooks.add(newBook);
	}
	
	public void unBorrow(Book theBook) {
		borrowedBooks.remove(theBook);
	}
	
	
	public void displayBorrowedBooks() {
		for (Book aBook : borrowedBooks) {
			System.out.println("ID: "+aBook.getID()+" Author: "+aBook.getAuthor()+" Subject: "+aBook.getSubject());
		}
	}

}
