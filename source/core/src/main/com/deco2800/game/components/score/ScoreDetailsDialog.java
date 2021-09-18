package com.deco2800.game.components.score;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.files.GameRecords;
import com.deco2800.game.files.GameRecords.Score;
import com.deco2800.game.ui.UIComponent;
import com.deco2800.game.utils.DateTimeUtils;

import java.util.List;

public class ScoreDetailsDialog extends UIComponent {
    private static final String OPEN_SCORE_DETAILS_DIALOG = "scoreDetailsDialog";

    private Dialog dialog;

    @Override
    public void create() {
        super.create();

        // Event when a score row is clicked
        entity.getEvents().addListener(OPEN_SCORE_DETAILS_DIALOG, (Score score) -> {
            // Get the list of the best achievements for that particular game
            List<BaseAchievementConfig> bestAchievements = GameRecords.getBestAchievementsByGame(score.game);
            // Open the dialog
            openDialog(score, bestAchievements);
        });
    }

    private void openDialog(Score score, List<BaseAchievementConfig> bestAchievements) {
        // Display gui
        dialog = new Dialog("", skin);
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(true);
        Image background = new Image(new Texture("images/story/chapterDialog.png"));
        background.setScaling(Scaling.fit);
        dialog.setBackground(background.getDrawable());

        dialog.pad(50).padTop(120);
        // Render the date
        String date = DateTimeUtils.getVerboseDate(score.getDateTime());

        // Render the time
        String time = DateTimeUtils.getFormattedTime(score.getDateTime());

        // Render the list of the best achievements with name, asset and bonus

        // Render final score
        dialog.getButtonTable().add(renderCloseButton()).size(50, 50).row();
        // Show dialog on screen
        dialog.show(stage);
    }

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
    private void closeDialog(){
        dialog.hide();
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }
}
