import java.io.*;
import java.util.*;

public class FinalExam {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		
		String inputLine;
		inputLine = br.readLine();
		
		Library library = new Library();
		
		// number_of_book
		int amount = Integer.parseInt(inputLine);
		for (int i=0; i<amount; i++) {
			inputLine = br.readLine();
			String[] inputs = inputLine.split("\\s");
			library.doAddBook(inputs[0], inputs[1]);
		}
		
		// number_of_user
		inputLine = br.readLine();
		amount = Integer.parseInt(inputLine);
		for (int i=0; i<amount; i++) {
			inputLine = br.readLine();
			String[] inputs = inputLine.split("\\s");
			if (inputs[0].equals("Staff")) {
				library.setStaffUser(inputs[1]);
			}
			else if (inputs[0].equals("Borrower")) {
				library.setBorrowerUser(inputs[1], Integer.parseInt(inputs[2]));
			}
		}
		
		
		
		
		
		
		
		while ((inputLine = br.readLine()) != null) {
			
			

			
			String[] inputs = inputLine.split("\\s");
			
			if (inputs[1].equals("addBook")) {
				String[] bookInfo = br.readLine().split("\\s");
				library.doUserAddBook(inputs[0], bookInfo[0], bookInfo[1]);
			}
			else if (inputs[1].equals("removeBook")) {
				library.doRemoveBook(inputs[0], Integer.parseInt(inputs[2]));
			}
			else if (inputs[1].equals("checkout")) {
				String[] checkOutInfo = br.readLine().split("\\s");
				ArrayList<Integer> bookIDs = new ArrayList<Integer>();
				for (int i=0; i<checkOutInfo.length; i++) {
					bookIDs.add(Integer.parseInt(checkOutInfo[i]));
				}
				library.doCheckOut(inputs[0], bookIDs, inputs[2]);
			}
			else if (inputs[1].equals("return")) {
				library.doReturn(inputs[0], Integer.parseInt(inputs[2]));
			}
			else if (inputs[1].equals("listAuthor")) {
				Iterator iter = library.createAuthorIterator(inputs[2]);
				doIterPrint(iter);
			}
			else if (inputs[1].equals("listSubject")) {
				Iterator iter = library.createSubjectIterator(inputs[2]);
				doIterPrint(iter);
			}
			else if (inputs[1].equals("findChecked")) {
				library.findBorrowedBooks(inputs[0], inputs[1]);
			}
			else if (inputs[1].equals("findBorrower")) {
				library.doFindBorrower(inputs[0], Integer.parseInt(inputs[2]));
			}
		}
		br.close();
	}
	
	public static void doIterPrint(Iterator iter) {
		while (iter.hasNext()) {
			Book currItem = iter.next();
			System.out.println("ID: "+currItem.getID()+" Author: "+currItem.getAuthor()+" Subject: "+currItem.getSubject());
		}
	}
}
