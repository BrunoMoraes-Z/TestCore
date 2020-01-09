package me.Bruno.TestCore.Driver.Rest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

public class Response {

    private int code;
    private String message;
    private BufferedReader response;

    public Response(int code, String message, BufferedReader response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public BufferedReader getBufferedMessage() {
        return this.response;
    }

    public Json getJson() {
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = response.readLine()) != null && !line.isEmpty()) {
                sb.append(line.trim());
            }
            return new Json(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public class Json {

        private String json;

        public Json(String json) {
            this.json = json;
        }

        public JSONObject getObject() {
            return new JSONObject(json);
        }

        public JSONArray getArray() {
            return new JSONArray(json);
        }

    }

}
