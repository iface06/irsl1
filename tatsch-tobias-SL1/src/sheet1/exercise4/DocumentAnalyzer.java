package sheet1.exercise4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.TreeMap;
import java.util.regex.Pattern;

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
        List<String> tokens = Arrays.asList(documentText.split("\\W{1,}"));
        Map<String, Integer> countedTokens = new HashMap<String, Integer>();
        for (String token : tokens) {
            String lowerCasedToken = token.toLowerCase();
            if(countedTokens.containsKey(lowerCasedToken)){
                Integer numberOfToken = countedTokens.get(lowerCasedToken) + 1;
                countedTokens.put(lowerCasedToken, numberOfToken);
            } else {
                countedTokens.put(lowerCasedToken, 1);
            }
        }
        System.out.println(countedTokens);
        return countedTokens;
    }

    // Exercise 4.3
    public static void updateCFsAndDFs(Map<String, Integer> termfreqmap) {
        for (String token : termfreqmap.keySet()) {
            if(dfs.containsKey(token)){
                Integer numberOfDocuments = dfs.get(token);
                dfs.put(token, numberOfDocuments + 1);
            } else {
                dfs.put(token, 1);
            }
            
            if(cfs.containsKey(token)){
                Integer numberOfTokens = termfreqmap.get(token);
                Integer totalNumberOfTokens = cfs.get(token);
                cfs.put(token, numberOfTokens + totalNumberOfTokens);
            } else {
                Integer numberOfTokens = termfreqmap.get(token);
                cfs.put(token, numberOfTokens);
            }
        }
    }
}
