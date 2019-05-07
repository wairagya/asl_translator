package com.example.ou.asl_translator;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ou.asl_translator.adapter.DictListAdapter;
import com.example.ou.asl_translator.api.ApiRequest;
import com.example.ou.asl_translator.api.RetroClient;
import com.example.ou.asl_translator.model.ChildRow;
import com.example.ou.asl_translator.model.ParentRow;
import com.example.ou.asl_translator.model.ResponseApiDict;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DictActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener{
    private SearchManager searchManager;
    private SearchView searchView;
    private DictListAdapter listAdapter;
    private ExpandableListView myList ;
    private ArrayList<ParentRow> parentList = new ArrayList<>();
    private ArrayList<ParentRow> showTheseParentList = new ArrayList<>();
    private MenuItem searchItem;
    private List<ChildRow> mItem = new ArrayList<>();
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        dialog = new ProgressDialog(DictActivity.this);
        dialog.setMessage("Loading...");
        showTheseParentList = new ArrayList<>();
        dialog.show();
        final List list = new ArrayList(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","r","t","u","v","w","x","y","z"));
        loadData(list);

    }
    private void loadData(final List list) {
        ApiRequest Api = RetroClient.getRequestService();
        Call<ResponseApiDict> dict = Api.dict(list.get(0).toString());
        dict.enqueue(new Callback<ResponseApiDict>() {
            @Override
            public void onResponse(Call<ResponseApiDict> call, Response<ResponseApiDict> response) {
                if(response.body().getKode().equals("1")){
                    mItem=response.body().getResult();
                    ArrayList<ChildRow> childRows = new ArrayList<>();
                    for (int j=0; j<mItem.size();j++){
                        String temp=mItem.get(j).getWord();
                        childRows.add(new ChildRow(temp));
                    }
                    ParentRow parentRow;
                    parentRow = new ParentRow(list.get(0).toString(), childRows);
                    parentList.add(parentRow);
                    list.remove(0);
                    if (list.isEmpty()){
                        displayList(parentList);
                    }
                    else if (!(list.isEmpty())){
                        loadData(list);
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseApiDict> call, Throwable t) {
                Log.w("List gagal", list.get(0).toString());
                Toast.makeText(DictActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            myList.expandGroup(i);
        }
        dialog.dismiss();
    }

    private void displayList(ArrayList<ParentRow> parentList) {
        myList = findViewById(R.id.expandableListView_search);
        listAdapter = new DictListAdapter(DictActivity.this, parentList);
        myList.setAdapter(listAdapter);
        expandAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dict, menu);
        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo
                (searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.requestFocus();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onClose() {
        listAdapter.filterData("");
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        listAdapter.filterData(query);
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listAdapter.filterData(newText);
        expandAll();
        return false;
    }
}
