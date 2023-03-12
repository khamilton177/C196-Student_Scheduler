package com.thecodebarista.c196_studentscheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thecodebarista.c196_studentscheduler.R;
import com.thecodebarista.c196_studentscheduler.database.StudentSchedulerRepo;
import com.thecodebarista.c196_studentscheduler.entities.Term;

import java.util.List;

public class TermsActivity extends AppCompatActivity {

    /**
     * Sets the elements of for the recycler view and repository list.
     * @return List of all terms
     */
    private List<Term> termRepoConstruct() {
        RecyclerView recyclerView = findViewById(R.id.termsRV);
        final TermsAdapter termsAdapter = new TermsAdapter(this);
        recyclerView.setAdapter(termsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StudentSchedulerRepo studentSchedulerRepo = new StudentSchedulerRepo(getApplication());
        return studentSchedulerRepo.getAllTerms();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        List<Term> allTerms = termRepoConstruct();
        final TermsAdapter termsAdapter = new TermsAdapter(this);
        FloatingActionButton fab = findViewById(R.id.fabPlusAdd);
        termsAdapter.setTerms(allTerms);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermsActivity.this, TermDetailsActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Term> allTerms = termRepoConstruct();
        final TermsAdapter termsAdapter = new TermsAdapter(this);
        termsAdapter.setTerms(allTerms);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}