package com.deco2800.game.components.foodAndwater;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.score.ScoringSystemV1;
import com.deco2800.game.screens.MainGameScreen;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import java.util.ArrayList;

/**
 * A ui component for displaying food system. Player lose a chicken for 3 sec
 */
public class FoodDisplay extends UIComponent{
    static Table table;     //store food icon
    static Table table1;    // to store hunger icon
    private final CountFoodSystem countFoodSystem = new CountFoodSystem();
    private final CountWaterSystem countFoodSystem1 = new CountWaterSystem();
    private final ScoringSystemV1 timeCount = new ScoringSystemV1();
    public static ArrayList<Image> foodImage = new ArrayList<>();
    private Image hungerIcon = new Image(ServiceLocator.getResourceService()
            .getAsset("buff-debuff-manual/low_statu_hunger1.png", Texture.class));

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
        table.padTop(100f).padLeft(10f);


        //Add Food image
        float foodIconSize = 30f;
        foodImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/food1.png", Texture.class)));
        foodImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/food1.png", Texture.class)));
        foodImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/food1.png", Texture.class)));
        foodImage.add(new Image(ServiceLocator.getResourceService()
                .getAsset("images/food1.png", Texture.class)));


        //add images into the screen
        table.add(foodImage.get(0)).size(foodIconSize).pad(3);
        table.add(foodImage.get(1)).size(foodIconSize).pad(3);
        table.add(foodImage.get(2)).size(foodIconSize).pad(3);
        table.add(foodImage.get(3)).size(foodIconSize).pad(3);
        stage.addActor(table);

        // add a new table in screen to put hunger icon
        table1 = new Table();
        stage.addActor(table1);
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
        int dis = (minutes * 60 + seconds)/ 10;
        int dis1 = (minutes * 60 + seconds);
        if(dis > countFoodSystem.getTimer()){
            countFoodSystem.setDifference(1);
            countFoodSystem.setTimer(dis);
        }else {
            countFoodSystem.setDifference(0);
        }
        if(dis1 > countFoodSystem1.getTimer()){
            countFoodSystem1.setDifference(2);
            countFoodSystem1.setTimer(dis1);
        }else {
            countFoodSystem1.setDifference(0);
        }
        //update the food regularly
        entity.getEvents().trigger("updateChicken", countFoodSystem.getDifference());
        entity.getEvents().trigger("updateHealth", countFoodSystem1.getDifference());
    }

    public void updatePlayerHealth(int dis){
        int health = MainGameScreen.players.getComponent(CombatStatsComponent.class).getHealth();
        if(dis == 2){
            if(foodImage.size() <= 0 && health <100){
                //if water icon count less than 0, then it will be into that codes to run. it reduce player health value.
                //reduce player health value
                MainGameScreen.players.getComponent(CombatStatsComponent.class).setHealth(
                        MainGameScreen.players.getComponent(CombatStatsComponent.class).getHealth() + 1
                );
            }
        }
    }
    
    /**
     * Updates the Chicken ui time by time increase.
     */
    public void updatePlayerTimerUI(int dis) {
        if(dis > 0){
            if(foodImage.size() > 0){
                table1.reset();
                table.reset();
                table.top().left();
                table.setFillParent(true);
                table.padTop(100f).padLeft(10f);
                foodImage.remove(foodImage.size() - 1);
                for (Image ima: foodImage) {
                    table.add(ima).size(30f).pad(3);
                }
            }
            if(foodImage.size() <= 0){
                table1.reset();
                table1.top().left();
                table1.setFillParent(true);
                table1.padTop(260f).padLeft(60f);
                table1.add(hungerIcon).size(50f).pad(3);
            }
        }
    }

    /**
     * remove foodImage by useing a for loop
     */
    @Override
    public void dispose() {
        super.dispose();
        for (int i = 0; i < foodImage.size(); ++i){
            foodImage.remove(i);
        }
    }

    /**
     * return the foodImage
     */
    public ArrayList<Image> getHunger(){
        return foodImage;
    }

    /**
     * Add/remove a image
     * @param value = 1, add a image;
     * @param value = -1, remove a image
     */
    public static void addOrRemoveImage(int value){
        if(value == 1){
            if(foodImage.size() < 4){
                foodImage.add(new Image(ServiceLocator.getResourceService()
                        .getAsset("images/food1.png", Texture.class)));
                table.add(foodImage.get(foodImage.size() - 1)).size(30f).pad(3);
            }
        }else if(value == -1){
            if(foodImage.size() > 0){
                table.removeActor(foodImage.get(foodImage.size() - 1));
            }
        }
    }

    /**
     * when player have no food, this function will return true
     */
    public static boolean isHunger(){
        return foodImage.size() <= 0;
    }

}
