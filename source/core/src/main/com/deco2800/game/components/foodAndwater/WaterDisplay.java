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
 * A ui component for displaying water system.
 */
public class WaterDisplay extends UIComponent {
    static Table tables;
    private Label timeLabel;
    private final CountWaterSystem countWaterSystem = new CountWaterSystem();
    private final ScoringSystemV1 countTime = new ScoringSystemV1();
    public static ArrayList<Image> waterImage = new ArrayList<>();


    public WaterDisplay() {
    }


    @Override
    public void create() {
        super.create();
        addActors();
        entity.getEvents().addListener("updateWater", this::updatePlayerTimerUI);
    }


    private void addActors() {
        tables = new Table();
        tables.top().left();
        tables.setFillParent(true);
        tables.padTop(150f).padLeft(10f);

        //Add water image ---use clock at that moment
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

        tables.add(waterImage.get(0)).size(clockSideLength).pad(3);
        tables.add(waterImage.get(1)).size(clockSideLength).pad(3);
        tables.add(waterImage.get(2)).size(clockSideLength).pad(3);
        tables.add(waterImage.get(3)).size(clockSideLength).pad(3);
        stage.addActor(tables);
    }

    @Override
    public void draw(SpriteBatch batch) {
        // Designs of the timer, a small clock icon will go here
    }


    @Override
    public void update() {
        super.update();
        int minutes = countTime.getMinutes();
        int seconds = countTime.getSeconds();

        int dis = (minutes*60+seconds)/2;
        if(dis> countWaterSystem.getTimer()){
            countWaterSystem.setDiffrence(1);
            countWaterSystem.setTimer(dis);
        }else {
            countWaterSystem.setDiffrence(0);
        }
        //update the clock regularly
        entity.getEvents().trigger("updateWater", countWaterSystem.getDiffrence());
    }

    /**
     * Updates the Chicken ui time on the ui.
     */
    public void updatePlayerTimerUI(int dis) {

        if(dis > 0){
            if(waterImage.size() > 0){
                tables.reset();
                tables.top().left();
                tables.setFillParent(true);
                tables.padTop(150f).padLeft(10f);
                waterImage.remove(waterImage.size() - 1);

                for (Image ima: waterImage) {
                    tables.add(ima).size(30f).pad(3);
                }
            }
        }
    }


    public ArrayList<Image> getThirst(){
        return waterImage;
    }

    @Override
    public void dispose() {
        super.dispose();
        timeLabel.remove();
        for (int i=0; i<waterImage.size(); ++i){
            waterImage.remove(i);
        }
    }

    //add a image/remove a image
    public static void addOrRemoveImage(int value){
        if(value==1){
            if(waterImage.size()<4){
                waterImage.add(new Image(ServiceLocator.getResourceService()
                        .getAsset("images/water1.png", Texture.class)));
                tables.add(waterImage.get(waterImage.size()-1)).size(30f).pad(3);
            }

        }else if(value==-1){
            if(waterImage.size()>0){
                tables.removeActor(waterImage.get(waterImage.size()-1));
            }
        }
    }
}
