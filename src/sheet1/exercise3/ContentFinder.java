package sheet1.exercise3;

import com.sun.org.apache.xerces.internal.impl.xs.identity.Selector;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
         Pattern p = Pattern.compile("(<\\s*?script.*?>)(.*?)(<\\s*?/\\s*?script.*?>)", Pattern.CASE_INSENSITIVE);
         Matcher m = p.matcher(text);
         return m.replaceAll(" ");
    }

    // Part 3.3 (see exercise)
    public String createBitString(String input) {
        
        String[] splitted = input.split(" ");
        String bitString = new String();
        for (int i = 0; i < splitted.length; i++) {
            bitString += splitted[i].getBytes();
            
        }
        // TODO Your implementation should go here!
        return bitString;
    }

    // Part 3.4 in O(N^3) (see exercise)
    public int[] identifyMainText(String bitstring) {

        // TODO Your implementation should go here!
        return null;
    }


    
}
