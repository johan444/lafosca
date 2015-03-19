package com.example.joan.lafosca.activities;

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
import com.example.joan.lafosca.model.ModelKid;
import com.example.joan.lafosca.ui.KidAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class KidsActivity extends ActionBarActivity {

    private ArrayList<ModelKid> kidsList;
    KidAdapter adapter;

    @InjectView(R.id.kidsList) ListView list;
    @InjectView(R.id.searchId) EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kids);
        ButterKnife.inject(this);

        Bundle data = getIntent().getExtras();
        kidsList = data.getParcelableArrayList("kids");
        ArrayList<ModelKid> aux = new ArrayList<ModelKid>();
        aux.addAll(kidsList);
        adapter = new KidAdapter(this, R.layout.list_item, aux);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KidAdapter kid = (KidAdapter)parent.getAdapter();
                Crouton.makeText(KidsActivity.this,kid.getData(position).getName(), Style.ALERT).show();
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
}
