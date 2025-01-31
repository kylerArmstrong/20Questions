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

	public QuestionsGame(Scanner input) throws IOException { // spec-questions.txt
		String current = input.nextLine();
		current = input.nextLine();
		overallRoot = new QuestionNode(current);
		System.out.println(current);
		current = input.nextLine();
		while (current != null) {
			System.out.println(current);
			if (current.equals("Q:")) {
				String next = input.nextLine();
				System.out.println("ISQ " + next);
				writeTree(next, true);
			} else if (current.equals("A:")) {
				String next = input.nextLine();
				System.out.println("ISA " + next);
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
		QuestionNode node = writeRecur(data, isQuestion, overallRoot);
		if (node.left == null) {
			node.left = new QuestionNode(data, isQuestion);
		} else {
			node.right = new QuestionNode(data, isQuestion);
		}
	}

	private QuestionNode writeRecur(String data, Boolean isQuestion, QuestionNode current) {
		// needs stop code traversing should work
		// traverse is just if can go left and it question go left then same with right
		if (current.left == null) {
			return current;
		} else if (current.right == null && leftSideIsDone())
			if (current.left != null) {
				if (current.left.isQuestion) {
					return writeRecur(data, isQuestion, current.left);
				}
			}
		if (current.right != null) {
			if (current.right.isQuestion) {
				return writeRecur(data, isQuestion, current.right);
			}
		}

		return null;

	}

	private boolean leftSideIsDone() {
		return leftSizeRecur(overallRoot.left);
	}

	private boolean leftSizeRecur(QuestionNode current) {
		//check if the left size is complete with answers
		if (current.left != null) {
			if (current.left.isQuestion) {
				return leftSizeRecur(current.left);
			}
		}
		if (current.right != null) {
			if (current.right.isQuestion) {
				return leftSizeRecur(current.right);
			}
		}
		return false;
	}

	private boolean containsRecur(QuestionNode current, String str) {
		if (current.data.equals(str)) {
			return true;
		} else if (current.left != null && current.left != null) {
			return false;
		} else {
			Boolean ret = false;
			if (current.left != null) {
				ret = containsRecur(current.left, str);

			}
			if (!ret && current.right != null) {
				ret = containsRecur(current.right, str);
			}
			return ret;
		}

	}

	public void saveQuestions(PrintStream output) {
		if (output == null) {
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
		if (current.left != null) {
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
			saveQuestions(new PrintStream(new File("spec-questions.txt")));
		}
	}

	public QuestionNode playRecur(QuestionNode root, Scanner keyboard) {
		if (!root.isQuestion) {
			return root;
		} else {
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
		String oldAns = finalAnsNode.data;
		finalAnsNode = new QuestionNode(playerQue, true);
		if (playerQueYN.trim().toLowerCase().startsWith("y")) {
			finalAnsNode.left = new QuestionNode(playerObj, false);
			finalAnsNode.right = new QuestionNode(oldAns, false);
		} else {
			finalAnsNode.right = new QuestionNode(playerObj, false);
			finalAnsNode.left = new QuestionNode(oldAns, false);
		}
	}

}