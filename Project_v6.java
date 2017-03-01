/*Shaun Shareed
* Steve Orsino
* 11/9/ 2016
* Project Version 5
*/
import java.util.Scanner;
import java.io.*;
import javax.swing.JOptionPane;
public class Project_v6 {
	public static void main(String[] args) throws IOException {
		// Declare variables
		String userName;
		char choice;
		int totalPoints = 0;
		
		final int SIZE = 10;
		String[] questionArray = new String[SIZE];
		String[] answerA = new String[SIZE];
		String[] answerB = new String[SIZE];
		String[] answerC = new String[SIZE];
		String[] answerD = new String[SIZE];
		String[] correctAnswer = new String[SIZE];
		int[] pointValue = new int[SIZE];
		
		String[] highNames = new String[3];
		int[] highScores = new int[3];
		
		//declare file object and scanner object to read from file
		File questions = new File("questions.txt");
		Scanner infile = new Scanner(questions);
		
		JOptionPane.showMessageDialog(null, "Welcome to the Java Quiz!!!");// Intro
		//Prompt user for name
		userName = JOptionPane.showInputDialog("Enter your name here: ");
		//prompt user for menu choice
		
		//Begin main do while loop to validate the menu choice and allow for multiple plays
		do {			
			//display menu and accept user's choice
			choice = DisplayMainMenu();
			
			//case statement to handle menu choice
			
			switch(choice) {
			case '1':
				DisplayRules();
				break;
			case '2':	
				//iterate through all questions with for loop
				for (int i = 0; i < 8; i++) {
					// store lines from file into variables				
					questionArray[i] = infile.nextLine();
					answerA[i] = infile.nextLine();
					answerB[i] = infile.nextLine();
					answerC[i] = infile.nextLine();
					answerD[i] = infile.nextLine();
					correctAnswer[i] = infile.nextLine();
					pointValue[i] = infile.nextInt();
					//eat the remaining newline char
					infile.nextLine();
					//if answer is correct, add point value to totalPoints, add zero if incorrect
					totalPoints += 	ProcessQuestion(questionArray[i], answerA[i], answerB[i],answerC[i] , answerD[i], correctAnswer[i], pointValue[i]);
					//display total score after every question
					DisplayScore(totalPoints);
				}
				infile.close();
				//display score
				JOptionPane.showMessageDialog(null, "Your total score is " + totalPoints +"!\nThanks for playing, " + userName +"!");
				//read in high score from file, create the file if it does not exist
				ReadInHighScores(highNames, highScores);
				//compare high score to the user's score. if users score is higher, overwrite the file with the new high score
				CompareScores(totalPoints, userName, highNames, highScores);
				UpdateHighScores(highNames, highScores);
				break;
			case '3':
				JOptionPane.showMessageDialog(null, "Thank you " + userName + "! Goodbye!!!!!!");
				break;
				default:
					JOptionPane.showMessageDialog(null, "You must choose 1 - 3");
							
			}
		} while (choice != '3');
			
	}

	//This Method prompts for and return the main Menu choice
	public static char DisplayMainMenu(){
		String menuChoice;
		menuChoice = JOptionPane.showInputDialog("1) See Rules\n2) Play Game\n3) Exit\nEnter Your Choice: ");
		char choice = menuChoice.charAt(0);
		return choice;
	}
	
	public static void DisplayRules() {
		//Display rules
		JOptionPane.showMessageDialog(null, "This will be a series of three java related questions.\nYou will be given the question and prompted for your choice. ");
	}
	//This function displays a question and possible answers to the user, displays if the answer is correct and returns the point value if answered correctly
	 public static int ProcessQuestion(String q1, String ansA, String ansB, String ansC, String ansD, String correctAns, int pointValue) {
		 String choiceInput;
		 do {//do while loop to validate the choice
				choiceInput	= JOptionPane.showInputDialog(q1 + "\n\t" + ansA + "\n\t" + ansB + "\n\t" + ansC + "\n\t" + ansD + "\n\tEnter your choice:");
				if(!(choiceInput.equals("a")||choiceInput.equals("b")||choiceInput.equals("c")||choiceInput.equals("d"))){//Alert user if the choice is invalid
					JOptionPane.showMessageDialog(null, "You must enter a through d");
				}
			} while (!(choiceInput.equals("a")||choiceInput.equals("b")||choiceInput.equals("c")||choiceInput.equals("d")));
			
			//decision structure to indicate to the user whether each answer is correct or incorrect
			if (choiceInput.equals(correctAns)) {
				JOptionPane.showMessageDialog(null,  "Yes, " + choiceInput + " is correct!");
				//Add point value to totalPoints
				 return pointValue;
			} else {
				JOptionPane.showMessageDialog(null,  "Sorry, " + choiceInput + " is incorrect");
				return 0;
			}		
	 }
	 //This function opens the high score and reads in the names and scores. 
	 public static void ReadInHighScores(String[] name, int[] score) throws IOException {
		 File file = new File("highscore.txt");
		 Scanner infile = new Scanner(file);
		 for (int i = 0; i < 3;i++) {
		 name[i] = infile.next();
		 score[i] = infile.nextInt();
		 //infile.nextLine();
		 }
		 infile.close();
	 }
	 //if the users score is higher than the high score, this function overwrites the file with the user score
	 public static void CompareScores(int userScore, String userName, String[] name, int score[]) {
		 String tempName;
		 int tempScore;
		 if (userScore > score[0]) {
			 tempScore = score[0];
			 tempName = name[0];
			 score[0] = userScore;
			 name[0] = userName;
			 score[2] = score[1];
			 name[2] = name[1];
			 score[1] = tempScore;
			 name[1] = tempName;
		 } else if (userScore > score[1]) {
			 tempScore = score[1];
			 tempName = name[1];
			 score[1] = userScore;
			 name[1] = userName;
			 score[2] = tempScore;
			 name[2] = tempName;
		 } else if (userScore > score[2]) {
			 score[2] = userScore;
			 name[2] = userName;
		 } else {
			 JOptionPane.showMessageDialog(null,  "Sorry, you do not have a high score.  Try again!");
		 }
	
	 }
	 //updates the highscore.txt file with the current name and score arrays
	 public static void UpdateHighScores(String[] highName, int[] highScore) throws IOException {
		 PrintWriter outfile = new PrintWriter("highscore.txt");
		 for (int i = 0; i < 3; i++) {
			 outfile.println(highName[i] + " " + highScore[i]);
		 }
		 outfile.close();
	 }
	 //Display the score
	 public static void DisplayScore(int score) {
		 JOptionPane.showMessageDialog(null ,"Your current score is " + score + ".");
	 }
	
}
