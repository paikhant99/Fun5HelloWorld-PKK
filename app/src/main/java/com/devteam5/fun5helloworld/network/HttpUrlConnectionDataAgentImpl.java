package com.devteam5.fun5helloworld.network;

import android.os.AsyncTask;
import android.util.Log;

import com.devteam5.fun5helloworld.events.ApiErrorEvent;
import com.devteam5.fun5helloworld.events.SuccessGetNewsEvent;
import com.devteam5.fun5helloworld.network.responses.GetNewsResponse;
import com.devteam5.fun5helloworld.utils.MMNewsConstants;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by paikhantko on 6/10/18.
 */

public class HttpUrlConnectionDataAgentImpl implements NewsDataAgent {

    private static HttpUrlConnectionDataAgentImpl objInstance;

    private HttpUrlConnectionDataAgentImpl() {
    }

    public static HttpUrlConnectionDataAgentImpl getObjInstance() {
        if (objInstance == null) {
            objInstance = new HttpUrlConnectionDataAgentImpl();
        }
        return objInstance;
    }


    @Override
    public void loadNewsList(final int page, final String accessToken,boolean isForceRefresh) {
        NetworkCallTask mnetworkCallTask=new NetworkCallTask(accessToken,page);
        mnetworkCallTask.execute();
    }


    private static class NetworkCallTask extends AsyncTask<Void, Void, String> {

        String maccessToken;
        int mpage;

        public NetworkCallTask(String accessToken,int page) {
            this.maccessToken=accessToken;
            this.mpage=page;
        }

        @Override
        protected String doInBackground(Void... voids) {
            URL url;
            BufferedReader reader = null;
            StringBuilder stringBuilder;
            try {
                url = new URL(MMNewsConstants.API_BASE_URL + MMNewsConstants.GET_NEWS);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");

                connection.setReadTimeout(15 * 1000);

                connection.setDoInput(true);
                connection.setDoOutput(true);

                List<NameValuePair> params = new ArrayList<>(); //6.
                params.add(new BasicNameValuePair(MMNewsConstants.PARAM_ACCESS_TOKEN, maccessToken));
                params.add(new BasicNameValuePair(MMNewsConstants.PARAM_PAGE, String.valueOf(mpage)));

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(getQuery(params));
                writer.flush();
                writer.close();
                outputStream.close();

                connection.connect(); //7.

                // read the output from the server
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); //8.
                stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                String responseString = stringBuilder.toString(); //9.

                return responseString;

            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String responseString) {
            super.onPostExecute(responseString);
            Gson gson = new Gson();
            GetNewsResponse getNewsResponse = gson.fromJson(responseString, GetNewsResponse.class);
            Log.d("OnPostExecute", "News List Size : " + getNewsResponse.getMmNews().size());

            if (getNewsResponse.isResponseOK()) {
                SuccessGetNewsEvent event = new SuccessGetNewsEvent(getNewsResponse.getMmNews());
                EventBus.getDefault().post(event);
            } else {
                ApiErrorEvent errorEvent = new ApiErrorEvent(getNewsResponse.getMessage());
                EventBus.getDefault().post(errorEvent);
            }
        }

        private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();
            boolean first = true;

            for (NameValuePair pair : params) {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            }

            return result.toString();
        }
    }
}
