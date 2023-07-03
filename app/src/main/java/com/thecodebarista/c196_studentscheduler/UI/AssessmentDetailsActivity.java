package com.thecodebarista.c196_studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thecodebarista.c196_studentscheduler.R;
import com.thecodebarista.c196_studentscheduler.database.StudentSchedulerRepo;
import com.thecodebarista.c196_studentscheduler.entities.Assessment;

import java.util.ArrayList;
import java.util.Calendar;

public class AssessmentDetailsActivity extends AppCompatActivity implements DegreePlanner {
    private EditText courseIdTextInput;
    private EditText assessmentTitleTextInput;
    private EditText typeTextInput;
    final Calendar dpStartDtCalendar = Calendar.getInstance();
    final Calendar dpEndDtCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dpStartDate;
    DatePickerDialog.OnDateSetListener dpEndDate;
    private EditText startTextInput;
    private EditText endTextInput;
    // private EditText statusTextInput;
    FloatingActionButton fabCheckSave;
    // FloatingActionButton fabAddAssessment;
    private int assessmentID;
    private int courseID;
    private int courseTitle; // not in model
    private String assessmentType;
    private String assessmentTitle;
    private String startDt;
    private String endDt;
    private Assessment.AssessmentType assessmentTypeEnum; // Not part of constructor
    //private Enum<Assessment.AssessmentType> assessmentTypeEnum; // Not part of constructor
    private boolean inEditMode;
    private boolean showSave;
    AssessmentsAdapter updateAdapter;
    Assessment selectedAssessment;
    int assessmentPosition;
    StudentSchedulerRepo studentSchedulerRepo;
    Spinner spinnerType;

    private void intentSetAssessmentDetailData() {
        studentSchedulerRepo = new StudentSchedulerRepo(getApplication());
        courseIdTextInput = findViewById(R.id.courseIdInput);
        typeTextInput = findViewById(R.id.assessmentTypeInput);
        assessmentTitleTextInput = findViewById(R.id.assessmentTitleInput);
        startTextInput = findViewById(R.id.assessmentStartInput);
        endTextInput = findViewById(R.id.assessmentEndInput);
        fabCheckSave = findViewById(R.id.fabCheckSave);
        // enableInputs();
        assessmentID = getIntent().getIntExtra("assessmentID",-1);
        courseID = getIntent().getIntExtra("courseID", -1);
        assessmentTitle = getIntent().getStringExtra("assessmentTitle");
        assessmentType = getIntent().getStringExtra("assessmentType");
        // assessmentTypeEnum = Assessment.AssessmentType.valueOf(getIntent().getStringExtra("statusEnum"));
        startDt = getIntent().getStringExtra("startDt");
        endDt = getIntent().getStringExtra("endDt");
        if (assessmentID > 0 ) {
            assessmentPosition = getIntent().getIntExtra("assessmentPosition", -1);
        }
        inEditMode = getIntent().getBooleanExtra("inEditMode", false);
        showSave = getIntent().getBooleanExtra("showSave", false);
        setAssessmentDetailData();
        setAssessmentTextEditable(inEditMode);

        if (inEditMode) {
            System.out.println("It's EDIT MODE");
        } else {
            System.out.println("It's READ ONLY");
        }
    }

    private void setAssessmentTextEditable(boolean canEdit) {
        courseIdTextInput = findViewById(R.id.courseIdInput);
        typeTextInput = findViewById(R.id.assessmentTypeInput);
        assessmentTitleTextInput = findViewById(R.id.assessmentTitleInput);
        startTextInput = findViewById(R.id.assessmentStartInput);
        endTextInput = findViewById(R.id.assessmentEndInput);
        assessmentTitleTextInput.setEnabled(canEdit);
        startTextInput.setEnabled(canEdit);
        endTextInput.setEnabled(canEdit);
        if (canEdit) {
            fabCheckSave.setVisibility(View.VISIBLE);
            //fabAddAssessment.setVisibility(View.INVISIBLE);
        } else {
            fabCheckSave.setVisibility(View.INVISIBLE);
            //fabAddAssessment.setVisibility(View.VISIBLE);
        }
    }

    private void setAssessmentDetailData() {
        courseIdTextInput.setText(String.valueOf(courseID));
        typeTextInput.setText(String.valueOf(assessmentType));
        assessmentTitleTextInput.setText(assessmentTitle);
        startTextInput.setText(startDt);
        endTextInput.setText(endDt);
        System.out.println("Course ID " + courseIdTextInput.getText());
        System.out.println("Assess Type " + typeTextInput.getText());
        System.out.println("Assess Title " + assessmentTitleTextInput.getText());
        System.out.println("Assess Start " + startTextInput.getText());
        System.out.println("Assess End " + endTextInput.getText());
    }

    private Assessment createAssessmentDetailData() {
        if(assessmentID == -1) {
            assessmentID = 0;
        }
        return new Assessment(assessmentID, Integer.parseInt(courseIdTextInput.getText().toString()),
                typeTextInput.getText().toString(), assessmentTitleTextInput.getText().toString(),
                startTextInput.getText().toString(), endTextInput.getText().toString());
    }

    public void onCheckSaveClick(View view) {
        Assessment assessment;
        System.out.println("Save button clicked. Assessment ID is- " + assessmentID);
        assessment = createAssessmentDetailData();
        System.out.println("This assessment ID is- " + assessment.getAssessmentID());
        studentSchedulerRepo = new StudentSchedulerRepo(getApplication());

        if(assessment.getAssessmentID() == 0){
            studentSchedulerRepo.insert(assessment);
        }
        else{
            studentSchedulerRepo.update(assessment);
            System.out.println("Assessment List INDEX: " + assessmentPosition);
        }

        //Process pending Notifications for Start date for new and updated Start dates.
        if ((assessment.getCourseID() == 0) || (assessment.getCourseID() > 0 && !startTextInput.getText().toString().equals(assessment.getStartDt()))) {
            Long startTrigger = CreateTriggerDate(startTextInput.getText().toString());
            CreatePendingIntent(AssessmentDetailsActivity.this, startTrigger, assessmentTitleTextInput.getText().toString(), ASSESSMENT_START_DATE_NOTIFY, startTextInput.getText().toString());
        }
        //Process pending Notifications for End date for new and updated End dates.
        if ((assessment.getCourseID() == 0) || (assessment.getCourseID() > 0 && !endTextInput.getText().toString().equals(assessment.getEndDt()))) {
            Long endTrigger = CreateTriggerDate(endTextInput.getText().toString());
            CreatePendingIntent(AssessmentDetailsActivity.this, endTrigger, assessmentTitleTextInput.getText().toString(), ASSESSMENT_END_DATE_NOTIFY, endTextInput.getText().toString());
        }

        if (assessment.getAssessmentID() == 0) {
            studentSchedulerRepo.getLastAssessmentInsert();
        }
        setAssessmentTextEditable(false);
        spinnerType.setEnabled(false);
        //finish();
    }

    @Override
    public boolean finishCallback() {
        this.finish();
        return true;
    }

    @Override
    public void notifyReqCallback() {
        Toast.makeText(AssessmentDetailsActivity.this, R.string.assessmentNotifyConf, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        intentSetAssessmentDetailData();

        // Create Types list for Spinner
        ArrayList<String> assessmentTypes = new ArrayList<>();
        Assessment.AssessmentType[] enumValues = Assessment.AssessmentType.values();
        String typeDefault = " ";
        int pos = 0;
        assessmentTypes.add(typeDefault);
        for (Assessment.AssessmentType v : enumValues) {
            assessmentTypes.add(v.name().toString());
        }

        //Create Assessment Type Spinner (dropdown)
        spinnerType = findViewById(R.id.assessmentTypeSpinner);
        ArrayAdapter assessmentTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, assessmentTypes);
        spinnerType.setAdapter(assessmentTypeAdapter);
        if (assessmentID > 0) {
            spinnerType.setEnabled(true);
            if (assessmentTypes.indexOf(typeTextInput.getText().toString()) > -1) {
                pos = assessmentTypes.indexOf(typeTextInput.getText().toString());
            }
            System.out.println("Position is " + pos);
            //typeDefault = typeTextInput.getText().toString();
        }
        spinnerType.setSelection(pos);
        spinnerType.setEnabled(fabCheckSave.getVisibility() == View.VISIBLE);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                String val;
                val = assessmentTypeAdapter.getItem(i).toString();
                System.out.println("Type value: " + val);
                // typeTextInput.setText(String.format("%s", Assessment.AssessmentType.values()[spinnerType.getSelectedItemPosition()])); //set input field to string of ENUM for DB.
                typeTextInput.setText(String.format("%s", assessmentTypeAdapter.getItem(i).toString())); //set input field to string of ENUM for DB.
                //System.out.println(String.format("Type ENUM to string? %s", Assessment.AssessmentType.values()[spinnerType.getSelectedItemPosition()-1])); //GOOD
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                new DatePickerDialog(AssessmentDetailsActivity.this, dpStartDate, dpStartDtCalendar
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
                new DatePickerDialog(AssessmentDetailsActivity.this, dpEndDate, dpEndDtCalendar
                        .get(Calendar.YEAR), dpEndDtCalendar.get(Calendar.MONTH),
                        dpEndDtCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                System.out.println("Assessment to EDIT- " + assessmentID);
                System.out.println("Assessment Course- " + courseID);
                System.out.println("Assessment Status Spinner- " + typeTextInput.getText().toString());
                setAssessmentTextEditable(true);
                spinnerType.setEnabled(true);
                return false;

            case R.id.delete:
                System.out.println("Assessment  to DELETE- " + assessmentID);
                setAssessmentTextEditable(false);
                studentSchedulerRepo = new StudentSchedulerRepo(getApplication());

                for (Assessment assessment : studentSchedulerRepo.getAllAssessments()) {
                    if (assessment.getAssessmentID() == assessmentID) {
                        selectedAssessment = assessment;
                        break;
                    }
                }
                // studentSchedulerRepo.delete(selectedAssessment);
                if (selectedAssessment != null) {
                    ConfirmDeleteDialog(AssessmentDetailsActivity.this, studentSchedulerRepo, selectedAssessment);
                }
                return false;

            case R.id.assessmentNotify:
                createManualNotify(AssessmentDetailsActivity.this, "Assessment", assessmentTitleTextInput.getText().toString(), startTextInput.getText().toString(), endTextInput.getText().toString());
                return false;
        }
        return super.onOptionsItemSelected(item);
    }
}