package com.deco2800.game.components.score;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.deco2800.game.entities.configs.achievements.BaseAchievementConfig;
import com.deco2800.game.entities.factories.AchievementFactory;
import com.deco2800.game.files.GameInfo;
import com.deco2800.game.files.GameRecords;
import com.deco2800.game.files.GameRecords.Score;
import com.deco2800.game.ui.UIComponent;
import com.deco2800.game.utils.DateTimeUtils;

import java.text.DecimalFormat;
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
        Image background = new Image(new Texture("images/achievements/trophyDialogSilver.png"));
        background.setScaling(Scaling.fit);
        dialog.setBackground(background.getDrawable());
        Table table = new Table();
        // Render the game count
        dialog.getContentTable().add(new Label("Attempt: " + GameInfo.getGameCount(), skin));
        dialog.getContentTable().row();
        // Render the date
        String date = DateTimeUtils.getVerboseDate(score.getDateTime());
        dialog.getContentTable().add(new Label("Date: " + date, skin));
        dialog.getContentTable().row();
        // Render the time
        String time = DateTimeUtils.getFormattedTime(score.getDateTime());
        dialog.getContentTable().add(new Label("Time: " + time, skin)).padBottom(25);
        dialog.getContentTable().row();

        // Render the list of the best achievements with name, asset and bonus
        bestAchievements.forEach(achievement -> {
            table.add(new Image(new Texture(AchievementFactory.getAchievementTrophy(achievement)))).size(45).padTop(5).padBottom(5);
            table.add(new Label(achievement.name, new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY)))
                    .padRight(40).padLeft(25);
            table.add(new Label("+ " + achievement.bonus, new Label.LabelStyle(new BitmapFont(), Color.YELLOW)));
            table.row();
        });

        dialog.getContentTable().add(table).width(600).row();
        dialog.pad(50).padTop(120);
        // Render distance travelled, if it exists
        if (score.distance > 1) {
            String formattedDistance = new DecimalFormat("#.##").format(score.distance);
            dialog.getButtonTable().add(new Label("Distance: " + formattedDistance + "m", skin)).row();
        }
        // Render final score
        dialog.getButtonTable().add(new Label("Score: " + score.getScore().toString(), skin)).row();
        dialog.getButtonTable().add(renderCloseButton()).size(50, 50).row();
        dialog.getButtonTable().padBottom(69);
        // Show dialog on screen
        dialog.show(stage);
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
                closeDialog();
            }
        });

        return closeButton;
    }

    private void closeDialog() {
        dialog.hide();
    }

    @Override
    protected void draw(SpriteBatch batch) {
        // This is empty because LibGDX tables provide an easier
        // interface to implement GUI. Also, draw is handled
        // by the stage.
    }
}
