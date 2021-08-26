package com.deco2800.game.components.score;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.score.ScoringSystem;
import com.deco2800.game.components.score.ScoringSystemV1;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

/**
 * A ui component for displaying player score
 */
public class ScoreDisplay extends UIComponent {
    Table table;
    private Label scoreLabel;

    // Import the scoring system (potentially bad code)
    // Not needed anymore remove in next commit


    private final GameTime gameTime = new GameTime();
    private final ScoringSystemV1 scoringSystem = new ScoringSystemV1();

    /**
     * Creates reusable ui styles and adds actors to the stage.
     * And starts the timer.
     */
    @Override
    public void create() {
        super.create();
        addActors();

        entity.getEvents().addListener("updateScore", this::updatePlayerScoreUI);
    }

    /**
     * Creates actors and positions them on the stage using a table.
     *
     * @see Table for positioning options
     */
    private void addActors() {
        table = new Table();
        table.bottom().right();
        table.setFillParent(true);
        table.padTop(45f).padLeft(5f);

        // Score text
        int score = 0;
        CharSequence scoreText = String.format("Score: %d", score);
        scoreLabel = new Label(scoreText, skin, "large");

        table.add(scoreLabel);
        stage.addActor(table);
    }

    @Override
    public void draw(SpriteBatch batch) {
        // Designs of the Scoreboard will go here
    }

    @Override
    public void update() {
        super.update();
        entity.getEvents().trigger("updateScore",
                scoringSystem.getScore(scoringSystem.getScoreSeconds()));
    }

    /**
     * Updates the player's score on the ui.
     */
    public void updatePlayerScoreUI(int score) {
        CharSequence text = String.format("Score: %d", score);
        scoreLabel.setText(text);
    }

    @Override
    public void dispose() {
        super.dispose();
        scoreLabel.remove();
    }

}
