package com.deco2800.game.components.buff;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.deco2800.game.GdxGame;
import com.deco2800.game.services.ServiceLocator;
import com.deco2800.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstructionsDisplay extends UIComponent {

    private final GdxGame game;
    Table bgTable;
    Table buttonTable;

    public InstructionsDisplay(GdxGame game) {
        this.game = game;
    }
    public void create() {
        super.create();
        createInstructionManual();
    }


    private void createInstructionManual() {
        // Create Button to the Instruction menu
        TextButton mainMenuButton = new TextButton("Main Menu", skin);
        mainMenuButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
                    }
                });


        Image bgImage = new Image(ServiceLocator.getResourceService()
                .getAsset("images/TutorialScreen1.png", Texture.class));
        bgImage.setScaling(Scaling.fit);
        buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.top().left();
        buttonTable.add(mainMenuButton);

        bgTable = new Table();
        bgTable.setFillParent(true);
        bgTable.center();
        bgTable.add(bgImage);
        // add the board to the stage first so that its can be under of score data
        stage.addActor(bgTable);
        stage.addActor(buttonTable);


    }
    @Override
    public void dispose() {
        bgTable.clear();
        super.dispose();
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }
}
