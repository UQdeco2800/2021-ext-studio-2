package com.deco2800.game.components.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
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
    private final float buffSideLength = 80f;
    Table table;
    Table goldTable;
    // import here for implementing the clock
    ScoringSystemV1 clock = new ScoringSystemV1();
    private Image heartImage;
    private Image poisoningImage;
    private Image decreaseSpeedImage;
    private Image decreaseHealthImage;
    private Image increaseHealthImage;
    private Image addMaxHealthImage;
    private Label healthLabel;
    private Image goldImage;
    private Label bonusGoldLabel;
    private Label goldLabel;

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

        bonusGoldLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        bonusGoldLabel.setFontScale(1.3f);

        AchievementsHelper.getInstance()
                .getEvents()
                .addListener(AchievementsHelper.EVENT_ADD_BONUS_GOLD, this::displayBonusGold);
        AchievementsHelper.getInstance()
                .getEvents()
                .addListener(AchievementsHelper.EVENT_UNLOCKED_ACHIEVEMENT_DISPOSE, this::clearBonusGold);

        clock.startGameClock();
    }

    private void displayBonusGold(int gold) {
        bonusGoldLabel.setText("  + " + gold);
        bonusGoldLabel.setVisible(true);
        table.add(bonusGoldLabel);
    }

    private void clearBonusGold() {
        bonusGoldLabel.remove();
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
        poisoningImage = new Image(ServiceLocator.getResourceService().getAsset("images/Sprint2_Buffs_Debuffs/Poisoning.png",
                Texture.class));
        decreaseSpeedImage = new Image(ServiceLocator.getResourceService().getAsset("images/Sprint2_Buffs_Debuffs" +
                "/decrease_speed.png", Texture.class));
        increaseHealthImage = new Image(ServiceLocator.getResourceService().getAsset("images/Sprint2_Buffs_Debuffs/increase_health.png",
                Texture.class));
        addMaxHealthImage = new Image(ServiceLocator.getResourceService().getAsset("images/Sprint2_Buffs_Debuffs/increase_health_limit.png",
                Texture.class));
        decreaseHealthImage = new Image(ServiceLocator.getResourceService().getAsset("images/Sprint2_Buffs_Debuffs/decrease_health.png",
                Texture.class));
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

    public void addPoisoningImage() {
        table.add(poisoningImage).size(buffSideLength).pad(5);
        stage.addActor(table);
    }

    public void removePoisoningImage() {
        poisoningImage.remove();
    }

    public void addDecreaseSpeedImage() {
        table.add(decreaseSpeedImage).size(buffSideLength).pad(5);
    }

    public void removeDecreaseSpeedImage() {
        decreaseSpeedImage.remove();
    }

    public void addDecreaseHealthImage() {
        table.add(decreaseHealthImage).size(buffSideLength).pad(5);
    }

    public void removeDecreaseHealthImage() {
        decreaseHealthImage.remove();
    }

    public void addIncreaseHealthImage() {
        if (table.getCell(increaseHealthImage)==null){

            table.add(increaseHealthImage).size(buffSideLength).pad(5);
        }
    }

    public void addAddMaxHealthImage() {
        if (table.getCell(addMaxHealthImage)==null){
            table.add(addMaxHealthImage).size(buffSideLength).pad(5);
        }
    }

    public void removeAddMaxHealthImage() {
        table.reset();
        addActors();

    }

    public void removeAddMaxHealthImage() {
        table.reset();
        addActors();
    }

    public void removeIncreaseHealthImage() {
        table.reset();
        addActors();
    }

    public void removeAllBuff() {
        poisoningImage.remove();
        decreaseSpeedImage.remove();
        decreaseHealthImage.remove();
        increaseHealthImage.remove();
        table.reset();
        addActors();
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
