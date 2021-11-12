import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyPanel2 extends JPanel {
	private JPanel tilePanel;
	private JPanel buttPanel;
	private JButton addButt;
	private JButton copButt;
	private JButton zerButt;
	private JButton disButt;
	private JButton removeButt;
	private JButton addNewButt;
	private ArrayList<JButton> buttonListEquiv;
	private ArrayList<GLACTuple> smolEquivList;
	private EquivPanel displayPanel;
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.black);
		
		Graphics g2D = (Graphics2D) g;
		
		
	}
	
	public MyPanel2(LookAndLayout ss, ArrayList<GLACTuple> tupList, ArrayList<ArrayList<GLACompone>> graphFunc) {
		JFrame frame = new JFrame("MyPanel");
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(new Dimension(400, 200));

		tilePanel = new JPanel();
		displayPanel = new EquivPanel(graphFunc);

		this.setBackground(Color.red);
		tilePanel.setBackground(Color.gray);
		displayPanel.setBackground(Color.white);
		
		buttonListEquiv = new ArrayList<JButton>();
		smolEquivList = new ArrayList<GLACTuple>();

		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(1, 1, 1, 1);
		gc.fill = GridBagConstraints.BOTH;

		displayButtons(tupList, ss, gc);
		
		frame.setLayout(new BorderLayout());

		frame.add(displayPanel, BorderLayout.CENTER);
		frame.add(tilePanel, BorderLayout.SOUTH);
	}

	

	private void displayButtons(ArrayList<GLACTuple> tupList, LookAndLayout ss, GridBagConstraints gc) {
		for (int i = 0; i < tupList.size(); i++) {
			if (tupList.get(i) != null) {
				JButton equivButt = new JButton("equiv" + tupList.get(i).getEqNo());

				// tilePanel.add(equivButt);
				smolEquivList.add(tupList.get(i));
				buttonListEquiv.add(equivButt);

				setPosition(0, i, equivButt, gc, tilePanel);
			}
		}

		
		for (int i = 0; i < buttonListEquiv.size(); i++) {
			final int iCopy = i;
			buttonListEquiv.get(i).addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseEntered(java.awt.event.MouseEvent evt) {
			        //jButton1.setBackground(Color.GREEN);
			    }

			    public void mouseExited(java.awt.event.MouseEvent evt) {
			        //jButton1.setBackground(UIManager.getColor("control"));
			    }
				
				
				public void mousePressed(java.awt.event.MouseEvent evt) {
					ss.doEquiv(smolEquivList.get(iCopy));
					ss.printGLA();
					
					ArrayList<ArrayList<GLACompone>> data = new ArrayList<ArrayList<GLACompone>>();
					data.addAll(ss.ds2ds());
					
					displayPanel.removeAll();
					displayPanel.setArrList(data);
					displayPanel.repaint();
					displayPanel.revalidate();
					displayPanel.validate();
					
					tilePanel.removeAll();
					tilePanel.revalidate();
					tilePanel.validate();
					tilePanel.repaint();
					
					smolEquivList.clear();
					buttonListEquiv.clear();
					
					displayButtons(ss.getEquiv(), ss, gc);
					
				}
			});
		}
		
	}

	private void setPosition(int x, int y, Object o, GridBagConstraints gc, JPanel panel) {
		gc.gridx = x;
		gc.gridy = y;
		panel.add((Component) o, gc);
	}
	
	
	
}
