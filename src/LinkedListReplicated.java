/*
 * LinkedListReplicated.java
 * 
 */

import java.io.File;
import java.io.IOException;

public class LinkedListReplicated {
	static LinkedIntList list;
	static LinkedIntList list2;
	public static void print(Object j) {
		System.out.println(j);
	}
	
	public static void printAll() {
		//print("PrintAll");
		print("list: "+list);
		print("lis2: "+list2);
		//print("END");
	}
	
	public static void main(String s[]) {

		// Students create 2 LinkedIntList objects.
		// a) Uses the default constructor
		// b) Uses the the construtor that allows an integer value to be passed in.
		list = new LinkedIntList();
		list2 = new LinkedIntList(1);

		// Test out each of your various methods (add, insert, get, indexOf, remove,
		// size, toString))
		// using either of the instantiated LinkedIntList objects
		
		printAll();
		list.add(1);
		printAll();
		list.add(2);
		list2.add(2);
		printAll();
		print(list.get(0));
		print(list2.get(1));
		list.add(0, 3);
		printAll();
		list2.add(1, 69);
		list2.add(1, 420);
		list.add(1, 100);
		printAll();
		print(list.size());
		print(list2.size());
		print(list.indexOf(100));
		print(list2.indexOf(69));
		print(list.remove(1));
		print(list2.remove(2));
		printAll();
		print(list.indexOf(100));
		print(list2.indexOf(69));
		print(list.size());
		print(list2.size());
		list.sort();
		list2.sort();
		printAll();
		list.clear();
		list2.clear();
		printAll();
	}
}
