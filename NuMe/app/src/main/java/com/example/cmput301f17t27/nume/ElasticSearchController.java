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


public class ElasticSearchController {
    private static JestClient client;


    public static class AddProfileTask extends AsyncTask<Profile, Void, String> {
        /**
         * This Async task adds a profile to ElasticSearch.
         * @param profiles The one profile you are adding (Only supports one argument)
         * @return The ID where the added profile is stored on ElasticSearch
         */
        @Override
        protected String doInBackground(Profile... profiles) {
            verifySettings();

            //Make the index
            Index index = new Index.Builder(profiles[0]).index("cmput301f17t27_nume").type("profile").build();

            try {
                //Execute the index
                DocumentResult result = client.execute(index);

                //Return the id
                return result.getId();
            }

            catch (Exception e) {
                Log.i("Error", "Failed to add profile to ElasticSearch");
            }

            return null;
        }
    }



    public static class UpdateProfileTask extends AsyncTask<Profile, Void, Void> {
        /**
         * This Async task updates a profile on ElasticSearch.
         * @param profiles The one profile you are updating (Only supports one argument)
         */
        @Override
        protected Void doInBackground(Profile... profiles) {
            verifySettings();

            //Get the profile argument
            Profile profile = profiles[0];

            //Build the index
            Index index = new Index.Builder(profile).index("cmput301f17t27_nume").type("profile").id(profile.getId()).build();

            try {
                //Execute the index
                DocumentResult result = client.execute(index);

                if (!result.isSucceeded()) {
                    Log.i("Error", "Failed to update the profile on ElasticSearch");
                }
            }

            catch (Exception e) {
                Log.i("Error", "Failed to update the profile on ElasticSearch");
            }

            return null;
        }
    }



    public static class GetProfileTask extends AsyncTask<String, Void, Profile> {
        /**
         * This Async task gets a profile from ElasticSearch.
         * @param usernames The username of the profile to get (Only supports one argument)
         * @return The profile object associated with that username
         */
        @Override
        protected Profile doInBackground(String... usernames) { //String defines the param type; ...means multiple params
            verifySettings();

            //Get the username argument
            String username = usernames[0];

            //Formulate the search query
            String query = "{\"query\" : {\"match\" : { \"username\" : \"" + username + "\" }}}";

            //Build the search object
            Search search = new Search.Builder(query).addIndex("cmput301f17t27_nume").addType("profile").build();

            try {
                //Execute the search
                SearchResult result = client.execute(search);

                if (result.isSucceeded()) {
                    //Get and return the profile
                    SearchResult.Hit<Profile, Void> hit = result.getFirstHit(Profile.class);
                    return hit.source;
                }

                else {
                    Log.i("Error", "Failed to get the profile from ElasticSearch");
                }
            }

            catch (Exception e) {
                Log.i("Error", "Failed to get the profile from ElasticSearch");
            }

            return null;
        }
    }



    /**
     * This function initializes the JestDroidClient and sets up ElasticSearch.
     */
    private static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}