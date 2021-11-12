import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class LookAndLayout extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3381219102987203299L;
	ArrayList<GLACompone> compList = new ArrayList<GLACompone>();
	GLACompone start, end;

	int x = 80;
	int y = 80;
	ImageIcon idIm = new ImageIcon(displayImage("unit", x, y));

	protected LeftPanel leftPanel;
	protected RightPanel rightPanel;

	// For better layout
	protected JPanel topPanel;
	protected JPanel bottomPanel;

	protected int id, butts;

	private ArrayList<GLACompone> seen;

	public LookAndLayout(String title) {
		setLookAndFeel();
		setup(title);
	}

	public void setup(String title) {

		this.setTitle(title);

		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		butts = 10;

		GLACompone[] next = new GLACompone[butts];
		GLACompone[] prev = new GLACompone[butts];
		int[] nextLink = new int[butts];
		int[] prevLink = new int[butts];

		start = new GLACompone(-1, 0, butts, next, null, nextLink, null, null, "strt");
		end = new GLACompone(-2, butts, 0, null, prev, null, prevLink, null, "end");

		id = 0;

		leftPanel = new LeftPanel(this);
		rightPanel = new RightPanel(this);

		add(leftPanel, BorderLayout.LINE_START);
		add(rightPanel, BorderLayout.LINE_END);

		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.setPreferredSize(new Dimension(100, 50));
		topPanel.setBackground(Color.gray);
		add(topPanel, BorderLayout.NORTH);

		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.setPreferredSize(new Dimension(100, 50));
		bottomPanel.setBackground(Color.gray);
		add(bottomPanel, BorderLayout.SOUTH);

		setSize(new Dimension(850, 650));
		// setResizable(false);
		setLocationRelativeTo(null);
		revalidate();
	}

	public void addComponent(int left, int right, ImageIcon image, String type) {

		GLACompone[] next = new GLACompone[right];
		GLACompone[] prev = new GLACompone[left];
		int[] nextLink = new int[right];
		int[] prevLink = new int[left];
		GLACompone comp = new GLACompone(id, left, right, next, prev, nextLink, prevLink, image, type);

		id++;
		compList.add(comp);
		leftPanel.addPanel(comp);
		repaint();
	}

	public ArrayList<GLACompone> getComponenetList() {
		return compList;
	}

	public GLACompone getNode(int id) {
		// cycle
		ArrayList<GLACompone> seen = new ArrayList<GLACompone>();
		GLACompone node = start;
		for (int i = 0; i < start.getRight(); i++) {// each node connected to start
			// if node.next not null??
			if (searchNode(node.getNext()[i], id).getId() == id) {// new seen arraylist each time. fix?
				return node;
			}
		}
		seen.clear();
		return null;
	}

	private GLACompone searchNode(GLACompone node, int id) {
		seen.add(node);

		if (node.getId() == id)
			return node;

		if (node.getRight() == 0)
			return null;

		for (int i = 0; i < node.getRight(); i++) {
			if (!in(node.getNext()[i], seen)) {
				if (searchNode(node.getNext()[i], id).getId() == id) {
					return node;
				}
			}
		}
		return null;
	}

	public ArrayList<GLACTuple> getEquiv() {
		seen = new ArrayList<GLACompone>();
		ArrayList<GLACTuple> list = new ArrayList<GLACTuple>();
		GLACompone node = start;
		seen.add(node);

		for (int i = 0; i < start.getRight(); i++) {
			if (start.getNext()[i] != null) {
				if (!in(start.getNext()[i], seen)) {
					node = start.getNext()[i];
					list.addAll(getE(node));
				}
			}
		}
		seen.clear();
		return list;
	}

	private ArrayList<GLACTuple> getE(GLACompone node) {
		ArrayList<GLACTuple> list = new ArrayList<GLACTuple>();
		seen.add(node);

		if (node.getType() == "add") {
			// adding
			list.add(checkAdd1(node));
			list.add(checkAdd2(node));
			list.add(checkAdd3(node));
			// adding meets copying
			list.add(checkAdd4(node));
			// adding meets zero
			list.add(checkAdd5(node));
		} else if (node.getType() == "cop") {
			// copying
			list.add(checkCop1(node));
			list.add(checkCop2(node));
			list.add(checkCop3(node));
			// adding meets copying
			list.add(checkCop4(node));
		} else if (node.getType() == "zer") {
			// zero meets copying
			list.add(checkZer1(node));
		} else if (node.getType() == "dis") {
			// nothing
		}
		for (int i = 0; i < node.getRight(); i++) {
			if (!in(node.getNext()[i], seen) && node.getNext()[i] != null) {
				list.addAll(getE(node.getNext()[i]));

			}
		}
		return list;
	}

	//// make sure not null!!!!!!
	private GLACTuple checkZer1(GLACompone node) {
		// TODO Auto-generated method stub
		return null;
	}

	private GLACTuple checkCop4(GLACompone node) {
		// TODO Auto-generated method stub
		return null;
	}

	private GLACTuple checkCop3(GLACompone node) {
		GLACTuple tuple;
		for (int i = 0; i < node.getRight(); i++) {
			if (node.getNext()[i].getType().equals("dis")) {
				tuple = new GLACTuple(node, i + 9);
				return tuple;
			}
		}
		return null;
	}

	private GLACTuple checkCop2(GLACompone node) {
		GLACTuple tuple;
		if (node.getPrev()[0].getType().equals("cop")) {// change to .equals or something
			if (node.getPrevLink()[0] == 1) {
				tuple = new GLACTuple(node, 7);
			} else {
				tuple = new GLACTuple(node, 8);
			}
			return tuple;
		}
		return null;
	}

	private GLACTuple checkCop1(GLACompone node) {
		GLACTuple tuple = new GLACTuple(node, 6);
		return tuple;
	}

	private GLACTuple checkAdd5(GLACompone node) {
		GLACTuple tuple;
		if (node.getNext()[0].getType().equals("dis")) {// change to .equals or something
			tuple = new GLACTuple(node, 13);
			return tuple;
		}
		return null;
	}

	private GLACTuple checkAdd4(GLACompone node) {
		GLACTuple tuple;
		if (node.getNext()[0].getType().equals("cop")) {// change to .equals or something
			tuple = new GLACTuple(node, 11);
			return tuple;
		}
		return null;
	}

	private GLACTuple checkAdd3(GLACompone node) {
		GLACTuple tuple;
		for (int i = 0; i < node.getLeft(); i++) {
			if (node.getPrev()[i].getType().equals("zer")) {
				tuple = new GLACTuple(node, i + 4);
				return tuple;
			}
		}
		return null;
	}

	private GLACTuple checkAdd2(GLACompone node) {
		GLACTuple tuple;
		if (node.getNext()[0].getType().equals("add")) {// change to .equals or something
			if (node.getNextLink()[0] == 1) {
				tuple = new GLACTuple(node, 2);
			} else {
				tuple = new GLACTuple(node, 3);
			}
			return tuple;
		}
		return null;
	}

	private GLACTuple checkAdd1(GLACompone node) {
		GLACTuple tuple = new GLACTuple(node, 1);
		return tuple;
	}

	private Boolean in(GLACompone node, ArrayList<GLACompone> seen) {
		for (int i = 0; i < seen.size(); i++) {
			if (node == seen.get(i)) {
				return true;
			}
		}
		return false;
	}

	public void printGLA() {
		ArrayList<GLACompone> seen = new ArrayList<GLACompone>();
		GLACompone node = start;

		printNode(node, seen);
		System.out.println();
	}

	private void printNode(GLACompone node, ArrayList<GLACompone> seen) {
		System.out.print("id: " + node.getId() + " points to right:");
		for (int i = 0; i < node.getRight(); i++) {
			if (node.getNext()[i] != null) {
				System.out.print(" " + node.getNext()[i].getId());
			}
		}
		System.out.print(", to left:");
		for (int i = 0; i < node.getLeft(); i++) {
			if (node.getPrev()[i] != null) {
				System.out.print(" " + node.getPrev()[i].getId());
			}
		}
		System.out.println();

		seen.add(node);

		if (node.getRight() == 0)
			return;

		for (int i = 0; i < node.getRight(); i++) {
			if (!in(node.getNext()[i], seen)) {
				if (node.getNext()[i] != null) {
					printNode(node.getNext()[i], seen);
				}
			}
		}

		return;
	}

	public void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public void doEquiv(GLACTuple glacTuple) {

		GLACompone node = glacTuple.getNode();
		int eNo = glacTuple.getEqNo();
		if (eNo == 1)
			do1(node);

		else if (eNo == 2)
			do2(node);

		else if (eNo == 3)
			do3(node);

		else if (eNo == 4)
			do4(node);

		else if (eNo == 5)
			do5(node);

		else if (eNo == 6)
			do6(node);

		else if (eNo == 7)
			do7(node);

		else if (eNo == 8)
			do8(node);

		else if (eNo == 9)
			do9(node);

		else if (eNo == 10)
			do10(node);

		else if (eNo == 11)
			do11(node);

		else if (eNo == 12)
			do12(node);

		else if (eNo == 13)
			do13(node);

		else if (eNo == 14)
			do14(node);

		else if (eNo == 15)
			do15(node);

		return;
	}

	private void do15(GLACompone node) {
		// TODO Auto-generated method stub

	}

	private void do14(GLACompone node) {
		// TODO Auto-generated method stub

	}

	private void do13(GLACompone node) {
		// TODO Auto-generated method stub

	}

	private void do12(GLACompone node) {
		// TODO Auto-generated method stub

	}

	private void do11(GLACompone node) {
		// TODO Auto-generated method stub

	}

	private void do10(GLACompone node) {
		GLACompone node1 = node.getNext()[0];
		GLACompone node3 = node.getPrev()[0];

		node1.setPrev(node3, node.getNextLink()[0]);
		node1.setPrevLink(node.getPrevLink()[0], node.getNextLink()[0]);

		node3.setNext(node1, node.getPrevLink()[0]);
		node3.setNextLink(node.getNextLink()[0], node.getPrevLink()[0]);

	}

	private void do9(GLACompone node) {
		GLACompone node1 = node.getNext()[1];
		GLACompone node3 = node.getPrev()[0];

		node1.setPrev(node3, node.getNextLink()[1]);
		node1.setPrevLink(node.getPrevLink()[0], node.getNextLink()[1]);

		node3.setNext(node1, node.getPrevLink()[0]);
		node3.setNextLink(node.getNextLink()[1], node.getPrevLink()[0]);

	}

	private void do8(GLACompone node) {
		GLACompone nNode = node.getPrev()[0];
		GLACompone node1 = node.getNext()[0];
		GLACompone node3 = nNode.getNext()[1];

		int nextLink = nNode.getNextLink()[1];

		node1.setPrev(nNode, node.getNextLink()[0]);
		node1.setPrevLink(1, node.getNextLink()[0]);

		nNode.setNext(node1, 1);
		nNode.setNextLink(node.getPrevLink()[0], 1);

		node3.setPrev(node, nextLink);
		node3.setPrevLink(0, nextLink);

		node.setNext(node3, 0);
		node.setNextLink(nextLink, 0);

		do6(node);
		do6(nNode);

	}

	private void do7(GLACompone node) {

		GLACompone nNode = node.getPrev()[0];
		GLACompone node1 = nNode.getNext()[0];
		GLACompone node3 = node.getNext()[1];

		int NextLink = nNode.getNextLink()[0];

		node3.setPrev(nNode, node.getNextLink()[1]);
		node3.setPrevLink(0, node.getNextLink()[1]);

		nNode.setNext(node3, 0);
		nNode.setPrevLink(node.getNextLink()[1], 0);

		node1.setPrev(node, NextLink);
		node1.setPrevLink(1, NextLink);

		node.setNext(node1, 1);
		node.setNextLink(NextLink, 1);

		do6(node);
		do6(nNode);

	}

	private void do6(GLACompone node) {
		GLACompone node1 = node.getNext()[0];
		GLACompone node2 = node.getNext()[1];

		node1.setPrevLink(1, node.getNextLink()[0]);
		node2.setPrevLink(0, node.getNextLink()[1]);

		GLACompone next0 = node.getNext()[0];
		int nextLink = node.getNextLink()[0];

		node.setNext(node.getNext()[1], 0);
		node.setNextLink(node.getNextLink()[1], 0);

		node.setNext(next0, 1);
		node.setNextLink(nextLink, 1);

	}

	private void do5(GLACompone node) {
		GLACompone node1 = node.getPrev()[0];
		GLACompone node3 = node.getNext()[0];

		node1.setNext(node3, node.getPrevLink()[0]);
		node1.setNextLink(node.getNextLink()[0], node.getPrevLink()[0]);

		node3.setPrev(node1, node.getNextLink()[0]);
		node3.setPrevLink(node.getPrevLink()[0], node.getNextLink()[0]);

	}

	private void do4(GLACompone node) {
		GLACompone node1 = node.getPrev()[1];
		GLACompone node3 = node.getNext()[0];

		node1.setNext(node3, node.getPrevLink()[1]);
		node1.setNextLink(node.getNextLink()[0], node.getPrevLink()[1]);

		node3.setPrev(node1, node.getNextLink()[0]);
		node3.setPrevLink(node.getPrevLink()[1], node.getNextLink()[0]);

	}

	private void do3(GLACompone node) {
		GLACompone nNode = node.getNext()[0];
		GLACompone node1 = node.getPrev()[0];
		GLACompone node3 = nNode.getPrev()[1];

		int prevLink = nNode.getPrevLink()[1];

		node1.setNext(nNode, node.getPrevLink()[0]);
		node1.setNextLink(1, node.getPrevLink()[0]);

		nNode.setPrev(node1, 1);
		nNode.setPrevLink(node.getPrevLink()[0], 1);

		node3.setNext(node, prevLink);
		node3.setNextLink(0, prevLink);

		node.setPrev(node3, 0);
		node.setPrevLink(prevLink, 0);

		do1(node);
		do1(nNode);

	}

	private void do2(GLACompone node) {
		GLACompone nNode = node.getNext()[0];
		GLACompone node1 = nNode.getPrev()[0];
		GLACompone node3 = node.getPrev()[1];

		int prevLink = nNode.getPrevLink()[0];

		node3.setNext(nNode, node.getPrevLink()[1]);
		node3.setNextLink(0, node.getPrevLink()[1]);

		nNode.setPrev(node3, 0);
		nNode.setPrevLink(node.getPrevLink()[1], 0);

		node1.setNext(node, prevLink);
		node1.setNextLink(1, prevLink);

		node.setPrev(node1, 1);
		node.setPrevLink(prevLink, 1);

		do1(node);
		do1(nNode);
	}

	private void do1(GLACompone node) {
		GLACompone node1 = node.getPrev()[0];
		GLACompone node2 = node.getPrev()[1];

		node1.setNextLink(1, node.getPrevLink()[0]);
		node2.setNextLink(0, node.getPrevLink()[1]);

		// set node prev links
		GLACompone prev0 = node.getPrev()[0];
		int prevLink = node.getPrevLink()[0];

		node.setPrev(node.getPrev()[1], 0);
		node.setPrevLink(node.getPrevLink()[1], 0);

		node.setPrev(prev0, 1);
		node.setPrevLink(prevLink, 1);

	}

	// returns the non null elements of a component, in particular start
	public ArrayList<GLACompone> notNull(GLACompone GLAArray) {
		ArrayList<GLACompone> GLAList = new ArrayList<GLACompone>();

		for (int i = 0; i < butts; i++) {
			if (start.getNext()[i] != null) {
				GLAList.add(start.getNext()[i]);
			}
		}
		return GLAList;
	}

	public ArrayList<ArrayList<GLACompone>> ds2ds() {
		int size;
		int c = 1;
		ArrayList<ArrayList<GLACompone>> full = new ArrayList<ArrayList<GLACompone>>();
		ArrayList<GLACompone> startList = notNull(start);
		ArrayList<GLACompone> prevList = new ArrayList<GLACompone>();
		ArrayList<GLACompone> nextCol = new ArrayList<GLACompone>();

		prevList.add(start);

		nextCol = removeNextPrevNotSeen(startList, prevList);
		nextCol = removeDuplicates(nextCol);

		prevList.addAll(nextCol);
		full.add(new ArrayList<GLACompone>(nextCol));

		/*
		 * for (int i = 0; i < prevList.size(); i++) { System.out.println("i: " + i);
		 * System.out.println("size " + nextCol.size()); System.out.println("next col: "
		 * + nextCol.get(i).getId() + "\n");//new col is empty after first iteration
		 * 
		 * nextCol = ds2dsRec(getNextList(nextCol.get(i)), prevList);//i based on
		 * prevlist, not next col! prevList.addAll(nextCol); full.add(nextCol); }
		 * 
		 * //full = addId(full, start.getRight());
		 */
		ArrayList<GLACompone> potCol = new ArrayList<GLACompone>();
		// for each column, given by full
		for (int i = 0; i < full.size(); i++) {
			potCol.clear();
			nextCol.clear();
			// get potential next column
			for (int j = 0; j < full.get(i).size(); j++) {
				potCol.addAll(array2List(full.get(i).get(j).getNext(), full.get(i).get(j).getRight()));
			}

			nextCol = removeDuplicates(potCol);
			nextCol = removeNextPrevNotSeen(nextCol, prevList);
			nextCol.remove(end);

			prevList.addAll(nextCol);
			if (nextCol.size() != 0) {
				full.add(new ArrayList<GLACompone>(nextCol));
			}

		}

		for (int i = 0; i < full.size(); i++) {
			for (int j = 0; j < full.get(i).size(); j++) {
				for (int k = 0; k < full.get(i).get(j).getRight(); k++) {
					full.get(i).get(j).setNextDummy(null, k);
				}
			}
		}

		addId(full);
		return full;
	}

	private ArrayList<GLACompone> array2List(GLACompone[] next, int size) {
		ArrayList<GLACompone> arrayList = new ArrayList<GLACompone>();
		for (int i = 0; i < size; i++) {
			arrayList.add(next[i]);
		}
		return arrayList;
	}

	private ArrayList<GLACompone> removeDuplicates(ArrayList<GLACompone> potCol) {
		ArrayList<GLACompone> noDup = new ArrayList<GLACompone>();

		for (int i = 0; i < potCol.size(); i++) {
			if (!in(potCol.get(i), noDup)) {
				noDup.add(potCol.get(i));
			}

		}
		return noDup;
	}

	// adds identities
	private ArrayList<ArrayList<GLACompone>> addId(ArrayList<ArrayList<GLACompone>> full) {

		ArrayList<ArrayList<Integer>> leftRight = new ArrayList<ArrayList<Integer>>();
		int l;
		int r;

		// for each coloumn
		for (int i = 0; i < full.size(); i++) {
			// leftRight = array of inputs/outputs of each column
			leftRight.add(new ArrayList<Integer>());
			leftRight.get(i).add(0);
			leftRight.get(i).add(0);

			for (int j = 0; j < full.get(i).size(); j++) {

				l = leftRight.get(i).get(0);
				r = leftRight.get(i).get(1);
				leftRight.get(i).set(0, l + full.get(i).get(j).getLeft());
				leftRight.get(i).set(1, r + full.get(i).get(j).getRight());
			}
		}

		int x, index;
		x = notNullSize(start) - leftRight.get(0).get(0);
		for (int i = 0; i < x; i++) {

			// index = getNextNotSeen(start.getNext(), i, full);
			index = getNextStart(start.getNext(), i, full.get(0));

			GLACompone[] next = { start.getNext()[index] };
			int[] nextLink = { start.getNextLink()[index] };
			full.get(0).add(new GLACompone(id, 1, 1, next, null, nextLink, null, idIm, "id"));
			id++;
		}

		int value0 = leftRight.get(0).get(0);
		int value1 = leftRight.get(0).get(1);
		value0 = value0 + x;
		value1 = value1 + x;

		leftRight.get(0).set(0, value0);
		leftRight.get(0).set(1, value1);

		for (int i = 1; i < leftRight.size(); i++) {
			x = leftRight.get(i - 1).get(1) - leftRight.get(i).get(0);
			System.out.println("x: " + x);
			ArrayList<nextLinkTup> nextTupArr = getNextArray(full.get(i - 1));
			nextLinkTup tup;

			// for every identity needed to be added
			for (int j = 0; j < x; j++) {

				tup = getIndex(nextTupArr, full.get(i), j);
				GLACompone[] next = { tup.getNext() };
				int[] nextLink = { tup.getLink() };

				GLACompone nextId = new GLACompone(id, 1, 1, next, null, nextLink, null, idIm, "id");
				id++;

				full.get(i).add(nextId);

				// set dummy to be null
				//////////////////////////////
				// but somewhere else so that all nodes are affected
				////////////////////////////////

				if (tup.getNode().getType().equals("id")) {
					tup.getNode().setNext(nextId, 0);
					tup.getNode().setNextLink(nextLink);
				} else {
					tup.getNode().setNextDummy(nextId, 0);// which position -> tup.getK()
															// tup.getNode().setNextDummy(nextId, tup.getK());
					tup.getNode().setNextLinkDummy(nextLink); // tup.getNode().setNextLinkDummy(nextLink[0],
																// tup.getK());
				}

			}

			value0 = leftRight.get(i).get(0);
			value1 = leftRight.get(i).get(1);
			value0 = value0 + x;
			value1 = value1 + x;

			leftRight.get(i).set(0, value0);
			leftRight.get(i).set(1, value1);

			// leftRight.get(i).set(0, +x);
			// leftRight.get(i).set(1, +x);
		}
		System.out.println(full);
		return full;
	}

	private nextLinkTup getIndex(ArrayList<nextLinkTup> nextTupArr, ArrayList<GLACompone> arrayList, int j) {
		int count = 0;

		for (int i = 0; i < nextTupArr.size(); i++) {
			if (!in(nextTupArr.get(i).getNext(), arrayList)) {
				if (count == j) {
					return nextTupArr.get(i);
				}
				count++;
			}
		}
		return null;
	}

	private ArrayList<nextLinkTup> getNextArray(ArrayList<GLACompone> coolumn) {
		ArrayList<nextLinkTup> nextTupArr = new ArrayList<nextLinkTup>();

		for (int j = 0; j < coolumn.size(); j++) {
			for (int k = 0; k < coolumn.get(j).getRight(); k++) {
				nextLinkTup nextTup = new nextLinkTup(coolumn.get(j), coolumn.get(j).getNext()[k],
						coolumn.get(j).getNextLink()[k]);
				nextTupArr.add(nextTup);
			}
		}
		return nextTupArr;
	}

	private int getNextStart(GLACompone[] next, int idNo, ArrayList<GLACompone> currCol) {
		int count = 0;
		for (int i = 0; i < start.getRight(); i++) {
			// skips if next is null
			if (next[i] != null) {
				// skips if node is already accounted for in currCol
				if (!in(next[i], currCol)) {
					if (count == idNo) {
						return i;
					}
					count++;
				}
			}
		}
		return 0;
	}

	private Integer notNullSize(GLACompone node) {
		int count = 0;
		for (int i = 0; i < node.getRight(); i++) {
			if (node.getNext()[i] != null) {
				count++;
			}
		}
		return count;
	}

	private boolean inALAL(GLACompone node, ArrayList<ArrayList<GLACompone>> full) {
		for (int i = 0; i < full.size(); i++) {
			for (int j = 0; j < full.get(i).size(); i++) {
				if (node == full.get(i).get(j)) {
					return true;
				}
			}

		}
		return false;
	}

	private ArrayList<GLACompone> getNextList(GLACompone comp) {
		ArrayList<GLACompone> nextList = new ArrayList<GLACompone>();
		for (int i = 0; i < comp.getRight(); i++) {
			nextList.add(comp.getNext()[i]);
		}
		return nextList;
	}

	public ArrayList<GLACompone> removeNextPrevNotSeen(ArrayList<GLACompone> GLAList, ArrayList<GLACompone> prevList) {
		Boolean notIn;
		ArrayList<GLACompone> newCol = new ArrayList<GLACompone>();

		// for each new node
		for (int i = 0; i < GLAList.size(); i++) {
			notIn = false;
			// for each prev node
			for (int j = 0; j < GLAList.get(i).getLeft(); j++) {

				// check if prev is already seen (ie notIn = true)
				if (!in(GLAList.get(i).getPrev()[j], prevList)) { // or prev prevlist etc
					notIn = true;
				}
			}
			// if not seen previously, do nothing
			if (notIn) {

				// else add it to the list
			} else {
				newCol.add(GLAList.get(i));
			}

		}

		return newCol;
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

	public void createTest(int x) {

		GLACompone[] next = new GLACompone[x+1];
		GLACompone[] prev = new GLACompone[1];
		int[] nextLink = new int[x+1];
		int[] prevLink = new int[1];
		
		start = new GLACompone(-1, 0, x+1, next, null, nextLink, null, null, "strt");
		end = new GLACompone(-2, 1, 0, null, prev, null, prevLink, null, "end");
		
		
		GLACompone[] nextComp = new GLACompone[1];
		GLACompone[] prevComp = new GLACompone[2];
		int[] nextLinkComp = new int[1];
		int[] prevLinkComp = new int[2];
		
		GLACompone node0 = new GLACompone(0, 2, 1, nextComp, prevComp, nextLinkComp, prevLinkComp, null, "add");
		start.setNext(node0, 0);
		start.setNextLink(0, 0);
		start.setNext(node0, 1);
		start.setNextLink(1, 1);
		
		node0.setPrev(start, 0);
		node0.setPrevLink(0, 0);
		node0.setPrev(start, 1);
		node0.setPrevLink(1, 1);
		
		GLACompone prevNode = node0; 
		
		for(int i = 1; i < x; i++) {
			GLACompone[] nexti = new GLACompone[1];
			GLACompone[] previ = new GLACompone[2];
			int[] nextLinki = new int[1];
			int[] prevLinki = new int[2];
			
			GLACompone nodei = new GLACompone(0, 2, 1, nexti, previ, nextLinki, prevLinki, null, "add");
			
			prevNode.setNext(nodei, 0);
			prevNode.setNextLink(0, 0);
			nodei.setPrev(prevNode, 0);
			nodei.setPrevLink(0, 0);
			
			start.addNext(nodei, i+1);
			start.setNextLink(1, i+1);
			nodei.setPrev(start, 1);
			nodei.setPrevLink(i+1, 1);
			
			prevNode = nodei;
		}
		prevNode.setNext(end, 0);
		prevNode.setNextLink(0, 0);
		end.setPrev(prevNode, 0);
		end.setPrevLink(0, 0);
	}

}
