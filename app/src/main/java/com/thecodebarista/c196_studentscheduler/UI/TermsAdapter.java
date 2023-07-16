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
import com.thecodebarista.c196_studentscheduler.entities.Term;

import java.util.List;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.TermsViewHolder> {
    private List<Term> Terms;
    private final Context context;
    private final LayoutInflater inflater;

    public TermsAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setTerms(List<Term> terms){
        Terms = terms;
        //notifyItemInserted(0);
        notifyDataSetChanged();
    }

    class TermsViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;

        public TermsViewHolder(@NonNull View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.termItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    final Term latest = Terms.get(position);
                    com.thecodebarista.c196_studentscheduler.UI.TermDetailsActivity.TERM_EDIT_MODE = false;
                    Intent intent= new Intent(context,TermDetailsActivity.class);
                    intent.putExtra("termID", latest.getTermID());
                    System.out.println("Selected - " + latest.getTermID());
                    intent.putExtra("title", latest.getTitle());
                    intent.putExtra("startDt", latest.getStartDt());
                    intent.putExtra("endDt", latest.getEndDt());
                    intent.putExtra("inEditMode", false);
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public TermsAdapter.TermsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_term, parent, false);
        return new TermsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermsAdapter.TermsViewHolder holder, int position) {
        if (Terms != null) {
            Term latest = Terms.get(position);
            holder.termItemView.setText(latest.getTitle());
        } else {
            holder.termItemView.setText(R.string.no_terms);
        }
    }

    @Override
    public int getItemCount() {
        if (Terms != null) {
            return Terms.size();
        } else {
            return 0;
        }
    }
}
