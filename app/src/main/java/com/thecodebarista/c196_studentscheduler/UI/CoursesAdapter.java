package com.thecodebarista.c196_studentscheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thecodebarista.c196_studentscheduler.R;
import com.thecodebarista.c196_studentscheduler.dao.InstructorDao;
import com.thecodebarista.c196_studentscheduler.database.StudentSchedulerRepo;
import com.thecodebarista.c196_studentscheduler.entities.Course;
import com.thecodebarista.c196_studentscheduler.entities.Instructor;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseViewHolder> {
    private List<Course> Courses;
    private final Context context;
    private final LayoutInflater inflater;

    public CoursesAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setCourses(List<Course> courses) {
        Courses = courses;
        notifyDataSetChanged();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.courseItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    final Course latest = Courses.get(position);
                    com.thecodebarista.c196_studentscheduler.UI.CourseDetailsActivity.COURSE_EDIT_MODE = false;
                    Intent intent= new Intent(context,CourseDetailsActivity.class);
                    intent.putExtra("courseID", latest.getCourseID());
                    System.out.println("Selected - " + latest.getTermID());
                    intent.putExtra("termID", latest.getTermID());
                    intent.putExtra("title", latest.getTitle());
                    intent.putExtra("status", latest.getStatus());
                    intent.putExtra("startDt", latest.getStartDt());
                    intent.putExtra("endDt", latest.getEndDt());
                    intent.putExtra("instructorID", latest.getInstructorID());
                    intent.putExtra("spinnerInstructor", latest.getInstructorID());
                    intent.putExtra("notes", latest.getNotes());
                    intent.putExtra("coursePosition", position);
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if (Courses != null) {
            Course latest = Courses.get(position);
            holder.courseItemView.setText(latest.getTitle());
        } else {
            holder.courseItemView.setText(R.string.no_course);
        }
    }

    @Override
    public int getItemCount() {
        if (Courses != null) {
            return Courses.size();
        } else {
            return 0;
        }
    }

}
