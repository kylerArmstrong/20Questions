import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

// This is a starter file for QuestionsGame.
//
// You should delete this comment and replace it with your class
// header comment.

public class QuestionsGame {
	// Your code here
	public QuestionNode overallRoot;

	static class QuestionNode {
		// Your code here
		public String data;
		public Boolean isQuestion;
		public QuestionNode left;
		public QuestionNode right;

		public QuestionNode(String d) {
			this(d, null, null, true);
		}
		
		public QuestionNode(String d, Boolean q) {
			this(d, null, null, q);
		}
		
		public QuestionNode(String d, QuestionNode l, QuestionNode r, Boolean q) {
			data = d;
			left = l;
			right = r;
			isQuestion = q;
		}
	}

	public QuestionsGame(String object) {
		overallRoot = new QuestionNode(object);
	}

	public QuestionsGame(Scanner input) throws IOException {
		String current = input.next();
		current = input.next();
		overallRoot = new QuestionNode(current);
		current = input.next();
		while (current != null) {
			if (current.equals("Q:")){
				writeTree(input.next(), true);
			} else if (current.equals("A:")) {
				writeTree(input.next(), false);
			} else {
				throw new IOException("File formatting error");
			}
			
				
		}
	}

	private void writeTree(String data, Boolean isQuestion) {
		writeRecur(data, isQuestion, overallRoot);
	}
	
	private void writeRecur(String data, Boolean isQuestion, QuestionNode current) {
		if (current.isQuestion && current.left == null) {
			current.left = new QuestionNode(data, isQuestion);
		} else if (current.isQuestion && current.right == null) {
			current.right = new QuestionNode(data, isQuestion);
		} else if(current.isQuestion) {
			if(!current.left.isQuestion) {
				 writeRecur(data, isQuestion, current.left);
			}
			if(!current.right.isQuestion && !containsRecur(overallRoot, data)) {
				 writeRecur(data, isQuestion, current.right);
			}
		}
	}
	
	private boolean containsRecur(QuestionNode current, String str) {
		if ( current.data.equals(str)) {
			return true;
		} else if(current.left != null && current.left != null) {
			return false;
		} else {
			Boolean ret = false;
			ret = containsRecur(current.left, str);
			if (!ret) {
				ret = containsRecur(current.right, str);
			}
			return ret;
		}
		
	}
	
	public void saveQuestions(PrintStream output) {
		if(output == null)
		{
			throw new IllegalArgumentException("Invalid Printstream");
		}
		saveRecur(overallRoot, output);
	}
	
	private void saveRecur(QuestionNode current, PrintStream output) {
		if (current.isQuestion) {
			output.append("Q:");
		} else {
			output.append("A:");
		}
		output.append(current.data);
		if(current.left != null)
		{
			saveRecur(current.left, output);
		}
		if (current.right != null) {
			saveRecur(current.right, output);
		}
	}

	public void play() throws IOException {
		Scanner keyboard = new Scanner(System.in);
		
		QuestionNode finalAnsNode = playRecur(overallRoot, keyboard);
		String finalAnswer = finalAnsNode.data;
		System.out.println("Was you object: " + finalAnswer);
		String playerYN = keyboard.nextLine();//players yes or no response
		if(playerYN.trim().toLowerCase().startsWith("y"))
		{
			System.out.println("Computer wins");
		} 
		else
		{
			System.out.println("What object were you thinking of?");
			String playerObj = keyboard.nextLine();//player object
			System.out.println("How can I distinguish your object from the object before?");
			String playerQue = keyboard.nextLine();//player question
			System.out.println("Is your object a yes or no to that question?");
			String playerQueYN = keyboard.nextLine();//player question yes or no
			
			newQuestion(finalAnsNode, playerObj, playerQue, playerQueYN);
			saveQuestions(new PrintStream(new File("spec-questions.txt")));
		}
	}
	
	public QuestionNode playRecur(QuestionNode root, Scanner keyboard)
	{
		if(!root.isQuestion)
		{
			return root;
		}
		else
		{
			System.out.println(root.data);
			String response = keyboard.next();
			if(response.trim().toLowerCase().startsWith("y"))
			{
				System.out.println();
				return playRecur(root.left, keyboard);
			}
			else
			{
				System.out.println();
				return playRecur(root.right, keyboard);
			}
			
			
		}
	}
	
	private void newQuestion(QuestionNode finalAnsNode, String playerObj, String playerQue, String playerQueYN)
	{
		String oldAns = finalAnsNode.data;
		finalAnsNode = new QuestionNode(playerQue, true);
		if(playerQueYN.trim().toLowerCase().startsWith("y"))
		{
			finalAnsNode.left = new QuestionNode(playerObj, false);
			finalAnsNode.right = new QuestionNode(oldAns, false);
		}
		else
		{
			finalAnsNode.right = new QuestionNode(playerObj, false);
			finalAnsNode.left = new QuestionNode(oldAns, false);
		}
	}
   
}