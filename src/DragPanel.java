import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class DragPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2969147195059028530L;

	private LookAndLayout ss;

	ImageIcon add = new ImageIcon("test.png");
	ImageIcon cop = new ImageIcon("copyg.png");
	ImageIcon zer = new ImageIcon("zerog.png");
	ImageIcon dis = new ImageIcon("discardg.png");
	ImageIcon[] imArr = { add, cop, zer, dis };

	JPanel centre;

	final int WIDTH = add.getIconWidth();
	final int HEIGHT = add.getIconHeight();
	Point imageCorner;
	Point prevPt;

	private Boolean del;

	int currX, currY, lastX, lastY;

	private int lastButtSide;
	private GLACompone lastComp;
	private int lastButt;

	public DragPanel(LookAndLayout ss) {

		del = false;
		imageCorner = new Point(0, 0);
		// ClickListener clickListener = new ClickListener();
		// DragListener dragListener = new DragListener();

		// this.addMouseListener(clickListener);
		// this.addMouseMotionListener(dragListener);
		JPanel start = new JPanel();
		JPanel end = new JPanel();

		start.setLayout(new GridBagLayout());
		end.setLayout(new GridBagLayout());

		ArrayList<JButton> buttonListStart = new ArrayList<JButton>();
		ArrayList<JButton> buttonListEnd = new ArrayList<JButton>();

		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(1, 1, 1, 1);
		gc.fill = GridBagConstraints.BOTH;

		for (int i = 0; i < ss.start.getRight(); i++) {
			JButton startButt = new JButton("strt");
			JButton endButt = new JButton("end");

			start.add(startButt);
			end.add(endButt);

			buttonListStart.add(startButt);
			buttonListEnd.add(endButt);
			setPosition(0, i, startButt, gc, start);
			setPosition(0, i, endButt, gc, end);
		}

		for (int i = 0; i < ss.start.getRight(); i++) {
			final int iCopy = i;
			buttonListStart.get(i).addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent evt) {

					if (lastButtSide * 1 < 0) {
						// System.out.println("connection created between start and " + lastComp.getId()
						// + "r" + lastButt);
						// + ss.start.getId() + "l" + iCopy + " and "

						// removes links current node and start node previously had
						if (ss.start.getNext()[iCopy] != null)
							ss.start.getNext()[iCopy].setPrev(null, ss.start.getNextLink()[iCopy]);
						if (lastComp.getPrev()[lastButt] != null)
							lastComp.getPrev()[lastButt].setNext(null, lastComp.getPrevLink()[lastButt]);

						// create connections
						ss.start.addNext(lastComp, iCopy);
						lastComp.addPrev(ss.start, lastButt);

						// set links
						ss.start.setNextLink(lastButt, iCopy);
						lastComp.setPrevLink(iCopy, lastButt);

						// reset click
						lastButtSide = 0;
					} else {
						lastButtSide = 1;
						lastComp = ss.start;
						lastButt = iCopy;
					}

					// System.out.println("hello from panel: " + comp.getId() + ", left cheek,
					// button: " + iCopy);
				}
			});
		}

		for (int i = 0; i < ss.end.getLeft(); i++) {
			final int iCopy = i;
			buttonListEnd.get(i).addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent evt) {

					if (lastButtSide * -1 < 0) {
						// createConnection
						// System.out.println("connection created between " + lastComp.getId() + "l" +
						// lastButt + " and end" );
						// + ss.end.getId()+ "r" + jCopy
						if (ss.end.getPrev()[iCopy] != null)
							ss.end.getPrev()[iCopy].setNext(null, ss.end.getPrevLink()[iCopy]);
						if (lastComp.getNext()[lastButt] != null)
							lastComp.getNext()[lastButt].setPrev(null, lastComp.getNextLink()[lastButt]);

						// create connections
						ss.end.addPrev(lastComp, iCopy);
						lastComp.addNext(ss.end, lastButt);

						// set links
						ss.end.setPrevLink(lastButt, iCopy);
						lastComp.setNextLink(iCopy, lastButt);

						lastButtSide = 0;
					} else {
						// System.out.println("nuh uh");
						lastButtSide = -1;
						lastComp = ss.end;
						lastButt = iCopy;
					}

					// System.out.println("hello from panel: " + comp.getId() + ", right cheek,
					// button: " + iCopy);
				}
			});
		}

		this.setLayout(new BorderLayout());

		this.add(start, BorderLayout.WEST);
		this.add(end, BorderLayout.EAST);

		centre = new JPanel();
		centre.setBackground(Color.white);
		centre.setLayout(new FlowLayout());

		this.add(centre, BorderLayout.CENTER);

		this.ss = ss;

		this.setPreferredSize(new Dimension(600, 200));
		this.setBackground(Color.blue);

		lastButtSide = 0;

	}

	public void addPanel(GLACompone comp) {
		JLabel label = new JLabel();
		label.setIcon(comp.getIm());

		JPanel panel = new JPanel();
		panel.setBackground(Color.cyan);
		panel.setLayout(new BorderLayout());
		centre.add(panel);

		JPanel left = new JPanel();
		JPanel right = new JPanel();

		left.setBackground(Color.white);
		right.setBackground(Color.white);

		left.setLayout(new GridBagLayout());
		right.setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(1, 1, 1, 1);
		gc.fill = GridBagConstraints.BOTH;

		ArrayList<JButton> buttonListLeft = new ArrayList<JButton>();
		ArrayList<JButton> buttonListRight = new ArrayList<JButton>();

		LinesComponent line = new LinesComponent();
		// final Rectangle bbox = panel.getBounds();

		for (int i = 0; i < comp.getLeft(); i++) {
			JButton leftButt = new JButton(" ");
			leftButt.setBackground(new Color(200, 100 * i, 200));
			buttonListLeft.add(leftButt);
			setPosition(0, i, leftButt, gc, left);
		}

		for (int i = 0; i < comp.getRight(); i++) {
			JButton rightButt = new JButton(" ");
			rightButt.setBackground(new Color(200, 100 * i, 200));
			buttonListRight.add(rightButt);
			setPosition(0, i, rightButt, gc, right);
		}

		for (int i = 0; i < comp.getLeft(); i++) {
			final int iCopy = i;
			buttonListLeft.get(i).addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent evt) {
					if (del) {
						for (int j = 0; j < comp.getPrev().length; j++) {
							if (comp.getPrev()[j] != null)
								comp.getPrev()[j].setNext(null, comp.getPrevLink()[j]);
							comp.setPrev(null, j);
						}

						for (int j = 0; j < comp.getNext().length; j++) {
							if (comp.getNext()[j] != null)
								comp.getNext()[j].setPrev(null, comp.getNextLink()[j]);
							comp.setNext(null, j);
						}
						panel.setVisible(false);

					} else if (lastButtSide * -1 < 0) {

						// System.out.println("connection created between " + lastComp.getId() + "r" +
						// lastButt + " and " + comp.getId() + "l" + iCopy);

						if (comp.getPrev()[iCopy] != null)
							comp.getPrev()[iCopy].setNext(null, comp.getPrevLink()[iCopy]);
						if (lastComp.getNext()[lastButt] != null)
							lastComp.getNext()[lastButt].setPrev(null, lastComp.getNextLink()[lastButt]);

						// create connections
						comp.addPrev(lastComp, iCopy);
						lastComp.addNext(comp, lastButt);

						// set links
						comp.setPrevLink(lastButt, iCopy);
						lastComp.setNextLink(iCopy, lastButt);

						// currX = bbox.x + evt.getX();
						// currY = bbox.y + evt.getY();

						// currX = panel.getX() + buttonListLeft.get(iCopy).getX();
						// currY = panel.getY() + buttonListLeft.get(iCopy).getY();

						line.addLine(currX, currY, lastX, lastY, Color.black);

						repaint();
						// reset stored coords
						lastButtSide = 0;
					} else {
						// System.out.println("nuh uh");
						lastButtSide = -1;
						lastComp = comp;
						lastButt = iCopy;

						// lastX = bbox.x + evt.getX();
						// lastY = bbox.y + evt.getY();
						// lastX = panel.getX() + buttonListLeft.get(iCopy).getX();
						// lastY = panel.getY() + buttonListLeft.get(iCopy).getY();

					}
					// System.out.println("hello from panel: " + comp.getId() + ", left cheek,
					// button: " + iCopy);
				}
			});
		}

		for (int i = 0; i < comp.getRight(); i++) {
			final int iCopy = i;
			buttonListRight.get(i).addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent evt) {
					if (del) {
						
						for (int j = 0; j < comp.getPrev().length; j++) {
							if (comp.getPrev()[j] != null)
								comp.getPrev()[j].setNext(null, comp.getPrevLink()[j]);
							comp.setPrev(null, j);
						}

						for (int j = 0; j < comp.getNext().length; j++) {
							if (comp.getNext()[j] != null)
								comp.getNext()[j].setPrev(null, comp.getNextLink()[j]);
							comp.setNext(null, j);
						}
						
						panel.setVisible(false);
					} else if (lastButtSide * 1 < 0) {
						// createConnection
						// System.out.println("connection created between " + comp.getId()+ "r" + iCopy
						// + " and " + lastComp.getId() + "l" + lastButt);

						// removes links current node and start node previously had
						if (comp.getNext()[iCopy] != null)
							comp.getNext()[iCopy].setPrev(null, comp.getNextLink()[iCopy]);
						if (lastComp.getPrev()[lastButt] != null)
							lastComp.getPrev()[lastButt].setNext(null, lastComp.getPrevLink()[lastButt]);

						// create connections
						comp.addNext(lastComp, iCopy);
						lastComp.addPrev(comp, lastButt);

						// set links
						comp.setNextLink(lastButt, iCopy);
						lastComp.setPrevLink(iCopy, lastButt);

						// currX = panel.getX() + buttonListRight.get(iCopy).getX();
						// currY = panel.getY() + buttonListRight.get(iCopy).getY();

						line.addLine(currX, currY, lastX, lastY, Color.black);

						repaint();

						lastButtSide = 0;
					} else {
						// System.out.println("nuh uh");
						lastButtSide = 1;
						lastComp = comp;
						lastButt = iCopy;

						// lastX = panel.getX() + buttonListRight.get(iCopy).getX();
						// lastY = panel.getY() + buttonListRight.get(iCopy).getY();
					}

					// System.out.println("hello from panel: " + comp.getId() + ", right cheek,
					// button: " + iCopy);
				}
			});
		}

		panel.add(label, BorderLayout.CENTER);
		panel.add(left, BorderLayout.WEST);
		panel.add(right, BorderLayout.EAST);

		new Movement(panel);

		revalidate();
	}

	private void setPosition(int x, int y, Object o, GridBagConstraints gc, JPanel panel) {
		gc.gridx = x;
		gc.gridy = y;
		panel.add((Component) o, gc);
	}

	public void setDel(Boolean bool) {
		del = bool;
	}

	void drawLines(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawLine(currX, currY, lastX, lastY);
	}

	public void paint(Graphics g) {
		super.paint(g);
		drawLines(g);
	}

	/*
	 * @Override public void paintComponent (Graphics g) { super.paintComponent(g);
	 * for(int i = 0; i < ss.getComponenetList().size(); i++) {
	 * //ss.getComponenetList().get(i).getIm().paintIcon(this, g,
	 * (int)imageCorner.getX()+i*150, (int) imageCorner.getY()+i*100); }
	 * //add.paintIcon(this, g, (int)imageCorner.getX(), (int) imageCorner.getY());
	 * }
	 * 
	 * 
	 * private class ClickListener extends MouseAdapter{ public void
	 * mousePressed(MouseEvent e) { prevPt = e.getPoint(); } }
	 * 
	 * 
	 * private class DragListener extends MouseMotionAdapter{ public void
	 * mouseDragged(MouseEvent e) { Point currentPt = e.getPoint();
	 * imageCorner.translate( (int)(currentPt.getX() - prevPt.getX()),
	 * (int)(currentPt.getY() - prevPt.getY()) ); prevPt = currentPt; repaint(); } }
	 */
}
