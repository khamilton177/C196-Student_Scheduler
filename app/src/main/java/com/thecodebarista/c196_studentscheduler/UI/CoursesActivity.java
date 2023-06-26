package com.thecodebarista.c196_studentscheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thecodebarista.c196_studentscheduler.R;
import com.thecodebarista.c196_studentscheduler.database.StudentSchedulerRepo;
import com.thecodebarista.c196_studentscheduler.entities.Course;
import com.thecodebarista.c196_studentscheduler.entities.Term;

import java.util.List;

public class CoursesActivity extends AppCompatActivity implements DegreePlanner {
    private StudentSchedulerRepo studentSchedulerRepo;

    /**
     * Sets the elements of for the recycler view and repository list.
     * @return List of all courses
     */
    private void courseRepoConstruct() {

        if (studentSchedulerRepo == null) {
            studentSchedulerRepo = new StudentSchedulerRepo(getApplication());
        }

        List<Course> allCourses = studentSchedulerRepo.getAllCourses();
        if (allCourses != null)
            System.out.println("Course List Size " + allCourses.size());
        RecyclerView recyclerView = findViewById(R.id.coursesRV);
        final CoursesAdapter coursesAdapter = new CoursesAdapter(this);
        recyclerView.setAdapter(coursesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        coursesAdapter.setCourses(allCourses);
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
        setContentView(R.layout.activity_courses);
        courseRepoConstruct();
        FloatingActionButton fab = findViewById(R.id.fabPlusAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.thecodebarista.c196_studentscheduler.UI.CoursesAdapter.COURSE_EDIT_MODE = true;
                Intent intent = new Intent(CoursesActivity.this, CourseDetailsActivity.class);
                intent.putExtra("inEditMode", com.thecodebarista.c196_studentscheduler.UI.CoursesAdapter.COURSE_EDIT_MODE);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        courseRepoConstruct();
    }
}