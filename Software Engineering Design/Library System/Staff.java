import java.util.ArrayList;

public class Staff extends User {

	public Staff(String name) {
		super.setName(name);
	}
	
	@Override
	public boolean addBook(Book book) {
		return true;
	}

	@Override
	public boolean removeBook(int BookID) {
		return true;
	}

	@Override
	public boolean checkOut(ArrayList<Integer> bookIDs, String userName) {
		return true;
	}

	@Override
	public boolean returnBook(int bookID) {
		return true;
	}

	@Override
	public void findBorrowedBooks(String userName) {
		super.displayBorrowedBooks();
	}

	@Override
	public boolean findBorrower(int bookID) {
		return true;
	}

	@Override
	public boolean isExceedLimit(int count) {
		return false;
	}

}
