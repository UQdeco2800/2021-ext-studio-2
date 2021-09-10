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
 * A ui component for displaying food system.
 */
public class ChickenDisplay extends UIComponent {
    static Table tables;
    private Label timeLabel;
    private final ChickenSystemV1 chickenSystem = new ChickenSystemV1();
    private final ScoringSystemV1 timeCount = new ScoringSystemV1();
    public static ArrayList<Image> ChickenImage = new ArrayList<>();
    private final int count_image=4;
    private int chickencurrent = 4;



    public ChickenDisplay() {
    }


    @Override
    public void create() {
        super.create();
        addActors();
        entity.getEvents().addListener("updateChicken", this::updatePlayerTimerUI);
    }

    /**
     * Creates actors and positions them on the stage using a tables.
     *
     */
    private void addActors() {
        tables = new Table();
        tables.top().left();
        tables.setFillParent(true);
        tables.padTop(100f).padLeft(10f);

        //Add Food image
        float clockSideLength = 30f;
        ChickenImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/heart.png", Texture.class)));
        ChickenImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/heart.png", Texture.class)));
        ChickenImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/heart.png", Texture.class)));
        ChickenImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/heart.png", Texture.class)));
        //Add Clock text

        CharSequence TimerText =chickencurrent+"";
        timeLabel = new Label(TimerText, skin, "large");

        tables.add(ChickenImage.get(0)).size(clockSideLength).pad(3);
        tables.add(ChickenImage.get(1)).size(clockSideLength).pad(3);
        tables.add(ChickenImage.get(2)).size(clockSideLength).pad(3);
        tables.add(ChickenImage.get(3)).size(clockSideLength).pad(3);
        stage.addActor(tables);
    }

    @Override
    public void draw(SpriteBatch batch) {
    }

    @Override
    public void update() {
        super.update();
        int minutes = timeCount.getMinutes();
        int seconds = timeCount.getSeconds();

        int dis = (minutes * 60 + seconds) / 3;
        if(dis>chickenSystem.getTimer()){
            chickenSystem.setDiffrence(1);
            chickenSystem.setTimer(dis);
        }else {
            chickenSystem.setDiffrence(0);
        }
        //update the clock regularly
        entity.getEvents().trigger("updateChicken",chickenSystem.getDiffrence());
    }

    /**
     * Updates the Chicken ui time on the ui.
     */
    public void updatePlayerTimerUI(int dis) {

        if(dis>0){

            if(ChickenImage.size()>0){
                tables.reset();
                tables.top().left();
                tables.setFillParent(true);
                tables.padTop(100f).padLeft(10f);
                ChickenImage.remove(ChickenImage.size()-1);

                for (Image ima: ChickenImage) {
                    tables.add(ima).size(30f).pad(3);
                }
            }
        }
    }


    public ArrayList<Image> getHunger(){
        return ChickenImage;
    }

    @Override
    public void dispose() {
        super.dispose();
        timeLabel.remove();
        for (int i=0;i<ChickenImage.size();++i){
            ChickenImage.remove(i);
        }
    }

    //add a image/remove a image
    public static void addOrRemoveImage(int value){
        if(value==1){
            if(ChickenImage.size()<4){
                ChickenImage.add(new Image(ServiceLocator.getResourceService()
                        .getAsset("images/heart.png", Texture.class)));
                tables.add(ChickenImage.get(ChickenImage.size()-1)).size(30f).pad(3);
            }

        }else if(value==-1){
            if(ChickenImage.size()>0){
                tables.removeActor(ChickenImage.get(ChickenImage.size()-1));
            }
        }
    }
}
