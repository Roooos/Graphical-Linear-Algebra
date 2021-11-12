import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GLAView extends LookAndLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 946553867993213407L;

	public GLAView(ActionListener act, String id) {

		super("GLA");

		Box box = Box.createHorizontalBox();
		box.add(Box.createRigidArea(new Dimension(1, 0)));
		box.add(Box.createHorizontalGlue());
		add(box);
		bottomPanel.add(box, BorderLayout.LINE_END);

		revalidate();
	}
}
