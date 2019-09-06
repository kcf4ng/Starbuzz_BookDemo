package com.hfad.starbuzz;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends AppCompatActivity {
    public static final String EXTRA_DRINKID ="drinkId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkId = (Integer) getIntent().getExtras().get(EXTRA_DRINKID);


        TextView name = findViewById(R.id.name);
        TextView description = findViewById(R.id.description);
        ImageView photo = findViewById(R.id.photo);
        CheckBox favorite =  findViewById(R.id.favorite);

//從 class Drink 之中，獲得資料，放進ＵＩ上面。
//        Drink drink = Drink.drinks[drinkId];
//        name.setText(drink.getName());
//        description.setText(drink.getDescription());
//        photo.setImageResource(drink.getImageResourceIdId());
//        photo.setContentDescription(drink.getName());

        SQLiteOpenHelper helper = new StarbuzzDatabaseHelper(this);

        try {
            SQLiteDatabase db= helper.getReadableDatabase();
            Cursor cursor = db.query("DRINK",
                    new String[] {"NAME","DESCRIPTION","IMAGE_RESOURCE_ID","FAVORITE"},
                    "_id="+drinkId,
                    null,
                    null,
                    null,
                    null);


            //移往資料的第一筆紀錄
            if( cursor.moveToFirst()){
                //從Cursor取得飲料詳情
                String nameText= cursor.getString(0);
                String descriptionText= cursor .getString(1);
                int photoId = cursor . getInt(2);
                Boolean isFavorite = (cursor.getInt(3)==1);


                name.setText(nameText);
                description.setText(descriptionText);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
                favorite.setChecked(isFavorite);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast.makeText(this,
                    "Database  not found",
                    Toast.LENGTH_SHORT).show();
        }


    }

    public void onFavoriteClick(View view) {

        int drinkId = (Integer) getIntent().getExtras().get(EXTRA_DRINKID);

//將這些程式碼移到AsyncTask裡面執行
//        //取得確認方塊的值
//        CheckBox favorite = findViewById(R.id.favorite);
//        ContentValues data = new ContentValues();
//        data.put("FAVORITE",favorite.isChecked());
//
//        SQLiteOpenHelper helper = new StarbuzzDatabaseHelper(this);
//        try {
//            SQLiteDatabase db = helper.getWritableDatabase();
//            db.update("DRINK",
//                    data,
//                    "_id=?",
//                    new String[]{Integer.toString(drinkId)});
//
//        } catch (SQLiteException e) {
//
//
//            Toast.makeText(this,"DataBase not found",Toast.LENGTH_SHORT).show();
//        }

        new UpdateDrinkTask().execute(drinkId);
    }

private class UpdateDrinkTask extends AsyncTask<Integer, Void, Boolean> {
    ContentValues data ;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        CheckBox favorite = findViewById(R.id.favorite);
        data = new ContentValues();
        data.put("FAVORITE",favorite.isChecked());
    }


    @Override
    protected Boolean doInBackground(Integer... drinks) {
         int drinkId = drinks[0];
        SQLiteOpenHelper helper = new StarbuzzDatabaseHelper(DrinkActivity.this);

        try {
            SQLiteDatabase db = helper.getWritableDatabase();
            db.update("DRINK",
                    data,
                    "_id=?",
                    new String[]{Integer.toString(drinkId)});

            db.close();
            return  true;

        } catch (SQLiteException e) {
            return false;
        }


    }


    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        if(!success){
            Toast.makeText(DrinkActivity.this,"DataBase not found",Toast.LENGTH_SHORT).show();
        }
    }
}
}
