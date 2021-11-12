import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class EquivPanel extends JPanel {

	private ArrayList<ArrayList<GLACompone>> graphFunc;

	Image add = new ImageIcon("test.png").getImage();
	Image cop = new ImageIcon("copyg.png").getImage();
	Image zer = new ImageIcon("zerog.png").getImage();
	Image dis = new ImageIcon("discardg.png").getImage();
	Image[] imArr = { add, cop, zer, dis };

	int x = 80;
	int y = 80;

	EquivPanel(ArrayList<ArrayList<GLACompone>> graphFunc) {
		this.graphFunc = graphFunc;// new ArrayList<ArrayList<GLACompone>>();

		this.setPreferredSize(new Dimension(500, 500));
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;

		g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		int max = 0;

		for (int i = 0; i < graphFunc.size(); i++) {

			if (graphFunc.get(i).size() > max) {
				max = graphFunc.get(i).size();
			}
		}

		for (int i = 0; i < graphFunc.size(); i++) {

			for (int j = 0; j < graphFunc.get(i).size(); j++) {
				g2D.drawImage(graphFunc.get(i).get(j).getIm().getImage(), i * (x + x / 2), (j+getWPos(max, graphFunc.get(i).size())) * y, null);

			}
		}

		int n, y;
		for (int i = 0; i < graphFunc.size() - 1; i++) {
			for (int j = 0; j < graphFunc.get(i).size(); j++) {
				for (int k = 0; k < graphFunc.get(i).get(j).getRight(); k++) {

					g2D.setStroke(new BasicStroke(3));
					g2D.drawLine(getThisXCo(i), getThisY(graphFunc.get(i).get(j), k, j, max, i), getNextXCo(i),
							getNextY(graphFunc.get(i).get(j), k, max, i));

				}
			}
		}

	}

	private int getThisXCo(int i) {
		return i * (x + x / 2) + x;
	}

	private int getNextXCo(int i) {
		return i * (x + x / 2) + x + x / 2;
	}

	private int getYCo(int j, int p, int max, int i) {

		return (j+getWPos(max, graphFunc.get(i).size())) * y + p;
	}

	private int getThisY(GLACompone glaCompone, int k, int j, int max, int i) {
		int p;
		int n;

		n = glaCompone.getRight();
		p = getNodePos(n, k);

		return getYCo(j, p, max, i);
	}
	
	
	/////////////if dummy not null, then get dummy, else get next

	private int getNextY(GLACompone glaCompone, int k, int max, int i) {
		int jDash = 0;
		int p;
		int n;
		
		GLACompone[] next = new GLACompone[glaCompone.getRight()];
		
		for (int j = 0; j < graphFunc.get(i + 1).size(); j++) {
			
			
			for(int r = 0; r < glaCompone.getRight(); r++) {
				if(glaCompone.getNextDummy()[r] == null) {
					glaCompone.getNextDummy()[r] = glaCompone.getNext()[r];
				}
			}
			next = glaCompone.getNextDummy();
			
			if (graphFunc.get(i + 1).get(j) == next[k]) {
				jDash = j;
//			if (graphFunc.get(i + 1).get(j) == glaCompone.getNext()[k]) {
//				jDash = j;
			}
		}
		n = next[k].getLeft();
		p = getNodePos(n, glaCompone.getNextLink()[k]);
		
//		n = glaCompone.getNext()[k].getLeft();
//		p = getNodePos(n, glaCompone.getNextLink()[k]);

		return getYCo(jDash, p, max, i+1);
	}

	private int getNodePos(int n, int k) {
		if (n == 1) {
			return 40;
		} else if (n == 2) {
			if (k == 0) {
				return 8;
			} else {
				return 72;
			}
		}
		return -1;
	}

	public void setArrList(ArrayList<ArrayList<GLACompone>> newArr) {
		graphFunc = newArr;
	}
	
	public int getWPos(int max, int colSize) {
		int x = (max - colSize)/2;
		
		return x;
	}
}