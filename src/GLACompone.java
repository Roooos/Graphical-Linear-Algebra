import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;

/**
 * ResultItem, class for the items displayed in the result panel
 */
public class GLACompone implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7059921764387944857L;
	/* Basic info to display */
	private int id;
	private int left, right;
	private GLACompone[] next, prev;
	private int[] nextLink, prevLink;
	private ImageIcon image;
	private String type;
	
	private GLACompone[] dummyNext;
	private int[] dummyNextLink;
	

	//
	/**
	 * Default constructor sets all variables to null!
	 */
	public GLACompone() {
		id = 0;
		left = 0;
		right = 0;
		next = null;
		prev = null;
		image = null;

	}

	/** Another Constructor **/
	public GLACompone(int id, int left, int right, GLACompone[] next, GLACompone[] prev, int[] nextLink, int[] prevLink,
			ImageIcon image, String type) {
		this.id = id;
		this.left = left;
		this.right = right;

		this.next = next;
		this.prev = prev;
		this.nextLink = nextLink;
		this.prevLink = prevLink;

		this.image = image;
		
		this.type = type;
		
		dummyNext = new GLACompone[right];
		dummyNextLink = new int[right];
		
	}

	public void addNext(GLACompone comp, int i, int j) {
		next[i] = comp;
		comp.addPrev(this, j);
	}

	public void addNext(GLACompone comp, int i) {
		next[i] = comp;
	}

	public void addPrev(GLACompone comp, int i, int j) {
		prev[i] = comp;
		comp.addNext(this, j);
	}

	public void addPrev(GLACompone comp, int i) {
		prev[i] = comp;
	}

	public void removeNext(GLACompone comp) {
		for (int i = 0; i < right; i++) {
			if (next[i].getId() == comp.id) {
				next[i] = null;
			}
		}
	}

	// goes through all prev nodes and removes those with same id
	// does not remove specific node
	public void removePrev(GLACompone comp) {
		for (int i = 0; i < left; i++) {
			if (prev[i].getId() == comp.id) {
				prev[i] = null;
			}
		}
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}

	public void setNext(GLACompone[] next) {
		this.next = next;
	}
	
	public void setNextDummy(GLACompone[] dummyNext) {
		this.dummyNext = dummyNext;
	}

	public void setNext(GLACompone next, int i) {
		this.next[i] = next;
	}
	
	public void setNextDummy(GLACompone dummyNext, int i) {
		this.dummyNext[i] = dummyNext;
	}

	public void setPrev(GLACompone[] prev) {
		this.prev = prev;
	}

	public void setPrev(GLACompone prev, int i) {
		this.prev[i] = prev;
	}

	public void setNextLink(int[] nextLink) {
		this.nextLink = nextLink;
	}
	
	public void setNextLinkDummy(int[] dummyLink) {
		this.dummyNextLink = dummyLink;
	}

	public void setNextLink(int link, int i) {
		this.nextLink[i] = link;
	}
	
	public void setNextLinkDummy(int dummyLink, int i) {
		this.dummyNextLink[i] = dummyLink;
	}

	public void setPrevLink(int[] prevLink) {
		this.prevLink = prevLink;
	}

	public void setPrevLink(int link, int i) {
		this.prevLink[i] = link;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public int getLeft() {
		return left;
	}

	public int getRight() {
		return right;
	}

	public GLACompone[] getNext() {
		return next;
	}
	
	public GLACompone[] getNextDummy() {
		return dummyNext;
	}
	
	public ArrayList<GLACompone> getNextL() {
		return (ArrayList<GLACompone>) Arrays.asList(next);
	}

	public GLACompone[] getPrev() {
		return prev;
	}

	public int[] getNextLink() {
		return nextLink;
	}
	
	public int[] getNextLinkDummy() {
		return dummyNextLink;
	}

	public int[] getPrevLink() {
		return prevLink;
	}

	public ImageIcon getIm() {
		return image;
	}

	public String getType() {
		return type;
	}
	
}