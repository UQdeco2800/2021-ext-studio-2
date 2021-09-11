package com.deco2800.game.components.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.achievements.AchievementsHelper;
import com.deco2800.game.components.score.ScoringSystemV1;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

/**
 * A ui component for displaying player stats, e.g. health.
 */
public class PlayerStatsDisplay extends UIComponent {
    Table table;
    Table goldTable;
    private Image heartImage;
    private Label healthLabel;
    private Image goldImage;
    private Label goldLabel;
    // import here for implementing the clock
    ScoringSystemV1 clock = new ScoringSystemV1();

    /**
     * Creates reusable ui styles and adds actors to the stage.
     * And starts the timer.
     */
    @Override
    public void create() {
        super.create();
        addActors();

        entity.getEvents().addListener("updateHealth", this::updatePlayerHealthUI);
        entity.getEvents().addListener("updateGold", this::updatePlayerGold);
        clock.startGameClock();
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
        table.padTop(45f).padLeft(5f);

        // Heart image
        float heartSideLength = 30f;
        heartImage = new Image(ServiceLocator.getResourceService().getAsset("images/heart.png", Texture.class));

        // Health text
        int health = entity.getComponent(CombatStatsComponent.class).getHealth();
        CharSequence healthText = String.format("Health: %d", health);
        healthLabel = new Label(healthText, skin, "large");

        table.add(heartImage).size(heartSideLength).pad(5);
        table.add(healthLabel);
        stage.addActor(table);

        goldTable = new Table();
        goldTable.top().right();
        table.setFillParent(true);
        table.padTop(45f).padLeft(5f);

        // Gold image
        float goldSideLength = 40f;
        goldImage = new Image(ServiceLocator.getResourceService().getAsset("images/Items/goldCoin.png", Texture.class));

        // Gold text
        entity.getComponent(InventoryComponent.class).setGold(-1);
        int gold = entity.getComponent(InventoryComponent.class).getGold();
        CharSequence goldText = String.format("Gold: %d", gold);
        goldLabel = new Label(goldText, skin, "large");

        table.add(goldImage).size(goldSideLength).pad(5);
        table.add(goldLabel);
        stage.addActor(goldTable);
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

        // Track changes in health
        AchievementsHelper.getInstance().trackHealthEvent(health);

        //when player is dead the timer stops.
        if (health <= 0) {
            ScoringSystemV1.stopTimerTask();
        }
    }

    public void updatePlayerGold(int gold) {
        CharSequence text = String.format("Gold: %d", gold);
        goldLabel.setText(text);
    }

    @Override
    public void dispose() {
        super.dispose();
        heartImage.remove();
        healthLabel.remove();
    }

}
