package com.example.quizapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.List;

public class SwipeAdapter extends RecyclerView.Adapter<SwipeAdapter.SwipeViewHolder> {

    private Context context;
    private List<QuestionHandler> questionHandlerList;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();


    public SwipeAdapter(Context context, List<QuestionHandler> questionHandlerList) {
        this.context = context;
        this.questionHandlerList = questionHandlerList;
    }

    public void setData(List<QuestionHandler> questionHandlerList) {
        this.questionHandlerList = questionHandlerList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SwipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_list, parent, false);
        return new SwipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SwipeViewHolder holder, int position) {
        viewBinderHelper.setOpenOnlyOne(true);

        viewBinderHelper.bind(holder.swipeLayout, String.valueOf(questionHandlerList.get(position).getCategory()));
        viewBinderHelper.closeLayout(String.valueOf(questionHandlerList.get(position).getCategory()));
        viewBinderHelper.bind(holder.swipeLayout, String.valueOf(questionHandlerList.get(position).getDifficulty()));
        viewBinderHelper.closeLayout(String.valueOf(questionHandlerList.get(position).getDifficulty()));
        viewBinderHelper.bind(holder.swipeLayout, String.valueOf(questionHandlerList.get(position).getType()));
        viewBinderHelper.closeLayout(String.valueOf(questionHandlerList.get(position).getType()));
        viewBinderHelper.bind(holder.swipeLayout, String.valueOf(questionHandlerList.get(position).getAmount()));
        viewBinderHelper.closeLayout(String.valueOf(questionHandlerList.get(position).getAmount()));

        holder.bindData(questionHandlerList.get(position));
    }

    @Override
    public int getItemCount() { return questionHandlerList.size(); }


    public class SwipeViewHolder extends RecyclerView.ViewHolder {
        TextView queueNumber;
        TextView category;
        TextView difficulty;
        TextView type;
        TextView amount;
        Button play;
        Button delete;
        SwipeRevealLayout swipeLayout;


        public SwipeViewHolder(@NonNull View ItemView) {
            super(ItemView);
            queueNumber = ItemView.findViewById(R.id.queueNumberText);
            category = ItemView.findViewById(R.id.categoryText);
            difficulty = ItemView.findViewById(R.id.difficultyText);
            type = ItemView.findViewById(R.id.typeText);
            amount = ItemView.findViewById(R.id.amountText);
            swipeLayout = ItemView.findViewById(R.id.swipeLayout);

            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Started Quiz", Toast.LENGTH_SHORT).show();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Deleted Quiz", Toast.LENGTH_SHORT).show();
                }
            });

        }

        void bindData(QuestionHandler questionHandler) {
            category.setText(questionHandler.getCategory());
            difficulty.setText(questionHandler.getDifficulty());
            type.setText(questionHandler.getType());
            amount.setText(questionHandler.getAmount());
        }

    }

}
