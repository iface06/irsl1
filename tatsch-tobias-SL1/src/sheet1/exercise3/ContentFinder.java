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
        //test: "1111000011111000001000001111110011" startIndex: 13; endIndex: 24
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
        cleanText = removeMulipleWhitespaces(cleanText);
        cleanText = removePoints(cleanText);
        return cleanText;
    }

    // Part 3.3 (see exercise)
    public String createBitString(String input) {
        content = input.split(" ");
        String bits = new String();
        for (int i = 0; i < content.length; i++) {
            String bitified = content[i];
            bitified = replaceByPattern(bitified, "\\#", "1");
            bitified = replaceByPattern(bitified, "[^1]", "0");
            bits += bitified;
        }

        return bits;
    }

    // Part 3.4 in O(N^3) (see exercise)
    public int[] identifyMainText(String bits) {
        char tag = "1".charAt(0);
        char token = "0".charAt(0);
        int startIndex = 0;
        int endIndex = 0;
        int tolarance = 5;
        int max = 0;
        for (int L = 0; L < bits.length(); L++) { //L
            char bit = bits.charAt(L);
            if (bit == token) {
                for (int U = L + 1; U < bits.length(); U++) { //U
                    int countTokens = 0;
                    char nextBit = bits.charAt(U);
                    if (nextBit == token) {
                        int I = 0;
                        for (I = L + 1; I < U; I++) {
                            char nextNextBit = bits.charAt(I);
                            if (nextNextBit == token) {
                                countTokens++;
                            }
                        }
                        if (countTokens > max) {
                            max = countTokens;
                            startIndex = I;
                        }
                    }
                }

            }
        }


        return new int[]{startIndex, startIndex + max, max};
    }

    public String removeScriptTags(String text) {
        return replaceByPattern(text, "(<\\s*?script.*?>)(.*?)(<\\s*?/\\s*?script.*?>)", " ");

    }

    private String removeTabAndLineBreaks(String text) {
        return replaceByPattern(text, "\\n|\\t", " ");
    }

    private String removeMulipleWhitespaces(String text) {
        return replaceByPattern(text, " {2,}", " ");
    }

    private String removeHtmlTags(String text) {
        return replaceByPattern(text, "<.*?>", "#");
    }

    private String removePoints(String cleanText) {
        return replaceByPattern(cleanText, "\\.*", "");
    }

    private String replaceByPattern(String text, String regex, String replacement) {
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(text);
        return m.replaceAll(replacement);
    }
}
