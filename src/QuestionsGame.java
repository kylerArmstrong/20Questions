import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

// This is a starter file for QuestionsGame.
//
// Bejnamin Houhseolder & Kyler Armstrong
// Methods to play a game of 20 quesions

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

	public QuestionsGame(Scanner input) throws IOException { // spec-questions.txt
		String current = input.nextLine();
		current = input.nextLine();
		overallRoot = new QuestionNode(current);
		//System.out.println(current);
		current = input.nextLine();
		while (current != null) {
			//System.out.println(current);
			if (current.equals("Q:")) {
				String next = input.nextLine();
				//ystem.out.println("ISQ " + next);
				writeTree(next, true);
			} else if (current.equals("A:")) {
				String next = input.nextLine();
				//System.out.println("ISA " + next);
				writeTree(next, false);
			} else {
				throw new IOException("File formatting error");
			}
			try {
				current = input.nextLine();
			} catch (Exception e) {
				current = null;
			}

		}
	}

	private void writeTree(String data, Boolean isQuestion) {
		if (overallRoot == null) {
			overallRoot = new QuestionNode(data, isQuestion);
		} else {
			writeRecur(data, isQuestion, overallRoot);
		}
	}

	private boolean writeRecur(String data, Boolean isQuestion, QuestionNode current) {
		if (!current.isQuestion) {
			return false; // Can't add to an answer node. This should never happen though
		}

		if (current.left == null) {
			// if left is empty; always add new node
			current.left = new QuestionNode(data, isQuestion);
			return true;
		}

		//check if left was written to and if so, don't write to right
		if (writeRecur(data, isQuestion, current.left)) {
			return true;
		}

		//if left was not written to, write to right
		if (current.right == null) {
			current.right = new QuestionNode(data, isQuestion);
			return true;
		}

		//keep going
		return writeRecur(data, isQuestion, current.right);
	}

	public void saveQuestions(PrintStream output) {
		if (output == null) {
			// throw if broken printstream
			throw new IllegalArgumentException("Invalid Printstream");
		}
		saveRecur(overallRoot, output);
	}

	private void saveRecur(QuestionNode current, PrintStream output) {
		//print to given printstream
		if (current.isQuestion) {
			//append Q and A headers
			output.append("Q:\n");
		} else {
			output.append("A:\n");
		}
		//append current data
		output.append(current.data+"\n");
		if (current.left != null) {
			// if left exists keep going
			saveRecur(current.left, output);
		}
		if (current.right != null) {
			//same for right
			saveRecur(current.right, output);
		}
	}

	public void play() throws IOException {
		Scanner keyboard = new Scanner(System.in);

		QuestionNode finalAnsNode = playRecur(overallRoot, keyboard);
		String finalAnswer = finalAnsNode.data;
		System.out.println("Was your object: " + finalAnswer+" ?");
		keyboard.nextLine();
		String playerYN = keyboard.nextLine();// players yes or no response
		if (playerYN.trim().toLowerCase().startsWith("y")) {
			System.out.println("Computer wins");
		} else {
			System.out.println("What object were you thinking of?");
			String playerObj = keyboard.nextLine();// player object
			System.out.println("How can I distinguish your object from the object before?");
			String playerQue = keyboard.nextLine();// player question
			System.out.println("Is your object a yes or no to that question?");
			String playerQueYN = keyboard.nextLine();// player question yes or no

			newQuestion(finalAnsNode, playerObj, playerQue, playerQueYN);
			System.out.println("Where would you like to save the new questions? ");
			String filename = keyboard.nextLine().trim();

			/* Create the Questions File if it doesn't exist */
			File questionsFile = new File(filename);
			if (!questionsFile.exists()) {
				questionsFile.createNewFile();
			}
			saveQuestions(new PrintStream(questionsFile));
		}
	}

	public QuestionNode playRecur(QuestionNode root, Scanner keyboard) {
		if (!root.isQuestion) {
			return root;
		} else {
			// ask question and wait for response, returning the last answered question
			System.out.println(root.data);
			String response = keyboard.next();
			if (response.trim().toLowerCase().startsWith("y")) {
				System.out.println();
				return playRecur(root.left, keyboard);
			} else {
				System.out.println();
				return playRecur(root.right, keyboard);
			}

		}
	}

	private void newQuestion(QuestionNode finalAnsNode, String playerObj, String playerQue, String playerQueYN) {
		//grab old answer and replace with new question
		String oldAns = finalAnsNode.data;
		finalAnsNode.data = playerQue;
		finalAnsNode.isQuestion = true;
		if (playerQueYN.trim().toLowerCase().startsWith("y")) {
			// if yes, left is new object, right is old object
			finalAnsNode.left = new QuestionNode(playerObj, false);
			finalAnsNode.right = new QuestionNode(oldAns, false);
		} else {
			finalAnsNode.right = new QuestionNode(playerObj, false);
			finalAnsNode.left = new QuestionNode(oldAns, false);
		}
	}

}
