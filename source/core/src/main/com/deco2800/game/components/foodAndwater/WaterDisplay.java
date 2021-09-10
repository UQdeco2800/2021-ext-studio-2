package com.deco2800.game.components.foodAndwater;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.components.score.ScoringSystemV1;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A ui component for displaying player time. This is a similar class of ScoreDisplay
 */
public class WaterDisplay extends UIComponent {
    //prepare values and ui labels.
    Table table;
    private Label timeLabel;
    private final WaterSystemV1 waterSystem = new WaterSystemV1();
    private final ScoringSystemV1 scoringSystem = new ScoringSystemV1();
    private ArrayList<Image> waterImage = new ArrayList<>();

    private int chickencurrent = 4;

    DecimalFormat formatter = new DecimalFormat("00");

    /**
     * Creates reusable ui styles and adds actors to the stage.
     * And starts the timer.
     */
    @Override
    public void create() {
        super.create();

        addActors();
        entity.getEvents().addListener("updatewater", this::updatePlayerTimerUI);
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
        table.padTop(150f).padLeft(10f);

        //Add Clock image
        float clockSideLength = 30f;
        waterImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/clock.png", Texture.class)));
        waterImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/clock.png", Texture.class)));
        waterImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/clock.png", Texture.class)));
        waterImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/clock.png", Texture.class)));


        CharSequence TimerText = chickencurrent+"";
        timeLabel = new Label(TimerText, skin, "large");

        table.add(waterImage.get(0)).size(clockSideLength).pad(3);
        table.add(waterImage.get(1)).size(clockSideLength).pad(3);
        table.add(waterImage.get(2)).size(clockSideLength).pad(3);
        table.add(waterImage.get(3)).size(clockSideLength).pad(3);
        stage.addActor(table);
    }

    @Override
    public void draw(SpriteBatch batch) {
        // Designs of the timer, a small clock icon will go here
    }

    @Override
    public void update() {
        super.update();
        int minutes = scoringSystem.getMinutes();
        int seconds = scoringSystem.getSeconds();

        int dis = (minutes*60+seconds)/10;
        if(dis>waterSystem.getTimer()){
            waterSystem.setDiffrence(1);
            waterSystem.setTimer(dis);
        }else {
            waterSystem.setDiffrence(0);
        }

        //update the clock regularly
        //update the clock regularly
        entity.getEvents().trigger("updatewater",waterSystem.getDiffrence());
    }

    /**
     * Updates the player's gaming time on the ui.
     */
    public void updatePlayerTimerUI(int dis) {
        if(chickencurrent>0&&waterSystem.getDiffrence()>0){
            chickencurrent-=dis;
            if(waterImage.size()>0){
                table.removeActor(waterImage.get(waterImage.size()-1));
            }
        }else {
            chickencurrent-=0;
        }

//        CharSequence TimerText = chickencurrent+"";
//        timeLabel.setText(TimerText);

    }

    //添加或者删除图片
    public void addorremoveImage(int value){
        if(value==1){
           if(waterImage.size()<4){
               waterImage.add(new Image(ServiceLocator.getResourceService()
                       .getAsset("images/clock.png", Texture.class)));
               table.add(waterImage.get(waterImage.size()-1)).size(30f).pad(3);
           }
        }else if(value==-1){
            if(waterImage.size()>0){
                table.removeActor(waterImage.get(waterImage.size()-1));
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        timeLabel.remove();
        for(int i=0;i<waterImage.size();++i){
            waterImage.remove(i);
        }
    }
}
