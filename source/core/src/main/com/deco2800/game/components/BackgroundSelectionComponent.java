package com.deco2800.game.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

public class BackgroundSelectionComponent extends UIComponent {
    private final String[] textures = {"images/settings/musicSelectionDialog.png",
            "images/settings/musicSelectionButton.png", "images/settings/radioButtonOn.png", "images/settings/radioButtonOff.png"};
    private Table musicSelectionTable;
    private Dialog dialog;

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
        Image musicImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/settings/musicSelectionButton.png", Texture.class));
        ImageButton musicImgBtn = new ImageButton(musicImg.getDrawable());
        musicImgBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showMusicDialog();
            }
        });

        musicSelectionTable = new Table();
        musicSelectionTable.setFillParent(true);
        musicSelectionTable.bottom().right();
        musicSelectionTable.add(musicImgBtn);

        stage.addActor(musicSelectionTable);
    }

    private void showMusicDialog() {
        dialog = new Dialog("", skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(true);

        Image dialogBackgroundImg = new Image(ServiceLocator.getResourceService()
                .getAsset("images/settings/musicSelectionDialog.png", Texture.class));

        dialog.setBackground(dialogBackgroundImg.getDrawable());

        dialog.pad(50).padTop(120);

        Label heading = new Label("Select background music", skin);
        heading.setFontScale(1.5f);

        dialog.getContentTable().add(heading).expandX().row();
        dialog.getContentTable().add(getTrackTable("Track1 placeholder text ", false)).padTop(70).row();
        dialog.getContentTable().add(getTrackTable("Track2 placeholder text ", true)).padTop(30).row();

        dialog.getButtonTable().add(renderCloseButton()).size(50, 50).row();

        dialog.show(stage);


    }

    private Table getTrackTable(String trackName, boolean selected) {
        Table table = new Table();
        table.add(getRadioButton(selected)).size(50, 50);
        table.add(getLabel(trackName)).width(350);

        return table;
    }


    private ImageButton getRadioButton(boolean status) {
        Image radioButton;
        if (status) {
            radioButton = new Image(ServiceLocator.getResourceService()
                    .getAsset("images/settings/radioButtonOn.png", Texture.class));
        } else {
            radioButton = new Image(ServiceLocator.getResourceService()
                    .getAsset("images/settings/radioButtonOff.png", Texture.class));
        }

        return new ImageButton(radioButton.getDrawable());
    }

    private Label getLabel(String text) {
        Label trackLabel = new Label(text, skin);
        trackLabel.setFontScale(1.1f);
        trackLabel.setWrap(true);
        trackLabel.setAlignment(Align.topLeft);
        return trackLabel;
    }

    /**
     * The close button which triggers a task to close the dialog.
     *
     * @return closeButton image
     */
    private ImageButton renderCloseButton() {
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
