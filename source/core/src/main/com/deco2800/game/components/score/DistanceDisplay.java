package com.deco2800.game.components.score;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

public class DistanceDisplay extends UIComponent {
    Table tableForText;
    Table tableForBoard;
    private Label distanceLabel;
    private Image distanceBoard;

    /**
     * Creates reusable ui styles and adds actors to the stage.
     * And starts the timer.
     */
    @Override
    public void create() {
        super.create();
        addActors();
        entity.getEvents().addListener("updateDistance", this::updatePlayerDistanceUI);
    }

    /**
     * Creates actors and positions them on the stage using a table.
     *
     * @see Table for positioning options
     */
    private void addActors() {
        //Use two tables to fill the png with the score text
        tableForText = new Table();
        tableForText.bottom().left();
        tableForText.setFillParent(true);
        tableForText.padBottom(40).padLeft(90);

        tableForBoard = new Table();
        tableForBoard.bottom().left();
        tableForBoard.setFillParent(true);
        tableForBoard.padBottom(-35).padLeft(0);

        // Distance Board
        float boardSideLength = 250f;
        distanceBoard = new Image(ServiceLocator.getResourceService()
                .getAsset("images/distanceboard.png", Texture.class));

        // Distance text
        double distance = 0;
        CharSequence distanceText = "" + distance;
        distanceLabel = new Label(distanceText, new Label.LabelStyle(new BitmapFont(), Color.VIOLET));
        distanceLabel.setFontScale(2f);

        tableForBoard.add(distanceBoard).size(boardSideLength);
        tableForText.add(distanceLabel);
        stage.addActor(tableForBoard);
        stage.addActor(tableForText);
    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    @Override
    public void update() {
        super.update();
        entity.getEvents().trigger("updateDistance",
                ServiceLocator.getDistanceService().getDistance());
    }

    /**
     * Updates the player's distance on the ui.
     */
    public void updatePlayerDistanceUI(double distance) {
        CharSequence text = "" + (int)distance+ "m";
        distanceLabel.setText(text);
    }


    @Override
    public void dispose() {
        super.dispose();
        distanceLabel.remove();
        distanceBoard.remove();
    }
}
