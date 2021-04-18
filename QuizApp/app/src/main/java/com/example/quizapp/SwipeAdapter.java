package com.example.quizapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.List;

public class SwipeAdapter extends RecyclerView.Adapter<SwipeAdapter.SwipeViewHolder>{

    private final DatabaseHelper db;
    private final Context context;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private List<QuestionHandler> questionHandlerList;

    public SwipeAdapter(Context context, List<QuestionHandler> questionHandlerList) {
        this.context = context;
        this.questionHandlerList = questionHandlerList;
        db = new DatabaseHelper(context);
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

        db.updatePositionOnRecycler(String.valueOf(questionHandlerList.get(position).getId()), (position + 1));
        holder.queueNumber.setText("#" + (position + 1));
    }

    @Override
    public int getItemCount() { return questionHandlerList.size(); }

    public class SwipeViewHolder extends RecyclerView.ViewHolder {
        TextView id;
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

            id = ItemView.findViewById(R.id.idText);
            queueNumber = ItemView.findViewById(R.id.queueNumberText);
            category = ItemView.findViewById(R.id.categoryText);
            difficulty = ItemView.findViewById(R.id.difficultyText);
            type = ItemView.findViewById(R.id.typeText);
            amount = ItemView.findViewById(R.id.amountText);
            play = ItemView.findViewById(R.id.queuePlayButton);
            delete = ItemView.findViewById(R.id.queueDeleteButton);
            swipeLayout = ItemView.findViewById(R.id.swipeLayout);

            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    db.deleteQuiz(String.valueOf(id.getText()));
//                    questionHandlerList.remove(getAdapterPosition());
//                    notifyItemRemoved(getAdapterPosition());
//                    notifyItemRangeChanged(getAdapterPosition(), questionHandlerList.size());
                    Toast.makeText(context, "Started Quiz", Toast.LENGTH_SHORT).show();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.deleteQuiz(String.valueOf(id.getText()));
                    questionHandlerList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), questionHandlerList.size());
                    Toast.makeText(v.getContext(), "Quiz Deleted.", Toast.LENGTH_SHORT).show();
                }
            });

        }

        void bindData(QuestionHandler questionHandler) {
            id.setText(String.valueOf(questionHandler.getId()));
            category.setText(String.valueOf(questionHandler.getCategory()));
            difficulty.setText(String.valueOf(questionHandler.getDifficulty()));
            type.setText(String.valueOf(questionHandler.getType()));
            amount.setText(String.valueOf(questionHandler.getAmount()));
        }

    }

}
