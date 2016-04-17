public class Country {
	private int id;
	private int cont;
	public  int owner;
	private int soldier;

	public Country(){
		this.soldier = 0;
		this.owner = 1;
	}

	public void add_soldier(int i){
		this.soldier+=i;
		return;
	}

	public void kill_soldier(int i){
		this.soldier--;
		return;
	}

	public void change_soldier(int i){
		this.soldier -= i;
		return;
	}

	public void change_owner(int i){
		this.owner = i;
		return;
	}

	public int get_owner(){
		return this.owner;
	}

	public int get_cont(){
		return this.cont;
	}

	public int get_soldier(){
		return this.soldier;
	}

	public String toString(){
		return "ID = " + this.id + "Owner = " + this.owner;
	}
}