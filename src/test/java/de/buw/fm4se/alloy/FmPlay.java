package de.buw.fm4se.alloy;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class FmPlay {
    // map for caching code from permalink
    private static Map<String, String> codeCache = new HashMap<String, String>();

    public static void main(String[] args) {
        String plink = "https://play.formal-methods.net/?check=SMT&p=ex-3-task-3";
        System.out.println(getCodeFromPermalink(plink));
    }

    /*
     * Returns the code from a permalink of FM playground
     * 
     * @param plink permalink
     * 
     * @return code of if the permalink is valid, otherwise "error"
     */
    public static String getCodeFromPermalink(String plink) {
        if (codeCache.containsKey(plink)) {
            return codeCache.get(plink);
        }
        String[] parts = plink.split("\\?");
        if (parts.length < 2) {
            return "error";
        }
        String apiurl = parts[0]+"api/permalink/?"+parts[1];
        try {
            // Create URL object
            URL url = new URL(apiurl);

            // Open a connection
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // Set the request method
            con.setRequestMethod("GET");

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(content.toString());
            String codeContent = jsonResponse.getString("code");
            codeCache.put(plink, codeContent);
            return codeContent;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }
}