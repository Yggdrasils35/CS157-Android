package info.androidhive.recyclersearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Search> searchList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSearches();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        SearchAdapter adapter = new SearchAdapter(searchList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter.setOnItemClickListener(MyItemClickListener);
    }

    private void initSearches() {
        for (int i = 0; i < 100; i++) {
            String text = "这是第" + Integer.toString(i+1) + "行";
            Search search = new Search(text, i);
            searchList.add(search);
        }
    }

    private SearchAdapter.OnItemClickListener MyItemClickListener = new SearchAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            final TextView name = (TextView) v.findViewById(R.id.search_text);
            v.findViewById(R.id.recycler_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    intent.putExtra("id", position);
                    startActivity(intent);
                }
            });
        }

        @Override
        public void onItemLongClick(View v) {

        }
    };
}