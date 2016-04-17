public class Deck {

	private Card[] cards = new Card[27];
	private int top;
	
	public Deck() {
		int i;
		int[] index = new int[27];
		for(i=0;i<27;i++){
			index[i] = i;
		}
		java.util.Random rnd = new java.util.Random();
		for(i=index.length-1;i>=0;i--){
			int j = rnd.nextInt(i+1);
			int tmp = index[j];
			index[j] = index[i];
			index[i] = tmp;
		}
		for(i=0;i<27;i++){
			if(index[i] < 8){
				cards[i] = new Card(0);
			}
			else if(index[i] < 16){
				cards[i] = new Card(1);
			}
			else if(index[i] < 24){
				cards[i] = new Card(2);
			}
			else if(index[i] < 26){
				cards[i] = new Card(3);
			}
			else if(index[i] == 26){
				cards[i] = new Card(4);
			}
		}
		top = 0;
	}

	public Card get_top() {
		int oldtop = top;
		top++;
		return cards[oldtop];
	}
}