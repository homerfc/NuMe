package com.example.cmput301f17t27.nume;

/**
 * Created by xuan on 2017-11-11.
 */

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by Matt on 2017-11-09.
 */

public class ElasticsearchHabitEventController {
    private static JestClient client;


    public static class addHabitEventTask extends AsyncTask<HabitEvent, Void, Void> {
        @Override
        protected Void doInBackground(HabitEvent... HabitEvents) {
             verifySettings();


            for (HabitEvent habitEvents : HabitEvents) {
                //String userName = profile.getUserName();
                Index index = new Index.Builder(habitEvents).index("cmput301f17t27_nume").type("habitEvent").build();
                try {
                    client.execute(index);
                } catch (Exception e) {
                    Log.i("Error", "Failed to add habitEvent to Elasticsearch");
                    //e.printStackTrace();
                }
            }
            return null;
        }


    }

    public static class GetHabitEventTask extends AsyncTask<String, Void, ArrayList<HabitEvent>> {
        @Override
        protected ArrayList<HabitEvent> doInBackground(String... search_parameters) { //String defines the param type; ...means multiple params
            verifySettings();
            //Log.i("search_parameters",search_parameters[0]);  successfully return the right value
            ArrayList<HabitEvent> habitEvents = new ArrayList<HabitEvent>();


            if (search_parameters.length == 1) {
                String search_username = search_parameters[0];
                //Log.i("debugInfo",search_username); successfully return the parameters;
                //String query = "{\"query\" : {\"match\" : { \"username\" : \"" + search_parameters[0] + "\" }}}";
                //Log.i("query",query);
                String query = "";
                Search search = new Search.Builder(query)
                        .addIndex("cmput301f17t27_nume")
                        .addType("habitEvent").build();



                //broken since here: throw the NetworkOnMainThreadException
                try {
                    SearchResult result = client.execute(search);
                    //Log.i("SearchresultInfo:",result.getJsonString());

                    if (result.isSucceeded()) {
                        List<HabitEvent> foundhabitEvents = result.getSourceAsObjectList(HabitEvent.class);

                        habitEvents.addAll(foundhabitEvents);
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

            return habitEvents;
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