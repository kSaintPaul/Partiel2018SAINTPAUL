package Model;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

public class Release {
    private String status;
    private String thumb;
    private String format;
    private String title;
    private String catno;
    private int year;
    private String resourceUrl;
    private String artist;
    private int id;

    /**
     * No args constructor for use in serialization
     *
     */
    public Release() {
    }

    /**
     *
     * @param id
     * @param catno
     * @param title
     * @param status
     * @param year
     * @param artist
     * @param format
     * @param resourceUrl
     * @param thumb
     */
    public Release(String status, String thumb, String format, String title, String catno, int year, String resourceUrl, String artist, int id) {
        super();
        this.status = status;
        this.thumb = thumb;
        this.format = format;
        this.title = title;
        this.catno = catno;
        this.year = year;
        this.resourceUrl = resourceUrl;
        this.artist = artist;
        this.id = id;
    }


    public static ArrayList<Release> GetReleasesByCursor(Cursor cursor){
        ArrayList<Release> releases = new ArrayList<>();
        Release release;
        while(cursor.moveToNext()){
            release = new Release();

            release.id = cursor.getInt(cursor.getColumnIndex("id"));
            release.title = cursor.getString(cursor.getColumnIndex("title"));
            release.thumb = cursor.getString(cursor.getColumnIndex("thumb"));
            release.catno = cursor.getString(cursor.getColumnIndex("catno"));
            release.year = cursor.getInt(cursor.getColumnIndex("year"));
            release.resourceUrl = cursor.getString(cursor.getColumnIndex("resourceUrl"));
            release.artist = cursor.getString(cursor.getColumnIndex("artist"));
            release.status = cursor.getString(cursor.getColumnIndex("status"));

            releases.add(release);
        }
        return releases;
    }

    public static ContentValues ToContentValues(Release release){
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", release.id);
        contentValues.put("year", release.year);

        if(release.title != null) {contentValues.put("nom", release.title);}
        if(release.catno != null) {contentValues.put("desc", release.catno);}
        if(release.resourceUrl != null) {contentValues.put("price", release.resourceUrl);}
        if(release.artist != null) {contentValues.put("price", release.artist);}
        if(release.status != null) {contentValues.put("price", release.status);}
        if(release.thumb != null) {contentValues.put("price", release.thumb);}

        return contentValues;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCatno() {
        return catno;
    }

    public void setCatno(String catno) {
        this.catno = catno;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
