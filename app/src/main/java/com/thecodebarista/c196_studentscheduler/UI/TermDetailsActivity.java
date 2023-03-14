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
    private int termID;
    private String title;
    private String startDt;
    private String endDt;
    Term selectedTerm;
    StudentSchedulerRepo studentSchedulerRepo;

    private void setTermDetailData() {
        titleTextInput = findViewById(R.id.termTitleInput);
        startTextInput = findViewById(R.id.termStartInput);
        endTextInput = findViewById(R.id.termEndInput);
        termID = getIntent().getIntExtra("id",-1);
        title = getIntent().getStringExtra("title");
        startDt = getIntent().getStringExtra("startDt");
        endDt = getIntent().getStringExtra("endDt");
        titleTextInput.setText(title);
        startTextInput.setText(startDt);
        endTextInput.setText(endDt);
    }

    private void getTermDetailData() {
        System.out.println(titleTextInput.getText());
        System.out.println(startTextInput.getText());
        System.out.println(endTextInput.getText());
    }

    private Term createTermDetailData() {
        if(termID == -1) {
            termID = 0;
        }
        return new Term(termID,titleTextInput.getText().toString(),
                startTextInput.getText().toString(),endTextInput.getText().toString());
    }

    public void onCheckSaveClick(View view) {
        Term term;
        System.out.println("Save button clicked. Term ID is- " + termID);
        getTermDetailData();
        term = createTermDetailData();
        System.out.println("This term ID is- " + term.getTermID());
        studentSchedulerRepo = new StudentSchedulerRepo(getApplication());

        if(term.getTermID() == 0){
            studentSchedulerRepo.insert(term);
        }
        else{
            studentSchedulerRepo.update(term);
        }
/*
      if(termID == -1){
            term = new Term(0,titleTextInput.getText().toString(),
                    startTextInput.getText().toString(),endTextInput.getText().toString());
            System.out.println("What's the title? " + term.getTitle());
            studentSchedulerRepo.insert(term);
        }
        else{
            term = new Term(termID,titleTextInput.getText().toString(),
                    startTextInput.getText().toString(),endTextInput.getText().toString());
            studentSchedulerRepo.update(term);
        }
        */
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