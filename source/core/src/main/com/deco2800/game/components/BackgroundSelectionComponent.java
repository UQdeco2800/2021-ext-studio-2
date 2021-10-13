package com.deco2800.game.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

public class BackgroundSelectionComponent extends UIComponent {
    public static final String ON_CLICK = "OnClick";
    private final String[] textures = {"images/settings/musicSelectionDialog.png",
            "images/settings/musicSelectionButton.png"};
    private Table musicSelectionTable;

    @Override
    public void create() {
        super.create();
        loadAssets();
        addActors();
    }

    private void loadAssets() {
        ServiceLocator.getResourceService().loadTextures(textures);
        ServiceLocator.getResourceService().loadAll();
    }

    private void addActors() {
        Image img = new Image(ServiceLocator.getResourceService()
                .getAsset("images/settings/musicSelectionDialog.png", Texture.class));

        Image musicImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/settings/musicSelectionButton.png", Texture.class));
        ImageButton musicImgBtn = new ImageButton(musicImg.getDrawable());
        musicImgBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            }
        });

        musicSelectionTable = new Table();
        musicSelectionTable.setFillParent(true);
        musicSelectionTable.bottom().right();
        musicSelectionTable.add(musicImgBtn);

        stage.addActor(musicSelectionTable);
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }

    @Override
    public void dispose() {
        super.dispose();
        musicSelectionTable.clear();
        unloadAssets();
    }

    private void unloadAssets() {
        ServiceLocator.getResourceService().unloadAssets(textures);
    }
}
