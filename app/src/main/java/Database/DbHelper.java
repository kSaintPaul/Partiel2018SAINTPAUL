package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static String DB_Name = "DbRelease";

    public static String Table_Release = "Release";
    public static String Table_Artist = "Artist";

    public DbHelper(Context context){
        super(context, DB_Name,null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `" + Table_Release +"` (" +
                "`id` INTEGER," +
                "`status` TEXT," +
                "`thumb` TEXT," +
                "`format` TEXT," +
                "`title` TEXT," +
                "`catno` TEXT," +
                "`year` INTEGER," +
                "`resourceUrl` TEXT," +
                "`artist` TEXT," +
                "PRIMARY KEY(`id`)" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        for (int i = 1; i <= newVersion; i++){
            InitDataBase(db, i);
        }


    }

    private void InitDataBase(SQLiteDatabase db, int version) {
        switch (version){
            case 2 :
                db.execSQL("CREATE TABLE " + Table_Artist + " (" +
                        "`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        "`name` TEXT," +
                        "`location` TEXT," +
                        "`phone` TEXT" +
                        ");");

                db.execSQL("ALTER TABLE `" + Table_Release + "` " +
                        " ADD `idArtist` INTEGER;");
                break;
            case 3 :

                break;
        }

    }
}
