package sheet1.exercise4;

public class CollectionMaker {

	// total number of sonetts
	public static int numberOfSonnets = 154;

	// Main method for testing
	public static void main(String[] args) {
		String[] documents = readDocuments("data/collection.txt");

		System.out.println("Shakespeare's Sonnets");
		for (int i = 0; i < documents.length; i++) {
			System.out.println(i + "> " + documents[i]);
		}
	}

	// Exercise 4.1
	public static String[] readDocuments(String string) {
		
		//TODO Your implementation should go here!
		return null;
	}
}
