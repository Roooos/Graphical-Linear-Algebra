import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RightPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8824373262073603341L;

	private LookAndLayout ss;

	private JButton addNewButt, removeButt, addButt, copButt, disButt, zerButt;
	private JPanel tilePanel, buttPanel;
	private ArrayList<GLACompone> compList;

	public RightPanel(LookAndLayout ss) {
		this.ss = ss;

		tilePanel = new JPanel();
		buttPanel = new JPanel();

		int x = 80;
		int y = 80;
		addButt = new JButton();
		copButt = new JButton();
		zerButt = new JButton();
		disButt = new JButton();
		ImageIcon addIm = new ImageIcon(displayImage("add", x, y));
		ImageIcon copIm = new ImageIcon(displayImage("cop", x, y));
		ImageIcon zerIm = new ImageIcon(displayImage("zer", x, y));
		ImageIcon disIm = new ImageIcon(displayImage("dis", x, y));
		addButt.setIcon(addIm);
		copButt.setIcon(copIm);
		zerButt.setIcon(zerIm);
		disButt.setIcon(disIm);

		addNewButt = new JButton("+");
		removeButt = new JButton("-");

		addButt.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				ss.addComponent(2, 1, addIm, "add");
			}
		});

		copButt.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				ss.addComponent(1, 2, copIm, "cop");
			}
		});

		zerButt.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				ss.addComponent(0, 1, zerIm, "zer");
			}
		});

		disButt.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				ss.addComponent(1, 0, disIm, "dis");
			}
		});

		this.setLayout(new BorderLayout());
		this.setBackground(new Color(65, 185, 255));
		this.setPreferredSize(new Dimension(250, 500));

		this.add(tilePanel, BorderLayout.CENTER);
		this.add(buttPanel, BorderLayout.SOUTH);

		tilePanel.setLayout(new GridBagLayout());
		tilePanel.setBackground(new Color(30, 30, 30));
		tilePanel.setPreferredSize(new Dimension(250, 400));

		buttPanel.setLayout(new GridBagLayout());
		buttPanel.setBackground(new Color(50, 50, 50));
		buttPanel.setPreferredSize(new Dimension(250, 100));

		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.fill = GridBagConstraints.BOTH;

		// tileButtons
		setPosition(0, 0, addButt, gc, tilePanel);
		setPosition(0, 1, copButt, gc, tilePanel);
		setPosition(0, 2, zerButt, gc, tilePanel);
		setPosition(0, 3, disButt, gc, tilePanel);

		// otherButtons
		setPosition(0, 0, addNewButt, gc, buttPanel);
		setPosition(1, 0, removeButt, gc, buttPanel);

	}

	// adds component to certain position to panel
	private void setPosition(int x, int y, Object o, GridBagConstraints gc, JPanel panel) {
		gc.gridx = x;
		gc.gridy = y;
		panel.add((Component) o, gc);
	}

	private BufferedImage displayImage(String name, int x, int y) {
		BufferedImage image = null;

		try {
			image = ImageIO.read(new File(name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			return resizeImage(image, x, y);
		} catch (IOException e) {
			e.printStackTrace();
		}
		;

		return image;
	}

	BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
		Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
		BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

		outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
		return outputImage;
	}

	public ArrayList<GLACompone> getCompList() {
		return compList;
	}

	/*
	 * tagListPanel = new TagPanel(new ArrayList<String>(), new ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent e) { // do nothing } });
	 * tagListPanel.setPreferredSize(new Dimension(100, 100));
	 * tagListPanel.setBackground(new Color(60,160, 255));
	 */
}