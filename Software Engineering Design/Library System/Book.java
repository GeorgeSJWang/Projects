
public class Book {
	private String subject;
	private String author;
	private int ID;
	private boolean checkedOut = false;
	private User borrower;
	
	public Book(String author, String subject, int id) {
		this.author = author;
		this.subject = subject;
		this.ID = id;
	}
	
	
	
	public int getID() {
		return ID;
	}
	
	public String getAuthor() {
		return author;
	}

	public String getSubject() {
		return subject;
	}
	
	public boolean isCheckedOut(){
		return checkedOut;
	}

	public void setCheckedOut(User newUser) {
		borrower = newUser;
		checkedOut = true;
	}
	
	
	public void undoCheckedOut() {
		borrower = null;
		checkedOut = false;
	}
	
	public User getBorrower() {
		return borrower;
	}


	
}
