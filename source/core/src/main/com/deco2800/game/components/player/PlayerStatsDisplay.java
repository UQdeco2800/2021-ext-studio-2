package com.deco2800.game.components.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.score.ScoringSystemV1;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

/**
 * A ui component for displaying player stats, e.g. health.
 */
public class PlayerStatsDisplay extends UIComponent {
    Table table;
    Table consumeTable;
    private Image heartImage;
    private Label healthLabel;
    private Image foodImage;
    private Image waterImage;
    // import here for implementing the clock
    //ScoringSystemV1 clock = new ScoringSystemV1();

    /**
     * Creates reusable ui styles and adds actors to the stage.
     * And starts the timer.
     */
    @Override
    public void create() {
        super.create();
        addActors();

        entity.getEvents().addListener("updateHealth", this::updatePlayerHealthUI);
        //clock.startGameClock();
    }

    /**
     * Creates actors and positions them on the stage using a table.
     *
     * @see Table for positioning options
     */
    private void addActors() {
        table = new Table();
        consumeTable = new Table();
        table.top().left();
        consumeTable.top().right();
        table.setFillParent(true);
        consumeTable.setFillParent(true);
        table.padTop(45f).padLeft(5f);
        consumeTable.padTop(45f).padRight(5f);



        // Heart image
        float heartSideLength = 30f;
        float waterSideLength = 30f;
        float foodSideLength = 30f;
        heartImage = new Image(ServiceLocator.getResourceService().getAsset("images/heart.png", Texture.class));
        waterImage = new Image(ServiceLocator.getResourceService().getAsset("images/water.png", Texture.class));
        foodImage = new Image(ServiceLocator.getResourceService().getAsset("images/food.png", Texture.class));


        // Health text
        int health = entity.getComponent(CombatStatsComponent.class).getHealth();
        CharSequence healthText = String.format("Health: %d", health);
        healthLabel = new Label(healthText, skin, "large");


        table.add(heartImage).size(heartSideLength).pad(5);
        table.add(healthLabel);
        consumeTable.add(waterImage).size(waterSideLength).pad(30);
        consumeTable.add(foodImage).size(foodSideLength).pad(45);
        stage.addActor(table);
        stage.addActor(consumeTable);
    }

    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage
    }

    /**
     * Updates the player's health on the ui.
     * And check if the player is dead. If dead, then timer stops.
     *
     * @param health player health
     */
    public void updatePlayerHealthUI(int health) {
        CharSequence text = String.format("Health: %d", health);
        healthLabel.setText(text);

        //when player is dead the timer stops.
        if (health <= 0) {
            ScoringSystemV1.stopTimerTask();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        heartImage.remove();
        healthLabel.remove();
    }

}
