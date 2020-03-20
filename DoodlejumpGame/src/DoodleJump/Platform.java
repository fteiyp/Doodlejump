package DoodleJump;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The platform class creates the rectangular platforms that are contained in
 * the platforms array in the game class. This class is simple and only has a
 * constructor, accessor methods for the x and y coordinates of the rectangles,
 * and mutators for the x and y values of the rectangular platforms.
 */
public class Platform {

	private Rectangle _rectangle;

	/**
	 * The constructor of the platform instantiates a rectangle and sets its
	 * color to blue. It then adds the rectangle to the GamePane.
	 */
	public Platform(Pane doodlePane) {
		_rectangle = new Rectangle(40, 10);
		_rectangle.setFill(Color.BLUE);
		doodlePane.getChildren().addAll(_rectangle);
	}
	
	public Rectangle getRectangle() {
		return _rectangle;
	}

	/*
	 * This accessor returns the numeric value of a platforms x-coordinate.
	 */
	public double getX() {
		return _rectangle.getX();
	}

	/*
	 * This accessor returns the numeric value of a platforms y-coordinate.
	 */
	public double getY() {
		return _rectangle.getY();
	}

	/*
	 * This mutator changes the numeric value of a platforms x-coordinate.
	 */
	public void setX(double x) {
		_rectangle.setX(x);
	}

	/*
	 * This mutator changes the numeric value of a platforms y-coordinate.
	 */
	public void setY(double y) {
		_rectangle.setY(y);
	}

}
