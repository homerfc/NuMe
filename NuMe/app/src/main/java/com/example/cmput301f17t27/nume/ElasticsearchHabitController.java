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
import io.searchbox.core.Delete;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by Matt on 2017-11-09.
 */

public class ElasticsearchHabitController {
    private static JestClient client;


    public static class addHabitTask extends AsyncTask<Habit, Void, Void> {
        @Override
        protected Void doInBackground(Habit... habits) {
            verifySettings();


            for ( Habit habit : habits) {
                Index index = new Index.Builder(habit).index("cmput301f17t27_nume").type("habit").build();
                try {
                    client.execute(index);
                } catch (Exception e) {
                    Log.i("Error", "Failed to add habit to Elasticsearch");
                }
            }
            return null;
        }


    }

    public static class GetHabitTask extends AsyncTask<String, Void, ArrayList<Habit>> {
        @Override
        protected ArrayList<Habit> doInBackground(String... search_parameters) { //String defines the param type; ...means multiple params
            verifySettings();

            ArrayList<Habit> habits = new ArrayList<Habit>();

            if (search_parameters.length == 1) {

                String query = "{\n" + " \"query\": { \"match\": {\"owner\":\"" + search_parameters[0] + "\"} }\n" + "}";

                Search search = new Search.Builder(query)
                        .addIndex("cmput301f17t27_nume")
                        .addType("habit").build();




                try {
                    SearchResult result = client.execute(search);

                    if (result.isSucceeded()) {
                        List<Habit> foundHabits = result.getSourceAsObjectList(Habit.class);

                        habits.addAll(foundHabits);
                    } else {
                        Log.i("Error", "Search Query Failed ");
                    }

                } catch (Exception e) {
                    //client.shutdownClient();
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                    Log.e("Error", "error", e);
                }

            } else {
                Log.i("Test", "doInBackground: search_parameters!: " + search_parameters[0] + " Next 1:" + search_parameters[0]);

            }

            return habits;
        }
    }

    //TODO deletion
    public static class DeleteHabitTask extends AsyncTask<Habit, Void, Void> {
        @Override
        protected Void doInBackground(Habit... searchParameteres) {
            verifySettings();


            //Delete deleteHabit = new Delete.Builder().index("cmput301f17t27_nume").type("habit").build();
            try {
                //client.execute(deleteHabit);
            } catch (Exception e) {
                Log.i("Error", "Failed to add habit to Elasticsearch");
                    //e.printStackTrace();
            }

        return null;
        }

    }

    public static class UpdateHabitTask extends AsyncTask<Habit, Void, Void> {
        @Override
        protected Void doInBackground(Habit... habits) {
            verifySettings();

            Habit updateHabit = habits[0];

            Index index = new Index.Builder(updateHabit).index("cmput301f17t27_nume").type("habit").build();

            try {
                client.execute(index);
            } catch (Exception e) {
                Log.i("Error", "Failed to add habit to Elasticsearch");
                //e.printStackTrace();
            }

            return null;
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