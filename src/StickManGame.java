import java.util.Random;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StickManGame extends Application {

	// variables
	static final int WINDOWWIDTH = 900;
	static final int WINDOWHEIGHT = 600;
	static final int HORIZON = 450;
	static final int PLAYER_SIZE = 60;
	static final int PLAYER_DAMAGE = 10;
	static final int ENEMY_NUMBER = 1;
	static final int PLAYER_LIFE = 100;
	static final int ENEMY_LIFE = 100;
	static final int BOSS_LIFE = 250;
	static final int BOSS_DAMAGE = 10;

	// images
	static final Image[] JUMPIMAGES = { new Image("Images/jump/jump_0000.png"), new Image("Images/jump/jump_0001.png"),
			new Image("Images/jump/jump_0002.png"), new Image("Images/jump/jump_0003.png"),
			new Image("Images/jump/jump_0004.png"), new Image("Images/jump/jump_0005.png"),
			new Image("Images/jump/jump_0006.png"), new Image("Images/jump/jump_0007.png"),
			new Image("Images/jump/jump_0008.png"), new Image("Images/jump/jump_0009.png") };

	static final Image[] WALKIMAGES = { new Image("Images/walk/walk_0000.png"), new Image("Images/walk/walk_0001.png"),
			new Image("Images/walk/walk_0002.png"), new Image("Images/walk/walk_0003.png"),
			new Image("Images/walk/walk_0004.png"), new Image("Images/walk/walk_0005.png"),
			new Image("Images/walk/walk_0006.png"), new Image("Images/walk/walk_0007.png"),
			new Image("Images/walk/walk_0008.png"), new Image("Images/walk/walk_0009.png"),
			new Image("Images/walk/walk_0010.png"), new Image("Images/walk/walk_0011.png"),
			new Image("Images/walk/walk_0012.png"), new Image("Images/walk/walk_0013.png"),
			new Image("Images/walk/walk_0014.png"), new Image("Images/walk/walk_0015.png"),
			new Image("Images/walk/walk_0016.png"), new Image("Images/walk/walk_0017.png"),
			new Image("Images/walk/walk_0018.png"), new Image("Images/walk/walk_0019.png"),
			new Image("Images/walk/walk_0020.png"), new Image("Images/walk/walk_0021.png"),
			new Image("Images/walk/walk_0022.png"), new Image("Images/walk/walk_0023.png"),
			new Image("Images/walk/walk_0024.png") };

	static final Image[] ATTACKIMAGES = { new Image("Images/punch/punch_0000.png"),
			new Image("Images/punch/punch_0001.png"), new Image("Images/punch/punch_0002.png"),
			new Image("Images/punch/punch_0003.png"), new Image("Images/punch/punch_0004.png"),
			new Image("Images/punch/punch_0005.png"), new Image("Images/punch/punch_0006.png") };

	static final Image[] ATTACKEFFECTIMAGES = { new Image("Images/punch/effect_0000.png"),
			new Image("Images/punch/effect_0001.png"), new Image("Images/punch/effect_0002.png"),
			new Image("Images/punch/effect_0003.png"), new Image("Images/punch/effect_0004.png"),
			new Image("Images/punch/effect_0005.png"), new Image("Images/punch/effect_0006.png") };

	static final Image[] ATTACKDUSTIMAGES = { new Image("Images/punch/dust_0000.png"),
			new Image("Images/punch/dust_0001.png"), new Image("Images/punch/dust_0002.png"),
			new Image("Images/punch/dust_0003.png"), new Image("Images/punch/dust_0004.png"),
			new Image("Images/punch/dust_0005.png"), new Image("Images/punch/dust_0006.png") };

	static final Image[] BEINGHITIMAGES = { new Image("Images/bleed/bleed_0000.png") };

	static final Image[] BLEEDEFFECTIMAGES = { new Image("Images/bleed/bleedEffect_0000.png"),
			new Image("Images/bleed/bleedEffect_0001.png"), new Image("Images/bleed/bleedEffect_0002.png"),
			new Image("Images/bleed/bleedEffect_0003.png") };

	static final Image[] BEAMIMAGES = { new Image("Images/Boss/Pikachu/Beam/Beam_0000.png"),
			new Image("Images/Boss/Pikachu/Beam/Beam_0001.png"), new Image("Images/Boss/Pikachu/Beam/Beam_0002.png"),
			new Image("Images/Boss/Pikachu/Beam/Beam_0003.png"), new Image("Images/Boss/Pikachu/Beam/Beam_0004.png"),
			new Image("Images/Boss/Pikachu/Beam/Beam_0005.png"), new Image("Images/Boss/Pikachu/Beam/Beam_0006.png"),
			new Image("Images/Boss/Pikachu/Beam/Beam_0007.png"), new Image("Images/Boss/Pikachu/Beam/Beam_0008.png"),
			new Image("Images/Boss/Pikachu/Beam/Beam_0009.png"), new Image("Images/Boss/Pikachu/Beam/Beam_0010.png"),
			new Image("Images/Boss/Pikachu/Beam/Beam_0011.png"), new Image("Images/Boss/Pikachu/Beam/Beam_0012.png"),
			new Image("Images/Boss/Pikachu/Beam/Beam_0013.png"), new Image("Images/Boss/Pikachu/Beam/Beam_0014.png"),
			new Image("Images/Boss/Pikachu/Beam/Beam_0015.png"), new Image("Images/Boss/Pikachu/Beam/Beam_0016.png"),
			new Image("Images/Boss/Pikachu/Beam/Beam_0017.png"), new Image("Images/Boss/Pikachu/Beam/Beam_0018.png"),
			new Image("Images/Boss/Pikachu/Beam/Beam_0019.png"), new Image("Images/Boss/Pikachu/Beam/Beam_0020.png"),
			new Image("Images/Boss/Pikachu/Beam/Beam_0020.png"), new Image("Images/Boss/Pikachu/Beam/Beam_0021.png"),
			new Image("Images/Boss/Pikachu/Beam/Beam_0022.png"), new Image("Images/Boss/Pikachu/Beam/Beam_0023.png"),
			new Image("Images/Boss/Pikachu/Beam/Beam_0024.png") };

	String[] AttackSoundsPath = { "Sounds/attack.mp3", "Sounds/punchSound.mp3" };

	public GraphicsContext gc;
	public Map map;
	static public Pane root;
	static public Stage stage;

	private MainCharacter player;
	private Enemy[] enemies;
	private Boss pikachu;

	private Timeline eventTimeline;
	private Timeline refreshTimeline;

	private Random random = new Random();

	private int playerBuff = 0;
	private int bossBuff = 0;
	private int addEnemy = 0;
	private int rotationStep = 0;

	private boolean gaming = false;
	private boolean loseFlag = false;

	@Override
	public void start(Stage primaryStage) {

		stage = primaryStage;
		int character_X = 100;
		int enemy_X = 500;
		double VS_X = 300;
		double VS_Y = 150;
		Image character1 = new Image("Images/stand/stand_0000.png");
		Image character2 = new Image("Images/stand/stand_0001.png");
		ImageView character = new ImageView(new Image("Images/stand/stand_0000.png"));
		ImageView enemy = new ImageView(new Image("Images/pikachu1.png"));
		ImageView VS = new ImageView(new Image("Images/VS.png"));
		ImageView background = new ImageView(new Image("Images/background.jpg"));
		VS.setFitHeight(WINDOWHEIGHT / 3);
		VS.setFitWidth(WINDOWWIDTH / 3);
		VS.setLayoutX(VS_X);
		VS.setLayoutY(VS_Y);
		character.setLayoutX(character_X);
		character.setLayoutY(VS_Y);
		character.setFitHeight(WINDOWHEIGHT / 3);
		character.setFitWidth(WINDOWWIDTH / 3);

		enemy.setFitHeight(WINDOWHEIGHT / 3);
		enemy.setFitWidth(WINDOWWIDTH / 3);
		enemy.setLayoutX(enemy_X);
		enemy.setLayoutY(VS_Y);
		background.setFitHeight(WINDOWHEIGHT);
		background.setFitWidth(WINDOWWIDTH);

		Timeline bulletTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {// 斗的速率
			if (VS.getLayoutX() == VS_X && VS.getLayoutY() == VS_Y) {
				VS.setLayoutX(VS.getLayoutX() + 5);
				rotationStep = 1;
				enemy.setLayoutX(enemy.getLayoutX() + 5);
			} else if (rotationStep == 1) {
				VS.setLayoutY(VS.getLayoutY() - 5);
				rotationStep = 2;
				enemy.setLayoutY(enemy.getLayoutY() - 5);
			} else if (rotationStep == 2) {
				VS.setLayoutX(VS.getLayoutX() - 5);
				rotationStep = 3;
				enemy.setLayoutX(enemy.getLayoutX() - 5);
			} else {
				VS.setLayoutY(VS.getLayoutY() + 5);
				rotationStep = 0;
				enemy.setLayoutY(enemy.getLayoutY() + 5);
			}

			if (rotationStep % 2 == 0) {
				System.out.println(rotationStep % 2 == 0);
				character.setImage(character2);
			} else {
				character.setImage(character1);
				System.out.println(rotationStep % 2 == 0);
			}
		}));

		Button startButton = new Button("Start");
		startButton.setStyle("-fx-font: 30 arial; -fx-base: #ffff00;");
		startButton.setLayoutX(VS_X + 75);
		startButton.setLayoutY(VS_Y + 300);
		startButton.setPrefSize(150, 50);
		startButton.setOnAction(gameStart -> {
			System.out.println("game start");
			bulletTimeline.stop();
			gaming = true;
			root = new Pane();
			Scene gameScene = new Scene(root, WINDOWWIDTH, WINDOWHEIGHT);

			stage.setScene(gameScene);

			setup(root);

			gameScene.setOnKeyPressed(event -> {
				KeyCode keyCode = event.getCode();
				if (keyCode == KeyCode.UP) {
					player.jump();
				} else if (keyCode == KeyCode.DOWN) {

				} else if (keyCode == KeyCode.LEFT) {
					player.moveLeft();
				} else if (keyCode == KeyCode.RIGHT) {
					player.moveRight();
				} else if (keyCode == KeyCode.CONTROL) {
					player.attack();
				}
			});

			// Handle key release events
			gameScene.setOnKeyReleased(event -> {
				KeyCode keyCode = event.getCode();
				if (keyCode == KeyCode.LEFT || keyCode == KeyCode.RIGHT) {
					player.stopMoving();
					player.stand();
				}
			});

		});
		Button buffButton = new Button("課金");
		buffButton.setStyle("-fx-font: 20 arial; -fx-base: #ffff00;");
		buffButton.setLayoutX(0);
		buffButton.setLayoutY(0);
		buffButton.setPrefSize(150, 50);
		buffButton.setOnAction(gameStart -> {
			System.out.println("player life +100");
			playerBuff += 100;
		});
		Button harderButton = new Button("困難一點好嗎");
		harderButton.setStyle("-fx-font: 20 arial; -fx-base: #ffff00;");
		harderButton.setLayoutX(0);
		harderButton.setLayoutY(0);
		harderButton.setPrefSize(150, 50);
		harderButton.setOnAction(gameStart -> {
			System.out.println("Enemy +5");
			System.out.println("boss life +100");
			addEnemy += 5;
			bossBuff += 100;
		});
		Button buffResetButton = new Button("重設難度");
		buffResetButton.setStyle("-fx-font: 20 arial; -fx-base: #ffff00;");
		buffResetButton.setLayoutX(0);
		buffResetButton.setLayoutY(50);
		buffResetButton.setPrefSize(150, 50);
		buffResetButton.setOnAction(gameStart -> {
			System.out.println("reset buff");
			playerBuff = 0;
			addEnemy = 0;
			bossBuff = 0;
		});

		if (!(gaming)) {
			root = new Pane();
			if (loseFlag)
				root.getChildren().addAll(background, character, startButton, buffButton, buffResetButton, VS, enemy);
			else
				root.getChildren().addAll(background, character, startButton, harderButton, buffResetButton, VS, enemy);
			Scene menuScene = new Scene(root, WINDOWWIDTH, WINDOWHEIGHT);
			stage.setScene(menuScene);
			bulletTimeline.setCycleCount(Timeline.INDEFINITE);
			bulletTimeline.play();
		}

		stage.setTitle("火柴人大戰皮卡丘");
		stage.setResizable(false);
		stage.show();

	}

	private void setup(Pane root) {
		// 背景
		root.setPrefSize(WINDOWWIDTH, WINDOWHEIGHT);
		BackgroundImage backgroundImage = new BackgroundImage(new javafx.scene.image.Image("Images/PKbackground.jpg"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, false));
		Background background = new Background(backgroundImage);
		root.setBackground(background);
		Rectangle rectangle = new Rectangle(WINDOWWIDTH, WINDOWHEIGHT);
		rectangle.setFill(Color.WHITE);
		rectangle.setOpacity(0.2);
		root.getChildren().add(rectangle);

		// 地圖 (y值從小到大)
//		Land[] lands = { new Land(130, 360, 200), new Land(570, 900, 200), new Land(380, 550, 325),
//				new Land(0, 900, HORIZON) };
		Land[] lands = { new Land(0, 900, HORIZON) };

		Map map = new Map(lands);
		drawMap(map);

		// 地平線扣掉人的高度 (腳剛好站在地上)
		double personOnFloorHeight = HORIZON
				- (Person.PERSONHEIGHT - Person.FEETFLOATINGHEIGHT) * Person.SIZEPROPORTION;

		// Enemies
		enemies = new Enemy[ENEMY_NUMBER + addEnemy];
		for (int i = 0; i < enemies.length; i++) {
			double rand = random.nextInt(850) + 1;
			enemies[i] = new Enemy(rand, personOnFloorHeight, ENEMY_LIFE);
			enemies[i].setup();
			enemies[i].patrol();
			root.getChildren().addAll(enemies[i].characterImageView, enemies[i].dustImageView);
		}

		// Boss
		double bossOnFloorHeight = HORIZON - Boss.BOSSHEIGHT * Boss.SIZEPROPORTION;
		double bossActualLife = BOSS_LIFE + bossBuff;
		pikachu = new Boss(WINDOWWIDTH * 0.9, bossOnFloorHeight, new Image("Images/Boss/Pikachu/pikachu.gif"),
				bossActualLife);
		pikachu.setup();
		Text bossLifeText = new Text("BOSS LIFE");
		bossLifeText.setFont(Font.font("Wingdings" + "", FontWeight.BOLD, 20));
		bossLifeText.setFill(Color.WHITE);
		bossLifeText.setX(WINDOWWIDTH - 100);
		bossLifeText.setY(30);
		ProgressBar bossLifeBar = new ProgressBar();
		bossLifeBar.setPrefWidth(180);
		bossLifeBar.setPrefHeight(20);
		bossLifeBar.setLayoutX(WINDOWWIDTH - 180);
		bossLifeBar.setLayoutY(40);
		root.getChildren().addAll(pikachu.bossImageView, bossLifeText, bossLifeBar);

		// Player (最後加入畫面才會在最上層)
		player = new MainCharacter(100, personOnFloorHeight, PLAYER_LIFE + playerBuff);
		player.setup();
		player.stand();
		Text playerLifeText = new Text("PLAYER LIFE");
		playerLifeText.setFont(Font.font("Wingdings" + "", FontWeight.BOLD, 20));
		playerLifeText.setFill(Color.WHITE);
		playerLifeText.setX(0);
		playerLifeText.setY(30);
		ProgressBar playerLifeBar = new ProgressBar();
		playerLifeBar.setPrefWidth(180);
		playerLifeBar.setPrefHeight(20);
		playerLifeBar.setLayoutX(0);
		playerLifeBar.setLayoutY(40);
		root.getChildren().addAll(player.imageViewGroup, playerLifeText, playerLifeBar);

		// 音效
		Media winMedia = new Media(getClass().getResource("Sounds/win.mp3").toExternalForm());
		MediaPlayer winMediaPlayer = new MediaPlayer(winMedia);
		Media loseMedia = new Media(getClass().getResource("Sounds/died.mp3").toExternalForm());
		MediaPlayer loseMediaPlayer = new MediaPlayer(loseMedia);
		Media hitMedia = new Media(getClass().getResource("Sounds/bleedSound.mp3").toExternalForm());
		MediaPlayer hitMediaPlayer = new MediaPlayer(hitMedia);

		// Animation
		refreshTimeline = new Timeline(new KeyFrame(Duration.millis(60), event -> {
			// player
			if (!(player.dead)) {
				player.updateCoordinate();
			}
			if (player.life <= 0) {
				player.dead = true;
				// 播放輸了音效
				loseMediaPlayer.play();

				// 顯示GameOver
				System.out.println("GAME OVER");
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("戰鬥結果");
				alert.setHeaderText("你死了");
				alert.setContentText("沒看過會破壞死光的皮卡丘吧!");
				alert.setOnCloseRequest(restart -> {
					System.out.println("restart");
					// reset game
					loseFlag = true;
					pikachu.die();
					for (Enemy enemy : enemies) {
						enemy.die();
					}

					loseFlag = true;
					start(stage);
				});
				alert.show();
				gaming = false;
				gameStop();
			}

			for (int i = 0; i < enemies.length; i++) {
				if (enemies[i].life <= 0) {
					enemies[i].die();
					enemies[i].dead = true;
				}
			}
			// boss
			if (0 < pikachu.life && pikachu.life < bossActualLife) {
				pikachu.castSkill();
			}
			if (pikachu.life <= 0) {
				pikachu.die();
				pikachu.dead = true;
			}

			// check life
			double playerLifeRatio = player.life / (PLAYER_LIFE + playerBuff);
			double bossLifeRatio = pikachu.life / bossActualLife;
			playerLifeBar.setProgress(playerLifeRatio);
			if (playerLifeRatio <= 0.3) {
				playerLifeBar.setStyle("-fx-accent: red;");
			} else if (0.3 <= playerLifeRatio && playerLifeRatio <= 0.7) {
				playerLifeBar.setStyle("-fx-accent: orange;");
			} else {
				playerLifeBar.setStyle("-fx-accent: DeepSkyBlue;");
			}
			bossLifeBar.setProgress(bossLifeRatio);
			if (bossLifeRatio <= 0.3) {
				bossLifeBar.setStyle("-fx-accent: red;");
			} else if (0.3 <= bossLifeRatio && bossLifeRatio <= 0.7) {
				bossLifeBar.setStyle("-fx-accent: orange;");
			} else {
				bossLifeBar.setStyle("-fx-accent: DeepSkyBlue;");
			}

			// check Win
			if (checkWin()) {
				// 播放贏了音效
				winMediaPlayer.play();

				// 顯示你贏了回到主畫面
				System.out.println("YOU WIN");
				if (gaming) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("戰鬥結果");
					alert.setHeaderText("你贏了");
					alert.setContentText("嗚嗚~課金的人才是老大..");
					alert.setOnCloseRequest(restart -> {
						System.out.println("restart");
						// reset game
						player.die();

						loseFlag = false;

						start(stage);
					});
					alert.show();
					gaming = false;
					gameStop();
				}
			}

		}));
		refreshTimeline.setCycleCount(Timeline.INDEFINITE);
		refreshTimeline.play();

		eventTimeline = new Timeline(new KeyFrame(Duration.millis(200), event -> {

			// 技能碰撞
			for (Enemy enemy : enemies) {
				if (!(enemy.dead)) {
					if (player.effectImageView.getBoundsInParent()
							.intersects(enemy.characterImageView.getBoundsInParent())) {
						Text text = new Text(String.valueOf(random.nextInt(299999) + 700000));
						text.setFont(Font.font("Papyrus" + "", FontWeight.BOLD, 36));
						text.setFill(Color.AZURE);
						text.setX(enemy.getX() - 40);
						text.setY(enemy.getY() - random.nextDouble(50));
						FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), text);
						fadeTransition.setFromValue(1.0);
						fadeTransition.setToValue(0.0);
						fadeTransition.play();
						fadeTransition.setOnFinished(e -> {
							root.getChildren().remove(text);
						});
						root.getChildren().add(text);
						enemy.bleed(PLAYER_DAMAGE);
						// 播放打到的音效
						Media attackMedia = new Media(
								getClass().getResource(AttackSoundsPath[random.nextInt(2)]).toExternalForm());
						MediaPlayer attackMediaPlayer = new MediaPlayer(attackMedia);
						attackMediaPlayer.play();
						// 播放流血音效
						hitMediaPlayer.play();
					}
				}
			}
			if (!(pikachu.dead)) {
				// player 打 Boss
				if (player.effectImageView.getBoundsInParent().intersects(pikachu.bossImageView.getBoundsInParent())) {
					Text text = new Text(String.valueOf(random.nextInt(299999) + 700000));
					text.setFont(Font.font("Papyrus" + "", FontWeight.BOLD, 36));
					text.setFill(Color.AZURE);
					text.setX(pikachu.getX() - 40);
					text.setY(pikachu.getY() - random.nextDouble(50));
					FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), text);
					fadeTransition.setFromValue(1.0);
					fadeTransition.setToValue(0.0);
					fadeTransition.play();
					fadeTransition.setOnFinished(e -> {
						root.getChildren().remove(text);
					});
					root.getChildren().add(text);
					pikachu.bleed(PLAYER_DAMAGE);
					// 播放打到的音效
					Media attackMedia = new Media(
							getClass().getResource(AttackSoundsPath[random.nextInt(2)]).toExternalForm());
					MediaPlayer attackMediaPlayer = new MediaPlayer(attackMedia);
					attackMediaPlayer.play();
					// 播放流血音效
					hitMediaPlayer.play();

				}
				// Boss 技能打 player
				if (pikachu.beamImageView.getBoundsInParent()
						.intersects(player.characterImageView.getBoundsInParent())) {
					Text text = new Text("5000");
					text.setFont(Font.font("Papyrus" + "", FontWeight.BOLD, 36));
					text.setFill(Color.RED);
					text.setX(player.getX() - 20);
					text.setY(player.getY() - random.nextDouble(50));
					FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), text);
					fadeTransition.setFromValue(1.0);
					fadeTransition.setToValue(0.0);
					fadeTransition.play();
					fadeTransition.setOnFinished(e -> {
						root.getChildren().remove(text);
					});
					root.getChildren().add(text);
					player.bleed(BOSS_DAMAGE);
				}

			}

		}));

		eventTimeline.setCycleCount(Timeline.INDEFINITE);
		eventTimeline.play();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void addImageView(ImageView iv) {
		root.getChildren().add(iv);
	}

	public static void removeImageView(ImageView iv) {
		root.getChildren().remove(iv);
	}

	public static void drawMap(Map map) {
		for (Land land : map.lands) {
			land.line.setStrokeWidth(5);
			DropShadow dropShadow = new DropShadow();
			land.line.setEffect(dropShadow);
			root.getChildren().add(land.line);
		}
	}

	public boolean checkWin() {
		boolean flag = true;
		for (Enemy enemy : enemies) {
			if (!(enemy.dead))
				flag = false;
		}
		if (!(pikachu.dead))
			flag = false;
		return flag;

	}

	public void gameStop() {
		refreshTimeline.stop();
		eventTimeline.stop();
	}

}
