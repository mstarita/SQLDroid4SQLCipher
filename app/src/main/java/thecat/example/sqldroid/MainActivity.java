package thecat.example.sqldroid;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String DEFAULT_DB_PATH =
            Environment.getExternalStorageDirectory().toString() + File.separator;
    private static final String DB_NAME = "person.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText output = (EditText) findViewById(R.id.flywayOutput);

        try {
            // Load the SQLCipher libraries before the use of SQLDroid4SQLCipher jdbc driver
            SQLiteDatabase.loadLibs(this);

            int numberOfMigrations = FlywayDBMigration.migrate(
                    this,
                    DEFAULT_DB_PATH + DB_NAME,
                    "a_password");


            output.setText("number of migration: " + numberOfMigrations);
        } catch (Exception ex) {
            Log.e(MainActivity.class.getCanonicalName(), "exception on onCreate()", ex);
            output.setText("exception on migration: " + ex.getMessage());
        }
    }
}
