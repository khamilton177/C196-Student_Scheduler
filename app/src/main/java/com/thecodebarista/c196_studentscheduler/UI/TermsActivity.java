package com.thecodebarista.c196_studentscheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thecodebarista.c196_studentscheduler.R;
import com.thecodebarista.c196_studentscheduler.database.StudentSchedulerRepo;
import com.thecodebarista.c196_studentscheduler.entities.Instructor;
import com.thecodebarista.c196_studentscheduler.entities.Term;

import java.util.List;

public class TermsActivity extends AppCompatActivity {
    private StudentSchedulerRepo studentSchedulerRepo;

    /**
     * Sets the elements of for the recycler view and repository list.
     * @return List of all terms
     */
    private void termRepoConstruct() {

        if (studentSchedulerRepo == null) {
            studentSchedulerRepo = new StudentSchedulerRepo(getApplication());
        }

        List<Term> allTerms = studentSchedulerRepo.getAllTerms();
        if (allTerms != null)
            System.out.println("Term List Size " + allTerms.size());
        RecyclerView recyclerView = findViewById(R.id.termsRV);
        final TermsAdapter termsAdapter = new TermsAdapter(this);
        recyclerView.setAdapter(termsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termsAdapter.setTerms(allTerms);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        termRepoConstruct();
        FloatingActionButton fab = findViewById(R.id.fabPlusAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.thecodebarista.c196_studentscheduler.UI.TermsAdapter.TERM_EDIT_MODE = true;
                Intent intent = new Intent(TermsActivity.this, TermDetailsActivity.class);
                intent.putExtra("inEditMode", com.thecodebarista.c196_studentscheduler.UI.TermsAdapter.TERM_EDIT_MODE);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        termRepoConstruct();
    }
}