import java.util.Random;

public class Combat
{
	public Combat(){
		
	}

	public static boolean fight(Country off, Country def, int off_sol_num){
		
		while(off_sol_num > 0 && def.get_soldier() > 0){
			int dice_offense_num = (off_sol_num > 3)? 3:off_sol_num;
			int dice_defense_num = (def.get_soldier() > 2)? 2:def.get_soldier();
			int[] dice_offense = new int[dice_offense_num];
			int[] dice_defense = new int[dice_defense_num];
			Random rand = new Random();

			for(int i = 0; i < dice_offense_num; i++){
				dice_offense[i] = rand.nextInt(6)+1;
			}
			java.util.Arrays.sort(dice_offense);
			for(int i = 0; i < dice_defense_num; i++){
				dice_defense[i] = rand.nextInt(6)+1;
			}
			java.util.Arrays.sort(dice_defense);

			for(int i = 1; i <= dice_offense_num && i <= dice_defense_num; i++){
				if(dice_offense[dice_offense_num-i] > dice_defense[dice_defense_num-i]){
					off_sol_num--;
					off.kill_soldier(1);
				}
				else
					def.kill_soldier(1);
				if(off_sol_num <= 0 || def.get_soldier() <= 0)
					break;
			}

		}
			if(off_sol_num > 0){
				def.change_soldier(off_sol_num);
				return true;
			}
			else
				return false;
	}
}