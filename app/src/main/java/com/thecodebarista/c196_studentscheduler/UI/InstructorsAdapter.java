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
import com.thecodebarista.c196_studentscheduler.entities.Course;
import com.thecodebarista.c196_studentscheduler.entities.Instructor;

import java.util.List;

public class InstructorsAdapter extends RecyclerView.Adapter<InstructorsAdapter.InstructorViewHolder> {

    private List<Instructor> Instructors;
    private final Context context;
    private final LayoutInflater inflater;

    public InstructorsAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setInstructors(List<Instructor> instructors) {
        Instructors = instructors;
        notifyDataSetChanged();
    }

    class InstructorViewHolder extends RecyclerView.ViewHolder {
        private final TextView instructorItemView;

        public InstructorViewHolder(@NonNull View itemView) {
            super(itemView);
            instructorItemView = itemView.findViewById(R.id.instructorItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    final Instructor latest = Instructors.get(position);
                    Intent intent= new Intent(context,CourseDetailsActivity.class);
                    intent.putExtra("instructorID", latest.getInstructorID());
                    System.out.println("Selected - " + latest.getInstructorID());
                    // intent.putExtra("courseID", latest.getCourseID());
                    intent.putExtra("name", latest.getName());
                    intent.putExtra("phoneNumber", latest.getPhoneNumber());
                    intent.putExtra("email", latest.getEmail());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public InstructorsAdapter.InstructorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_instructor, parent, false);
        return new InstructorViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(@NonNull InstructorViewHolder holder, int position) {
        if (Instructors != null) {
            Instructor latest = Instructors.get(position);
            holder.instructorItemView.setText(latest.getName());
        } else {
            holder.instructorItemView.setText(R.string.no_instructor);
        }
    }

    @Override
    public int getItemCount() {
        if (Instructors != null) {
            return Instructors.size();
        } else {
            return 0;
        }
    }
}
