import java.io.Serializable;

public class Test implements Serializable
{
	private static final long serialVersionUID = 5950169519310163575L;
	public int id;
	public Test(int i){
		this.id = i;
	}
	public int getId(){
		return this.id;
	}

	public String toString(){
		return "ID = "+ getId();
	}
}