package com.example.cmput301f17t27.nume;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Colman on 13/11/2017.
 */

public class LocalFileController {
    //Local save/load var declarations
    private static final String FILENAME = "file.sav";


    public static Profile loadFromFile(Context context){
        /*
            Description:
                This function loads the user's profile from a locally saved file
                on the device. It saves it to the profile member variable.

            Args:
                None

            Returns:
                None
        */

        Profile profile;
        try {
            FileInputStream stream = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            Gson gson = new Gson();
            profile = gson.fromJson(in, Profile.class);
        }

        catch (FileNotFoundException e) {
            profile = new Profile("TempName", "TempUser");
        }
        return profile;
    }



    public static void saveToFile(Context context, Profile profile) {
        /*
            Description:
                This function saves the user's profile to a locally saved file
                on the device.

            Args:
                None

            Returns:
                None
        */

        try {
            FileOutputStream stream = context.openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            Gson gson = new Gson();
            gson.toJson(profile, writer);
            writer.flush();
        }

        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
