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
        getLinkForCategory(getCategory());

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

    /**
     * Gets the helpful links for the given category.
     *
     * @param category The category name.
     */
    private void getLinkForCategory(String category) {
        switch (category) {
            case "General Knowledge":
                suggestionLink1.setText(R.string.GeneralKnowledgeLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.GeneralKnowledgeLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Books":
                suggestionLink1.setText(R.string.BooksLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.BooksLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Films":
                suggestionLink1.setText(R.string.FilmsLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.FilmsLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Music":
                suggestionLink1.setText(R.string.MusicLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.MusicLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Musical and Theatres":
                suggestionLink1.setText(R.string.MusicalAndTheatersLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.MusicalAndTheatersLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Television":
                suggestionLink1.setText(R.string.TelevisionLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.TelevisionLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Video Games":
                suggestionLink1.setText(R.string.VideoGamesLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.VideoGamesLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Board Games":
                suggestionLink1.setText(R.string.BoardGamesLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.BoardGamesLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Science and Nature":
                suggestionLink1.setText(R.string.ScienceAndNatureLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.ScienceAndNatureLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Computers":
                suggestionLink1.setText(R.string.ComputersLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.ComputersLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Math":
                suggestionLink1.setText(R.string.MathLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.MathLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Mythology":
                suggestionLink1.setText(R.string.MythologyLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.MythologyLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Sports":
                suggestionLink1.setText(R.string.SportsLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.SportsLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Geography":
                suggestionLink1.setText(R.string.GeographyLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.GeographyLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Politics":
                suggestionLink1.setText(R.string.PoliticsLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.PoliticsLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Art":
                suggestionLink1.setText(R.string.ArtLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.ArtLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Celebrities":
                suggestionLink1.setText(R.string.CelebritiesLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.CelebritiesLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Animals":
                suggestionLink1.setText(R.string.AnimalsLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.AnimalsLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Vehicles":
                suggestionLink1.setText(R.string.VehiclesLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.VideoGamesLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Comics":
                suggestionLink1.setText(R.string.ComicsLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.ComicsLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Gadgets":
                suggestionLink1.setText(R.string.GadgetsLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.GadgetsLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Japanese Anime and Manga":
                suggestionLink1.setText(R.string.JapaneseAnimeAndMangaLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.JapaneseAnimeAndMangaLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case "Cartoons and Animations":
                suggestionLink1.setText(R.string.CartoonsAndAnimationsLink1);
                suggestionLink1.setMovementMethod(LinkMovementMethod.getInstance());
                suggestionLink2.setText(R.string.CartoonsAndAnimationsLink2);
                suggestionLink2.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            default:
        }
    }
}