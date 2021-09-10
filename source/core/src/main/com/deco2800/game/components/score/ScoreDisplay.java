package com.deco2800.game.components.score;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.achievements.AchievementsHelper;
import com.deco2800.game.components.score.ScoringSystem;
import com.deco2800.game.components.score.ScoringSystemV1;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

/**
 * A ui component for displaying player score
 */
public class ScoreDisplay extends UIComponent {
    Table tableForText;
    Table tableForBoard;
    private Label scoreLabel;
    private final ScoringSystemV1 scoringSystem = new ScoringSystemV1();
    private Image scoreBoard;

    /**
     * Creates reusable ui styles and adds actors to the stage.
     * And starts the timer.
     */
    @Override
    public void create() {
        super.create();
        addActors();

        AchievementsHelper.getInstance().getEvents()
                .addListener(AchievementsHelper.ACHIEVEMENTS_BONUS_POINTS, this::updateScoreByPoints);
        entity.getEvents().addListener("updateScore", this::updatePlayerScoreUI);
    }

    /**
     * Creates actors and positions them on the stage using a table.
     *
     * @see Table for positioning options
     */
    private void addActors() {
        //Use two tables to fill the png with the score text
        tableForText = new Table();
        tableForText.bottom().right();
        tableForText.setFillParent(true);
        tableForText.padBottom(60).padRight(80);

        tableForBoard = new Table();
        tableForBoard.bottom().right();
        tableForBoard.setFillParent(true);
        tableForBoard.padBottom(-35).padRight(-52);

        // Score Board
        float boardSideLength = 250f;
        scoreBoard = new Image(ServiceLocator.getResourceService()
                .getAsset("images/scoreboard.png", Texture.class));

        // Score text
        int score = 0;
        CharSequence scoreText = "" + score;
        scoreLabel = new Label(scoreText, skin, "large");

        tableForBoard.add(scoreBoard).size(boardSideLength);
        tableForText.add(scoreLabel);
        stage.addActor(tableForBoard);
        stage.addActor(tableForText);
    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    @Override
    public void update() {
        super.update();
        entity.getEvents().trigger("updateScore",
                scoringSystem.getScore());
    }

    /**
     * Updates the player's score on the ui.
     */
    public void updatePlayerScoreUI(int score) {
        CharSequence text = "" + score;
        scoreLabel.setText(text);
    }

    /**
     * Updates the score based on bonus points
     * @param bonusPoints bonus points of each achievement
     */
    private void updateScoreByPoints(int bonusPoints){
        // Add bonusPoints to the score here
        scoringSystem.addToScore(bonusPoints);
    }

    @Override
    public void dispose() {
        super.dispose();
        scoreLabel.remove();
        scoreBoard.remove();
    }

}
