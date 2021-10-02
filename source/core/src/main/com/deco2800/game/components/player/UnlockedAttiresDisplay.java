package com.deco2800.game.components.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.deco2800.game.GdxGame;
import com.deco2800.game.entities.configs.PlayerConfig;
import com.deco2800.game.files.FileLoader;
import com.deco2800.game.files.MPCConfig;
import com.deco2800.game.services.ResourceService;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;

public class UnlockedAttiresDisplay extends UIComponent {

    private static final Logger logger = LoggerFactory.getLogger(UnlockedAttiresDisplay.class);
    private final GdxGame game;
    private int goldAchievements;
    private Table bgTable;
    private Table table;
    private Table crossTable;
    private Table unlockedAttiresTable;
    private String attire;
    private String attireType;
    private PlayerConfig stats;
    private Dialog dialog;
    private FileReader fileReader;
    private int i = 0;
    private static final String[] bgTextures = {"images/menu_background/attires_background.png"};
    private static final String[] achievementTextures = {"images/achievements/veteranSilverTrophy.png", "images/achievements/veteranGoldTrophy.png"};
    private static final String[] attireTextures = {"images/mpc/attires/veteranSilver.png", "images/mpc/attires/veteranGold.png"};

    public UnlockedAttiresDisplay(GdxGame game,int goldAchievements) {
        this.goldAchievements = goldAchievements;
        this.game = game;
        this.stats = FileLoader.readClass(PlayerConfig.class, "configs/player.json");
        this.attire = stats.attire;
        ServiceLocator.registerResourceService(new ResourceService());
        loadAssets();

    }

    private void loadAssets() {
        logger.debug("Loading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(bgTextures);
        resourceService.loadTextures(achievementTextures);
        resourceService.loadTextures(attireTextures);


        ServiceLocator.getResourceService().loadAll();
    }

    private void unloadAssets() {
        logger.debug("Unloading assets");
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.unloadAssets(attireTextures);
        resourceService.unloadAssets(achievementTextures);
        resourceService.unloadAssets(bgTextures);
    }
    @Override
    public void create() {
        super.create();
        addActors();
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }

    private void addActors() {
        Image background =
                new Image(
                        ServiceLocator.getResourceService()
                                .getAsset("images/menu_background/attires_background.png", Texture.class));
        ImageButton crossImg = getImageButton("images/achievements/crossButton.png");
        crossImg.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(GdxGame.ScreenType.MAIN_MENU);
            }
        });

        bgTable = new Table();
        System.out.println(goldAchievements);
        bgTable.setFillParent(true);
        bgTable.add(background);
        crossTable = new Table();
        crossTable.setFillParent(true);
        crossTable.top().right();
        crossTable.add(crossImg);

        table = new Table();
        table.setFillParent(true);

        Label label1 = new Label("Unlocked Attires", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        label1.setFontScale(4);

        table.center();
        table.add(label1);
        table.row();
        unlockedAttiresTable = new Table();

        goldAchievements = 2;
        if (goldAchievements == 0) {
            renderZeroUnlockedAttiresTable();
        } else {
            renderUnlockedAttiresTable();
        }

      //  renderUnlockedAttires(goldAchievements, 0.55f);

        table.add(unlockedAttiresTable);
        table.row();
        stage.addActor(bgTable);
        stage.addActor(table);
        stage.addActor(crossTable);
    }

    /**
     * Renders all unlocked attires
     */

    private void renderUnlockedAttiresTable() {
        renderUnlockedAttires(goldAchievements, 1);
    }

    /**
     * Renders screens to show zero unlocked attires
     */
    private void renderZeroUnlockedAttiresTable() {
        Label message1 = new Label("You haven't unlocked any new attires yet!",
                new Label.LabelStyle(new BitmapFont(), Color.RED));
        message1.setFontScale(3f);
        table.add(message1).padTop(20f).center();
        table.row();
        Label message2 = new Label("Unlock more Gold Achievements to access new attires!",
                new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        message2.setFontScale(2f);
        table.add(message2).padTop(20f).center();
        table.row();

        Image silverAttire = new Image(ServiceLocator.getResourceService()
                .getAsset("images/mpc/attires/veteranSilver.png", Texture.class));
        table.add(silverAttire).center().padLeft(10f).padRight(10f).padTop(20f).size(220, 150);

        Image goldAttire = new Image(ServiceLocator.getResourceService()
                .getAsset("images/mpc/attires/veteranGold.png", Texture.class));
        table.add(goldAttire).center().padLeft(10f).padRight(10f).padTop(20f).size(220, 150);

    }


    /**
     * Utility function to render the given list of achievements and corresponding unlocked attires
     * @param goldAchievements number of gold achievements
     * @param alpha the opacity of each image (low for the ones which are locked)
     */
    private void renderUnlockedAttires(int goldAchievements, float alpha) {
        Label label2 = new Label("Select an attire", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        label2.setFontScale(2);
        table.center();
        table.add(label2).padTop(10f).padBottom(10f);
        table.row();

        if(goldAchievements < 2) {
            table.removeActor(label2);
            Label message1 = new Label("Unlock 1 more Gold achievement to access new attires!",
                    new Label.LabelStyle(new BitmapFont(), Color.RED));
            message1.setFontScale(3f);
            table.add(message1).padTop(20f).center();
            table.row();

        }
        // Unlock 1 new attire
        if(goldAchievements == 2 || goldAchievements == 3) {
            lessThanFour(alpha);
        }
        // Unlock 2 new attires
        if(goldAchievements == 4 || goldAchievements == 5) {
            lessThanFour(alpha);
            lessThanSix(alpha);
        }
        // Unlock 3 new attires
        if(goldAchievements >= 6) {
            morethanSix(alpha);

        }

    }

    private void lessThanFour(float alpha) {

        Image achievementImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/achievements/veteranGoldTrophy.png", Texture.class));
        achievementImg.setScaling(Scaling.fit);
        achievementImg.setColor(255, 255, 255, alpha);

        ImageButton attireImg = getImageButton("images/mpc/attires/veteranSilver.png");
        attireImg.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                attireType = "gold_2";
                MPCConfig.updateAttire(attireType);
                confirmSelection("Veteran Silver", "veteranSilver");

            }
        });
        unlockedAttiresTable.add(achievementImg).right().padLeft(10f).padRight(10f).size(220, 150);
        unlockedAttiresTable.add(attireImg).left().padLeft(10f).padRight(10f).size(220, 150);

        if(goldAchievements == 3) {
            Label message1 = new Label("Unlock 1 more Gold achievement to access a new attire!",
                    new Label.LabelStyle(new BitmapFont(), Color.RED));
            message1.setFontScale(1.5f);
            unlockedAttiresTable.row();
            unlockedAttiresTable.add(message1).padLeft(10f).padRight(10f).size(120, 50);
            unlockedAttiresTable.row();
        }
        if (i % 3 == 0) {
            unlockedAttiresTable.row();
        }

    }

    private void lessThanSix(float alpha) {

        Image achievementImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/achievements/veteranGoldTrophy.png", Texture.class));
        achievementImg.setScaling(Scaling.fit);
        achievementImg.setColor(255, 255, 255, alpha);

        ImageButton attireImg = getImageButton("images/mpc/attires/veteranGold.png");
        attireImg.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                attireType = "gold_4";
                MPCConfig.updateAttire(attireType);
                confirmSelection("Veteran Gold", "veteranGold");

            }
        });

        unlockedAttiresTable.add(achievementImg).right().padLeft(10f).padRight(10f).size(220, 150);
        unlockedAttiresTable.add(attireImg).left().padLeft(10f).padRight(10f).size(220, 150);

        if(goldAchievements == 5) {
            Label message1 = new Label("Unlock 1 more Gold achievement to access a new attire!",
                    new Label.LabelStyle(new BitmapFont(), Color.RED));
            message1.setFontScale(1.5f);
            unlockedAttiresTable.row();
            unlockedAttiresTable.add(message1).padLeft(10f).padRight(10f).size(120, 50).center();
            unlockedAttiresTable.row();
        }
        if (i % 3 == 0) {
            unlockedAttiresTable.row();
        }
    }

    private void morethanSix(float alpha) {

        Label message1 = new Label("You have unlocked all attires for now!",
                new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        message1.setFontScale(2f);
        unlockedAttiresTable.add(message1).padTop(20f).padBottom(20f).center();
        unlockedAttiresTable.row();
        unlockedAttiresTable.center();
        lessThanFour(alpha);
        lessThanSix(alpha);

        Image achievementImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/achievements/veteranGoldTrophy.png", Texture.class));
        achievementImg.setScaling(Scaling.fit);
        achievementImg.setColor(255, 255, 255, alpha);

        ImageButton attireImg = getImageButton("images/mpc/attires/veteranGold.png");
        attireImg.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                attireType = "gold_6";
                MPCConfig.updateAttire(attireType);
                confirmSelection("Veteran Gold", "veteranGold");

            }
        });

        unlockedAttiresTable.add(achievementImg).right().padLeft(10f).padRight(10f).size(220, 150);
        unlockedAttiresTable.add(attireImg).left().padLeft(10f).padRight(10f).size(220, 150);
    }

    @Override
    public void dispose() {
        super.dispose();
        crossTable.clear();
        table.clear();
        bgTable.clear();
    }

    private void confirmSelection (String attireType, String attirePath) {

        dialog = new Dialog("You have selected the " + attireType + " attire!", skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(true);

        dialog.pad(50).padTop(120);
        Image attire = new Image(new Texture("images/mpc/attires/" + attirePath + ".png"));
        Label heading = new Label("Attire Selected!", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        heading.setFontScale(2f);
        dialog.getContentTable().add(heading).expandX().row();
        dialog.getContentTable().add(attire);
        dialog.getButtonTable().add(renderCloseButton()).size(50, 50).row();

        dialog.show(stage);


    }


    /**
     * Returns an image button to be reused everywhere.
     * @param path the image path
     * @return ImageButton to be displayed
     */
    private ImageButton getImageButton(String path) {
        return new ImageButton(new TextureRegionDrawable(new TextureRegion(
                new Texture(path))));
    }
    private ImageButton renderCloseButton() {
        return getImageButton(dialog);
    }

    public static ImageButton getImageButton(Dialog dialog) {
        Image crossButtonImg = new Image(new Texture("images/achievements/crossButton.png"));

        ImageButton closeButton = new ImageButton(crossButtonImg.getDrawable());

        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dialog.hide();
            }
        });

        return closeButton;
    }
}