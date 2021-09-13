package com.deco2800.game.components.foodAndwater;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.components.score.ScoringSystemV1;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import java.util.ArrayList;

/**
 * A ui component for displaying food system. Player lose a chicken for 3 sec
 */
public class FoodDisplay extends UIComponent {
    static Table table;
    private Label timeLabel;
    private final CountFoodSystem countFoodSystem = new CountFoodSystem();
    private final ScoringSystemV1 timeCount = new ScoringSystemV1();
    public static ArrayList<Image> chickenImage = new ArrayList<>();
    private final int count_image = 4;
    private int chickenCurrent = 4;

    public FoodDisplay() { }

    /**
     * Creates reusable food ui styles and adds actors to the stage.
     * And starts the counting the time.
     */
    @Override
    public void create() {
        super.create();
        addActors();
        entity.getEvents().addListener("updateChicken", this::updatePlayerTimerUI);
    }

    /**
     * Creates actors, add images into list, and positions them on the stage using a table.
     * @see Table for positioning options
     */
    private void addActors() {
        table = new Table();
        table.top().left();
        table.setFillParent(true);
        table.padTop(100f).padLeft(10f);

        //Add Food image
        float clockSideLength = 30f;
        chickenImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/food1.png", Texture.class)));
        chickenImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/food1.png", Texture.class)));
        chickenImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/food1.png", Texture.class)));
        chickenImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/food1.png", Texture.class)));

        CharSequence TimerText =chickenCurrent + "";
        timeLabel = new Label(TimerText, skin, "large");
        //add images into the screen
        table.add(chickenImage.get(0)).size(clockSideLength).pad(3);
        table.add(chickenImage.get(1)).size(clockSideLength).pad(3);
        table.add(chickenImage.get(2)).size(clockSideLength).pad(3);
        table.add(chickenImage.get(3)).size(clockSideLength).pad(3);
        stage.addActor(table);
    }

    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage
    }

    /**
     * Reduce food when time increase
     */
    @Override
    public void update() {
        super.update();
        int minutes = timeCount.getMinutes();
        int seconds = timeCount.getSeconds();
        int dis = (minutes * 60 + seconds) / 3;
        if(dis > countFoodSystem.getTimer()){
            countFoodSystem.setDifference(1);
            countFoodSystem.setTimer(dis);
        }else {
            countFoodSystem.setDifference(0);
        }
        //update the water regularly
        entity.getEvents().trigger("updateChicken", countFoodSystem.getDifference());
    }

    /**
     * Updates the Chicken ui time by time increase.
     */
    public void updatePlayerTimerUI(int dis) {
        if(dis > 0){
            if(chickenImage.size() > 0){
                table.reset();
                table.top().left();
                table.setFillParent(true);
                table.padTop(100f).padLeft(10f);
                chickenImage.remove(chickenImage.size() - 1);
                for (Image ima: chickenImage) {
                    table.add(ima).size(30f).pad(3);
                }
            }
        }
    }

    /**
     * remove chickenImage by useing a for loop
     */
    @Override
    public void dispose() {
        super.dispose();
        timeLabel.remove();
        for (int i = 0; i < chickenImage.size(); ++i){
            chickenImage.remove(i);
        }
    }

    /**
     * return the chickenImage
     */
    public ArrayList<Image> getHunger(){
        return chickenImage;
    }

    /**
     * Add/remove a image
     * @param value = 1, add a image;
     * @param value = -1, remove a image
     */
    public static void addOrRemoveImage(int value){
        if(value == 1){
            if(chickenImage.size() < 4){
                chickenImage.add(new Image(ServiceLocator.getResourceService()
                        .getAsset("images/food1.png", Texture.class)));
                table.add(chickenImage.get(chickenImage.size() - 1)).size(30f).pad(3);
            }
        }else if(value == -1){
            if(chickenImage.size() > 0){
                table.removeActor(chickenImage.get(chickenImage.size() - 1));
            }
        }
    }

    /**
     * when player have no food, this function will return true
     */
    public boolean isHunger(){
        return chickenImage.size() <= 0;
    }

    /*    *//**
     * test buff effect for the first aid kit increases 1 food image
     * @param target entity of food
     *//*
    public void increaseFood(Entity target) {

        if (target != null) {
            if (FoodDisplay.chickenImage.size() < 4) {
                FoodDisplay.addOrRemoveImage(1);
            }
        }
    }*/
}
