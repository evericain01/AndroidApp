package com.example.quizapp.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizapp.R;

public class SuggestionsActivity extends AppCompatActivity {
    TextView categoryName, suggestionLink1, suggestionLink2;
    Button backToHomePageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);

        categoryName = findViewById(R.id.categoryNameText);
        suggestionLink1 = findViewById(R.id.suggestionLink1);
        suggestionLink2 = findViewById(R.id.suggestionLink2);

        categoryName.setText(getCategory());

        switch (getCategory()) {
            case "General Knowledge":
                suggestionLink1.setText(R.string.GeneralKnowledgeLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.GeneralKnowledgeLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
//            case "Books":
//                category = "Books";
//                break;
//            case "Films":
//                category = "Films";
//                break;
//            case "Music":
//                category = "Music";
//                break;
//            case "Musical and Theatres":
//                category = "Musical and Theatres";
//                break;
//            case "Television":
//                category = "Television";
//                break;
//            case "Video Games"
//                category = "Video Games";
//                break;
//            case "Board Games":
//                category = "Board Games";
//                break;
//            case "Science and Nature":
//                category = "Science and Nature";
//                break;
//            case "Computers":
//                category = "Computers";
//                break;
//            case "Math":
//                category = "Math";
//                break;
//            case "Mythology":
//                category = "Mythology";
//                break;
//            case "Sports":
//                category = "Sports";
//                break;
//            case "Geography":
//                category = "Geography";
//                break;
//            case "Politics":
//                category = "Politics";
//                break;
//            case "Art":
//                category = "Art";
//                break;
//            case "Celebrities":
//                category = "Celebrities";
//                break;
//            case "Animals":
//                category = "Animals";
//                break;
//            case "Vehicles":
//                category = "Vehicles";
//                break;
//            case "Comics":
//                category = "Comics";
//                break;
//            case "Gadgets":
//                category = "Gadgets";
//                break;
//            case "Japanese Anime and Manga":
//                category = "Japanese Anime and Manga";
//                break;
//            case "Cartoons and Animations":
//                category = "Cartoons and Animations";
//                break;
            default:
        }

        backToHomePageButton = findViewById(R.id.backToHomePageButton);
        backToHomePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homePage = new Intent(SuggestionsActivity.this, HomePageActivity.class);
                homePage.putExtra("USER_ID", getCurrentUserId());
                startActivity(homePage);
            }
        });

    }


    /**
     * Gets the current user ID.
     *
     * @return The user ID as a String.
     */
    public String getCurrentUserId() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String currentUserID = "";
        if (bundle != null) {
            currentUserID = (String) bundle.get("USER_ID");
        }

        return currentUserID;
    }

    /**
     * Gets the category of the quiz.
     *
     * @return The category.
     */
    public String getCategory() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String category = "";
        if (bundle != null) {
            category = (String) bundle.get("category");
        }

        return category;
    }

}