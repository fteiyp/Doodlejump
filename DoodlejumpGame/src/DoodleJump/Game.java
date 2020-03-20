package DoodleJump;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * The game class contains the logic of the game. The timehandler that is the
 * basis of the doodle and platforms movement is contained here, and the
 * keyhandler that shifts the doodle back and forth horizontally based on key
 * presses. This class manages the platforms generated in the game with an
 * arraylist. The timehandler calls a series of methods each .016 seconds. Each
 * method checks for certain conditions and updates the game based on them.
 */
public class Game {

	private Doodle _doodle;
	private Rectangle _rectangle;
	private Pane _gamePane;
	private ArrayList<Platform> _platforms;
	private double _doodleYPosition;
	private double _doodleVelocity;
	private Timeline _timeline;
	private PaneOrganizer _paneOrganizer;
	private KeyHandler _keyHandler;
	private int _score;
	private Label _scoreLabel;

	/**
	 * The game constructor takes in the paneorganizer as an argument so that it
	 * can store it in order to call the gameOver method on it later that
	 * displays "game over" in the bottom pane. The addDoodle method
	 * instantiates the doodlePane, then the Doodle; the doodle adds itself to
	 * the doodle pane. The platforms array list is instantiated and the first
	 * platforms is added to it and displayed. The timeline is then created
	 * which starts the game animation.
	 */
	public Game(PaneOrganizer paneOrganizer) {
		this.addDoodle();
		_score = 0;
		_scoreLabel = new Label("0");
		_paneOrganizer = paneOrganizer;
		_rectangle = _doodle.getRectangle();
		_platforms = new ArrayList<Platform>();
		_platforms.add(new Platform(_gamePane));
		_platforms.get(_platforms.size() - 1).setX(170);
		_platforms.get(_platforms.size() - 1).setY(600);
		this.setupTimeline();

	}

	/**
	 * This accessor method returns the gamePane so that the platforms and
	 * doodle can add themselves to it (via association).
	 */
	public Pane getGamePane() {
		return _gamePane;
	}

	/**
	 * This accessor method returns the velocity number that is currently stored
	 * in the doodle class. This is important as this method is needed for each
	 * keyframe to use the current velocity to calculate the new velocity of the
	 * doodle as acceleration effects it.
	 */
	public Double getDoodleVelocity() {
		return _doodleVelocity;
	}
	
	/**
	 * This accessor method returns the score label to the paneorganizer so that
	 * the score can be displayed at the bottom of the screen in the game.
	 */
	public Label getScoreLabel() {
		return _scoreLabel;
	}

	/**
	 * This method is called once in the constructor and adds the doodle to the
	 * game. The doodle is passed the gamePane so that it can add the rectangle
	 * that graphically makes it up to be visually displayed. This method also
	 * instantiates the keyhandler, for which the handle method is called by
	 * keypresses that move the doodle horizontally.
	 */
	private void addDoodle() {
		_gamePane = new Pane();
		_doodle = new Doodle(_gamePane, this);
		_keyHandler = new KeyHandler();
		_gamePane.addEventHandler(KeyEvent.KEY_PRESSED, _keyHandler);
		_gamePane.setFocusTraversable(true);
	}

	/**
	 * This method is called once in the constructor of the game class and
	 * starts the timehandler that carries out the animation of the game. With
	 * each keyframe, the 5 methods below this are called (updateDoodle,
	 * generatePlatforms, bounce, scroll, gameOver). Each checks for specific
	 * conditions and carries out specific measures depending on whether they
	 * are true or not.
	 */
	private void setupTimeline() {
		KeyFrame keyframe = new KeyFrame(Duration.seconds(Constants.DURATION),
				new TimeHandler());
		_timeline = new Timeline(keyframe);
		_timeline.setCycleCount(Animation.INDEFINITE);
		_timeline.play();
	}

	/**
	 * This method is called with each timeframe. It is responsible for the
	 * movement and illusion that the Doodle is under the effect of gravity. It
	 * uses two formulas to recalculate the doodle's new velocity and position
	 * and then changes the doodles y position based on those calculations. It
	 * then tells the Doodle what the new velocity is.
	 */
	private void updateDoodle() {
		_doodleVelocity = _doodle.getVelocity() + Constants.GRAVITY
				* Constants.DURATION;
		_doodleYPosition = _doodle.getYLoc() + _doodleVelocity
				* Constants.DURATION;
		_doodle.setYPosition(_doodleYPosition);
		_doodle.updateVelocity();
	}

	/**
	 * This method generates a new platform anywhere on the x-axis and anywhere
	 * between 60 to 140 pixels above the last platform placed. Platforms that
	 * move off of the screen are deleted from the arraylist.
	 */
	private void generatePlatforms() {
		if (_platforms.get(_platforms.size() - 1).getY() > 0) {
			double newPlatformY = _platforms.get(_platforms.size()-1).getY()-60-(int)(Math.random()*80);
			double newPlatformX = (int) (Math.random() * Constants.NEW_PLATFORM_X_MULTIPLIER);
			_platforms.add(new Platform(_gamePane));
			_platforms.get(_platforms.size() - 1).setX(newPlatformX);
			_platforms.get(_platforms.size() - 1).setY(newPlatformY);
			
		}
	}

	/**
	 * The bounce method checks to see if the doodle is both intersecting with a
	 * platform in the arraylist and has positive velocity (is moving
	 * downwards), and will change the doodle's velocity to be positive if both
	 * conditions to be satisfied. This results in the illusion of the doodle
	 * jumping off of a platform if it happens to fall onto one.
	 */
	private void bounce() {
		for (int i = 0; i < _platforms.size() - 1; i++) {
			if (_doodleVelocity >= 0
					&& _rectangle.intersects(_platforms.get(i).getX(),
							_platforms.get(i).getY(), 40, 10)) {
				_doodleVelocity = Constants.REBOUND_VELOCITY;
				_doodle.updateVelocity();
				_scoreLabel.setText("Score: " + _score++);
			}
		}
	}

	/**
	 * The scroll method checks to see if the doodles y position is above the
	 * midpoint of the screen and if the doodle is falling. If both condition
	 * are true, the platforms are moved down. If the the doodle has a negative
	 * velocity (is moving up) and is below the midpoint line, its y position
	 * will decrease instead of the platform's y position increasing.
	 */
	private void scroll() {
		if (_doodleYPosition < Constants.MIDDLE_SCREEN && _doodleVelocity < 0) {
			_doodle.setYPosition(Constants.MIDDLE_SCREEN);
			for (int i = 0; i < _platforms.size(); i++) {
				_platforms.get(i).setY(
						_platforms.get(i).getY() - _doodleVelocity
								* Constants.DURATION);
			}
		} else {
			_doodle.setYPosition(_doodleYPosition);
		}
		if (_platforms.get(0).getY() > Constants.SCREEN_BOTTOM) {
			
			_gamePane.getChildren().remove(_platforms.get(0).getRectangle());
			_platforms.remove(0);
			
		}
	}

	/**
	 * The game over method checks to see if the doodle has fallen off of the
	 * screen (if its positive is greater than 600 pixels). If it has, then the
	 * timeline is stopped, ending the game animation, and the paneorganizer
	 * calls its gameOver method which displays the label "game over" in the
	 * bottom box.
	 */
	private void gameOver() {
		if (_doodleYPosition >= Constants.SCREEN_BOTTOM) {
			_timeline.stop();
			_paneOrganizer.gameOver();
			_gamePane.removeEventHandler(KeyEvent.KEY_PRESSED, _keyHandler);
		}
	}

	/**
	 * This timehandler class sequentially calls the 5 methods above to carry
	 * out the core game logic for each timeframe. The methods are described
	 * above.
	 */
	private class TimeHandler implements EventHandler<ActionEvent> {
		
		/**
		 * The handle method is called every 16 milliseconds to carry out the core game logic for each timeframe.
		 * The handle method itself doesn't contain any real logic but the methods it calls do.
		 */
		@Override
		public void handle(ActionEvent event) {
			updateDoodle();
			generatePlatforms();
			bounce();
			scroll();
			gameOver();
			event.consume();
		}
	}

	/**
	 * The keyhandler is created in the addDoodle method and its handle method
	 * is called each time a key is pressed. If the Left or Right arrow key is
	 * pressed, the doodle will be moved left or right respectively. This is
	 * carried out by a switch.
	 */
	private class KeyHandler implements EventHandler<KeyEvent> {

		/**
		 * The handle method is triggered by keystrokes and the left and right
		 * arrow keys move the doodle left or right by 20 pixels, not affecting
		 * the y-value of the doodle. The switch calls the doodle's shift
		 * location method depending and gives it either 20 or -20.
		 */
		@Override
		public void handle(KeyEvent keyEvent) {
			KeyCode keyPressed = keyEvent.getCode();
			switch (keyPressed) {
			case LEFT:
				_doodle.shiftLocation(-20);
				break;
			case RIGHT:
				_doodle.shiftLocation(20);
				break;
			default:
				break;
			}
			keyEvent.consume();
		}
	}

}
