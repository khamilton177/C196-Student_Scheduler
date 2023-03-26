package com.thecodebarista.c196_studentscheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.thecodebarista.c196_studentscheduler.R;
import com.thecodebarista.c196_studentscheduler.database.StudentSchedulerRepo;
import com.thecodebarista.c196_studentscheduler.entities.Instructor;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private StudentSchedulerRepo studentSchedulerRepo;

    public boolean loadInstructors() {
        studentSchedulerRepo = new StudentSchedulerRepo(getApplication());
        List<Instructor> allInstructors = studentSchedulerRepo.getAllInstructors();
        if (allInstructors.isEmpty()) {
            String[] instructors = getApplicationContext().getResources().getStringArray(R.array.course_instructors);
            System.out.println("DOING INSTRUCTOR ARRAY");
            System.out.println("FROM INSTRUCTOR ARRAY: " + instructors.length);

            //Instructor instructor = new Instructor(0, "Professor Peabody", "347-555-2456", "prfpeadboyd@any.edu");
            //studentSchedulerRepo.insert(instructor);

            for (String s : instructors) {
                String[] instructorArray = s.split(",");
                System.out.println("FROM INSTRUCTOR NAME: " + instructorArray[0]);
                System.out.println("FROM INSTRUCTOR PHONE: " + instructorArray[1]);
                System.out.println("FROM INSTRUCTOR EMAIL: " + instructorArray[2]);

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
        Button enterBtn = findViewById(R.id.enterBtn);
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent intent=new Intent(MainActivity.this, HomeActivity.class);
                TermsAdapter.TERM_EDIT_MODE = true;
                Intent intent=new Intent(MainActivity.this, TermsActivity.class);
                intent.putExtra("isReadOnly", TermsAdapter.TERM_EDIT_MODE);
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
        }
        return super.onOptionsItemSelected(item);
    }
}