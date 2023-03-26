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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thecodebarista.c196_studentscheduler.R;
import com.thecodebarista.c196_studentscheduler.database.StudentSchedulerRepo;
import com.thecodebarista.c196_studentscheduler.entities.Course;
import com.thecodebarista.c196_studentscheduler.entities.Term;

import java.util.ArrayList;
import java.util.List;

public class TermDetailsActivity extends AppCompatActivity {
    private EditText titleTextInput;
    private EditText startTextInput;
    private EditText endTextInput;
    FloatingActionButton fabCheckSave;
    FloatingActionButton fabAddCourse;
    private int termID;
    private String title;
    private String startDt;
    private String endDt;
    private boolean inEditMode;
    private boolean showSave;
    Term selectedTerm;
    Course selectedCourse;
    StudentSchedulerRepo studentSchedulerRepo;

    private void intentSetTermDetailData() {
        titleTextInput = findViewById(R.id.termTitleInput);
        startTextInput = findViewById(R.id.termStartInput);
        endTextInput = findViewById(R.id.termEndInput);
        fabCheckSave = findViewById(R.id.fabCheckSave);
        fabAddCourse = findViewById(R.id.fabAddCourse);
        termID = getIntent().getIntExtra("termID",-1);
        title = getIntent().getStringExtra("title");
        startDt = getIntent().getStringExtra("startDt");
        endDt = getIntent().getStringExtra("endDt");
        inEditMode = getIntent().getBooleanExtra("inEditMode", false);
        setTermDetailData();
        setTermTextEditable(inEditMode);

        if (inEditMode) {
            System.out.println("It's EDIT MODE");
        } else {
            System.out.println("It's READ ONLY");
        }
    }

    private void setTermTextEditable(boolean canEdit) {
        titleTextInput = findViewById(R.id.termTitleInput);
        startTextInput = findViewById(R.id.termStartInput);
        endTextInput = findViewById(R.id.termEndInput);
        titleTextInput.setEnabled(canEdit);
        startTextInput.setEnabled(canEdit);
        endTextInput.setEnabled(canEdit);
        if (canEdit) {
            fabCheckSave.setVisibility(View.VISIBLE);
            fabAddCourse.setVisibility(View.INVISIBLE);
        } else {
            fabCheckSave.setVisibility(View.INVISIBLE);
            fabAddCourse.setVisibility(View.VISIBLE);
        }
    }

    private void setTermDetailData() {
        titleTextInput.setText(title);
        startTextInput.setText(startDt);
        endTextInput.setText(endDt);
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
        term = createTermDetailData();
        System.out.println("This term ID is- " + term.getTermID());
        studentSchedulerRepo = new StudentSchedulerRepo(getApplication());

        if(term.getTermID() == 0){
            studentSchedulerRepo.insert(term);
        }
        else{
            studentSchedulerRepo.update(term);
        }
        setTermTextEditable(false);
        //fabCheckSave.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        intentSetTermDetailData();
        //fabCheckSave.setVisibility(View.INVISIBLE);

        // Courses List on Terms Detail Page
        if (studentSchedulerRepo == null) {
            studentSchedulerRepo = new StudentSchedulerRepo(getApplication());
        }
        RecyclerView recyclerView = findViewById(R.id.coursesRV);
        final CoursesAdapter termCoursesAdapter = new CoursesAdapter(this);
        recyclerView.setAdapter(termCoursesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> termCourses = new ArrayList<>();
        for (Course course : studentSchedulerRepo.getAllCourses()) {
            if (course.getTermID() == termID) {
                termCourses.add(course);
            }
        }
        termCoursesAdapter.setCourses(termCourses);
        fabAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("ADD COURSE CLICKED FOR TERM ID: " + termID);
                com.thecodebarista.c196_studentscheduler.UI.CoursesAdapter.COURSE_EDIT_MODE = true;
                Intent intent = new Intent(TermDetailsActivity.this, CourseDetailsActivity.class);
                intent.putExtra("termID", termID);
                intent.putExtra("inEditMode", com.thecodebarista.c196_studentscheduler.UI.CoursesAdapter.COURSE_EDIT_MODE);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        getMenuInflater().inflate(R.menu.menu_term_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                System.out.println("Term to EDIT- " + termID);
                setTermTextEditable(true);
                //fabCheckSave.setVisibility(View.VISIBLE);
                return false;

            case R.id.delete:
                System.out.println("Term  to DELETE- " + termID);
                setTermTextEditable(false);
                //fabCheckSave.setVisibility(View.INVISIBLE);
                studentSchedulerRepo = new StudentSchedulerRepo(getApplication());

                for (Term term : studentSchedulerRepo.getAllTerms()) {
                    if (term.getTermID() == termID) {
                        selectedTerm = term;
                        break;
                    }
                }
                studentSchedulerRepo.delete(selectedTerm);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}