import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static String editingReport = "";

    public static void main(String[] args) throws Exception {
        for (;;) {
            System.out.println("1) Edit a web page");
            System.out.println("2) Print editing report");
            System.out.println("3) Quit");

            Scanner userInputScanner = new Scanner(System.in);
            String prompt = userInputScanner.nextLine();

            if (prompt.toLowerCase().equals("1")) {
                input();

            } else if (prompt.toLowerCase().equals("2")) {
                System.out.print(editingReport);

            } else if (prompt.toLowerCase().equals("3")) {
                System.exit(0);
            }
        }
    }

    public static void input() throws Exception {
        URL url = null;

        System.out.println("Please enter the URL you would like to edit:");

        Scanner userInputScanner = new Scanner(System.in);
        String urlString = userInputScanner.nextLine();

        try {
            url = new URL(urlString);

        } catch (MalformedURLException e) {
            System.err.println("URL invalid.");
            throw e;
        }

        try {
            Scanner siteScanner = new Scanner(url.openStream());
            String html = "";
            while (siteScanner.hasNextLine()) {
                html += siteScanner.nextLine();
            }

            // Find <title></title>
            siteScanner = new Scanner(html);
            siteScanner.useDelimiter("\\s*title>\\s*");
            String a = siteScanner.next();
            String title = siteScanner.next();
            String oldTitle = title;
            title = title.substring(0, title.length() - 2);

            // Find description
            siteScanner = new Scanner(html);
            siteScanner.useDelimiter("\\s*<meta name=\"description\" content=\"\\s*");
            String b = siteScanner.next();
            String description = siteScanner.next();
            String oldDescription = description;
            description = description.substring(0, description.indexOf("\""));

            // Find keywords
            siteScanner = new Scanner(html);
            siteScanner.useDelimiter("\\s*<meta name=\"keywords\" content=\"\\s*");
            String c = siteScanner.next();
            String keywords = siteScanner.next();
            String oldKeywords = keywords;
            keywords = keywords.substring(0, keywords.indexOf("\""));

            // Print it all out
            System.out.println("Title: " + title);
            System.out.println("Description: " + description);
            System.out.println("Keywords: " + keywords);

            // Edit prompt
            for (;;) {
                System.out.println();
                System.out.println("What would you like to edit?");

                String prompt = userInputScanner.nextLine();
                String oldValue = "";
                String newValue = "";
                if (prompt.toLowerCase().equals("title")) {
                    System.out.println("Please enter the new title.");
                    oldValue = title;
                    title = userInputScanner.nextLine();
                    newValue = title;

                } else if (prompt.toLowerCase().equals("description")) {
                    System.out.println("Please enter the new description.");
                    oldValue = description;
                    description = userInputScanner.nextLine();
                    newValue = description;

                } else if (prompt.toLowerCase().equals("keywords")) {
                    System.out.println("Please enter the new keywords.");
                    oldValue = keywords;
                    keywords = userInputScanner.nextLine();
                    newValue = keywords;

                } else {
                    break;
                }

                editingReport += urlString + "|" + title + "|" + oldValue + "|" + newValue + "\n";

                System.out.println("Would you like to continue editing?");
                prompt = userInputScanner.nextLine();

                if (!prompt.toLowerCase().equals("y") && !prompt.toLowerCase().equals("yes")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Your internet connection does not seem to be working. Try again later.");
            throw e;
        }
    }

}
