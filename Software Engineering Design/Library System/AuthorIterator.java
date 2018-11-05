import java.util.ArrayList;

public class AuthorIterator implements Iterator {
	private ArrayList<Book> books = new ArrayList<Book>();
	private int currIndex = 0;
	
	public AuthorIterator(String author, ArrayList<Book> allBooks) {
		int stop = allBooks.size()-1;
		for (int i=0; i<stop; i++) {
			String tmp = allBooks.get(i).getAuthor();
			if (tmp.equals(author)) {
				books.add(allBooks.get(i));
			}
		}
	}
	
	@Override
	public boolean hasNext() {
		return currIndex < books.size();
	}

	@Override
	public Book next() {
		currIndex++;
		return books.get(currIndex-1);
	}

}
