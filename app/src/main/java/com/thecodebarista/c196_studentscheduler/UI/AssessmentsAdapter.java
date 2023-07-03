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
import com.thecodebarista.c196_studentscheduler.entities.Assessment;

import java.util.List;

public class AssessmentsAdapter extends RecyclerView.Adapter<AssessmentsAdapter.AssessmentViewHolder> {
    protected static boolean ASSESSMENT_EDIT_MODE;

    private List<Assessment> Assessments;
    private final Context context;
    private final LayoutInflater inflater;

    public AssessmentsAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setAssessments(List<Assessment> assessments) {
        Assessments = assessments;
        notifyDataSetChanged();
    }

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;

        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.assessmentItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    final Assessment latest = Assessments.get(position);
                    com.thecodebarista.c196_studentscheduler.UI.AssessmentsAdapter.ASSESSMENT_EDIT_MODE = false;
                    Intent intent= new Intent(context,AssessmentDetailsActivity.class);
                    System.out.println("Selected Assessment - " + latest.getAssessmentID());
                    intent.putExtra("assessmentID", latest.getAssessmentID());
                    System.out.println("Selected Assessment Course ID - " + latest.getCourseID());
                    intent.putExtra("courseID", latest.getCourseID());
                    intent.putExtra("assessmentType", latest.getAssessmentType());
                    intent.putExtra("assessmentTitle", latest.getAssessmentTitle());
                    intent.putExtra("startDt", latest.getStartDt());
                    intent.putExtra("endDt", latest.getEndDt());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public AssessmentsAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_assessment, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        if (Assessments != null) {
            Assessment latest = Assessments.get(position);
            holder.assessmentItemView.setText(latest.getAssessmentTitle());
        } else {
            holder.assessmentItemView.setText(R.string.no_assessment);
        }
    }

    @Override
    public int getItemCount() {
        if (Assessments != null) {
            return Assessments.size();
        } else {
            return 0;
        }
    }

}
