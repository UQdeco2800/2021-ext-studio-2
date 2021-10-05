package com.deco2800.game.components.foodAndwater;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.components.items.TestBuffForItem;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

import java.util.ArrayList;

/**
 * A ui component for displaying recycle system.
 */
public class RecycleDisplay extends UIComponent {
    public static RecycleDisplay.recycleState recycleState = RecycleDisplay.recycleState.hp;
    public enum recycleState {
        food,
        water,
        hp
    }

    public static Table table;
    public static ArrayList<Image> recycleIcon = new ArrayList<>();
    public static int recycleNumber = 0;
    public static boolean isKey =false;


    public RecycleDisplay() { }
    /**
     * Creates reusable recycle ui styles and adds actors to the stage.
     * And starts the counting the time.
     */
    @Override
    public void create() {
        super.create();
        TestBuffForItem.countNumber = 0;
        addActors();
        entity.getEvents().addListener("updateRecover", this::updateRecyleIconUI);
    }


    /**
     * Creates actors, add images into list, and positions them on the stage using a table.
     */
    private void addActors() {
        table = new Table();
        table.bottom().right();
        table.setFillParent(true);
        table.padBottom(50).padRight(400);

        float iconLength = 60f;
        if(TestBuffForItem.countNumber >= 0){
            {
                switch (TestBuffForItem.countNumber){
                    case 0:{
                        recycleIcon.add(new Image(ServiceLocator.getResourceService()
                                .getAsset("images/itembar/recycle/recycle-256px-default.png", Texture.class)));
                        table.add(recycleIcon.get(0)).size(iconLength).pad(20);
                    }break;
                    case 1:{
                        if(recycleState == RecycleDisplay.recycleState.hp){
                            recycleIcon.add(new Image(ServiceLocator.getResourceService()
                                    .getAsset("images/itembar/recycle/recycle-256px-bb1.png", Texture.class)));
                        }else if(recycleState == RecycleDisplay.recycleState.food){
                            recycleIcon.add(new Image(ServiceLocator.getResourceService()
                                    .getAsset("images/itembar/recycle/recycle-256px-leg1.png", Texture.class)));
                        }else {
                            recycleIcon.add(new Image(ServiceLocator.getResourceService()
                                    .getAsset("images/itembar/recycle/recycle-256px-water1.png", Texture.class)));
                        }

                        table.add(recycleIcon.get(0)).size(iconLength).pad(20);
                    }break;
                    case 2:{
                        if(recycleState == RecycleDisplay.recycleState.hp){
                            recycleIcon.add(new Image(ServiceLocator.getResourceService()
                                    .getAsset("images/itembar/recycle/recycle-256px-bb2.png", Texture.class)));
                        }else if(recycleState == RecycleDisplay.recycleState.food){
                            recycleIcon.add(new Image(ServiceLocator.getResourceService()
                                    .getAsset("images/itembar/recycle/recycle-256px-leg2.png", Texture.class)));
                        }else {
                            recycleIcon.add(new Image(ServiceLocator.getResourceService()
                                    .getAsset("images/itembar/recycle/recycle-256px-water2.png", Texture.class)));
                        }

                        table.add(recycleIcon.get(0)).size(iconLength).pad(20);
                    }break;
                    case 3:{
                        if(recycleState == RecycleDisplay.recycleState.hp){
                            recycleIcon.add(new Image(ServiceLocator.getResourceService()
                                    .getAsset("images/itembar/recycle/recycle-256px-bb3.png", Texture.class)));
                        }else if(recycleState == RecycleDisplay.recycleState.food){
                            recycleIcon.add(new Image(ServiceLocator.getResourceService()
                                    .getAsset("images/itembar/recycle/recycle-256px-leg3.png", Texture.class)));
                        }else {
                            recycleIcon.add(new Image(ServiceLocator.getResourceService()
                                    .getAsset("images/itembar/recycle/recycle-256px-water3.png", Texture.class)));
                        }

                        table.add(recycleIcon.get(0)).size(iconLength).pad(20);
                        RecycleDisplay.recycleNumber = 1;
                    }
                }
            }
        }

        //add images into the screen

        stage.addActor(table);
    }

    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage
    }

    @Override
    public void update() {
        super.update();

        //update the food regularly
        entity.getEvents().trigger("updateRecover", 1);
    }

    /**
     * Updates the Chicken ui time by time increase.
     */
    public void updateRecyleIconUI(int dis) {
        if(dis > 0){
            for(int i=recycleIcon.size()-1; i>=0; --i){
                table.removeActor(recycleIcon.get(i));
                recycleIcon.remove(recycleIcon.get(i));
            }
            table.reset();

            table.bottom().right();
            table.setFillParent(true);
            table.padBottom(50).padRight(400);

            float iconLength = 60f;
            if(TestBuffForItem.countNumber >= 0){
                {
                    switch (TestBuffForItem.countNumber){
                        case 0:{
                            recycleIcon.add(new Image(ServiceLocator.getResourceService()
                                    .getAsset("images/itembar/recycle/recycle-256px-default.png", Texture.class)));
                            table.add(recycleIcon.get(0)).size(iconLength).pad(20);
                        }break;
                        case 1:{
                            if(recycleState == RecycleDisplay.recycleState.hp){
                                recycleIcon.add(new Image(ServiceLocator.getResourceService()
                                        .getAsset("images/itembar/recycle/recycle-256px-bb1.png", Texture.class)));
                            }else if(recycleState == RecycleDisplay.recycleState.food){
                                recycleIcon.add(new Image(ServiceLocator.getResourceService()
                                        .getAsset("images/itembar/recycle/recycle-256px-leg1.png", Texture.class)));
                            }else {
                                recycleIcon.add(new Image(ServiceLocator.getResourceService()
                                        .getAsset("images/itembar/recycle/recycle-256px-water1.png", Texture.class)));
                            }

                            table.add(recycleIcon.get(0)).size(iconLength).pad(20);
                        }break;
                        case 2:{
                            if(recycleState == RecycleDisplay.recycleState.hp){
                                recycleIcon.add(new Image(ServiceLocator.getResourceService()
                                        .getAsset("images/itembar/recycle/recycle-256px-bb2.png", Texture.class)));
                            }else if(recycleState == RecycleDisplay.recycleState.food){
                                recycleIcon.add(new Image(ServiceLocator.getResourceService()
                                        .getAsset("images/itembar/recycle/recycle-256px-leg2.png", Texture.class)));
                            }else {
                                recycleIcon.add(new Image(ServiceLocator.getResourceService()
                                        .getAsset("images/itembar/recycle/recycle-256px-water2.png", Texture.class)));
                            }

                            table.add(recycleIcon.get(0)).size(iconLength).pad(20);
                        }break;
                        case 3:{
                            if(recycleState == RecycleDisplay.recycleState.hp){
                                recycleIcon.add(new Image(ServiceLocator.getResourceService()
                                        .getAsset("images/itembar/recycle/recycle-256px-bb3.png", Texture.class)));
                            }else if(recycleState == RecycleDisplay.recycleState.food){
                                recycleIcon.add(new Image(ServiceLocator.getResourceService()
                                        .getAsset("images/itembar/recycle/recycle-256px-leg3.png", Texture.class)));
                            }else {
                                recycleIcon.add(new Image(ServiceLocator.getResourceService()
                                        .getAsset("images/itembar/recycle/recycle-256px-water3.png", Texture.class)));
                            }
                            table.add(recycleIcon.get(0)).size(iconLength).pad(20);
                            RecycleDisplay.recycleNumber =1;
                        }
                    }
                }
            }
            //add images into the screen
            stage.addActor(table);
        }
    }

    /**
     * remove recycleIcon by useing a for loop
     */
    @Override
    public void dispose() {
        super.dispose();
        for (int i = 0; i < recycleIcon.size(); ++i){
            recycleIcon.remove(i);
        }
    }
}
