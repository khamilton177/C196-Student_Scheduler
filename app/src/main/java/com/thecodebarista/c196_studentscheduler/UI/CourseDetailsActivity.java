package com.thecodebarista.c196_studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thecodebarista.c196_studentscheduler.R;
import com.thecodebarista.c196_studentscheduler.database.StudentSchedulerRepo;
import com.thecodebarista.c196_studentscheduler.entities.Assessment;
import com.thecodebarista.c196_studentscheduler.entities.Course;
import com.thecodebarista.c196_studentscheduler.entities.Instructor;
import com.thecodebarista.c196_studentscheduler.entities.Term;

import java.util.ArrayList;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity {
    private EditText termIdTextInput;
    private EditText titleTextInput;
    private EditText startTextInput;
    private EditText endTextInput;
    private EditText statusTextInput;
    private EditText instructorIdTextInput;
    private EditText notesTextInput;
    FloatingActionButton fabCheckSave;
    FloatingActionButton fabAddAssessment;
    private int courseID;
    private int termID;
    private int termTitle; // not in model
    private String title;
    private String status;
    private Enum<Course.CourseStatus> statusEnum;
    private String startDt;
    private String endDt;
    private int instructorID;
    private String instructor; // not in model
    private String notes;
    private boolean inEditMode;
    private boolean showSave;
    Term selectedTerm;
    Course selectedCourse;
    Assessment selectedAssessment;
    Instructor selectedInstructor;
    StudentSchedulerRepo studentSchedulerRepo;

    private void intentSetCourseDetailData() {
        termIdTextInput = findViewById(R.id.termIdInput);
        titleTextInput = findViewById(R.id.courseTitleInput);
        statusTextInput = findViewById(R.id.courseStatusInput);
        startTextInput = findViewById(R.id.courseStartInput);
        endTextInput = findViewById(R.id.courseEndInput);
        instructorIdTextInput = findViewById(R.id.courseInstructorInput);
        notesTextInput = findViewById(R.id.courseNotesInput);
        fabCheckSave = findViewById(R.id.fabCheckSave);
        fabAddAssessment = findViewById(R.id.fabAddAssessment);
        courseID = getIntent().getIntExtra("courseID",-1);
        termID = getIntent().getIntExtra("termID", -1);
        title = getIntent().getStringExtra("title");
        status = getIntent().getStringExtra("status");
        // statusEnum = Course.CourseStatus.valueOf(getIntent().getStringExtra("statusEnum"));
        startDt = getIntent().getStringExtra("startDt");
        endDt = getIntent().getStringExtra("endDt");
        instructorID = getIntent().getIntExtra("instructorID",-1);
        notes = getIntent().getStringExtra("notes");
        inEditMode = getIntent().getBooleanExtra("inEditMode", false);
        showSave = getIntent().getBooleanExtra("showSave", false);
        studentSchedulerRepo = new StudentSchedulerRepo(getApplication());
        setCourseDetailData();
        setCourseTextEditable(inEditMode);

        if (inEditMode) {
            System.out.println("It's EDIT MODE");
        } else {
            System.out.println("It's READ ONLY");
        }
    }

    private void setCourseTextEditable(boolean canEdit) {
        termIdTextInput = findViewById(R.id.termIdInput);
        titleTextInput = findViewById(R.id.courseTitleInput);
        statusTextInput = findViewById(R.id.courseStatusInput);
        startTextInput = findViewById(R.id.courseStartInput);
        endTextInput = findViewById(R.id.courseEndInput);
        instructorIdTextInput = findViewById(R.id.courseInstructorInput);
        notesTextInput = findViewById(R.id.courseNotesInput);
        titleTextInput.setEnabled(canEdit);
        startTextInput.setEnabled(canEdit);
        endTextInput.setEnabled(canEdit);
        instructorIdTextInput.setEnabled(canEdit);
        notesTextInput.setEnabled(canEdit);
        if (canEdit) {
            fabCheckSave.setVisibility(View.VISIBLE);
            fabAddAssessment.setVisibility(View.INVISIBLE);
        } else {
            fabCheckSave.setVisibility(View.INVISIBLE);
            fabAddAssessment.setVisibility(View.VISIBLE);
        }
    }

    private void setCourseDetailData() {
        termIdTextInput.setText(String.valueOf(termID));
        titleTextInput.setText(title);
        statusTextInput.setText(status);
        startTextInput.setText(startDt);
        endTextInput.setText(endDt);
        instructorIdTextInput.setText(String.valueOf(instructorID));
        notesTextInput.setText(notes);
        System.out.println(termIdTextInput.getText());
        System.out.println(titleTextInput.getText());
        System.out.println(statusTextInput.getText());
        System.out.println(startTextInput.getText());
        System.out.println(endTextInput.getText());
        System.out.println(instructorIdTextInput.getText());
        System.out.println(notesTextInput.getText());
    }

    private Course createCourseDetailData() {
        if(courseID == -1) {
            courseID = 0;
        }
        return new Course(courseID, Integer.parseInt(termIdTextInput.getText().toString()),
                titleTextInput.getText().toString(), startTextInput.getText().toString(),
                endTextInput.getText().toString(), statusTextInput.getText().toString(),
                notesTextInput.getText().toString(), Integer.parseInt(instructorIdTextInput.getText().toString()));
    }

    public void onCheckSaveClick(View view) {
        Course course;
        System.out.println("Save button clicked. Course ID is- " + courseID);
        course = createCourseDetailData();
        System.out.println("This course ID is- " + course.getCourseID());
        studentSchedulerRepo = new StudentSchedulerRepo(getApplication());

        if(course.getCourseID() == 0){
            studentSchedulerRepo.insert(course);
        }
        else{
            studentSchedulerRepo.update(course);
        }
        setCourseTextEditable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        intentSetCourseDetailData();

        // Create Statuses list for Spinner
        ArrayList<String> courseStatuses = new ArrayList<>();
        Course.CourseStatus[] enumsValues = Course.CourseStatus.values();
        for (Course.CourseStatus v : enumsValues) {
            courseStatuses.add(v.getLabel());
        }

        //Create Status Spinner (dropdown)
        Spinner spinnerStatus = findViewById(R.id.statusSpinner);
        ArrayAdapter courseStatusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseStatuses);
        spinnerStatus.setAdapter(courseStatusAdapter);
        String statusDefault = "Plan to Take";
        spinnerStatus.setSelection(courseStatusAdapter.getPosition(statusDefault));
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String val;
                val = courseStatusAdapter.getItem(i).toString();
                System.out.println("STATUS LABEL: " + val);
                statusTextInput.setText(String.format("%s", Course.CourseStatus.values()[spinnerStatus.getSelectedItemPosition()])); //set input field to string of ENUM for DB.
                System.out.println(String.format("DEFAULT STATUS ENUM? %s", Course.CourseStatus.values()[spinnerStatus.getSelectedItemPosition()])); //GOOD
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String statusDefault = "Plan to Take";
            }
        });

        //Create Instructor spinner (dropdown)
        studentSchedulerRepo = new StudentSchedulerRepo(getApplication());
        Spinner spinnerInstructor = findViewById(R.id.instructorIdSpinner);
        ArrayAdapter<Instructor> courseInstructorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, studentSchedulerRepo.getAllInstructors());
        spinnerInstructor.setAdapter(courseInstructorAdapter);
        spinnerInstructor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String val;
                // set dropdown value to empty or user selected value.
                val = (courseInstructorAdapter.getItem(i) != null ? courseInstructorAdapter.getItem(i).getName() : "");
                if (val != null) {
                    System.out.println("INSTRUCTOR LABEL: " + val);
                    instructorIdTextInput.setText(String.format("%s", courseInstructorAdapter.getItem(i).getInstructorID()));
                    System.out.println(String.format("INSTRUCTOR ID %d", courseInstructorAdapter.getItem(i).getInstructorID()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("Do nothing for now");
            }
        });

        // Assessments List on Courses Detail Page
        if (studentSchedulerRepo == null) {
            studentSchedulerRepo = new StudentSchedulerRepo(getApplication());
        }
        RecyclerView recyclerView = findViewById(R.id.assessmentsRV);
        final AssessmentsAdapter courseAssessmentsAdapter = new AssessmentsAdapter(this);
        recyclerView.setAdapter(courseAssessmentsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> courseAssessments = new ArrayList<>();
        for (Assessment assessment : studentSchedulerRepo.getAllAssessments()) {
            if (assessment.getCourseID() == courseID) {
                courseAssessments.add(assessment);
            }
        }
        courseAssessmentsAdapter.setAssessments(courseAssessments);
        fabAddAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("ADD ASSESSMENT CLICKED FOR COURSE- " + courseID);
                Intent intent = new Intent(CourseDetailsActivity.this, AssessmentDetailsActivity.class);
                intent.putExtra("courseID", courseID);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                System.out.println("Course to EDIT- " + courseID);
                setCourseTextEditable(true);
                // fabCheckSave.setVisibility(View.VISIBLE);
                return false;

            case R.id.delete:
                System.out.println("Course  to DELETE- " + courseID);
                setCourseTextEditable(false);
                // fabCheckSave.setVisibility(View.INVISIBLE);
                studentSchedulerRepo = new StudentSchedulerRepo(getApplication());

                for (Course course : studentSchedulerRepo.getAllCourses()) {
                    if (course.getCourseID() == courseID) {
                        selectedCourse = course;
                        break;
                    }
                }
                studentSchedulerRepo.delete(selectedCourse);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}