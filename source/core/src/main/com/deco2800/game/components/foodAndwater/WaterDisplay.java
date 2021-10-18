package com.deco2800.game.components.foodAndwater;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.score.ScoringSystemV1;
import com.deco2800.game.screens.MainGameScreen;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import java.util.ArrayList;

/**
 * A ui component for displaying water system. Player lose a chicken for 2 sec
 */
public class WaterDisplay extends UIComponent {
    static Table table;    //store water icon
    static Table table1;   // to store thirst icon
    private final CountWaterSystem countWaterSystem = new CountWaterSystem();
    private final CountWaterSystem countWaterSystem1 = new CountWaterSystem();
    private final ScoringSystemV1 countTime = new ScoringSystemV1();
    public static ArrayList<Image> waterImage = new ArrayList<>();
    private Image thirstIcon = new Image(ServiceLocator.getResourceService()
            .getAsset("buff-debuff-manual/low_statu_thirst1.png", Texture.class));



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
        entity.getEvents().addListener("updateHealth", this::updatePlayerHealth);
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
        float waterIconSize = 30f;
        waterImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/water1.png", Texture.class)));
        waterImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/water1.png", Texture.class)));
        waterImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/water1.png", Texture.class)));
        waterImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/water1.png", Texture.class)));


        //add images into the screen
        table.add(waterImage.get(0)).size(waterIconSize).pad(3);
        table.add(waterImage.get(1)).size(waterIconSize).pad(3);
        table.add(waterImage.get(2)).size(waterIconSize).pad(3);
        table.add(waterImage.get(3)).size(waterIconSize).pad(3);
        stage.addActor(table);

        // add a new table in screen to put thirst icon
        table1 = new Table();
        stage.addActor(table1);
    }

    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage
    }

    /**
     * Reduce water when time increase
     */
    @Override
    public void update() {
        super.update();
        int minutes = countTime.getMinutes();
        int seconds = countTime.getSeconds();
        int dis = (minutes * 60 + seconds)/ 1;
        int dis1 = (minutes * 60 + seconds); //it is reduce health value when every single second
        if(dis > countWaterSystem.getTimer()){
            countWaterSystem.setDifference(1);
            countWaterSystem.setTimer(dis);
        }else {
            countWaterSystem.setDifference(0);
        }
        if(dis1 > countWaterSystem1.getTimer()){
            countWaterSystem1.setDifference(2);
            countWaterSystem1.setTimer(dis1);
        }else {
            countWaterSystem1.setDifference(0);
        }
        //update the water regularly
        entity.getEvents().trigger("updateWater", countWaterSystem.getDifference());
        entity.getEvents().trigger("updateHealth", countWaterSystem1.getDifference());
    }

    public void updatePlayerHealth(int dis){
        if(dis == 2){
            if(waterImage.size() <= 0){
                //if water icon count less than 0, then it will be into that codes to run. it reduce player health value.
                //reduce player health value
                MainGameScreen.players.getComponent(CombatStatsComponent.class).setHealth(
                        MainGameScreen.players.getComponent(CombatStatsComponent.class).getHealth() - 1
                );
            }
        }
    }

    /**
     * Updates the water ui time by time increase.
     */
    public void updatePlayerTimerUI(int dis) {
        if(dis == 1){
            if(waterImage.size() <= 0){
                table1.reset();
                table1.top().left();
                table1.setFillParent(true);
                table1.padTop(260f).padLeft(5f);
                table1.add(thirstIcon).size(50f).pad(3);
            }
            if(waterImage.size() > 0){
                table1.reset();
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
        for (int i = 0; i < waterImage.size(); ++i){
            waterImage.remove(i);
        }
        thirstIcon.remove();
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
                        .getAsset("images/water1.png", Texture.class)));
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
    public static boolean isThirst(){
        return waterImage.size() <= 0;
    }
}
