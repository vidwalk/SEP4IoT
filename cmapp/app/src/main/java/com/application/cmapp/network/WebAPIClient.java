package com.application.cmapp.network;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class WebAPIClient {

    private String jsonString;
    //Make HTTP request to URL and return String to the async task;
    private String returnJsonString(String s)
    {
        return s;
    }

    public void setJsonString(String s)
    {
        jsonString = s;
    }

    public String makePutHttpRequest(URL url) throws IOException, JSONException {
        String jsonResponse = "Failed!";
        if (url == null)
            return jsonResponse;

        HttpURLConnection urlConnection = null;
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;

        DataOutputStream dataOutputStream = null;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Message", "open");

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("PUT");

            urlConnection.setReadTimeout(10000); //miliseconds
            urlConnection.setConnectTimeout(15000); //miliseconds
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Content-length", jsonObject.toString().getBytes().length + "");


            outputStream = urlConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            dataOutputStream = new DataOutputStream(outputStream);





            //dataOutputStream.writeBytes(jsonObject.toString());
            // outputStreamWriter.write(jsonObject.toString());
            outputStreamWriter.write(jsonObject.toString());
            outputStreamWriter.close();


            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {


                Log.d("cacat", String.valueOf(urlConnection.getResponseCode()));
                Log.d("cacat", String.valueOf(urlConnection.getResponseMessage()));

             //   outputStreamWriter.flush();
                //dataOutputStream.flush();

                //outputStream.close();
              //  jsonResponse = "Success!";

            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (outputStreamWriter != null) {
                // function must handle java.io.IOException here
                outputStreamWriter.close();
            }
        }
        return jsonResponse;
    }



    public String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null)
            return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;




        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);//miliseconds
            urlConnection.setConnectTimeout(15000); //miliseconds
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: Handle the exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    public String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();

            while (line != null)
            {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();

    }

    public class ReadingAsyncTask extends AsyncTask<String, String, String> implements ClientCallback{

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            String jsonResponse = "";
            try {
                url = new URL(strings[0]);
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            return jsonResponse;
        }


        @Override
        protected void onPostExecute(String jsonString)
        {
            try {
                onResponseReceived(jsonString);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onResponseReceived(String jsonString) throws JSONException {

        }

        @Override
        public void onFailed() {

        }

        @Override
        public void onDataSent(String jsonString)
        {

        }
    }

    public class PutAsyncTask extends AsyncTask<String, String, String> implements ClientCallback{

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            String jsonResponse = "";
            try {
                url = new URL(strings[0]);
                jsonResponse = makePutHttpRequest(url);
            } catch (IOException | JSONException e)
            {
                e.printStackTrace();
            }
            return jsonResponse;
        }


        @Override
        protected void onPostExecute(String jsonString)
        {
            try {
                onResponseReceived(jsonString);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onResponseReceived(String jsonString) throws JSONException {

        }

        @Override
        public void onFailed() {

        }

        @Override
        public void onDataSent(String jsonString)
        {

        }
    }
}
