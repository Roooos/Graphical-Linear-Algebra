import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LeftPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5354733981479553004L;
	private JPanel buttonPanel;
	private DragPanel dragPanel;
	private LookAndLayout ss;

	private Boolean del;

	// private JButton delButt;

	ImageIcon add = new ImageIcon("add.png");
	final int WIDTH = add.getIconWidth();
	final int HEIGHT = add.getIconHeight();
	Point imageCorner;
	Point prevPt;

	MyPanel2 myPan;

	public LeftPanel(LookAndLayout ss) {

		del = false;

		this.ss = ss;

		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(600, 200));
		this.setBackground(Color.blue);

		dragPanel = new DragPanel(ss);
		buttonPanel = new JPanel();

		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(1, 1, 1, 1);
		gc.fill = GridBagConstraints.BOTH;

		buttonPanel.setLayout(new GridBagLayout());
		buttonPanel.setBackground(Color.lightGray);
		buttonPanel.setPreferredSize(new Dimension(100, 100));

		JButton print = new JButton("print");
		JButton delButt = new JButton("delete");
		JButton equivButt = new JButton("equiv");
		JButton saveButt = new JButton("save");
		JButton newButt = new JButton("new");
		JButton openButt = new JButton("open");

		// buttonPanel.add(print, BorderLayout.WEST);
		// buttonPanel.add(delButt, BorderLayout.EAST);

		setPosition(0, 0, print, gc, buttonPanel);
		setPosition(1, 0, delButt, gc, buttonPanel);
		setPosition(2, 0, equivButt, gc, buttonPanel);
		setPosition(3, 0, newButt, gc, buttonPanel);
		setPosition(4, 0, openButt, gc, buttonPanel);
		// setPosition(2, 0, rightButt, gc, buttonPanel);

		print.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				ss.printGLA();
			}
		});

		delButt.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				del = !del;
				if (del) {
					delButt.setForeground(Color.red);
				} else {
					delButt.setForeground(Color.black);
				}
				dragPanel.setDel(del);
			}
		});

		equivButt.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				ArrayList<GLACTuple> tupList = new ArrayList<GLACTuple>();
				tupList = ss.getEquiv();
				
				ArrayList<ArrayList<GLACompone>> data = new ArrayList<ArrayList<GLACompone>>();
				data.addAll(ss.ds2ds());

				myPan = new MyPanel2(ss, tupList, data);
				
				
				
				System.out.println(data);
				
				System.out.print("(");
				for (int i=0; i<data.size(); i++) {
					System.out.print("(");
					 for(int j = 0; j < data.get(i).size(); j++) {
						 System.out.print(data.get(i).get(j).getId() + ", ");
					 }
					 System.out.print(")");
				}
				 System.out.print(")");
				System.out.print("\n");
			}
		});

		this.add(dragPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}

	public void addPanel(GLACompone comp) {
		dragPanel.addPanel(comp);
	}

	private void setPosition(int x, int y, Object o, GridBagConstraints gc, JPanel panel) {
		gc.gridx = x;
		gc.gridy = y;
		panel.add((Component) o, gc);
	}
}
