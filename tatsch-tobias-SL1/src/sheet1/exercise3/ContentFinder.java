package sheet1.exercise3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

public class ContentFinder {

    // Character indicating a Tag
    public static final String TAG_INDICATOR = "#";

    /*
     * Content of a HTML file with tags replaced and preprocessing being done.
     */
    public static String[] content = null;

    // Main method for testing
    public static void main(String[] args) throws IOException {

        ContentFinder contentFinder = new ContentFinder();

        // Part 3.1
        String text = contentFinder
                .extractBody("http://www.uni-bamberg.de/minf/leistungen/studium/bonuspunkte/");
        System.out.println(text);

        // Part 3.2
        text = contentFinder.preprocessBody(text);
        System.out.println(text);

        // Part 3.3
        String bitstring = contentFinder.createBitString(text);
        System.out.println(bitstring);

        // Part 3.4
        int[] result = contentFinder.identifyMainText(bitstring);

        if (result == null) {
            throw new RuntimeException("Work on the TODOs!");
        }

        // print the result
        System.out.println("Result:");
        System.out.println("left = " + result[0] + " right = " + result[1]
                + " maxsum:" + result[2]);

        for (int i = result[0]; i <= result[1]; i++) {
            System.out.print(content[i] + " ");
        }
    }

    // Part 3.1 (see exercise)
    public String extractBody(String urlstring) throws IOException {
        String htmlPage = readHtmlPage(urlstring);
        String body = extractBodyFrom(htmlPage);
        String cleanedBody = cleanBody(body);

        return cleanedBody;
    }

    private String readHtmlPage(String urlstring) throws IOException, MalformedURLException {
        URL oracle = new URL(urlstring);
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                yc.getInputStream()));
        String inputLine;
        String htmlPage = new String();
        while ((inputLine = in.readLine()) != null) {
            htmlPage += inputLine;
        }
        in.close();
        return htmlPage;
    }

    private String extractBodyFrom(String htmlPage) {
        int startIndex = htmlPage.indexOf("<body>") + "<body>".length();
        int endIndex = htmlPage.indexOf("</body>");
        return StringUtils.substring(htmlPage, startIndex, endIndex);
    }

    private String cleanBody(String body) {
        return StringEscapeUtils.unescapeHtml4(body);
    }

    // Part 3.2 (see exercise)
    public String preprocessBody(String text) {
        String cleanText = removeScriptTags(text);
        cleanText = removeTabAndLineBreaks(cleanText);
        cleanText = removeHtmlTags(cleanText);
        cleanText = removePoints(cleanText);
        cleanText = removeMulipleWhitespaces(cleanText);
        cleanText = cleanText.trim();
        return cleanText;
    }

    // Part 3.3 (see exercise)
    public String createBitString(String input) {
        content = input.split(" ");
        String bits = new String();
        for (int i = 0; i < content.length; i++) {
            String bitified = content[i];
            if (bitified.equals(TAG_INDICATOR)) {
                bitified = "1";
            } else {
                bitified = "0";
            }
            bits += bitified;
        }

        return bits;
    }

    // Part 3.4 in O(N^3) (see exercise)
    public int[] identifyMainText(String bits) {
        char token = "0".charAt(0);
        char tag = "1".charAt(0);
        int startIndex = 0;
        int endIndex = 0;
        int maxCosts = 0;
        double ratioBetweenTagsAndTokens = calculateRatioBetweenTagsAndTokens(bits);
        
        for (int L = 0; L < bits.length(); L++) { //L
            char bit = bits.charAt(L);
            if (bit == token) {
                for (int U = L; U < bits.length(); U++) { //U
                    int countedTokens = 0;
                    int countedTags = 1;
                    char nextBit = bits.charAt(U);
                    if (nextBit == token) {
                        int I = 0;
                        for (I = L; I < U; I++) { //I
                            char nextNextBit = bits.charAt(I);
                            if (nextNextBit == token) {
                                countedTokens++;
                            } else if(nextNextBit == tag && ((double)countedTags / countedTokens) < ratioBetweenTagsAndTokens){
                                countedTokens++;
                                countedTags++;
                            }else {
                                break;
                            }
                        }
                        if (countedTokens > maxCosts) {
                            maxCosts = countedTokens;
                            startIndex = L;
                            endIndex = I - 1;
                        }
                    }
                }

            }
        }
        return new int[]{startIndex, endIndex, maxCosts};
    }

    public String removeScriptTags(String text) {
        return replaceByPattern(text, "(<\\s*?script.*?>)(.*?)(<\\s*?/\\s*?script.*?>)", " ");

    }

    private String removeTabAndLineBreaks(String text) {
        return replaceByPattern(text, "\\n|\\t|\\n\\r", " ");
    }

    private String removeMulipleWhitespaces(String text) {
        return replaceByPattern(text, "\\s{2,}", " ");
    }

    private String removeHtmlTags(String text) {
        return replaceByPattern(text, "<.*?>", " # ");
    }

    private String removePoints(String cleanText) {
        return replaceByPattern(cleanText, "\\.*", "");
    }

    private String replaceByPattern(String text, String regex, String replacement) {
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(text);
        return m.replaceAll(replacement);
    }

    private double calculateRatioBetweenTagsAndTokens(String bits) {
        int numberOfTag = calculate(bits, "1");
        return (double) ((double)numberOfTag / bits.length()) / 3;
    }

    private int calculate(String bits, String expectedBit) {
        int counter = 0;
        for (int i = 0; i < bits.length(); i++) {
            char bit = bits.charAt(i);
            if(bit == expectedBit.charAt(0)){
                counter++;
            }
        }
        return counter;
    }
}
