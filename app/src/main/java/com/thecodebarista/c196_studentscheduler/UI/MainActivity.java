package com.thecodebarista.c196_studentscheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.thecodebarista.c196_studentscheduler.R;
import com.thecodebarista.c196_studentscheduler.database.StudentSchedulerRepo;
import com.thecodebarista.c196_studentscheduler.entities.Instructor;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DegreePlanner {
    public static int alarmNumber;
    private StudentSchedulerRepo studentSchedulerRepo;
    final Calendar calendar = Calendar.getInstance();

    @Override
    public boolean finishCallback() {
        this.finish();
        return true;
    }

    @Override
    public void notifyReqCallback() {
        //nothing yet
    }
    public boolean loadInstructors() {
        studentSchedulerRepo = new StudentSchedulerRepo(getApplication());
        List<Instructor> allInstructors = studentSchedulerRepo.getAllInstructors();
        if (allInstructors.isEmpty()) {
            String[] instructors = getApplicationContext().getResources().getStringArray(R.array.course_instructors);

            for (String s : instructors) {
                String[] instructorArray = s.split(",");
                Instructor instructor = new Instructor(0, instructorArray[0], instructorArray[1], instructorArray[2]);
                studentSchedulerRepo.insert(instructor);
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadInstructors();
        Button enterBtn = findViewById(R.id.enterBtn);
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent intent=new Intent(MainActivity.this, HomeActivity.class);
                Intent intent=new Intent(MainActivity.this, TermsActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_seed, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.seedData:
                return loadInstructors();

            case R.id.createMultipleTerms:
                studentSchedulerRepo = new StudentSchedulerRepo(getApplication());
                seedTerms(calendar, studentSchedulerRepo);
        }
        return super.onOptionsItemSelected(item);
    }
}