package com.example.cmput301f17t27.nume;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.SearchResult;

/**
 * Created by Matt on 2017-11-09.
 */

public class ElasticsearchController {
    //private static JestDroidClient client;
    private static JestClient client;
    public static class CreateHabit extends AsyncTask<Habit, Void, Void> {
        @Override
        protected Void doInBackground(Habit... habits) {
            verifySettings();

            for (Habit habit : habits) {
                Index index = new Index.Builder(habit).index("cmput301f17t27_nume").type("habit").build();
                try {
                    client.execute(index);
                } catch (Exception e) {
                    Log.i("Error", "Failed to add habit to Elasticsearch");
                    e.printStackTrace();
                }
            }
            return null;
        }


    }
    //TODO add reference to lab 5 notes
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
