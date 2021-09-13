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
 * A ui component for displaying water system. Player lose a chicken for 2 sec
 */
public class WaterDisplay extends UIComponent {
    static Table table;
    private Label timeLabel;
    private final CountWaterSystem countWaterSystem = new CountWaterSystem();
    private final ScoringSystemV1 countTime = new ScoringSystemV1();
    public static ArrayList<Image> waterImage = new ArrayList<>();


    public WaterDisplay() { }

    /**
     * Creates reusable water ui styles and adds actors to the stage.
     * And starts the counting the time.
     */
    @Override
    public void create() {
        super.create();
        addActors();
        entity.getEvents().addListener("updateWater", this::updatePlayerTimerUI);
    }

    /**
     * Creates actors, add images into list, and positions them on the stage using a table.
     * @see Table for positioning options
     */
    private void addActors() {
        table = new Table();
        table.top().left();
        table.setFillParent(true);
        table.padTop(150f).padLeft(10f);

        //Add water image
        float clockSideLength = 30f;
        waterImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/water1.png", Texture.class)));
        waterImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/water1.png", Texture.class)));
        waterImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/water1.png", Texture.class)));
        waterImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/water1.png", Texture.class)));

        int waterCurrent = 4;
        CharSequence TimerText = waterCurrent + "";
        timeLabel = new Label(TimerText, skin, "large");
        //add images into the screen
        table.add(waterImage.get(0)).size(clockSideLength).pad(3);
        table.add(waterImage.get(1)).size(clockSideLength).pad(3);
        table.add(waterImage.get(2)).size(clockSideLength).pad(3);
        table.add(waterImage.get(3)).size(clockSideLength).pad(3);
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
        int minutes = countTime.getMinutes();
        int seconds = countTime.getSeconds();
        int dis = (minutes*60+seconds)/2;
        if(dis> countWaterSystem.getTimer()){
            countWaterSystem.setDifference(1);
            countWaterSystem.setTimer(dis);
        }else {
            countWaterSystem.setDifference(0);
        }
        //update the water regularly
        entity.getEvents().trigger("updateWater", countWaterSystem.getDifference());
    }

    /**
     * Updates the water ui time by time increase.
     */
    public void updatePlayerTimerUI(int dis) {
        if(dis > 0){
            if(waterImage.size() > 0){
                table.reset();
                table.top().left();
                table.setFillParent(true);
                table.padTop(150f).padLeft(10f);
                waterImage.remove(waterImage.size() - 1);
                for (Image ima: waterImage) {
                    table.add(ima).size(30f).pad(3);
                }
            }
        }
    }

    /**
     * remove waterImage by using a for loop
     */
    @Override
    public void dispose() {
        super.dispose();
        timeLabel.remove();
        for (int i=0; i<waterImage.size(); ++i){
            waterImage.remove(i);
        }
    }

    /**
     * return the waterImage
     */
    public ArrayList<Image> getThirst(){
        return waterImage;
    }

    /**
     * Add/remove a image
     * @param value = 1, add a image;
     * @param value = -1, remove a image
     */
    public static void addOrRemoveImage(int value){
        if(value == 1){
            if(waterImage.size() < 4){
                waterImage.add(new Image(ServiceLocator.getResourceService()
                        .getAsset("images/food1.png", Texture.class)));
                table.add(waterImage.get(waterImage.size() - 1)).size(30f).pad(3);
            }
        }else if(value == -1){
            if(waterImage.size() > 0){
                table.removeActor(waterImage.get(waterImage.size() - 1));
            }
        }
    }

    /**
     * when player have no water, this function will return true
     */
    public boolean isThirst(){
        return waterImage.size() <= 0;
    }

    /*    *//**
     * test buff effect for the first aid kit increases 1 water image
     * @param target entity of food
     *//*
    public void increaseWater(Entity target) {

        if (target != null) {
            if (WaterDisplay.waterImage.size() < 4) {
                WaterDisplay.addOrRemoveImage(1);
            }
        }
    }*/


}
