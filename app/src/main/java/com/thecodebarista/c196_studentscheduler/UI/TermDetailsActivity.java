package com.thecodebarista.c196_studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.window.SplashScreen;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thecodebarista.c196_studentscheduler.R;
import com.thecodebarista.c196_studentscheduler.database.StudentSchedulerRepo;
import com.thecodebarista.c196_studentscheduler.entities.Course;
import com.thecodebarista.c196_studentscheduler.entities.Term;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TermDetailsActivity extends AppCompatActivity implements DegreePlanner{
    protected static boolean TERM_EDIT_MODE;
    private static final String SAVED_STATE = "savedState";
    final Calendar dpStartDtCalendar = Calendar.getInstance();
    final Calendar dpEndDtCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dpStartDate;
    DatePickerDialog.OnDateSetListener dpEndDate;
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
    RecyclerView recyclerView;

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
        System.out.println("Term Title " + titleTextInput.getText());
        System.out.println("Term Start " + startTextInput.getText());
        System.out.println("Term End " + endTextInput.getText());
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

        if (term.getTermID() == 0) {
            termID = studentSchedulerRepo.getLastTermInsert();
        }
        setTermTextEditable(false);
        com.thecodebarista.c196_studentscheduler.UI.TermDetailsActivity.TERM_EDIT_MODE = false;
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
        setContentView(R.layout.activity_term_details);
        intentSetTermDetailData();

        if (savedInstanceState != null) {
            setTermTextEditable(TERM_EDIT_MODE);
        }

        // Courses List on Terms Detail Page
        if (studentSchedulerRepo == null) {
            studentSchedulerRepo = new StudentSchedulerRepo(getApplication());
        }
        recyclerView = findViewById(R.id.coursesRV);
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
                //Launching Add Course from TermDetails after just creating term
                if (termID == 0) {
                    termID = studentSchedulerRepo.getLastTermInsert();
                    System.out.println("ADD COURSE CLICKED FOR NEWLY INSERTED TERM- " + termID);
                } else {
                    System.out.println("ADD COURSE CLICKED. TERM ID: " + termID);
                }

                System.out.println("ADD COURSE CLICKED FOR TERM- " + termID);
                com.thecodebarista.c196_studentscheduler.UI.CourseDetailsActivity.COURSE_EDIT_MODE = true;
                Intent intent = new Intent(TermDetailsActivity.this, CourseDetailsActivity.class);
                intent.putExtra("termID", termID);
                intent.putExtra("inEditMode", true);
                startActivity(intent);
            }
        });

        // Start Date DataPicker Listener
        dpStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                dpStartDtCalendar.set(Calendar.YEAR, year);
                dpStartDtCalendar.set(Calendar.MONTH, monthOfYear);
                dpStartDtCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                startTextInput.setText(dateFormatter.format(dpStartDtCalendar.getTime()));
            }
        };

        // Start Date Input Field Listener
        startTextInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TermDetailsActivity.this, dpStartDate, dpStartDtCalendar
                        .get(Calendar.YEAR), dpStartDtCalendar.get(Calendar.MONTH),
                        dpStartDtCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // End Date DataPicker Listener
        dpEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                dpEndDtCalendar.set(Calendar.YEAR, year);
                dpEndDtCalendar.set(Calendar.MONTH, monthOfYear);
                dpEndDtCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                endTextInput.setText(dateFormatter.format(dpEndDtCalendar.getTime()));
            }
        };

        // End Date Input Field Listener
        endTextInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TermDetailsActivity.this, dpEndDate, dpEndDtCalendar
                        .get(Calendar.YEAR), dpEndDtCalendar.get(Calendar.MONTH),
                        dpEndDtCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (studentSchedulerRepo == null) {
            studentSchedulerRepo = new StudentSchedulerRepo(getApplication());
        }

        List<Course> allTermCourses = studentSchedulerRepo.getAllTermCourses(termID);
        if (allTermCourses != null)
            System.out.println("Course List Size " + allTermCourses.size());
        final CoursesAdapter coursesAdapter = new CoursesAdapter(this);
        recyclerView.setAdapter(coursesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        coursesAdapter.setCourses(allTermCourses);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        //getMenuInflater().inflate(R.menu.menu_term_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                System.out.println("Term to EDIT- " + termID);
                com.thecodebarista.c196_studentscheduler.UI.TermDetailsActivity.TERM_EDIT_MODE = true;
                setTermTextEditable(true);
                return false;

            case R.id.delete:
                System.out.println("Term  to DELETE- " + termID);
                setTermTextEditable(false);
                studentSchedulerRepo = new StudentSchedulerRepo(getApplication());

                if (studentSchedulerRepo.getAllTermCourses(termID).size() > 0) {
                    Toast.makeText(TermDetailsActivity.this, "Cannot delete term with courses assigned.", Toast.LENGTH_LONG).show();
                } else {
                    for (Term term : studentSchedulerRepo.getAllTerms()) {
                        if (term.getTermID() == termID) {
                            selectedTerm = term;
                            break;
                        }
                    }
                    if (selectedTerm != null) {
                        ConfirmDeleteDialog(TermDetailsActivity.this, studentSchedulerRepo, selectedTerm);
                    }
                }
                return false;

            case R.id.deleteAllTermCourses:
                System.out.println("Term Courses to DELETE- " + termID);
                setTermTextEditable(false);
                //fabCheckSave.setVisibility(View.INVISIBLE);
                studentSchedulerRepo = new StudentSchedulerRepo(getApplication());

                for (Course course : studentSchedulerRepo.getAllTermCourses(termID)) {
                    if (course.getTermID() == termID) {
                        selectedCourse = course;
                        System.out.println("Deleting Course " + selectedCourse.getCourseID());
                        studentSchedulerRepo.delete(selectedCourse);
                    }
                }
                finish();
                //return true;
        }
        return super.onOptionsItemSelected(item);
    }
}