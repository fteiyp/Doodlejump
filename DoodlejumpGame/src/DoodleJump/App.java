package DoodleJump;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This application models the game DoodleJump: a doodle, a small rectangle in
 * this version, bounces on platforms as the user moves it side to side. The
 * point of the game is to climb as many platforms as possible and avoid falling
 * outside of the screen's view. Falling outside of the screen's view will end
 * the game. This app class instantiates the pane organizer which in turn
 * instantiates the game class that contains most of the other objects and some
 * private classes.
 */
public class App extends Application {

	/**
	 * This start method is passed the stage as an argument and sets its title
	 * to DoodleJump!. The paneorganizer is instantiated locally and the scene
	 * is passed borderpane (root) that the pane organizer instantiates. The
	 * scene is set as the scene of the stage and the stage is displayed (show).
	 */
	@Override
	public void start(Stage stage) {
		stage.setTitle("DoodleJump!");
		PaneOrganizer organizer = new PaneOrganizer();
		Scene scene = new Scene(organizer.getBorderPane());
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] argv) {
		// launch is a static method inherited from Application.
		launch(argv);
	}
}
