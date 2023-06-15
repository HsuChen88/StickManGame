import java.util.Random;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Boss {
	private double posX;
	private double posY;
	public double life;
	public boolean dead = false;

	final static double BOSSWIDTH = 50;
	final static double BOSSHEIGHT = 46;
	final static double SIZEPROPORTION = 1.5;
	final static double SKILLPROPORTION = 0.6;

	public ImageView bossImageView;
	public ImageView beamImageView = new ImageView();

	private Timeline BeamTimeline = new Timeline(new KeyFrame(Duration.millis(60), event -> handleBeam()));
	private int beamIndex = 0;

	private Random random = new Random();
	int rand;
	private PauseTransition randomPause = new PauseTransition();

	public Boss(double x, double y, Image img, double life) {
		posX = x;
		posY = y;
		bossImageView = new ImageView(img);
		this.life = life;
	}

	public void setup() {

		// setup for ImageView
		bossImageView.setLayoutX(posX);
		bossImageView.setLayoutY(posY);
		bossImageView.setFitHeight(BOSSHEIGHT * SIZEPROPORTION);
		bossImageView.setFitWidth(BOSSWIDTH * SIZEPROPORTION);

		beamImageView.setFitHeight(StickManGame.BEAMIMAGES[0].getHeight() * SKILLPROPORTION);
		beamImageView.setFitWidth(StickManGame.BEAMIMAGES[0].getWidth() * SKILLPROPORTION);
		beamImageView.setLayoutX(-100);
		beamImageView.setLayoutY(-100);
		beamImageView.setScaleX(-1);
		// end

		// setup for BeamTimeline
		BeamTimeline.setCycleCount(StickManGame.BEAMIMAGES.length);
		// end
	}

	public double getX() {
		return posX;
	}

	public double getY() {
		return posY;
	}

	public void handleBeam() {
		if (beamIndex == 0) {
			beamImageView.setImage(StickManGame.BEAMIMAGES[0]);
			beamImageView.setLayoutX(posX - beamImageView.getFitWidth());
			beamImageView.setLayoutY(posY + (BOSSHEIGHT / 2) * SIZEPROPORTION - beamImageView.getFitHeight() / 2);
			StickManGame.addImageView(beamImageView);
		} else if (beamIndex == StickManGame.BEAMIMAGES.length - 1) {
			StickManGame.removeImageView(beamImageView);
		}
		beamImageView.setImage(StickManGame.BEAMIMAGES[beamIndex]);
		beamIndex = (beamIndex + 1) % StickManGame.BEAMIMAGES.length;
	}

	public void castSkill() {
		BeamTimeline.play();

		randomPause.setOnFinished(e -> {
			BeamTimeline.play();
		});

		BeamTimeline.setOnFinished(e -> {
			// reset beamImageView
			beamImageView.setLayoutX(-100);
			beamImageView.setLayoutY(-100);

			rand = random.nextInt(5) + 1;
			System.out.println(rand);
			randomPause.setDuration(Duration.seconds(rand));
			randomPause.play();
		});
	}

	public void handleBleed() {
		int effectIndex = random.nextInt(4);
		ImageView newImageView = new ImageView(StickManGame.BLEEDEFFECTIMAGES[effectIndex]);

		newImageView.setFitHeight(BOSSHEIGHT * SIZEPROPORTION);
		newImageView.setFitWidth(BOSSWIDTH * SIZEPROPORTION);
		newImageView.setLayoutX(posX);
		newImageView.setLayoutY(posY);
		newImageView.setScaleX(-1);

		FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), newImageView);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.setAutoReverse(false);
		fadeOut.setOnFinished(event -> {
			StickManGame.removeImageView(newImageView);
		});

		fadeOut.play();

		StickManGame.addImageView(newImageView);
	}

	public void bleed(double damage) {
		// 若碰撞到MainCharacter的effectImageView的話要扣血
		life = life - damage;
		handleBleed();

	}

	public void die() {
		if (dead)
			return;
		System.out.println("Boss die");
		BeamTimeline.stop();
		randomPause.stop();
		StickManGame.removeImageView(bossImageView);
		StickManGame.removeImageView(beamImageView);
	}

}
