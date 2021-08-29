package com.deco2800.game.components.achievements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.entities.factories.AchievementFactory;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AchievementsDisplay extends UIComponent{
    Table table;
    private Image achievementImg;
    private Label achievementLabel;
    private static final String[] textures = AchievementFactory.getTextures();
    private ExecutorService service;

    @Override
    public void create() {
        super.create();
        loadAssets();
        addActors();

        service = Executors.newSingleThreadExecutor();

        entity.getEvents().addListener("updateAchievement", this::updateAchievementsUI);
    }


    private void loadAssets() {
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(textures);
        ServiceLocator.getResourceService().loadAll();
    }

    private void unloadAssets() {
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(textures);
    }


    private void addActors() {
        table = new Table();
        table.top();
        table.setFillParent(true);
        stage.addActor(table);
    }
    @Override
    public void draw(SpriteBatch batch) {

    }


    private void updateAchievementsUI(BaseAchievementConfig achievement) {

        service.execute(() -> {
            try {
                renderAchievement(achievement);
                Thread.sleep(5000);
                table.clear();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    private void renderAchievement(BaseAchievementConfig achievement){
        CharSequence text = achievement.message;
        achievementLabel = new Label(text, skin, "small");
        achievementImg = new Image(ServiceLocator.getResourceService()
                .getAsset(achievement.iconPath, Texture.class));
        table.add(achievementImg).size(300f,150f);
        table.row();
        table.add(achievementLabel);

    }

    @Override
    public void dispose() {
        super.dispose();
        if(achievementImg != null) {
            achievementImg.remove();
        }
        if(achievementLabel != null){
            achievementLabel.remove();
        }
        unloadAssets();
    }
}
