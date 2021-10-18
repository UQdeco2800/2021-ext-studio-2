package com.deco2800.game.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.deco2800.game.files.meta.BackgroundMusic;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;

public class BackgroundSelectionComponent extends UIComponent {
    private final String[] textures = {"images/settings/musicSelectionDialog.png",
            "images/settings/musicSelectionButton.png", "images/settings/radioButtonOn.png", "images/settings/radioButtonOff.png"};
    private String screenName;
    private final String iconPosition;
    private Table musicSelectionTable;
    private Dialog dialog;

    /**
     * @param screenName name of the screen
     *                   The position of the icon defaults to the bottom right unless specified in the
     *                   second argument of this contructor.
     */
    public BackgroundSelectionComponent(String screenName) {
        this.screenName = screenName;
        iconPosition = "tr";
    }

    /**
     * @param screenName   name of the screen
     * @param iconPosition Four positions are accepted: 'tl', 'tr', 'bl', 'br'.
     *                     These are abbreviations for the four corners of the screen. Position if not specified
     *                     defaults to the bottom right.
     */
    public BackgroundSelectionComponent(String screenName, String iconPosition) {
        this.screenName = screenName;
        this.iconPosition = iconPosition;
    }

    @Override
    public void create() {
        super.create();
        entity.getEvents().addListener("openSelectionDialog", (String screenName1) -> {
            this.screenName = screenName1;
            showMusicDialog();
        });

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

        switch (iconPosition) {
            case "tl":
                musicSelectionTable.top().left();
                break;
            case "bl":
                musicSelectionTable.bottom().left();
                break;
            case "br":
                musicSelectionTable.bottom().right();
                break;
            default:
                musicSelectionTable.top().right();
        }
        musicSelectionTable.add(musicImgBtn).size(120, 120);

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

        renderTracks();

        dialog.getButtonTable().add(renderCloseButton()).size(50, 50).row();

        dialog.show(stage);
    }

    private void renderTracks() {
        Label heading = new Label("Select background music for " + screenName, skin);
        heading.setFontScale(1.5f);
        dialog.getContentTable().add(heading).expandX().row();

        String chosenTrack = BackgroundMusic.getSelectedMusic(screenName);
        for (String trackPath : BackgroundMusic.getAllMusicByScreen(screenName)) {
            if (trackPath.equals(chosenTrack)) {
                dialog.getContentTable().add(getTrackTable(trackPath, true)).padTop(30).row();
            } else {
                dialog.getContentTable().add(getTrackTable(trackPath, false)).padTop(30).row();
            }
        }
    }

    private String getSongName(String trackPath) {
        return trackPath.replace("sounds/customBgm/", "").trim();
    }

    private Table getTrackTable(String trackPath, boolean selected) {
        String songName = getSongName(trackPath);
        Table table = new Table();
        table.add(getRadioButton(selected)).size(50, 50);
        table.add(getLabel(songName)).width(350);

        table.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                BackgroundMusic.selectMusic(screenName, trackPath);
                entity.getEvents().trigger("changeSong", trackPath);
                dialog.getContentTable().clear();
                renderTracks();
            }
        });

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
        //draw is handled by the stage
    }

    @Override
    public void dispose() {
        super.dispose();
        musicSelectionTable.clear();
    }
}
