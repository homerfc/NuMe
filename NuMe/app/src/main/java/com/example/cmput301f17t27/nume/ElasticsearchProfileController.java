package com.example.cmput301f17t27.nume;

/**
 * Created by xuan on 2017-11-11.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.SearchResult;

/**
 * Created by Matt on 2017-11-09.
 */

public class ElasticsearchProfileController {
    private static JestClient client;


    public static class addProfileTask extends AsyncTask<Profile, Void, Void> {
        @Override
        protected Void doInBackground(Profile... profiles) {
             verifySettings();


            for ( Profile profile : profiles) {
                String userName = profile.getUserName();
                Index index = new Index.Builder(profile).index("cmput301f17t27_nume").type("profile").build();
                try {
                    client.execute(index);
                } catch (Exception e) {
                    Log.i("Error", "Failed to add profile to Elasticsearch");
                    //e.printStackTrace();
                }
            }
            return null;
        }


    }

    public static class GetProfileTask extends AsyncTask<String, Void, ArrayList<Profile>> {
        @Override
        protected ArrayList<Profile> doInBackground(String... search_parameters) { //String defines the param type; ...means multiple params
            verifySettings();
            //Log.i("search_parameters",search_parameters[0]);  successfully return the right value
            ArrayList<Profile> profiles = new ArrayList<Profile>();


            if (search_parameters.length == 1) {
                String search_username = search_parameters[0];
                //Log.i("debugInfo",search_username); successfully return the parameters;
                String query = "{\"query\" : {\"match\" : { \"username\" : \"" + search_parameters[0] + "\" }}}";
                Log.i("query",query);
                Search search = new Search.Builder(query)
                        .addIndex("cmput301f17t27_nume")
                        .addType("profile").build();



                //broken since here: throw the NetworkOnMainThreadException
                try {
                    SearchResult result = client.execute(search);
                    Log.i("SearchresultInfo:",result.getJsonString());

                    if (result.isSucceeded()) {
                        List<Profile> foundProfiles = result.getSourceAsObjectList(Profile.class);

                        profiles.addAll(foundProfiles);
                    } else {
                        Log.i("Error", "Search Query Failed ");
                    }

                } catch (Exception e) {
                    //client.shutdownClient();
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                    Log.e("Error", "errror", e);
                }

            } else {
                Log.i("Test", "doInBackground: search_parameters!: " + search_parameters[0] + " Next 1:" + search_parameters[0]);

            }

            return profiles;
        }
    }


    private static void verifySettings() {
        if (client == null) {
                /*
                JestClientFactory factory = new JestClientFactory();
                factory.setDroidClientConfig(new DroidClientConfig
                        .Builder("http://cmput301.softwareprocess.es:8080")
                        .multiThreaded(true).maxTotalConnection(20).build());
                client = factory.getObject();
                */
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }





}