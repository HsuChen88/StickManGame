import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Person {
	final static double PERSONHEIGHT = 320;
	final static double PERSONWIDTH = 240;

	final static double SIZEPROPORTION = 0.5; // 50 %
	final static double FEETFLOATINGHEIGHT = PERSONHEIGHT * 0.17;
	final static double FROMFEETTOTOP = (PERSONHEIGHT - FEETFLOATINGHEIGHT) * SIZEPROPORTION;

	public double posX;
	public double posY;
	public double feetY;
	Line feetLine;
	Rectangle rectangle;

	Image clearImage = new Image("Images/punch/effect_0000.png");
	public ImageView characterImageView;
	public ImageView effectImageView;
	public ImageView dustImageView;
	public Group imageViewGroup = new Group();
	public int state = 0; // 0: stand, 1: walk, 2: jump, 3: attack, 4: be hit (bleed)
	public double life = 100;
	public boolean dead = false;

	boolean facingRight = true;
	int standIndex = 0;

	static final Image[] STANDIMAGES = { new Image("Images/stand/stand_0000.png"),
			new Image("Images/stand/stand_0001.png") };

	Timeline standTimeline = new Timeline(new KeyFrame(Duration.millis(500), event -> handleStand()));

	public Person(double x, double y, double life) {
		this.posX = x;
		this.posY = y;
		this.life = life;
	}

	public void setup() {

		feetY = posY + FROMFEETTOTOP;
		
		characterImageView = new ImageView("Images/stand/stand_0000.png");
		effectImageView = new ImageView(clearImage);
		dustImageView = new ImageView(clearImage);
		imageViewGroup.getChildren().addAll(characterImageView, dustImageView);

		feetLine = new Line(posX + 0.3 * PERSONWIDTH * SIZEPROPORTION, feetY, posX + 0.7 * PERSONWIDTH * SIZEPROPORTION, feetY);
		feetLine.setFill(Color.BLUE);

		// setup for 3 ImageViews (characterImageView, effectImageView, dustImageView)
		this.characterImageView.setFitHeight(PERSONHEIGHT * SIZEPROPORTION);
		this.characterImageView.setFitWidth(PERSONWIDTH * SIZEPROPORTION);
		this.characterImageView.setLayoutX(this.posX);
		this.characterImageView.setLayoutY(this.posY);

		this.effectImageView.setFitHeight(PERSONHEIGHT * SIZEPROPORTION);
		this.effectImageView.setFitWidth(PERSONWIDTH * SIZEPROPORTION);
		this.effectImageView.setLayoutX(-100.0);
		this.effectImageView.setLayoutY(-100.0);

		this.dustImageView.setFitHeight(PERSONHEIGHT * SIZEPROPORTION);
		this.dustImageView.setFitWidth(PERSONWIDTH * SIZEPROPORTION);
		this.dustImageView.setLayoutX(this.posX);
		this.dustImageView.setLayoutY(this.posY);
		// end

		// setup for standing
		this.standTimeline.setCycleCount(Timeline.INDEFINITE);
		// end

	}

	public double getX() {
		return posX;
	}

	public double getY() {
		return posY;
	}
	
	
	private void handleStand() {
		// switch stand images
		this.standIndex = (standIndex + 1) % STANDIMAGES.length;
		this.characterImageView.setImage(STANDIMAGES[standIndex]);
	}

	public void stand() {
		// Start the stand animation
		this.standTimeline.play();
	}

	public void updateCoordinate() {
//		posX = characterImageView.getLayoutX() + characterImageView.getTranslateX();
//		posY = characterImageView.getLayoutY() + characterImageView.getTranslateY();
//		feetY = posY + FROMFEETTOTOP;

		if (state == 0) {	// jump (有TranslateTransition)
//			System.out.println(state);
			posX = characterImageView.getLayoutX() + characterImageView.getTranslateX();
			posY = characterImageView.getLayoutY();
			feetY = posY + FROMFEETTOTOP;
		}
//		else if (state == 2) {
//			posX = characterImageView.getLayoutX() + characterImageView.getTranslateX();
//			posY = characterImageView.getLayoutY();
//			feetY = posY + FROMFEETTOTOP;
//		}
		else {
//			System.out.println("else");
			posX = characterImageView.getLayoutX() + characterImageView.getTranslateX();
			posY = characterImageView.getLayoutY() + characterImageView.getTranslateY();
			feetY = posY + FROMFEETTOTOP;
		}
	}

	public void setCleanEffectAndDust() {
		this.effectImageView.setImage(clearImage);
		this.dustImageView.setImage(clearImage);
	}
	
	public void updateImageView(double newX, double newY) {
		characterImageView.setLayoutX(newX);
		characterImageView.setLayoutY(newY);
		dustImageView.setLayoutX(newX);
		dustImageView.setLayoutY(newY);
//		for (Node node: imageViewGroup.getChildren()) {	// 不知道為什麼不能用
//			node.setLayoutX(newY);
//			node.setLayoutY(newY);
//		}
	}
		
//	public void setFeetLine() {
//		feetLine.setStartX(posX + 0.3 * PERSONWIDTH * SIZEPROPORTION);
//		feetLine.setEndX(posX + 0.7 * PERSONWIDTH * SIZEPROPORTION);
//		feetLine.setStartY(feetY);
//		feetLine.setEndY(feetY);
//	}
	
	public void die() {
		if(dead) return ;
		standTimeline.stop();
		StickManGame.removeImageView(characterImageView);
		StickManGame.removeImageView(dustImageView);
		StickManGame.removeImageView(effectImageView);
	}

}
