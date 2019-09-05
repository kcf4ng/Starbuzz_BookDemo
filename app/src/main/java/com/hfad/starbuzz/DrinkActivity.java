package com.hfad.starbuzz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

//從 class Drink 之中，獲得資料，放進ＵＩ上面。
//        Drink drink = Drink.drinks[drinkId];
//        name.setText(drink.getName());
//        description.setText(drink.getDescription());
//        photo.setImageResource(drink.getImageResourceIdId());
//        photo.setContentDescription(drink.getName());

        SQLiteOpenHelper helper = new StarbuzzDatabaseHelper(this);

        try {
            SQLiteDatabase db= helper.getReadableDatabase();
            drinkId +=1;
            Cursor cursor = db.query("DRINK",
                    new String[] {"NAME","DESCRIPTION","IMAGE_RESOURCE_ID"},
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

                name.setText(nameText);
                description.setText(descriptionText);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast.makeText(this,
                    "Database  not found",
                    Toast.LENGTH_SHORT).show();
        }


    }
}
