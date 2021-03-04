package info.androidhive.recyclersearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    private TextView searchID;
    private TextView searchMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String id_string = bundle.getString("id");
        searchID = (TextView) findViewById(R.id.number);
        searchID.setText(id_string);

        searchMessage = (TextView) findViewById(R.id.search_message);
        String message = "这是第" + id_string + "行";
        searchMessage.setText(message);

    }
}