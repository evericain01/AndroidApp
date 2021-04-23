package com.example.quizapp.Models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.quizapp.Controllers.OptionsActivity;
import com.example.quizapp.Controllers.QuizActivity;
import com.example.quizapp.R;

import java.util.List;

public class SwipeAdapter extends RecyclerView.Adapter<SwipeAdapter.SwipeViewHolder>{
    private final DatabaseHelper db;
    private final Context context;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private  List<QuestionHandler> questionHandlerList;

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
        TextView id, queueNumber, category, difficulty, type, amount;
        Button play, delete;
        SwipeRevealLayout swipeLayout;

        public SwipeViewHolder(@NonNull View ItemView) {
            super(ItemView);
//            context = ItemView.getContext();

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

                    int chosenCategory = questionHandlerList.get(getAdapterPosition()).getCategory() + 9;

                    String chosenDifficulty = questionHandlerList.get(getAdapterPosition()).getDifficulty();
                    String chosenType = questionHandlerList.get(getAdapterPosition()).getType();
                    String finalType = chosenType.equals("True or False") ? "boolean" : "multiple";
                    int amountOfQuestions = questionHandlerList.get(getAdapterPosition()).getAmount();

                    Intent quiz = new Intent(context, QuizActivity.class);

                    quiz.putExtra("amount", amountOfQuestions);
                    quiz.putExtra("category", chosenCategory);
                    quiz.putExtra("difficulty", chosenDifficulty);
                    quiz.putExtra("type", finalType);

                    context.startActivity(quiz);

                    db.deleteQuiz(String.valueOf(id.getText()));
                    questionHandlerList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), questionHandlerList.size());
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
            switch (String.valueOf(questionHandler.getCategory())) {
                case "9":
                    category.setText("General Knowledge");
                    break;
                case "10":
                    category.setText("Books");
                    break;
                case "11":
                    category.setText("Films");
                    break;
                case "12":
                    category.setText("Music");
                    break;
                case "13":
                    category.setText("Musical and Theatres");
                    break;
                case "14":
                    category.setText("Television");
                    break;
                case "15":
                    category.setText("Video Games");
                    break;
                case "16":
                    category.setText("Board Games");
                    break;
                case "17":
                    category.setText("Science and Nature");
                    break;
                case "18":
                    category.setText("Computers");
                    break;
                case "19":
                    category.setText("Math");
                    break;
                case "20":
                    category.setText("Mythology");
                    break;
                case "21":
                    category.setText("Sports");
                    break;
                case "22":
                    category.setText("Geography");
                    break;
                case "23":
                    category.setText("Politics");
                    break;
                case "24":
                    category.setText("Art");
                    break;
                case "25":
                    category.setText("Celebrities");
                    break;
                case "27":
                    category.setText("Animals");
                    break;
                case "28":
                    category.setText("Vehicles");
                    break;
                case "29":
                    category.setText("Comics");
                    break;
                case "30":
                    category.setText("Gadgets");
                    break;
                case "31":
                    category.setText("Japanese Anime and Manga");
                    break;
                case "32":
                    category.setText("Cartoons and Animations");
                    break;
                default:
            }
            switch (String.valueOf(questionHandler.getDifficulty())) {
                case "easy":
                    difficulty.setText("Easy");
                    break;
                case "medium":
                    difficulty.setText("Medium");
                    break;
                case "hard":
                    difficulty.setText("Hard");
                    break;
                default:
            }
            switch (String.valueOf(questionHandler.getType())) {
                case "boolean":
                    type.setText("True/False");
                    break;
                case "multiple":
                    type.setText("Multiple Choice");
                    break;
                default:
            }
            switch (String.valueOf(questionHandler.getAmount())) {
                case "10":
                    amount.setText("10");
                    break;
                case "20":
                    amount.setText("20");
                    break;
                case "30":
                    amount.setText("30");
                    break;
                case "40":
                    amount.setText("40");
                    break;
                case "50":
                    amount.setText("50");
                    break;
                default:
            }
        }
    }

}
