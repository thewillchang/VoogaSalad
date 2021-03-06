package authoring.view.icons;

import authoring.eventhandlers.GameHandler;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * @author Kevin Li
 * @author Chris Bernt
 * @author Wesley Valentine
 * @author Arjun Jain
 */
public class BaseIcon extends VBox {
	protected GameHandler[] myOnClick;
	protected String myLabel;

	public BaseIcon(String label, GameHandler... handler) {
		myLabel = label;
		myOnClick = handler;
	}

	public void addLabel() {
		this.getChildren().add(new Text(myLabel));
	}

	public String getName() {
		return myLabel;
	}
}
