package problem1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlProcessor {
    public static void extractBookInfo(String filename) {
        // FILL IN CODE
        Pattern p = Pattern.compile("<p>([^,]*),(\\s*)(.*?)<\\/p>");
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            //System.out.println(sb.toString());
            Matcher m = p.matcher(sb.toString());
            while (m.find())
                System.out.println(m.group(1) + " " + m.group(3));
        }
        catch (IOException e) {
            System.out.println(e);
        }


    }

    public static void main(String[] args) {
        extractBookInfo("books.html");
    }
}
