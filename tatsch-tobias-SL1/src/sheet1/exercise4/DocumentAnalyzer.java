package sheet1.exercise4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import java.util.TreeMap;

public class DocumentAnalyzer {

	// Map with Collection Frequencies cf
	public static Map<String, Integer> cfs = new TreeMap<String, Integer>();

	// Map with Document Frequencies df
	public static Map<String, Integer> dfs = new TreeMap<String, Integer>();

	public static void main(String[] args) {

		String path2txtfile = "data/collection.txt";
		String[] documents = CollectionMaker.readDocuments(path2txtfile);

		// iterate over docs
		for (int docID = 0; docID < documents.length; docID++) {

			// see exercise 4.2
			Map<String, Integer> termfreqmap = computeTermFreqs(documents[docID]);

			// see exercise 4.3
			updateCFsAndDFs(termfreqmap);
		}

		StringBuilder dfbuilder = new StringBuilder();
		StringBuilder cfbuilder = new StringBuilder();

		// printout DFs
		System.out.println("DFs");
		for (Map.Entry<String, Integer> entry : dfs.entrySet()) {
			dfbuilder.append(entry.getKey() + "," + entry.getValue() + "\n");
		}

		// printout CFs
		System.out.println("CFs");
		for (Map.Entry<String, Integer> entry : cfs.entrySet()) {
			cfbuilder.append(entry.getKey() + "," + entry.getValue() + "\n");
		}

		// write cfs.csv and dfs.csv
		try {
			FileWriter fwc = new FileWriter(new File("cfs.csv"));
			FileWriter fwd = new FileWriter(new File("dfs.csv"));

			fwc.write(cfbuilder.toString());
			fwd.write(dfbuilder.toString());

			fwc.close();
			fwd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Exercise 4.2
	public static Map<String, Integer> computeTermFreqs(String documentText) {

		// TODO Your implementation should go here!
		return null;
	}

	// Exercise 4.3
	public static void updateCFsAndDFs(Map<String, Integer> termfreqmap) {
		
		// TODO Your implementation should go here!
	}

}
