import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class BookApp {
    public static JSONObject getBookData(String bookTitle) {
        String urlString = "https://www.googleapis.com/books/v1/volumes?q=" + bookTitle.replace(" ", "+");
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if (conn.getResponseCode() != 200) {
                return null;
            }
            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder resultJson = new StringBuilder();
            while (scanner.hasNext()) {
                resultJson.append(scanner.nextLine());
            }
            scanner.close();
            conn.disconnect();

            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(resultJson.toString());
            JSONArray items = (JSONArray) jsonResponse.get("items");
            if (items == null || items.isEmpty()) {
                return null;
            }
            JSONObject book = (JSONObject) ((JSONObject) items.get(0)).get("volumeInfo");
            JSONObject bookData = new JSONObject();
            bookData.put("title", book.get("title"));
            bookData.put("author", ((JSONArray) book.get("authors")).get(0));
            bookData.put("genre", book.get("categories") != null ? ((JSONArray) book.get("categories")).get(0) : "Unknown");
            bookData.put("year", book.get("publishedDate"));
            bookData.put("pages", book.get("pageCount"));
            bookData.put("cover", ((JSONObject) ((JSONObject) book.get("imageLinks"))).get("thumbnail"));
            return bookData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
