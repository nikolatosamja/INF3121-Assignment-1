package hangman;

import java.util.Random;
import java.util.Scanner;

//Game class
public class Game {
	private static final String[] wordForGuesing = { "computer", "programmer",
			"software", "debugger", "compiler", "developer", "algorithm",
			"array", "method", "variable" };

	private String guesWord;
	private StringBuffer dashedWord;
	private FileReadWriter filerw;

	//Game start method
	public Game(boolean autoStart) {
		guesWord = getRandWord();
		dashedWord = getW(guesWord);
		filerw = new FileReadWriter();
		if(autoStart) {
			displayMenu();
		}
	}

	//Random word method
	private String getRandWord() {
		Random rand = new Random();
		return wordForGuesing[rand.nextInt(9)];
	}

	//Menu display method
	public void displayMenu() {
		System.out.println("Welcome to �Hangman� game. Please, try to guess my secret word.\n"
						+ "Use 'TOP' to view the top scoreboard, 'RESTART' to start a new game,"
						+ "'HELP' to cheat and 'EXIT' to quit the game.");

		findLetterAndPrintIt();
	}

	//This method prompts us with a letter
	private void findLetterAndPrintIt() {
		boolean isHelpUsed = false;
		String letter = "";
		StringBuffer dashBuff = new StringBuffer(dashedWord);
		int mistakes = 0;

		do {
			System.out.println("The secret word is: " + printDashes(dashBuff));
			System.out.println("DEBUG " + guesWord);
			do {
				System.out.println("Enter your gues(1 letter alowed): ");
				Scanner input = new Scanner(System.in);
				letter = input.next();

				if (letter.equals(Command.help.toString())) {
					isHelpUsed = true;
					int i = 0, j = 0;
					while (j < 1) {
						if (dashBuff.charAt(i) == '_') {
							dashBuff.setCharAt(i, guesWord.charAt(i));
							++j;
						}
						++i;
					}
					System.out.println("The secret word is: "
							+ printDashes(dashBuff));
				}// end if
				menu(letter);

			} while (!letter.matches("[a-z]"));

			int counter = 0;
			for (int i = 0; i < guesWord.length(); i++) {
				String currentLetter = Character.toString(guesWord.charAt(i));
				if (letter.equals(currentLetter)) {
					
					{
					
					++counter;
					}
					dashBuff.setCharAt(i, letter.charAt(0));
				}
			}
			//If given letter doesn't exist in the word
			if (counter == 0) {
				++mistakes;
				{
					System.out.printf(
							"Sorry! There are no unrevealed letters \'%s\'. \n",
							letter);
				}
			} else {
				System.out.printf("Good job! You revealed %d letter(s).\n",
						counter);
			}//end if/else

		} while (!dashBuff.toString().equals(guesWord));
		//If we didn't use help:
		if (isHelpUsed == false) {
			System.out.println("You won with " + mistakes + " mistake(s).");
			System.out.println("The secret word is: " + printDashes(dashBuff));

			System.out
					.println("Please enter your name for the top scoreboard:");
			Scanner input = new Scanner(System.in);
			String playerName = input.next();

			{
				filerw.openFileToWite();
				filerw.addRecords(mistakes, playerName);
				filerw.closeFileFromWriting();
				filerw.openFiletoRead();
				filerw.readRecords();
				filerw.closeFileFromReading();
				filerw.printAndSortScoreBoard();
			}

		//If we used help:
		} else {
			System.out
					.println("You won with "
							+ mistakes
							+ " mistake(s). but you have cheated. You are not allowed to enter into the scoreboard.");
			System.out.println("The secret word is: " + printDashes(dashBuff));
		}
		
		// restart the game
		new Game(true);
		
	}// end method

	/*This method starts the menu, and waits for us to
	give a command, or a letter*/
	private void menu(String letter) {
		if (letter.equals(Command.restart.toString())) {
			new Game(true);
		} else {
			if (letter.equals(Command.top.toString())) {
				filerw.openFiletoRead();
				filerw.readRecords();
				filerw.closeFileFromReading();
				filerw.printAndSortScoreBoard();
				new Game(true);
			} else {
				if (letter.equals(Command.exit.toString())) {
					System.exit(1);
				}
			}
		}
	}

	private StringBuffer getW(String word) {
		StringBuffer dashes = new StringBuffer("");
		for (int i = 0; i < word.length(); i++) {
			dashes.append("_");
		}
		return dashes;
	}

	private String printDashes(StringBuffer word) {
		String toDashes = "";
		for (int i = 0; i < word.length(); i++) {
			toDashes += (" " + word.charAt(i));
		}
		return toDashes;

	}
}