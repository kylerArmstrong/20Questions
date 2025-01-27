import java.io.PrintStream;
import java.util.Scanner;

// This is a starter file for QuestionsGame.
//
// You should delete this comment and replace it with your class
// header comment.

public class QuestionsGame {
	// Your code here
	public QuestionNode overallNode;

	private static class QuestionNode {
		// Your code here
		public Object data;
		public QuestionNode left;
		public QuestionNode right;

		public QuestionNode(String d) {
			this(d, null, null);
		}

		public QuestionNode(String d, QuestionNode l, QuestionNode r) {
			data = d;
			left = l;
			right = r;
		}
	}

	public QuestionsGame(String object) {
		overallNode = new QuestionNode(object);
	}

	public QuestionsGame(Scanner input) {

	}

	public void saveQuestions(PrintStream output) {

	}

	public void play() {

	}
	
   
}