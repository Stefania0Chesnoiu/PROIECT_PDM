package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private List<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;
    private SearchView editsearch;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        // searchView = (SearchView) findViewById(R.id.searchView);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        editsearch = (SearchView) findViewById(R.id.search);

        items = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        items.add("first");
        items.add("second");
        items.add("third");
        items.add("forth");
        items.add("fifth");
        lvItems.setAdapter(itemsAdapter);

        editsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                  int ok=0;
                  for(String el:items){
                      if(el.contains(query))
                      {ok=1;
                          itemsAdapter.getFilter().filter(query);
                      }
                 }
                  if(ok==0)
                  {Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                return false;
            }
        });
        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                });
    }


    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    private void readItems() {


        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "C:/Users/Stefania/Desktop/test.txt"));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        /*
        FileInputStream is;
        BufferedReader reader;
        System.out.println("inainte de tru");
        try{
            final File file = new File("C:\\Users\\Stefania\\Desktop\\test.txt");
            System.out.println("dupa try");
             if(file.exists())System.out.println("GaTA");
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            System.out.println("dupa try2");
            String line = reader.readLine();
            System.out.println("dupa try3");
            items=new ArrayList<>();
            while(line != null){
                items.add(line);
                System.out.println(line);
                line = reader.readLine();
            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }*/


    private void writeItems() {
        try {
            FileOutputStream fOut = new FileOutputStream("C:\\Users\\Stefania\\Desktop\\test.txt");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fOut));
            for (int i = 0; i < items.size(); i++) {
                bw.write(items.get(i));
                bw.newLine();
            }
            bw.close();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}