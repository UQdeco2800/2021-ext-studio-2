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

    /**
     * Creates a gold component that detects when the player collides with
     * the entity and when collided the player character will achieve a goldCoin.
     * If the player character died in the current game round, the amount of
     * goldCoin it got will be recorded and it could be used at the props shop
     * @param player  the entity which could pickup golds
     */
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

    /**
     * Checks if the collision is done with the player and then a goldCoin will be added to the character
     * and triggers "ItemPickedUp" event
     * @param me the fixture of player character
     * @param other the fixture of the entity which the component subjects to
     */
    private void onCollisionStart(Fixture me, Fixture other){

        if (PhysicsLayer.contains(PhysicsLayer.PLAYER, other.getFilterData().categoryBits)) // checking if the collision is done with the player
        {
            entity.getEvents().trigger("itemPickedUp");
            AchievementsHelper.getInstance().trackItemPickedUpEvent();
            this.player.getComponent(InventoryComponent.class).addGold(1);
            Body physBody = entity.getComponent(PhysicsComponent.class).getBody();
            if (physBody.getFixtureList().contains(other, true)) {
                physBody.destroyFixture(other);
            }
            try {
                entity.getComponent(TextureRenderComponent.class).dispose();
                ServiceLocator.getEntityService().unregister(entity);}
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * This func is used to store the amount of goldCoin got from the player character
     * @param  gold the amount of goldCoin got from the player character in the current game round
     */
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
