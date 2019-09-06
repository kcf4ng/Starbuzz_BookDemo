package com.hfad.starbuzz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TopLevelActivity extends AppCompatActivity {

    SQLiteDatabase db;
    Cursor favoriteCursor;
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position == 0){
                startActivity(new Intent(TopLevelActivity.this,DrinkCategoryActivity.class));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        InitialComponent();
        setupFavoriteListView();

    }

    private void setupFavoriteListView() {

        ListView listFavorites = findViewById(R.id.list_favorite);

        try {
            SQLiteOpenHelper helper = new StarbuzzDatabaseHelper(this);
            db = helper.getReadableDatabase();
            favoriteCursor =db.query("DRINK",
                    new String[]{"_id","NAME"},
                    "FAVORITE=1",
                    null,
                    null,
                    null,
                    null);

            CursorAdapter adapter = new SimpleCursorAdapter(TopLevelActivity.this,
                    android.R.layout.simple_expandable_list_item_1,
                    favoriteCursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0);
            listFavorites.setAdapter(adapter);
        } catch (SQLiteException e) {
            Toast.makeText(this,"Database not found", Toast.LENGTH_SHORT).show();
        }

        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TopLevelActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKID,(int)id);
                startActivity(intent);
            }
        });

    }


    private void InitialComponent() {
        listView = findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favoriteCursor.close();
        db.close();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Cursor newCursor = db.query("DRINK",
                new String[]{"_id","NAME"},
                "FAVORITE=1",
                null,
                null,
                null,
                null);

        ListView listFavorites = findViewById(R.id.list_favorite);
        CursorAdapter adapter  = (CursorAdapter) listFavorites.getAdapter();
        adapter.changeCursor(newCursor);
        favoriteCursor= newCursor;
    }

    ListView listView;


}
