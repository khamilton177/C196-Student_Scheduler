package com.thecodebarista.c196_studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thecodebarista.c196_studentscheduler.R;
import com.thecodebarista.c196_studentscheduler.database.StudentSchedulerRepo;
import com.thecodebarista.c196_studentscheduler.entities.Term;

import java.util.List;

public class InstructorsActivity extends AppCompatActivity {
    private StudentSchedulerRepo studentSchedulerRepo;

    /**
     * Sets the elements of for the recycler view and repository list.
     * @return List of all instructors
     */
    private void instructorRepoConstruct() {

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
        setContentView(R.layout.activity_instructors);
        instructorRepoConstruct();
        FloatingActionButton fab = findViewById(R.id.fabPlusAdd);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorsActivity.this, InstructorDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        instructorRepoConstruct();
    }
}