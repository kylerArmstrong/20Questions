//Benjamin Householder
//LinkedIntList
//To create an integer linked list
public class LinkedIntList {
	ListNode front;

	public LinkedIntList() {
		front = null;
	}

	public LinkedIntList(int value) {
		front = new ListNode(value);
	}

	public void add(int value) {
		// Adds a new value to the end
		if (front == null) {
			// creating a new if empty
			front = new ListNode(value);
		} else {
			// finding end node
			ListNode current = front;
			while (current.next != null) {
				current = front.next;
			}
			// setting end node next to value
			current.next = new ListNode(value);
		}
	}

	public void add(int index, int value) {
		ListNode current = front;
		ListNode previous = null;
		// finding desired node
		for (int pos = 0; pos < index; pos++) {
			previous = current;
			if (index < 0 || current.next == null) {
				// if at end
				throw new IndexOutOfBoundsException("Reached end of Linked List");
			}
			current = current.next;
		}
		// creating new node
		ListNode newNode = new ListNode(value, current);
		if (previous != null) {
			// splicing in new node
			previous.next = newNode;
		} else {
			// placing node at front if index 0
			front = newNode;
		}
	}

	public int get(int index) {
		ListNode current = front;
		// looping through to desired index
		for (int pos = 0; pos <= index; pos++) {
			if (index < 0 || current.next == null && pos != index) {
				// if at end
				throw new IndexOutOfBoundsException("Reached end of Linked List");
			} else {
				// otherwise keep looking
				if (pos != index) {
					current = front.next;
				}
			}

		}
		// return the last value the loop was on
		return current.data;
	}

	public int indexOf(int value) {
		ListNode current = front;
		// loop through list
		for (int pos = 0; pos < size(); pos++) {
			if (current.data == value) {
				// check each value to see if it matches
				return pos;
			}
			current = current.next;

		}
		// return -1 if loop ends and value never found
		return -1;
	}

	public int remove(int index) {
		ListNode current = front;
		ListNode previous = null;
		// check to see if index in bounds
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Reached end of Linked List");
		}
		// loop through to desired index
		for (int pos = 0; pos < index; pos++) {
			previous = current;
			// grab previous and current nodes
			current = current.next;
		}

		// grabbing old data to return
		int toReturn = current.data;

		if (previous == null) {
			// if at front
			front = front.next;
		} else {
			// splicing the two ends together
			previous.next = current.next;
		}
		// returning removed data
		return toReturn;
	}

	public int size() {
		int size = 0;
		ListNode current = front;
		if (current == null) {
			// check if empty
			return 0;
		}
		// do-while to loop through until next is null
		do {
			size++;
			current = current.next;
		} while (current != null);
		// return counted size
		return size;
	}

	public String toString() {
		ListNode current = front;
		if (current == null) {
			return "empty";
		}
		String toReturn = "";

		// do-while to loop through until next is null
		do {
			if (toReturn != "") {
				// if not first
				toReturn += ", ";
			}
			// add info
			toReturn += current.data;
			// next element
			current = current.next;

		} while (current != null);

		// return string
		return toReturn;
	}

	public void clear() {
		front = null;
	}

	public void sort() {
		//Bubble sort for linkedlist
		if (front == null || front.next == null) {
			return; // List is empty or has only one element
		}

		boolean swapped;
		// do-while for each element
		do {
			swapped = false;
			ListNode current = front;
			ListNode previous = null;
			ListNode next = current.next;
			// iterate through remaining elements, swapping if it's greater
			while (next != null) {
				if (current.data > next.data) {
					// Swap current and next nodes if greater
					if (previous == null) {
						front = next;
					} else {
						previous.next = next;
					}
					current.next = next.next;
					next.next = current;
					// Grab previous and next for next nodes
					previous = next;
					next = current.next;

					swapped = true;
				} else {
					// if not greater, keep looking
					previous = current;
					current = next;
					next = next.next;
				}
			}
		} while (swapped);
	}

}
