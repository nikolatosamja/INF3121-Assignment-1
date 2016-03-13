package hangman;
import java.io.Serializable;

//Player class
public class Players implements Serializable{
	
	private String name;
	private int scores;
	//Constructor
	public Players(String name, int scores) {
		this.name = name;
		this.scores = scores;
	}

	public String getName() {
		return name;
	}
	public int getScores() {
		return scores;
	}

}