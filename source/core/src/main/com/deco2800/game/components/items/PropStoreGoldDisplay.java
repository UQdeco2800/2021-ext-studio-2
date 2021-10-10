package com.deco2800.game.components.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.game.files.PropStoreRecord;
import com.deco2800.game.ui.UIComponent;

/**
 * A ui component for displaying the props shop gold .
 */
public class PropStoreGoldDisplay extends UIComponent {
    Label gold;
    @Override
    public void create() {
        super.create();
        entity.getEvents().addListener("GoldUpdate", this::displayGold);
        displayGold();
    }

    private void displayGold(){
        Table table = new Table();
        table.setFillParent(true);
        table.top().left();
        Label price = new Label("Total Gold $: " + PropStoreRecord.getGold(), new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        price.setFontScale(1.5f);
        price.setWrap(true);
        table.add(price);
        stage.addActor(table);


    }
    @Override
    protected void draw(SpriteBatch batch) {

    }
}
