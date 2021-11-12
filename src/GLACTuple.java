import javax.swing.ImageIcon;

public class GLACTuple {
	private GLACompone node;
	private int eqNo;

	

	/** Another Constructor **/
	public GLACTuple(GLACompone node, int eqNo) {
		this.node = node;
		this.eqNo = eqNo;
	}
	
	public void setNode(GLACompone node) {
		this.node = node;
	}
	
	public void SetEqno(int eqNo) {
		this.eqNo = eqNo;
	}
	
	public GLACompone getNode() {
		return node;
	}
	
	public int getEqNo() {
		return eqNo;
	}
	
}
