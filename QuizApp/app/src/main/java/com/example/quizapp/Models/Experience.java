package com.example.quizapp.Models;

import java.util.ArrayList;

public class Experience {

    private static final double LEVEL_CONST = 0.1;
    private static final double EASY_MULTIPLIER = 1;
    private static final double MED_MULTIPLIER = 1.5;
    private static final double HARD_MULTIPLIER = 3;
    private static final int CORRECT_QUESTION_CONST = 30;

    /**
     * Calculates the experience based off of the difficulty given to the function.
     *
     * @param difficulty How hard a question was, easy, medium or hard.
     * @return Total experience a user gained.
     */
    public static int calculateExperience(String difficulty) {
        int totalExperienceGained = 0;

            switch (difficulty) {
                case "easy":
                    totalExperienceGained += EASY_MULTIPLIER * CORRECT_QUESTION_CONST;
                    break;
                case "medium":
                    totalExperienceGained += MED_MULTIPLIER * CORRECT_QUESTION_CONST;
                    break;
                case "hard":
                    totalExperienceGained += HARD_MULTIPLIER * CORRECT_QUESTION_CONST;
                    break;
            }

        return totalExperienceGained;
    }

    /**
     * Gets how far along a user is in a current level, based off of the difference of the level
     * experience difference and the xp difference divided by the level xp difference.
     * Subtracting by one to reverse the number then multiplying by -100 to get a number between
     * 0 and 100.
     *
     * @param currentExperience The current experience that a user has.
     * @return An integer between 0 and 100 based off of how far into a level a user is.
     */
    public static int progressionRate(int currentExperience) {
        // Get the current level based on the current player experience.
        int currLevel =  calculateLevel(currentExperience);

        // Calculate the experience of the current level and the next level.
        double currLevelXp = calculateLevelExperience(currLevel);
        double nextLevelXp = calculateLevelExperience(currLevel + 1);

        // Difference between the next level total experience and the current level total experience.
        double levelXpDiff = nextLevelXp - currLevelXp;
        // Difference between the current experience and the difference of experience of the two levels.
        double xpDiff = currentExperience - currLevelXp;

        // Gets how far along a user is in a current level, based off of the difference of the level
        // experience difference and the xp difference divided by the level xp difference.
        // Subtracting by one to reverse the number then multiplying by -100 to get a number between
        // 0 and 100.
        return (int) (-100 * ((((levelXpDiff - xpDiff) / levelXpDiff) - 1)));
    }

    /**
     * Gets the experience needed to the next level.
     *
     * @param currentExperience The current experience that a user has.
     * @return The experience a user needs to obtain the next level.
     */
    public static double nextLevelXpNeeded(int currentExperience) {
        // Get the current level based on the current player experience.
        int currLevel = calculateLevel(currentExperience);

        //Calculates the xp needed for the next level.
        double nextLevelXp = calculateLevelExperience(currLevel + 1);

        // Difference between the current experience and the difference of experience of the two levels.
        return nextLevelXp - currentExperience;
    }

    /**
     * Calculates the experience a given level needs.
     *
     * @param level Level's experience to be calculated.
     * @return The experience needed to obtain a specified level.
     */
    private static double calculateLevelExperience(int level) {
        return (Math.pow(level, 2) / (LEVEL_CONST / 10));
    }

    /**
     * Calculates the current level based on the current experience a user has.
     *
     * @param currentExperience The current experience that a user has.
     * @return The level the user is at.
     */
    public static int calculateLevel(int currentExperience) {
        return (int)(LEVEL_CONST * Math.sqrt(currentExperience));
    }

}
