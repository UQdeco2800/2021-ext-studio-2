package com.deco2800.game.components.achievements.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.ui.UIComponent;

public class AchievementRecordsDisplay extends UIComponent {

    Table table;

    @Override
    public void create() {
        super.create();

        addActors();
    }

    private void addActors() {
        table = new Table();
        table.setFillParent(true);

        Label label = new Label("Your Best Achievements", skin);
        label.setFontScale(3f);
        table.top().left().add(label).pad(5);
        stage.addActor(table);

    }

    @Override
    protected void draw(SpriteBatch batch) {

    }

    @Override
    public void dispose() {
        super.dispose();

        table.clear();
    }
}
