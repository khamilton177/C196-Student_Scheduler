package com.thecodebarista.c196_studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thecodebarista.c196_studentscheduler.R;
import com.thecodebarista.c196_studentscheduler.database.StudentSchedulerRepo;
import com.thecodebarista.c196_studentscheduler.entities.Assessment;
import com.thecodebarista.c196_studentscheduler.entities.Term;

import java.util.List;

public class AssessmentsActivity extends AppCompatActivity  implements DegreePlanner{
    private StudentSchedulerRepo studentSchedulerRepo;

    /**
     * Sets the elements of for the recycler view and repository list.
     * @return List of all assessments
     */
    private void assessmentRepoConstruct() {

        if (studentSchedulerRepo == null) {
            studentSchedulerRepo = new StudentSchedulerRepo(getApplication());
        }

        List<Assessment> allAssessments = studentSchedulerRepo.getAllAssessments();
        if (allAssessments != null)
            System.out.println("Assessment List Size " + allAssessments.size());
        RecyclerView recyclerView = findViewById(R.id.assessmentsRV);
        final AssessmentsAdapter assessmentsAdapter = new AssessmentsAdapter(this);
        recyclerView.setAdapter(assessmentsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentsAdapter.setAssessments(allAssessments);
    }

    @Override
    public boolean finishCallback() {
        this.finish();
        return true;
    }


    @Override
    public void notifyReqCallback() {
        //nothing yet
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);
        assessmentRepoConstruct();
        FloatingActionButton fab = findViewById(R.id.fabPlusAdd);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.thecodebarista.c196_studentscheduler.UI.AssessmentsAdapter.ASSESSMENT_EDIT_MODE = true;
                Intent intent = new Intent(AssessmentsActivity.this, AssessmentDetailsActivity.class);
                intent.putExtra("inEditMode", com.thecodebarista.c196_studentscheduler.UI.AssessmentsAdapter.ASSESSMENT_EDIT_MODE);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        assessmentRepoConstruct();
    }
}
