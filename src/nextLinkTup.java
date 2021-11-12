import java.io.Serializable;

public class nextLinkTup implements Serializable {
	
	private static final long serialVersionUID = -255737140666123928L;

	private GLACompone node;
	private GLACompone next;
	private int link;

	public nextLinkTup() {
		node = null;
		next = null;
		link = -1;
	}

	/** Another Constructor **/
	public nextLinkTup(GLACompone node, GLACompone next, int link) {
		this.node = node;
		this.next = next;
		this.link = link;
	}
	
	public GLACompone getNode() {
		return node;
	}
	
	public GLACompone getNext() {
		return next;
	}
	
	public int getLink() {
		return link;
	}
	
	public void setNode(GLACompone node) {
		this.node = node;
	}
	
	public void setNext(GLACompone next) {
		this.next = next;
	}
	
	public void setLink(int link) {
		this.link = link;
	}
}