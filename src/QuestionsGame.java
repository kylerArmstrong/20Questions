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

	private static class QuestionNode {
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
		
	}

	public void play() {
		Scanner keyboard = new Scanner(System.in);
		String finalAnswer = playRecur(overallRoot, keyboard);
		
	}
	
	public String playRecur(QuestionNode root, Scanner keyboard)
	{
		if(!root.isQuestion)
		{
			return root.data;
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
	
   
}