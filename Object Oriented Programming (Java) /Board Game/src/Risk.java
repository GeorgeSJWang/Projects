class Risk{
	private static Map m;

	public static void main(String[] args){
		int i, j;
		int cur_reinforce;
		int cur_player = 0;
		boolean[] player_status = new boolean[4]; 
		int player_count = 4;
		int winner = 0;
		int input1 = 0, input2 = 0, input3 = 0;
		boolean end_card = false;
		int maxT = 0;


		boolean attacked = false;
		boolean fort = false;
		
		Player[] players = new Player[4];
		Deck de = new Deck();
		Card temp_card;

		m = new Map();
		while (input1 != -1) {
			input1 = m.getLastInput();
		}
		
		//initialise
		for(i = 0; i < 4; i++){
			players[i] = new Player(i);
			player_status[i] = true;
		}
		int[] index = new int[24];
		for (i = 0; i < 24;i++){
			index[i] = i;
		}

		java.util.Random rnd = new java.util.Random();
		for(i=index.length-1;i>=0;i--){
			j = rnd.nextInt(i+1);
			int tmp = index[j];
			index[j] = index[i];
			index[i] = tmp;
		}
		cur_player = 0;
		for (i = 0; i < 24; i++){
			m.get_country(index[i]).change_owner(cur_player);
			players[cur_player].add_count(true);
			cur_player++;
			if (cur_player == 4){
				cur_player = 0;
			}
		}

		//Distribute starting armies
		for (i = 0; i < 4; i++){
			cur_reinforce = 15;
			m.showPhase(3, Integer.toString(i+1), " ");
			while(cur_reinforce > 0){
				input1 = m.getLastInput();
				input2 = m.openMessageBox(input1);
				System.out.printf ("");
				m.get_country(input1).add_soldier(input2);
				cur_reinforce -= input2;
			}
		}

		cur_player = 0;

		//main game loop
		while(true){
			
			int cbonus = 0;
			for (i = 1; i < 5; i++) {
				if (m.continentCheck(i, cur_player) == true) {
					if (i == 1) {
						cbonus += 4;
					}
					else if (i == 2) {
						cbonus += 8;
					}
					else {
						cbonus += 6; 
					}
				}
			}
			players[cur_player].change_con_bonus(cbonus);
			cur_reinforce = players[cur_player].reinforcement();

			m.showPhase(0, Integer.toString(cur_player), Integer.toString(cur_reinforce));
			while (cur_reinforce > 0) {
				input1 = m.getLastInput();
				input2 = m.openMessageBox(input1);
				m.get_country(input1).add_soldier(input2);
				cur_reinforce -= input2;
			}
			m.showPhase(1, Integer.toString(cur_player), " ");
			attacked = false;
			while (true){
				input1 = m.getLastInput();
				if (input1 == -1){
					break;
				}
				else if (input1 == -2) {
					player_count = 1;
					for (int g = 0; g < 4; g++) {
						player_status[g] = false;
					}
					player_status[cur_player] = true;
					winner = cur_player;
				}
				else if (m.get_country(input1).get_owner() == cur_player) {
					input2 = m.getLastInput();
					if (input2 == -1){
						break;
					}
					else if (m.adjacent(input1, input2) && m.get_country(input2).get_owner() != cur_player){
						input3 = m.openMessageBox(input1);
						if (attacked == false) {
							attacked = m.invade(input1, input2, input3);
							if (attacked == true){
								//draw card
								temp_card = de.get_top();
								if (temp_card.get_type() == 4) {
									end_card = true;
									break;
								}
								else {
									players[cur_player].draw(temp_card);
								}
							}
						}
						else {
							m.invade(input1, input2, input3);
						}
						if (players[m.get_country(input2).get_owner()].get_country_count() == 0) {
							player_status[m.get_country(input2).get_owner()] = false;
							player_count--;
						}
					}
				}
			}
			if (end_card == true)
				break;
			
			m.showPhase(2, Integer.toString(cur_player), " ");
			//fortify phase
			fort = false;

			while (fort == false) {
				input1 = m.getLastInput();
				if (input1 == -1){
					break;
				}
				if (m.get_country(input1).get_owner() == cur_player){
					input2 = m.getLastInput();
					if (input2 == -1){
						break;
					}
					else if (m.adjacent(input1, input2) && m.get_country(input2).get_owner() == cur_player) {
						input3 = m.openMessageBox(input2);
						m.get_country(input1).kill_soldier(input3);
						m.get_country(input2).add_soldier(input3);
						fort = true;
					}
				}
			}
			
			//end game if only one player left
			if (player_count == 1){
				for(i=0;i<4;i++){
					if(player_status[i] == true){
						break;
					}
				}
				winner = i;
				break;
			}
			//change to next player
			cur_player++;
			if(cur_player >= player_count){
				cur_player = 0;
			}
			while(player_status[cur_player] == false){
				cur_player++;
				if(cur_player >= 4){
					cur_player = 0;
				}
			}
		}
		
		//declare winner
		if(player_count == 1){
			m.showPhase(5, Integer.toString(winner), " ");
		}
		else {
			for (i = 0; i < 4; i++) {
				if (player_status[i] == true) {
					if (players[i].get_country_count() > maxT) {
						maxT = players[i].get_country_count();
					}
				}
			}
		}		
	}
}