import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MainCharacter extends Person {

	private final double JUMP_DURATION = 1000; // One jump in milliseconds
	private final double JUMP_HEIGHT = 150; // Height of the jump in pixels
	private final double MOVE_DELTA = 5.0;
	private final double STEP_DURATION = 16; // One step in milliseconds

	private Timeline jumpTimeline = new Timeline(
			new KeyFrame(Duration.millis(JUMP_DURATION / StickManGame.JUMPIMAGES.length), event -> handleJump()));
	private SequentialTransition jumpSequentialTransition = new SequentialTransition();
	private ParallelTransition extendParallelTransition = new ParallelTransition();
	private ParallelTransition jumpParallelTransition = new ParallelTransition();
	private Timeline moveTimeline = new Timeline(
			new KeyFrame(Duration.millis(STEP_DURATION), event -> updatePosition()));
	private Timeline walkTimeline = new Timeline(
			new KeyFrame(Duration.millis(STEP_DURATION), event -> handleWalk()));
	private Timeline attackTimeline = new Timeline(new KeyFrame(Duration.millis(60), event -> handleAttack()));

	private String[] ManSoundsPath = { "Sounds/huhPunchSound.mp3", "Sounds/ohPunchSound.mp3"  };

	private Random random = new Random();

	private int jumpIndex = 0;
	private int walkIndex = 0;
	private int attackIndex = 0;

	public MainCharacter(double x, double y, double life) {
		super(x, y, life);
	}

	public void setup() {

		super.setup();

		// setup for moving
		moveTimeline.setCycleCount(Timeline.INDEFINITE);
		walkTimeline.setCycleCount(Timeline.INDEFINITE);

		// end

		// setup for jumping
		jumpTimeline.setCycleCount(StickManGame.JUMPIMAGES.length);

		TranslateTransition jumpTranslateTransition = new TranslateTransition(
				Duration.millis((JUMP_DURATION * 0.8) / 2), characterImageView);
		jumpTranslateTransition.setByY(-JUMP_HEIGHT);
		jumpTranslateTransition.setAutoReverse(true);
		jumpTranslateTransition.setCycleCount(2);

		ScaleTransition takeoffScaleTransition = new ScaleTransition(Duration.millis(JUMP_DURATION * 0.05),
				characterImageView);
		takeoffScaleTransition.setToY(0.8);
		takeoffScaleTransition.setAutoReverse(true);
		takeoffScaleTransition.setCycleCount(2);

		ScaleTransition extendScaleTransition = new ScaleTransition(Duration.millis(JUMP_DURATION * 0.4),
				characterImageView);
		extendScaleTransition.setToX(0.8);
		extendScaleTransition.setAutoReverse(true);
		extendScaleTransition.setCycleCount(2);

		ScaleTransition landScaleTransition = new ScaleTransition(Duration.millis(JUMP_DURATION * 0.05),
				characterImageView);
		landScaleTransition.setToY(0.8);
		landScaleTransition.setAutoReverse(true);
		landScaleTransition.setCycleCount(2);

		PauseTransition setStateDelay = new PauseTransition(Duration.millis(JUMP_DURATION * 0.2));
		setStateDelay.setOnFinished(event -> {
			state = 2; // state -> 2: jump
		});

		extendParallelTransition.getChildren().addAll(jumpTranslateTransition, extendScaleTransition);
		extendParallelTransition.setCycleCount(1);

		jumpSequentialTransition.getChildren().addAll(takeoffScaleTransition, extendParallelTransition,
				landScaleTransition);
		jumpSequentialTransition.setCycleCount(1);

		jumpParallelTransition.getChildren().addAll(jumpTimeline, jumpSequentialTransition, setStateDelay);
		jumpParallelTransition.setCycleCount(1);
		// end

		// setup for attacking
		attackTimeline.setCycleCount(StickManGame.ATTACKIMAGES.length);
		// end

		// setup for attack effect
//		effectTimeline.setCycleCount(StickManGame.ATTACKEFFECTIMAGES.length);
		// end

	}

	private void handleJump() {
//		super.switchFacing();	
//		System.out.println(characterImageView.getTranslateY());
//		System.out.println(characterImageView.getLayoutY());
		characterImageView.setImage(StickManGame.JUMPIMAGES[jumpIndex]);
		jumpIndex = (jumpIndex + 1) % StickManGame.JUMPIMAGES.length;

//		updateImageView();
	}

	public void jump() {
		if (state == 0 || state == 1) { // 只能在stand和walk的狀態下jump
			standTimeline.stop();
			walkTimeline.stop();
			jumpIndex = 0;

			// state -> 2: jump 狀態延遲一下下才設定 避免起跳的判斷

			// 以下暫時使用
			if (!(facingRight))
				characterImageView.setScaleX(1);
			// 以上暫時使用

			jumpParallelTransition.play();

			jumpParallelTransition.setOnFinished(e -> {
//				System.out.println("jump end");
				standTimeline.play();
				state = 0; // state -> 0: stand
				// 以下暫時使用
				if (!(facingRight))
					characterImageView.setScaleX(-1);
				// 以上暫時使用
			});
		}
	}

	private void handleAttack() {
		
		characterImageView.setImage(StickManGame.ATTACKIMAGES[attackIndex]);

		if (attackIndex == 0) {
			effectImageView.setImage(StickManGame.ATTACKEFFECTIMAGES[0]);
			effectImageView.setLayoutX(posX);
			effectImageView.setLayoutY(posY);
			StickManGame.addImageView(effectImageView);
		} else if (attackIndex == StickManGame.ATTACKEFFECTIMAGES.length - 1) {
			StickManGame.removeImageView(effectImageView);
		}
		effectImageView.setImage(StickManGame.ATTACKEFFECTIMAGES[attackIndex]);
		if (facingRight)
			effectImageView.setScaleX(1);
		else
			effectImageView.setScaleX(-1);

		dustImageView.setImage(StickManGame.ATTACKDUSTIMAGES[attackIndex]);
		attackIndex = (attackIndex + 1) % StickManGame.ATTACKIMAGES.length;
		
		// 播放攻擊音效
		if (random.nextInt(10) % 10 == 3) {	// 十分之一的機率會大吼
			Media attackMedia = new Media(getClass().getResource(ManSoundsPath[random.nextInt(2)]).toExternalForm());
			MediaPlayer attackMediaPlayer = new MediaPlayer(attackMedia);
			attackMediaPlayer.play();
		}
	}

	public void attack() {
		if (!(state == 0)) { // 只能在stand的狀態下attack
			return;
		}
		state = 3; // state -> 3: attack
		standTimeline.stop();
		walkTimeline.stop();
		attackIndex = 0;

		attackTimeline.play();

		attackTimeline.setOnFinished(e -> {
			standTimeline.play();
			effectImageView.setLayoutX(-100.0);
			effectImageView.setLayoutY(-100.0);
			setCleanEffectAndDust();
			state = 0; // state -> 0: stand
		});
	}

	public void switchFacing() {

		if (facingRight) { // facing Right
			characterImageView.setScaleX(1);
			dustImageView.setScaleX(1);
		} else { // facing Left
			characterImageView.setScaleX(-1);
			dustImageView.setScaleX(-1);
		}
	}

	private void updatePosition() {
		double newX;
		if (facingRight) { // facing Right
			newX = characterImageView.getTranslateX() + MOVE_DELTA;
			newX = effectImageView.getTranslateX() + MOVE_DELTA;
			newX = dustImageView.getTranslateX() + MOVE_DELTA;
		} else { // facing Left
			newX = characterImageView.getTranslateX() - MOVE_DELTA;
			newX = effectImageView.getTranslateX() - MOVE_DELTA;
			newX = dustImageView.getTranslateX() - MOVE_DELTA;
		}

		// 不能走出視窗
		posX = characterImageView.getLayoutX() + newX;
		if (0 <= posX && (posX + characterImageView.getFitWidth()) <= StickManGame.WINDOWWIDTH) {
			characterImageView.setTranslateX(newX);
			dustImageView.setTranslateX(newX);
		}
		if (posX < 0)
			posX = 0;
		if (posX > StickManGame.WINDOWWIDTH)
			posX = StickManGame.WINDOWWIDTH - characterImageView.getFitWidth();
	}

	public void moveRight() {

		if (state == 3)
			return; // attack時不能移動

		standTimeline.stop();

		facingRight = true;
		switchFacing();

		// check! 有些狀態下不能放動畫
		moveTimeline.play();
		if (state == 0 || state == 1)
			walkTimeline.play();

		if (state == 2)
			return; // jump時不能中斷成walk狀態
		
		state = 1; // state -> 1: walk
	}

	public void moveLeft() {

		if (state == 3)
			return; // attack時不能移動

		standTimeline.stop();

		facingRight = false;

		switchFacing();

		moveTimeline.play();

		if (state == 0 || state == 1) {
			walkTimeline.play();
		}

		if (state == 2)
			return; // jump時不能中斷成walk狀態
		state = 1; // state -> 1: walk

	}

	private void handleWalk() {
		if (state == 2)	// jump狀態下只需移動不用播放動畫
			return;
		characterImageView.setImage(StickManGame.WALKIMAGES[walkIndex]);
		walkIndex = (walkIndex + 1) % StickManGame.WALKIMAGES.length;
	}

	public void stopMoving() {
		moveTimeline.stop();
		walkTimeline.stop();
		state = 0;
	}

	public void bleed(double damage) {
		life -= damage;
	}
	
	public void die() {
		if(dead) return ;
		super.die();
		attackTimeline.stop();
		walkTimeline.stop();
		moveTimeline.stop();
		jumpParallelTransition.stop();
	}
	
}
