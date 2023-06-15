import java.util.Random;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Enemy extends Person {

	public double distanceOfPlayerAndEnemy;
	public double life = 30;

	private Random random = new Random();
	private int rand;
	private int walkIndex = 0;
	private Timeline patrolTimeline = new Timeline(new KeyFrame(Duration.millis(60), event -> handlePatrol(rand)));
	private PauseTransition randomPause = new PauseTransition();

	public Enemy(double x, double y, double life) {
		super(x, y, life);
	}

	public void setup() {

		super.setup();

		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setBrightness(0.25);
		characterImageView.setEffect(colorAdjust);
		dustImageView.setEffect(colorAdjust);

		// setup for patrol
		patrolTimeline.setCycleCount(rand);
		patrol(); // start playing patrol
		// end

	}

	public void switchFacing() {

		if (this.distanceOfPlayerAndEnemy < 0) { // 玩家在左邊
			facingRight = false;
			for (Node node : imageViewGroup.getChildren()) {
				node.setScaleX(-1);
			}
		} else { // 玩家在右邊
			facingRight = true;
			for (Node node : imageViewGroup.getChildren()) {
				node.setScaleX(1);
			}
		}
	}

	public void handleBleed() {
		characterImageView.setImage(StickManGame.BEINGHITIMAGES[0]);
		int effectIndex = random.nextInt(4);
		ImageView newImageView = new ImageView(StickManGame.BLEEDEFFECTIMAGES[effectIndex]);

		newImageView.setFitHeight(PERSONHEIGHT * SIZEPROPORTION);
		newImageView.setFitWidth(PERSONWIDTH * SIZEPROPORTION);
		newImageView.setLayoutX(posX);
		newImageView.setLayoutY(posY);

		// 判斷玩家在左邊還是右邊
		if (this.distanceOfPlayerAndEnemy < 0) { // 玩家在左邊
			newImageView.setScaleX(-1);
		} else { // 玩家在右邊
			newImageView.setScaleX(1);
		}

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

		// 判斷玩家在左邊還是右邊
		switchFacing();

		// 若碰撞到MainCharacter的effectImageView的話要扣血
		state = 4;
		life = life - damage;
		patrolTimeline.stop();

		handleBleed();
		patrolTimeline.play();
	}

	public void handlePatrol(int rand) {
		for (int i = 0; i < 3; i++) {
			handlePatrolMoving(rand);
		}
		characterImageView.setImage(StickManGame.WALKIMAGES[walkIndex]);
		walkIndex = (walkIndex + 1) % StickManGame.WALKIMAGES.length;
	}

	public void handlePatrolMoving(int rand) {
		if (posX < 0 || posX + PERSONWIDTH > StickManGame.WINDOWWIDTH)
			return;
		if (rand % 2 == 1) { // 奇數就向右走
			facingRight = true;
			posX = posX + 1;

			updateImageView(posX, posY);

			characterImageView.setScaleX(1);
			dustImageView.setScaleX(1);
		} else { // 偶數就向左走
			facingRight = false;
			posX = posX - 1;
			updateImageView(posX, posY);

			characterImageView.setScaleX(-1);
			dustImageView.setScaleX(-1);
		}

	}

	public void patrol() {
		rand = random.nextInt(50) + 1;
		patrolTimeline.setCycleCount(rand);
		patrolTimeline.play();

		patrolTimeline.setOnFinished(e -> {
			rand = random.nextInt(5) + 1;
			randomPause.setDuration(Duration.seconds(rand));
			randomPause.play();
			standTimeline.play();
		});

		randomPause.setOnFinished(e -> {
			standTimeline.stop();
			rand = random.nextInt(50) + 1;
			patrolTimeline.play();
		});
	}

	public void die() {
		if (dead) return ;
		patrolTimeline.stop();
		super.die();
	}

}
