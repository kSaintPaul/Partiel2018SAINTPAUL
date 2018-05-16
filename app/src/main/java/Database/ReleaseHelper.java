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

        ArrayList<Release> releases = Release.GetReleasesByCursor(cursor);

        if(releases.size() == 0)
            return db.insert(DbHelper.Table_Release
                ,nullColumnHack
                ,contentValues);
        else
            return releases.get(0).getId();
    }

    public int DeleteBeer(Release release){
        return DeleteBeer(release.getId());
    }

    public int DeleteBeer(Integer idRelease){
        return db.delete(
                DbHelper.Table_Release
                ,"id = ?"
                ,new String[]{String.valueOf(idRelease)});
    }

    public int UpdateBeer(Release release){
        ContentValues contentValues = Release.ToContentValues(release);

        return db.update(DbHelper.Table_Release,
                contentValues,
                "id = ?",
                new String[]{String.valueOf(release.getId())});
    }

    public int UpdateBeer(Integer idRelease, ContentValues contentValues){

        return db.update(DbHelper.Table_Release,
                contentValues,
                "id = ?",
                new String[]{String.valueOf(idRelease)});
    }

}
