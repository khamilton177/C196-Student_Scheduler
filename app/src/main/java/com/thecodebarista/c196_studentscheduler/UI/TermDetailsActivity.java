package com.thecodebarista.c196_studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.thecodebarista.c196_studentscheduler.R;
import com.thecodebarista.c196_studentscheduler.database.StudentSchedulerRepo;
import com.thecodebarista.c196_studentscheduler.entities.Term;

import java.util.List;

public class TermDetailsActivity extends AppCompatActivity {
    private EditText titleTextInput;
    private EditText startTextInput;
    private EditText endTextInput;
    private int id;
    private String title;
    private String start;
    private String end;

    private void setTermDetailData() {
        titleTextInput = findViewById(R.id.termTitleInput);
        startTextInput = findViewById(R.id.termStartInput);
        endTextInput = findViewById(R.id.termEndInput);
        id = getIntent().getIntExtra("id",-1);
        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        titleTextInput.setText(title);
        startTextInput.setText(start);
        startTextInput.setText(end);
    }
    public void onCheckSaveClick(View view) {
        Intent intent = new Intent(TermDetailsActivity.this, TermDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        setTermDetailData();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}