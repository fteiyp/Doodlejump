package DoodleJump;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This doodle class is instantiated by the game class. It adds itself to the
 * doodlepane that is also created by the game class. This allows it to be
 * displayed. Velocity is an important instance variable because it is updated
 * for each timeframe by the game class and then by the doodle.
 */
public class Doodle {

	private double _velocity;
	private Rectangle _rectangle;
	private Game _game;

	/**
	 * The constructor takes in the doodlePane and the game as arguments to its
	 * parameters so that it can add the rectangle that graphically represents
	 * the doodle to the doodlepane and so that it can store the game so that
	 * the upDateVelocity method can call the game's getVelocity method so the
	 * new velocity of each timeframe can be stored by the doodle.
	 */
	public Doodle(Pane doodlePane, Game game) {
		_game = game;
		_rectangle = new Rectangle(20, 40);
		_rectangle.setFill(Color.BLUE);
		_rectangle.setX(190);
		_rectangle.setY(550);
		doodlePane.getChildren().addAll(_rectangle);
		_velocity = 0;
	}

	/**
	 * The shiftLocation method changes the X position of the rectangle to be
	 * equal to the old position plus or minus some value x that is passed in as
	 * an argument.
	 */
	public void shiftLocation(double x) {
		_rectangle.setX(this.getXLoc() + x);
	}

	/**
	 * This accessor method returns the current value of the rectangles
	 * x-location so that the shift location method can use it and add whatever
	 * x value is passed in to it.
	 */
	private double getXLoc() {
		return _rectangle.getX();
	}

	/**
	 * This accessor method returns the current value of the rectangles
	 * y-locations so that the game class can use it in its velocity and
	 * position calculations.
	 */
	public double getYLoc() {
		return _rectangle.getY();
	}

	/**
	 * This accessor method returns the velocity value stored by the doodle
	 * class to use as the current velocity for the calculations made in the
	 * updateDoodle method.
	 */
	public double getVelocity() {
		return _velocity;
	}

	/**
	 * This accessor method returns the rectangle that graphically represents
	 * the doodle so that the game class can check if the rectangle intersects
	 * with a platforms in the bounce method.
	 */
	public Rectangle getRectangle() {
		return _rectangle;
	}

	/**
	 * This method passes in a double y that becomes the new value of the
	 * doodles y position. This method is called on by the update doodle method
	 * when it is changing the doodle's height at each timeframe.
	 */
	public void setYPosition(double y) {
		_rectangle.setY(y);
	}

	/**
	 * This mutator method changes the value of the rectangles X position to be
	 * equal to exactly the number that is passed in as an argument. This is
	 * called whenever the doodle is moved side to side by the keyhandler in the
	 * game class.
	 */
	public void setXPosition(double x) {
		_rectangle.setX(x);
	}

	/**
	 * This method is called whener the velocity is changed in the game class
	 * and the velocity of the doodle must be updated to match it. The two times
	 * this happens is when the doodle bounces off of a platform or when the
	 * doodle's velocity is being updated by the timehandler via the
	 * updateDoodle method.
	 */
	public void updateVelocity() {
		_velocity = _game.getDoodleVelocity();
	}

}
