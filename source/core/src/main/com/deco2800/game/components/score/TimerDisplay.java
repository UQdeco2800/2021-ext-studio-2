package com.deco2800.game.components.score;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.ui.UIComponent;

/**
 * A ui component for displaying player time. This is a similar class of ScoreDisplay
 */
public class TimerDisplay extends UIComponent {
    //prepare values and ui labels.
    Table table;
    private Label timeLabel;
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
        entity.getEvents().addListener("updateTime", this::updatePlayerTimerUI);
    }

    /**
     * Creates actors and positions them on the stage using a table.
     *
     * @see Table for positioning options
     */
    private void addActors() {
        table = new Table();
        table.top().left();
        table.setFillParent(true);
        table.padTop(90f).padLeft(10f);

        // Score text
        int minute = 0;
        int second = 0;
        CharSequence TimerText = minute + " : " + second;
        timeLabel = new Label(TimerText, skin, "large");

        table.add(timeLabel);
        stage.addActor(table);
    }

    @Override
    public void draw(SpriteBatch batch) {
        // Designs of the timer, a small clock icon will go here
    }

    @Override
    public void update() {
        super.update();
        //update the clock regularly
        entity.getEvents().trigger("updateTime", scoringSystem.getMinutes(), scoringSystem.getSeconds());
    }

    /**
     * Updates the player's gaming time on the ui.
     */
    public void updatePlayerTimerUI(int minute, int second) {
        CharSequence text = minute + " : " + second;
        timeLabel.setText(text);
    }

    @Override
    public void dispose() {
        super.dispose();
        timeLabel.remove();
    }
}
