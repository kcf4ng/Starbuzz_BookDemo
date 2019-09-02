package com.hfad.starbuzz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "starbuzz"; //資料庫名稱
    private static final int DB_VERSION = 1 ; //資料庫版本

    public StarbuzzDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null,DB_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE DRINK(_ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, DESCRIPTION TEXT, IMAGE_RESOURCE_ID INTEGER)");
        insertDrink(db,"Latte", "espresso + milk",R.drawable.coffee);
        insertDrink(db,"Cappuccino","espresso + milk + milk foam", R.drawable.beer);
        insertDrink(db,"Filter","filter water",R.drawable.water);

    }

    private void insertDrink(SQLiteDatabase db , String name, String description, int resourceId) {
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION ", description);
        drinkValues.put("IMAGE_RESOURCE_ID ", resourceId);
        db.insert("DRINK", null, drinkValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
