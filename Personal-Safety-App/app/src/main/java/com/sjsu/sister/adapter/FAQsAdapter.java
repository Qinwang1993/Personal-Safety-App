package com.sjsu.sister.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.sister.R;
import com.sjsu.sister.model.FAQs;

import java.util.List;

public class FAQsAdapter extends RecyclerView.Adapter<FAQsAdapter.MyViewHolder> {
    List<FAQs> faqsList;

    public FAQsAdapter(List<FAQs> faqsList){
        this.faqsList = faqsList;
    }


    @NonNull
    @Override
    public FAQsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_faq, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FAQsAdapter.MyViewHolder holder, int position) {
        FAQs faqs = faqsList.get(position);
        holder.title.setText(faqs.getTitle());
        holder.content.setText(faqs.getContent());
        boolean isExpandable = faqs.isExpendable();
        holder.expandableLayout.setVisibility(isExpandable? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return faqsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, content;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.faq_title);
            content = itemView.findViewById(R.id.faq_content);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            linearLayout.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    FAQs faqs = faqsList.get(getAdapterPosition());
                    faqs.setExpendable(!faqs.isExpendable());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }
}
