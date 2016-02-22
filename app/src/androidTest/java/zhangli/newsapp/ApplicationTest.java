package zhangli.newsapp;

import android.app.Application;
import android.content.res.AssetManager;
import android.test.ApplicationTestCase;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import zhangli.newsapp.news.News;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


    public String readInPutStream(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    //----------------------------1.11---------------------------------
    public void test() throws IOException {
        AssetManager assetManager = getContext().getAssets();
        InputStream inputStream = assetManager.open("news.json");
        String newsStr = readInPutStream(inputStream);

        Gson gson =new Gson();
        News News =gson.fromJson(newsStr, News.class);

    }
}