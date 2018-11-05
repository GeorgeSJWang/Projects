import java.util.ArrayList;

public class SubjectIterator implements Iterator {
	private ArrayList<Book> books = new ArrayList<Book>();
	private int currIndex = 0;
	
	public SubjectIterator(String subject, ArrayList<Book> allBooks) {
		int stop = allBooks.size()-1;
		for (int i=0; i<stop; i++) {
			String tmp = allBooks.get(i).getSubject();
			if (tmp.equals(subject)) {
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
