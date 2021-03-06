/*
 *                    GNU GENERAL PUBLIC LICENSE
 *                        Version 3, 29 June 2007
 *
 *  Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
 *  Everyone is permitted to copy and distribute verbatim copies
 *  of this license document, but changing it is not allowed.
 ******************************************************************************/

package semiworld.org.logger;

import android.app.Application;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.query.Select;

import java.util.Calendar;
import java.util.Locale;

import semiworld.org.logger.models.Note;
import semiworld.org.logger.models.Setting;

/**
 * Created on 07.06.2017.
 */


public class AppStart extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /*
        Here  is initial configuraiton for database .
        To use activeandroid effectively you could initialize in Manifest.xml .
        All created model must add as model class in this configuration.
        if you change anything on model class you shoul increment database version
        It is a kind of migration
        */
        Configuration conf = new Configuration.Builder(getApplicationContext())
                .setDatabaseName("LoggerDB")
                .setDatabaseVersion(9)
                .addModelClass(Note.class)
                .addModelClass(Setting.class)
                .create();

        ActiveAndroid.initialize(conf);

        // Here is first use to show user some dummy data
        initialSettings();
    }

    private void initialSettings() {

        // Have a look if there is any data
        boolean _thereIsNoData = new Select().from(Note.class).count() == 0;
        boolean _thereIsNoSetting = new Select().from(Setting.class).count() == 0;

        Log.v("abc", String.valueOf(_thereIsNoData + " " + _thereIsNoSetting));

        // if it is then return | do nothing
        if (_thereIsNoData) {
            // else create a dummy data and save it
            Note note = new Note("This is a simple note!",
                    Calendar.getInstance(Locale.getDefault()).getTime());
            note.save();
        }
        if (_thereIsNoSetting) {
            Setting setting = new Setting(false, "", 3);
            setting.save();
        }
    }
}
