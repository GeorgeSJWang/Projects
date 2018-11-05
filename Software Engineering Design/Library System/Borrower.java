import java.util.ArrayList;

public class Borrower extends User {

	private int threshold;
	
	public Borrower(String name, int limit) {
		super.setName(name);
		threshold = limit;
	}
	
	@Override
	public boolean addBook(Book book) {
		System.out.println("Borrower can not add book");
		return false;
	}

	@Override
	public boolean removeBook(int BookID) {
		System.out.println("Borrower can not remove book");
		return false;
	}

	@Override
	public boolean checkOut(ArrayList<Integer> bookIDs, String userName) {
		System.out.println("Borrower can not check out the books");
		return false;
	}

	@Override
	public boolean returnBook(int bookID) {
		System.out.println("Borrower can not return book");
		return false;
	}

	@Override
	public void findBorrowedBooks(String userName) {
		if (super.getName() != userName) {
			System.out.println("Borrower can not find books checked out by other users");
		}
		else {
			super.displayBorrowedBooks();
		}
	}

	@Override
	public boolean findBorrower(int bookID) {
		System.out.println("Borrower can not find borrower");
		return false;
	}

	@Override
	public boolean isExceedLimit(int count) {
		return count > this.threshold;
	}

}
