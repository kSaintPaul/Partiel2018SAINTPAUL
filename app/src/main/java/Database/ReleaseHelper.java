package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import Model.Release;

public class ReleaseHelper {
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public ReleaseHelper(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Release> GetAllReleases(){

        Cursor cursor = db.query(DbHelper.Table_Release,
                new String[]{"id", "status", "thumb", "format", "title", "catno", "year", "resourceUrl", "artist"},
                null,
                null,
                null,
                null,
                null);

        return Release.GetReleasesByCursor(cursor);
    }

    public long AddRelease(Release release, @Nullable String nullColumnHack){
        ContentValues contentValues = Release.ToContentValues(release);

        return db.insert(DbHelper.Table_Release
                ,nullColumnHack
                ,contentValues);
    }

    public long AddReleaseIfNotExist(Release release, @Nullable String nullColumnHack){
        ContentValues contentValues = Release.ToContentValues(release);

        Cursor cursor = db.query(DbHelper.Table_Release,
                new String[]{"id", "status", "thumb", "format", "title", "catno", "year", "resourceUrl", "artist"},
                "id = ?",
                new String[]{String.valueOf(release.getId())},
                null,
                null,
                null);

        return db.insert(DbHelper.Table_Release
                ,nullColumnHack
                ,contentValues);
    }

}
