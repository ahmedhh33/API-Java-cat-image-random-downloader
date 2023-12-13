package org.example;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        try {
            // Send GET request
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/images/search")
                    .build();

            Response response = client.newCall(request).execute();

            // Parse JSON response
            String jsonResponse = response.body().string();
            Map<String, Object>[] data = gson.fromJson(jsonResponse, Map[].class);

            // Extract image URL
            String imageUrl = (String) data[0].get("url");

            // Download and save image in the current directory
            byte[] imageBytes = client.newCall(new Request.Builder().url(imageUrl).build()).execute().body().bytes();
            FileUtils.writeByteArrayToFile(new File("cat_image.jpg"), imageBytes);

            System.out.println("Cat image saved as cat_image.jpg in the current directory");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}