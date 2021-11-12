import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GLAMain extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7658448251522366345L;
	private GLAView ssmv;
 
    public GLAMain(){

        
        ActionListener forMain = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
            	ssmv.setVisible(false);
            }
       };

       ssmv = new GLAView(forMain, "local");

       ssmv.setVisible(true);
    }

    public static void main(String[] args) {
        new GLAMain();
    }
}
 