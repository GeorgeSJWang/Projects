public class Player{

	private int id;
	private Card[] card = new Card[16];
	private int num_card;
	private int country_count;
	private int time_drop;
	private int con_bonus;
	
	public Player(int id) {
		this.id = id;
		this.num_card = 0;
		this.country_count = 0;
		this.time_drop = 0;
		this.con_bonus = 0;
	}
	public int reinforcement() {
		int n = this.country_count / 3;
		if(n < 3)
			n = 3;
		int m = 0;
		int old_time_drop = this.time_drop;
		if(drop_card()){
			m = 1;
			this.time_drop++;
		}
		return n + this.con_bonus + m * (4 + old_time_drop * 2); 
		// calculates the number of new soldiers the player gets at the
		// start of a new turn, based on countries owned, whole contin-
		// ents controlled and cards
	}
	public void add_count(boolean bool){
		if(bool)
			this.country_count++;
	}
	public void draw(Card c){
		this.card[this.num_card] = c;
		this.num_card++;
	}

	public int get_country_count(){
		return this.country_count;
	}

	public void change_con_bonus(int n){
		this.con_bonus = n;
	}

	private boolean drop_card(){
		if (this.num_card < 3)
			return false;
		int[] card_count = new int[4];
		boolean[] pos_discard = new boolean[16];
		int n = 0;
		boolean flag = false;
		for(int i = 0; i < 4; i++)
			card_count[i] = 0;
		for(int i = 0; i < 16; i++)
			pos_discard[i] = false;
		for(int i = 0; i < this.num_card; i++)
			card_count[this.card[i].get_type()]++;
		if(card_count[0] + card_count[3] == 3){
			flag = true;
			for(int i = 0; i < this.num_card; i++)
				if(this.card[i].get_type() == 0 || this.card[i].get_type() == 3)
					pos_discard[i] = true;
		}
		else if(card_count[1] + card_count[3] == 3){
			flag = true;
			for(int i = 0; i < this.num_card; i++)
				if(this.card[i].get_type() == 1 || this.card[i].get_type() == 3)
					pos_discard[i] = true;
		}
		else if(card_count[2] + card_count[3] == 3){
			flag = true;
			for(int i = 0; i < this.num_card; i++)
				if(this.card[i].get_type() == 2 || this.card[i].get_type() == 3)
					pos_discard[i] = true;			
		}
		else if(card_count[0] + card_count[1] + card_count[2] == 3){
			flag = true;
			boolean[] t = new boolean[3];
			t[0] = t[1] = t[2] = false;
			for(int i = 0; i < this.num_card; i++)
				if(this.card[i].get_type() != 3)
					if(!t[this.card[i].get_type()]){
						pos_discard[i] = true;
						t[this.card[i].get_type()] = true;
					}	
		}
		if(flag){
			for(int i = 0; i < this.num_card; i++){
				if(pos_discard[i])
					n++;
				if(i + n < this.num_card)
					this.card[i] = this.card[i+n];
			}
			this.num_card -=3;
		}
		return flag;
	}
}
