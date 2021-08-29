package com.deco2800.game.components.achievements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.entities.factories.AchievementFactory;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

public class AchievementsDisplay extends UIComponent{
    Table table;
    private Image achievementImg;
    private Label achievementLabel;
    private static final String[] textures = AchievementFactory.getTextures();
    long prevTime;

    @Override
    public void create() {
        super.create();
        loadAssets();
        addActors();
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


    public void updateAchievementsUI(BaseAchievementConfig achievement) {
        if(achievementLabel != null && achievementImg != null) {
            table.clear();
        }
        renderAchievement(achievement);
    }

    private void renderAchievement(BaseAchievementConfig achievement){
        CharSequence text = achievement.message;
        achievementLabel = new Label(text, skin, "small");
        achievementImg = new Image(ServiceLocator.getResourceService()
                .getAsset(achievement.iconPath, Texture.class));

        table.row().padTop(10f);
        table.add(achievementImg).size(300f,150f);
        table.row().padTop(15f);

        table.add(achievementLabel);
    }

    @Override
    public void dispose() {
        super.dispose();
        if(achievementImg.isVisible()){
            achievementImg.remove();
            achievementLabel.remove();
        }
        unloadAssets();
    }
}
