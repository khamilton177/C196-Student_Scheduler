package com.thecodebarista.c196_studentscheduler.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thecodebarista.c196_studentscheduler.R;
import com.thecodebarista.c196_studentscheduler.database.StudentSchedulerRepo;
import com.thecodebarista.c196_studentscheduler.entities.Assessment;
import com.thecodebarista.c196_studentscheduler.entities.Course;
import com.thecodebarista.c196_studentscheduler.entities.Instructor;
import com.thecodebarista.c196_studentscheduler.entities.Term;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity implements DegreePlanner {
    private EditText termIdTextInput;
    private EditText titleTextInput;
    final Calendar dpStartDtCalendar = Calendar.getInstance();
    final Calendar dpEndDtCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dpStartDate;
    DatePickerDialog.OnDateSetListener dpEndDate;
    private EditText startTextInput;
    private EditText endTextInput;
    private EditText statusTextInput;
    private EditText instructorIdTextInput;
    private TextView instructorPhone;
    private TextView instructorEmail;
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
    private String instructorName; // not in model
    private String notes;
    private boolean inEditMode;
    private boolean showSave;
    CoursesAdapter updateAdapter;
    Term selectedTerm;
    Course selectedCourse;
    int coursePosition;
    Assessment selectedAssessment;
    Instructor selectedInstructor;
    StudentSchedulerRepo studentSchedulerRepo;
    RecyclerView recyclerView;
    Spinner spinnerStatus;
    Spinner spinnerInstructor;

    private void intentSetCourseDetailData() {
        studentSchedulerRepo = new StudentSchedulerRepo(getApplication());
        termIdTextInput = findViewById(R.id.termIdInput);
        titleTextInput = findViewById(R.id.courseTitleInput);
        statusTextInput = findViewById(R.id.courseStatusInput);
        startTextInput = findViewById(R.id.courseStartInput);
        endTextInput = findViewById(R.id.courseEndInput);
        instructorIdTextInput = findViewById(R.id.courseInstructorIdInput);
        notesTextInput = findViewById(R.id.courseNotesInput);
        fabCheckSave = findViewById(R.id.fabCheckSave);
        fabAddAssessment = findViewById(R.id.fabAddAssessment);
        instructorPhone = findViewById(R.id.courseInstructorPhone);
        instructorEmail = findViewById(R.id.courseInstructorEmail);
        courseID = getIntent().getIntExtra("courseID",-1);
        termID = getIntent().getIntExtra("termID", -1);
        title = getIntent().getStringExtra("title");
        status = getIntent().getStringExtra("status");
        startDt = getIntent().getStringExtra("startDt");
        endDt = getIntent().getStringExtra("endDt");
        instructorID = getIntent().getIntExtra("instructorID",-1);
        if (courseID > 0 ) {
            selectedInstructor = studentSchedulerRepo.getInstructor(getIntent().getIntExtra("spinnerInstructor", -1));
            coursePosition = getIntent().getIntExtra("coursePosition", -1);
            instructorPhone.setText(String.format("Phone: %s", selectedInstructor.getPhoneNumber()));
            instructorEmail.setText(String.format("Email: %s", selectedInstructor.getEmail()));
        }
        notes = getIntent().getStringExtra("notes");
        inEditMode = getIntent().getBooleanExtra("inEditMode", false);
        showSave = getIntent().getBooleanExtra("showSave", false);
        setCourseDetailData();
        setCourseTextEditable(inEditMode);

        if (inEditMode) {
            System.out.println("It's EDIT MODE");
        } else {
            System.out.println("It's READ ONLY");
        }
    }

    protected void setCourseTextEditable(boolean canEdit) {
        termIdTextInput = findViewById(R.id.termIdInput);
        titleTextInput = findViewById(R.id.courseTitleInput);
        statusTextInput = findViewById(R.id.courseStatusInput);
        startTextInput = findViewById(R.id.courseStartInput);
        endTextInput = findViewById(R.id.courseEndInput);
        instructorIdTextInput = findViewById(R.id.courseInstructorIdInput);
        notesTextInput = findViewById(R.id.courseNotesInput);
        titleTextInput.setEnabled(canEdit);
        startTextInput.setEnabled(canEdit);
        endTextInput.setEnabled(canEdit);
        instructorIdTextInput.setEnabled(canEdit);
        notesTextInput.setEnabled(canEdit);
        if (canEdit) {
            fabCheckSave.setVisibility(View.VISIBLE);
            fabAddAssessment.setVisibility(View.INVISIBLE);
            instructorPhone.setVisibility(View.GONE);
            instructorEmail.setVisibility(View.GONE);
        } else {
            fabCheckSave.setVisibility(View.GONE);
            fabAddAssessment.setVisibility(View.VISIBLE);
            instructorPhone.setVisibility(View.VISIBLE);
            instructorEmail.setVisibility(View.VISIBLE);
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
        System.out.println("Term ID " + termIdTextInput.getText());
        System.out.println("Course Title " + titleTextInput.getText());
        System.out.println("Course Status " + statusTextInput.getText());
        System.out.println("Course Start " + startTextInput.getText());
        System.out.println("Course End " + endTextInput.getText());
        System.out.println("Instructor ID " + instructorIdTextInput.getText());
        System.out.println("Course Notes " + notesTextInput.getText());
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
            System.out.println("Course List INDEX: " + coursePosition);
        }

        //Process pending Notifications for Start date for new and updated Start dates.
        if ((course.getCourseID() == 0) || (course.getCourseID() > 0 && !startTextInput.getText().toString().equals(course.getStartDt()))) {
            Long startTrigger = CreateTriggerDate(startTextInput.getText().toString());
            CreatePendingIntent(CourseDetailsActivity.this, startTrigger, titleTextInput.getText().toString(), COURSE_START_DATE_NOTIFY, startTextInput.getText().toString());
        }

        //Process pending Notifications for End date for new and updated End dates.
        if ((course.getCourseID() == 0) || (course.getCourseID() > 0 && !endTextInput.getText().toString().equals(course.getEndDt()))) {
            Long endTrigger = CreateTriggerDate(endTextInput.getText().toString());
            CreatePendingIntent(CourseDetailsActivity.this, endTrigger, titleTextInput.getText().toString(), COURSE_END_DATE_NOTIFY, endTextInput.getText().toString());
        }

        selectedInstructor = studentSchedulerRepo.getInstructor(Integer.parseInt(instructorIdTextInput.getText().toString()));
        instructorPhone.setText(String.format("Phone: %s", selectedInstructor.getPhoneNumber()));
        instructorEmail.setText(String.format("Email: %s", selectedInstructor.getEmail()));
        if (course.getCourseID() == 0) {
            courseID = studentSchedulerRepo.getLastCourseInsert();
        }
        setCourseTextEditable(false);
        spinnerStatus.setEnabled(false);
        spinnerInstructor.setEnabled(false);
    }

    @Override
    public boolean finishCallback() {
        this.finish();
        return true;
    }

    @Override
    public void notifyReqCallback() {
        Toast.makeText(CourseDetailsActivity.this, R.string.courseNotifyConf, Toast.LENGTH_LONG).show();
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
        spinnerStatus = findViewById(R.id.statusSpinner);
        ArrayAdapter courseStatusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseStatuses);
        spinnerStatus.setAdapter(courseStatusAdapter);
        String statusDefault = "Plan to Take";
        if (courseID > 0) {
            spinnerStatus.setEnabled(true);
            statusDefault = Course.CourseStatus.getCourseStatus(statusTextInput.getText().toString()).getLabel();
        }
        spinnerStatus.setSelection(courseStatusAdapter.getPosition(statusDefault));
        spinnerStatus.setEnabled(fabCheckSave.getVisibility() == View.VISIBLE);
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

            }
        });

        //Create Instructor spinner (dropdown)
        spinnerInstructor = findViewById(R.id.instructorIdSpinner);
        studentSchedulerRepo = new StudentSchedulerRepo(getApplication());
        ArrayAdapter<Instructor> courseInstructorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, studentSchedulerRepo.getAllInstructors());
        spinnerInstructor.setAdapter(courseInstructorAdapter);
        int pos = 0;
        if (courseID > 0) {
            spinnerInstructor.setEnabled(true);
            for (int count = 0; count < courseInstructorAdapter.getCount(); count++) {
                if (courseInstructorAdapter.getItem(count).toString().equals(selectedInstructor.toString())) {
                    System.out.println(String.format("Found %s \nCount is #%d", courseInstructorAdapter.getItem(count).toString(), count));
                    pos = count;
                    break;
                }
            }
            spinnerInstructor.setSelection(pos);
        }
        spinnerInstructor.setEnabled(fabCheckSave.getVisibility() == View.VISIBLE);
        spinnerInstructor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String val;
                val = (!courseInstructorAdapter.isEmpty() ? courseInstructorAdapter.getItem(i).getName() : "");
                System.out.println("SPINNER VALUE " + courseInstructorAdapter.getItem(i).toString());
                if (val != null) {
                    System.out.println("INSTRUCTOR LABEL: " + val);
                    instructorIdTextInput.setText(String.format("%s", courseInstructorAdapter.getItem(i).getInstructorID()));
                    // instructorNameTextInput.setText(val);
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
        recyclerView = findViewById(R.id.assessmentsRV);
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
        fabAddAssessment.setTooltipText("Add Assessment");
        fabAddAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Launching Add Assessment from CourseDetails after just creating course
                if (courseID == 0) {
                    courseID = studentSchedulerRepo.getLastCourseInsert();
                    System.out.println("ADD ASSESSMENT CLICKED FOR NEWLY INSERTED COURSE- " + courseID);
                } else {
                    System.out.println("ADD ASSESSMENT CLICKED. COURSE ID: " + courseID);
                }

                com.thecodebarista.c196_studentscheduler.UI.AssessmentsAdapter.ASSESSMENT_EDIT_MODE = true;
                Intent intent = new Intent(CourseDetailsActivity.this, AssessmentDetailsActivity.class);
                intent.putExtra("courseID", courseID);
                intent.putExtra("inEditMode", com.thecodebarista.c196_studentscheduler.UI.AssessmentsAdapter.ASSESSMENT_EDIT_MODE);
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
                new DatePickerDialog(CourseDetailsActivity.this, dpStartDate, dpStartDtCalendar
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
                new DatePickerDialog(CourseDetailsActivity.this, dpEndDate, dpEndDtCalendar
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

        List<Assessment> allCourseAssessments = studentSchedulerRepo.getAllCourseAssessments(courseID);
        if (allCourseAssessments != null)
            System.out.println("Assessment List Size" + allCourseAssessments.size());
        final AssessmentsAdapter assessmentsAdapter = new AssessmentsAdapter(this);
        recyclerView.setAdapter(assessmentsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentsAdapter.setAssessments(allCourseAssessments);
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
                System.out.println("Course Term- " + termID);
                System.out.println("Course Instructor- " + instructorID);
                System.out.println("Course Status Spinner- " + statusTextInput.getText().toString());
                setCourseTextEditable(true);
                spinnerStatus.setEnabled(true);
                spinnerInstructor.setEnabled(true);
                return false;

            case R.id.delete:
                System.out.println("Course  to DELETE- " + courseID);
                setCourseTextEditable(false);
                studentSchedulerRepo = new StudentSchedulerRepo(getApplication());

                if (studentSchedulerRepo.getAllCourseAssessments(courseID).size() > 0) {
                    Toast.makeText(CourseDetailsActivity.this, "Cannot delete course with assessments assigned.", Toast.LENGTH_LONG).show();
                } else {
                    for (Course course : studentSchedulerRepo.getAllCourses()) {
                        if (course.getCourseID() == courseID) {
                            selectedCourse = course;
                            break;
                        }
                    }
                    // studentSchedulerRepo.delete(selectedCourse);
                    if (selectedCourse != null) {
                        ConfirmDeleteDialog(CourseDetailsActivity.this, studentSchedulerRepo, selectedCourse);
                    }
                }
                return false;

            case R.id.shareNotes:
                String shareTitle = String.format("Notes for %s:", titleTextInput.getText().toString());
                Intent shareNotes = ShareMessage(shareTitle, notesTextInput.getText().toString());
                startActivity(shareNotes);
                return true;

            case R.id.courseNotify:
                createManualNotify(CourseDetailsActivity.this, "Course", titleTextInput.getText().toString(), startTextInput.getText().toString(), endTextInput.getText().toString());
                return false;
        }
        return super.onOptionsItemSelected(item);
    }
}