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
 * A ui component for displaying player stats, e.g. health.
 */
public class ScoreDisplay extends UIComponent {
    Table table;
    private Label scoreLabel;

    //Import the scoring system (potentially bad code)
    private final ScoringSystem scoringSystem = new ScoringSystemV1();
    private GameTime gameTime = new GameTime();

    /**
     * Creates reusable ui styles and adds actors to the stage.
     * And starts the timer.
     */
    @Override
    public void create() {
        super.create();
        addActors();

        entity.getEvents().addListener("updateScore", this::updatePlayerScoreUI);

        // bad code, will replace in future
//        updatePlayerScoreUI();
        // Once the player is created, clock starts.
        //    scoringSystem.startGameClock();
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
        int score = scoringSystem.getScore();
        CharSequence scoreText = String.format("Score: %d", score);
        scoreLabel = new Label(scoreText, skin, "large");

        table.add(scoreLabel);
        stage.addActor(table);
    }

    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage
    }

    @Override
    public void update() {
        super.update();
        entity.getEvents().trigger("updateScore", (int) this.gameTime.getTime() / 1000);
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
