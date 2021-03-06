package guitarslist.codeclan.com.guitarslistapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class Favourite_Guitars_List_Activity extends AppCompatActivity {

    TopGuitars topGuitars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guitars);

        refreshGuitars();

        TopGuitarsAdapter guitarsAdapter = new TopGuitarsAdapter(this, topGuitars.getListFavourites());

        ListView listView = (ListView) findViewById(R.id.guitarListViewId);
        listView.setAdapter(guitarsAdapter);

    }

    private void refreshGuitars(){
        topGuitars = PersistenceHelper.loadApplicationState(this);
        if (topGuitars.getList().size() == 0 ){

            topGuitars = new TopGuitars();
            PersistenceHelper.saveApplicationState(this, topGuitars);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshGuitars();
        topGuitars.updateBudgetInfoOnGuitars();

        ListView listView = (ListView) findViewById(R.id.guitarListViewId);

        TopGuitarsAdapter guitarsAdapter = new TopGuitarsAdapter(this, topGuitars.getListFavourites());
        listView.setAdapter(guitarsAdapter);
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    public void onListItemClick(View listItem) {
        Guitar guitarClicked = (Guitar) listItem.getTag();
        openGuitarDetail(guitarClicked);
    }

    public void openGuitarDetail(Guitar guitarToOpen) {
        Intent intent = new Intent(this, GuitarDetailsActivity.class);
        intent.putExtra("guitarToOpen", guitarToOpen);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_favourites, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_main) {
            Intent intent = new Intent(this, Guitars_Activity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
