package com.deco2800.game.components.items;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.deco2800.game.components.CombatStatsComponent;
import com.deco2800.game.components.Component;
import com.deco2800.game.components.achievements.AchievementsHelper;
import com.deco2800.game.components.player.InventoryComponent;
import com.deco2800.game.entities.Entity;
import com.deco2800.game.physics.PhysicsLayer;
import com.deco2800.game.physics.components.PhysicsComponent;
import com.deco2800.game.rendering.TextureRenderComponent;
import com.deco2800.game.services.ServiceLocator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GoldComponent extends Component {
    Entity player;
    File file;
    FileWriter fileWriter;

    public GoldComponent(Entity player) {
        this.player = player;
        file =new File("./core/assets/gold.txt");
        try {
        fileWriter = new FileWriter(file.getName());}
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void create(){
        entity.getEvents().addListener("collisionStart", this::onCollisionStart);
        entity.getEvents().addListener("recordGold", this::recordGold);
    }
    public void update() {
        if (player.getComponent(CombatStatsComponent.class).isDead()) {
            entity.getEvents().trigger("recordGold", player.getComponent(InventoryComponent.class).getGold());
        }
    }

    private void onCollisionStart(Fixture me, Fixture other){

        if (PhysicsLayer.contains(PhysicsLayer.PLAYER, other.getFilterData().categoryBits)) // checking if the collision is done with the player
        {
            entity.getEvents().trigger("itemPickedUp");
            AchievementsHelper.getInstance().trackItemPickedUpEvent();
            Body physBody = entity.getComponent(PhysicsComponent.class).getBody();
            if (physBody.getFixtureList().contains(other, true)) {
                physBody.destroyFixture(other);
            }
            entity.getComponent(TextureRenderComponent.class).dispose();
            ServiceLocator.getEntityService().unregister(entity);
            this.player.getComponent(InventoryComponent.class).addGold(1);
        }
    }

    private void recordGold(int gold) {
        try{
            String goldAmountLastRound = Integer.toString(gold);

            fileWriter.write(goldAmountLastRound);
            fileWriter.flush();
            fileWriter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
