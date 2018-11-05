import java.util.*;

public class Library {
	private HashMap<Integer, Book> books = new HashMap<Integer, Book>();
	private HashMap<String, User> users = new HashMap<String, User>();
	private int currentBookID = 0;
	
	public void doAddBook(String author, String subject) {
		Book newBook = new Book(author, subject, currentBookID);
		books.put(currentBookID, newBook);
		currentBookID++;
	}

	public void setStaffUser(String userName) {
		Staff newUser = new Staff(userName);
		users.put(userName, newUser);
	}
	
	public void setBorrowerUser(String userName, int threshold) {
		Borrower newUser = new Borrower(userName, threshold);
		users.put(userName, newUser);
	}

	public void doUserAddBook(String userName, String author, String subject) {
		Book newBook = new Book(author, subject, currentBookID);
		// shoudl I put???
		books.put(currentBookID, newBook);
		currentBookID++;
		if (users.get(userName).addBook(newBook)) {
			// maybe not needed
		}
	}

	public void doRemoveBook(String userName, int bookID) {
		if (users.get(userName).removeBook(bookID)) {
			books.remove(bookID);
		}
	}

	public void doCheckOut(String doer, ArrayList<Integer> bookIDs, String userName) {
		if (users.get(doer).checkOut(bookIDs, userName)) {

			if (users.get(userName).isExceedLimit(bookIDs.size())) {
				System.out.println("Can not check out since the number of books exceed the limitation of user can check-out");
				return;
			}
			for (Integer anID : bookIDs) {
				if (books.get(anID).isCheckedOut()) {
					System.out.println("Can not check out since the book is checked out");
				}
				else {
					books.get(anID).setCheckedOut(users.get(userName));
					users.get(userName).setBorrowedBooks(books.get(anID));
				}
			}
		}
	}

	public void doReturn(String doer, int bookID) {
		if (users.get(doer).returnBook(bookID)) {
			if (!books.get(bookID).isCheckedOut()) {
				System.out.println("Can not return since the book isn't checked out");
			}
			else {
				books.get(bookID).undoCheckedOut();
				users.get(doer).unBorrow(books.get(bookID));
			}
		}
	}

	public Iterator createAuthorIterator(String authorName) {
		ArrayList<Book> allBooks = new ArrayList<Book>();
		for (int i=0; i<currentBookID; i++) {
			allBooks.add(books.get(i));
		}
		return new AuthorIterator(authorName, allBooks);
	}

	public Iterator createSubjectIterator(String subjectName) {
		ArrayList<Book> allBooks = new ArrayList<Book>();
		for (int i=0; i<currentBookID; i++) {
			allBooks.add(books.get(i));
		}
		return new SubjectIterator(subjectName, allBooks);
	}

	public void findBorrowedBooks(String doer, String userName) {
		users.get(doer).findBorrowedBooks(userName);
		ArrayList<Book> allBooks = new ArrayList<Book>();
		for (int i=0; i<currentBookID; i++) {
			allBooks.add(books.get(i));
		}
		

	}
	
	public void doFindBorrower(String doer, int bookID) {
		if (users.get(doer).findBorrower(bookID)) {
			System.out.println("User: "+books.get(bookID).getBorrower().getName());
		}
	}
	
}
