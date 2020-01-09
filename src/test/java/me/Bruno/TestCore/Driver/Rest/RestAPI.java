package me.Bruno.TestCore.Driver.Rest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.JsonObject;

public class RestAPI {

    private URL url;
    private Method method;
    private Property[] properties;
    private String body;

    public RestAPI(Method method, String url) {
        this.method = method;
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public RestAPI setHeader(Property... propertie) {
        if (properties != null) {
            Property[] props = new Property[properties.length + propertie.length];
            int i = 0;
            for (Property p : properties) {
                props[i] = p;
            }
            for (Property p : propertie) {
                props[i] = p;
            }
            properties = props;
        } else {
            properties = propertie;
        }
        return this;
    }

    public RestAPI setUrlEncodedBody(Property... propertie) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Property p : propertie) {
            try {
                if (first) {
                    first = false;
                } else {
                    result.append("&");
                }
                result.append(URLEncoder.encode(p.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(p.getValue(), "UTF-8"));
            } catch (Exception e) {

            }
        }
        this.body = result.toString();
        return this;
    }

    public String prepareJsonBody(Property... propertie) {
        JsonObject json = new JsonObject();
        for (Property p : propertie) {
            json.addProperty(p.getKey(), p.getValue());
        }
        return json.toString();
    }

    public RestAPI setJsonBody(String body) {
        this.body = body;
        return this;
    }

    public Response execute() {
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            con.setRequestMethod(method.name().toUpperCase());
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        for (Property p : properties) {
            con.setRequestProperty(p.getKey(), p.getValue());
        }

        if (method == Method.POST) {
            con.setDoOutput(true);
            OutputStream os = null;
            try {
                os = con.getOutputStream();
                os.write(body != null ? body.getBytes() : "".getBytes());
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int code = 0;
        String message = null;
        BufferedReader response = null;

        try {
            code = con.getResponseCode();
            message = con.getResponseMessage();
            InputStream input = con.getInputStream();
            InputStreamReader inputReader = new InputStreamReader(input);
            response = new BufferedReader(inputReader);
            // response = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Response(code, message, response);
    }

    

}
