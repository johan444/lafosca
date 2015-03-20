package com.example.joan.lafosca.activities;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.joan.lafosca.R;
import com.example.joan.lafosca.controllers.OrmHelper;
import com.example.joan.lafosca.model.ModelKid;
import com.example.joan.lafosca.model.OrmKid;
import com.example.joan.lafosca.ui.KidAdapter;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class KidsActivity extends ActionBarActivity {

    private ArrayList<ModelKid> kidsList;
    private List<OrmKid> ormList;
    KidAdapter adapter;

    @InjectView(R.id.kidsList) ListView list;
    @InjectView(R.id.searchId) EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.drawable.logo);
        menu.setDisplayUseLogoEnabled(true);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/OpenSans-Bold.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        setContentView(R.layout.activity_kids);
        ButterKnife.inject(this);

        Bundle data = getIntent().getExtras();

        OrmHelper databaseHelper = OpenHelperManager.getHelper(this, OrmHelper.class);
        Dao<OrmKid, Long> kidDao = null;

        try {
            kidDao = databaseHelper.getKidDataDao();

            ormList  = kidDao.queryBuilder().orderBy("age", false).query();
            for (OrmKid l:ormList) {
                //Log.d("nen111 ", l.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        kidsList = data.getParcelableArrayList("kids");
//        ArrayList<ModelKid> aux = new ArrayList<ModelKid>();
        String[] urls = new String[]{"http://i.imgur.com/Dvpvk.png",
                "http://fc04.deviantart.net/fs71/i/2013/113/6/e/jump_hurdles_by_zoranphoto-d62pl6q.jpg",
                "http://3.bp.blogspot.com/_rOVSEI0eaOM/S96XUmlM6zI/AAAAAAAABo8/NoADJWzZS6g/s1600/cat_desktop_wallpaper_1366x768.jpg",
                "http://www.graphics20.com/tattoos/wp-content/uploads/2013/03/Big-Cats-108.jpg",
                "http://comments20.com/Recados/wp-content/uploads/2012/05/Funny-Cat73.jpg",
                "http://2.bp.blogspot.com/-2loETEvhGXs/T5KYA_oHpeI/AAAAAAAAARo/9YAGOX4JnDw/s1600/Cat-Desktop-Wallpaper-3.jpg",
                "http://4.bp.blogspot.com/-OmS8pvBBEcA/T_VxH8N9ofI/AAAAAAAAALQ/_DpGUBbNvhc/s1600/catphotos3.jpg",
                };
        kidsList = new ArrayList<ModelKid>();
        ModelKid mkKid;
        int count = 0;
        for (OrmKid kid:ormList) {
            mkKid = new ModelKid(kid.getName(),kid.getAge(), urls[count]);
            count++;
            count = count%(urls.length);
            kidsList.add(mkKid);
        }
        ArrayList<ModelKid> aux = new ArrayList<ModelKid>();
        aux.addAll(kidsList);
        adapter = new KidAdapter(this, R.layout.list_item, aux);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KidAdapter kid = (KidAdapter) parent.getAdapter();
                Crouton.makeText(KidsActivity.this, kid.getData(position).getName(), Style.ALERT).show();
            }
        });

        searchInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
                // When user changed the Text

            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<ModelKid> result = new ArrayList<ModelKid>();
                int size = kidsList.size();
                Log.d("mida kids list ", String.valueOf(size));
                for (ModelKid kid : kidsList) {
                    String text = searchInput.getText().toString().toLowerCase(Locale.getDefault());
                    if(kid.getName().toLowerCase(Locale.getDefault()).contains(text)) {
                        result.add(kid);
                    }
                }

                adapter.setData(result);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kids, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
