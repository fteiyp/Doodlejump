package DoodleJump;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The paneorganizer class contains the game class. It also creates the
 * borderpane that lays out the game and the sub-panes - a vertical box at the
 * bottom and a pane that is retrieved from the game class. The getBorderPane
 * method is an accessor method that returns the borderpane to the app. The
 * gameOver method displays "game over" when the doodle falls. The
 * clickhandler's handle method calls the platform to exit when the exit button
 * is clicked.
 */
public class PaneOrganizer {

	private BorderPane _borderPane;
	private VBox _bottomPane;
	private Game _game;

	/**
	 * The constructor of the paneorganizer instantiates the borderpane and the
	 * game. It then changes the color of the borderpane (game's background) to
	 * be orange and sets the size of the borderpane to be the correct size. The
	 * quit button is instantiated and uses a clickhandler, and the vbox is set
	 * to be at the bottom of the borderpane.The vbox contains instructions for
	 * the game and the quit button. The game pane that is displayed in the
	 * center of the borderpane is transparent except for the doodle and
	 * platforms that it contains graphically.
	 */
	public PaneOrganizer() {
		_game = new Game(this);
		_borderPane = new BorderPane();
		_borderPane.setStyle("-fx-background-color: orange;");
		_borderPane.setPrefSize(Constants.PANESIZE_X, Constants.PANESIZE_Y);
		this.setupBottomPane();
	}

	/**
	 * This setupBottomPane method both instantiates the game class and sets up the
	 * Vbox that is graphically contained at the bottom of the borderpane. When the
	 * game ends, the Game Over! text is added to this bottom pane. The quit button
	 * is also here.
	 */
	private void setupBottomPane() {
		Button quitButton = new Button("Quit");
		quitButton.setOnAction(new ClickHandler());
		_bottomPane = new VBox();
		_bottomPane.setStyle("-fx-background-color: magenta;");
		_bottomPane.setPrefSize(Constants.BOTTOMPANE_X, Constants.BOTTOMPANE_Y);
		Label instructions = new Label("Left and right keys move the doodle!");
		_bottomPane.getChildren().addAll(instructions, _game.getScoreLabel(), quitButton);
		_bottomPane.setAlignment(Pos.CENTER);
		_bottomPane.setSpacing(10);
		_borderPane.setCenter(_game.getGamePane());
		_borderPane.setBottom(_bottomPane);
	}
	
	/**
	 * This accessor method allows the app class to pass in the borderpane for
	 * the scene. Returns the borderpane instance variable.
	 */
	public Pane getBorderPane() {
		return _borderPane;
	}
	
	/**
	 * The gameOver method is called by the game class when the Doodle falls out
	 * of the screen (past 600 pixels). It adds the game over label to the vbox
	 * at the bottom of the borderpane.
	 */
	public void gameOver() {
		Text gameOver = new Text("Game Over!");
		gameOver.setFont(Font.font("Verdana", 40));
		gameOver.setFill(Color.BLACK);
		_bottomPane.getChildren().add(gameOver);
		_bottomPane.setSpacing(10);
	}
	
	/**
	 * This clickhandler is for the quit button. Its handle method tells the
	 * platform to exit, quitting the application.
	 */
	private class ClickHandler implements EventHandler<ActionEvent> {

		/**
		 * This handle method is called everytime the quitbutton is clicked (the
		 * event is a mouse click). Quits the application with the Platform.exit
		 * command.
		 */
		@Override
		public void handle(ActionEvent event) {
			Platform.exit();
			event.consume();
		}
	}
}
