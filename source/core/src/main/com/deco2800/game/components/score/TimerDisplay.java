package com.deco2800.game.components.score;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.services.GameTime;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

import java.text.DecimalFormat;

/**
 * A ui component for displaying player time. This is a similar class of ScoreDisplay
 */
public class TimerDisplay extends UIComponent {
    //prepare values and ui labels.
    Table table;
    private Label timeLabel;
    private final ScoringSystemV1 scoringSystem = new ScoringSystemV1();
    private Image clockImage;

    DecimalFormat formatter = new DecimalFormat("00");

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
        table.padTop(200f).padLeft(10f);

        //Add Clock image
        float clockSideLength = 40f;
        clockImage = new Image(ServiceLocator.getResourceService()
                .getAsset("images/clockV2.png", Texture.class));

        //Add Clock text
        int minute = 0;
        int second = 0;
        String minuteFormatted = formatter.format(minute);
        String secondFormatted = formatter.format(second);
        CharSequence TimerText = minuteFormatted + " : " + secondFormatted;
        timeLabel = new Label(TimerText, skin, "large");

        table.add(clockImage).size(clockSideLength).pad(3);
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
        String minuteFormatted = formatter.format(minute);
        String secondFormatted = formatter.format(second);
        CharSequence text = minuteFormatted + " : " + secondFormatted;
        timeLabel.setText(text);
    }

    @Override
    public void dispose() {
        super.dispose();
        timeLabel.remove();
        clockImage.remove();
    }
}
