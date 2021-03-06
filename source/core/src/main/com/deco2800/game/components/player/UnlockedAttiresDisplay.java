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

/**
 * A UI component to display unlocked attires and the achievements needed to unlock attires
 */

public class UnlockedAttiresDisplay extends UIComponent {
    private final GdxGame game;
    private final int goldAchievements;
    private Table bgTable;
    private Table table;
    private Table crossTable;
    private Table unlockedAttiresTable;
    private String attire;
    public String attireType;
    private PlayerConfig stats;
    private Dialog dialog;
    private static final String ATTIRE_PATH = "images/mpc/attires/";
    private int i = 0;
    private static final String[] bgTextures = {"images/menu_background/attires_background.png"};
    private static final String[] achievementTextures = {ATTIRE_PATH + "trophies_2x.png", ATTIRE_PATH + "trophies_4x.png", ATTIRE_PATH + "trophies_6x.png"};
    private static final String[] attireTextures = {ATTIRE_PATH + "original.png", ATTIRE_PATH + "gold_2.png", ATTIRE_PATH + "gold_4.png", ATTIRE_PATH + "gold_6.png"};

    public UnlockedAttiresDisplay(GdxGame game,int goldAchievements) {
        this.goldAchievements = goldAchievements;
        this.game = game;
        this.stats = FileLoader.readClass(PlayerConfig.class, "configs/player.json");
        this.attire = stats.attire;
        ServiceLocator.registerResourceService(new ResourceService());
        loadAssets();

    }
    /**
     * Load all attires' and achievements' assets
     */
    private void loadAssets() {
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(bgTextures);
        resourceService.loadTextures(achievementTextures);
        resourceService.loadTextures(attireTextures);


        ServiceLocator.getResourceService().loadAll();
    }

    /**
     * Unload all attires' and achievements' assets
     */
    private void unloadAssets() {
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

    /**
     * @param batch Batch to render to.
     */
    @Override
    protected void draw(SpriteBatch batch) {
    // Draw batch
    }

    /**
     * Create a new table and add actors to the stage
     */

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
        bgTable.setFillParent(true);
        bgTable.add(background);
        crossTable = new Table();
        crossTable.setFillParent(true);
        crossTable.top().right();
        crossTable.add(crossImg);

        table = new Table();
        table.setFillParent(true);

        Label label1 = new Label("UNLOCKED ATTIRES", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        label1.setFontScale(4);

        table.center();
        table.add(label1);
        table.row();
        unlockedAttiresTable = new Table();

        
        if (goldAchievements == 0) {
            renderZeroUnlockedAttiresTable();
        } else {
            renderUnlockedAttiresTable();
        }


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
        Label message1 = new Label("YOU HAVEN'T UNLOCKED ANY NEW ATTIRES YET!",
                new Label.LabelStyle(new BitmapFont(), Color.RED));
        message1.setFontScale(3f);
        table.add(message1).padTop(20f).center();
        table.row();
        Label message2 = new Label("UNLOCK MORE GOLD ACHIEVEMENTS TO ACCESS NEW ATTIRES!",
                new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        message2.setFontScale(2f);
        table.add(message2).padTop(20f).center();
        table.row();

        Image gold_2 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/mpc/attires/gold_2.png", Texture.class));
        table.add(gold_2).padLeft(10f).padRight(10f).padTop(20f).size(220, 150);
        table.row();
        Image gold_4 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/mpc/attires/gold_4.png", Texture.class));
        table.add(gold_4).padLeft(10f).padRight(10f).padTop(20f).size(220, 150);
        table.row();
        Image gold_6 = new Image(ServiceLocator.getResourceService()
                .getAsset("images/mpc/attires/gold_6.png", Texture.class));
        table.add(gold_6).padLeft(10f).padRight(10f).padTop(20f).size(220, 150);
        table.row();
    }


    /**
     * Utility function to render the given list of achievements and corresponding unlocked attires
     * @param goldAchievements number of gold achievements
     * @param alpha the opacity of each image (low for the ones which are locked)
     */
    private void renderUnlockedAttires(int goldAchievements, float alpha) {
        Label label2 = new Label("SELECT AN ATTIRE", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        label2.setFontScale(2);
        table.center();
        table.add(label2).padTop(10f).padBottom(10f);
        table.row();

        if(goldAchievements < 2) {
            table.removeActor(label2);
            Label message1 = new Label("UNLOCK 1 MORE GOLD ACHIEVEMENT TO ACCESS NEW ATTIRES!",
                    new Label.LabelStyle(new BitmapFont(), Color.RED));
            message1.setFontScale(1.5f);
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
            moreThanSix(alpha);

        }

    }
    /**
     * Renders the original attire
     */

    private void renderOriginalAttire() {

        ImageButton attireImg = getImageButton("images/mpc/attires/original.png");
        attireImg.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                attireType = "OG";
                MPCConfig.updateAttire(attireType);
                confirmSelection("ORIGINAL", "original");

            }
        });
        unlockedAttiresTable.add(attireImg).left().padLeft(10f).padRight(10f).size(220, 150);
        unlockedAttiresTable.center();
        unlockedAttiresTable.row();
        
    }

    /**
     * Renders the gold_2 attire when the number of achievements < 4
     */

    private void lessThanFour(float alpha) {
        renderOriginalAttire();
        Image achievementImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/mpc/attires/trophies_2x.png", Texture.class));
        achievementImg.setScaling(Scaling.fit);
        achievementImg.setColor(255, 255, 255, alpha);

        ImageButton attireImg = getImageButton("images/mpc/attires/gold_2.png");
        attireImg.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                attireType = "gold_2";
                MPCConfig.updateAttire(attireType);
                confirmSelection("GOLD_2", "gold_2");

            }
        });
        unlockedAttiresTable.add(attireImg).left().padTop(10f).padLeft(10f).padRight(10f).size(220, 150);
        unlockedAttiresTable.add(achievementImg).right().padTop(10f).padLeft(10f).padRight(10f).size(220, 150);

        if(goldAchievements == 3) {
            Label message1 = new Label("UNLOCK 1 MORE GOLD ACHIEVEMENT TO ACCESS A NEW ATTIRE!",
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

    /**
     * Renders the gold_2 and gold_4 attires when the number of achievements < 6
     */

    private void lessThanSix(float alpha) {
        Image achievementImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/mpc/attires/trophies_4x.png", Texture.class));
        achievementImg.setScaling(Scaling.fit);
        achievementImg.setColor(255, 255, 255, alpha);

        ImageButton attireImg = getImageButton("images/mpc/attires/gold_4.png");
        attireImg.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                attireType = "gold_4";
                MPCConfig.updateAttire(attireType);
                confirmSelection("GOLD_4", "gold_4");

            }
        });


        unlockedAttiresTable.add(attireImg).left().padLeft(10f).padTop(10f).padRight(10f).size(220, 150);
        unlockedAttiresTable.add(achievementImg).right().padLeft(10f).padTop(10f).padRight(10f).size(220, 150);

        if(goldAchievements == 5) {
            Label message1 = new Label("UNLOCK 1 MORE GOLD ACHIEVEMENT TO ACCESS A NEW ATTIRE!",
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

    /**
     * Renders the gold_2, gold_4, gold_6 attires when the number of achievements > 6
     */

    private void moreThanSix(float alpha) {
        Label message1 = new Label("YOU HAVE UNLOCKED ALL ATTIRES FOR NOW!",
                new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        message1.setFontScale(2f);
        unlockedAttiresTable.add(message1).padTop(20f).padBottom(20f).center();
        unlockedAttiresTable.row();
        unlockedAttiresTable.center();
        lessThanFour(alpha);
        lessThanSix(alpha);

        Image achievementImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/mpc/attires/trophies_6x.png", Texture.class));
        achievementImg.setScaling(Scaling.fit);
        achievementImg.setColor(255, 255, 255, alpha);

        ImageButton attireImg = getImageButton("images/mpc/attires/gold_6.png");
        attireImg.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                attireType = "gold_6";
                MPCConfig.updateAttire(attireType);
                confirmSelection("GOLD_6", "gold_6");

            }
        });


        unlockedAttiresTable.add(attireImg).left().padLeft(10f).padTop(10f).padRight(10f).size(220, 150);
        unlockedAttiresTable.add(achievementImg).right().padLeft(10f).padTop(10f).padRight(10f).size(220, 150);
    }

    @Override
    public void dispose() {
        super.dispose();
        crossTable.clear();
        table.clear();
        bgTable.clear();
    }

    /**
     * UI Component for Confirm Selected Attire Pop Up Screen
     */

    private void confirmSelection (String attireType, String attirePath) {

        dialog = new Dialog("YOU HAVE SELECTED THE " + attireType + " ATTIRE!", skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(true);

        dialog.pad(50).padTop(120);
        Image attireImg = new Image(new Texture(ATTIRE_PATH + attirePath + ".png"));
        Label heading = new Label("ATTIRE SELECTED!", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        heading.setFontScale(2f);
        dialog.getContentTable().add(heading).expandX().row();
        dialog.getContentTable().add(attireImg);
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