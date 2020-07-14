
package labproject;
import javax.swing.JFrame;
import java.awt.*;
/**
 *
 * @author mddilshad
 */
public class FormFlow{
    static JFrame x;
    static JFrame defaultPage;
    static void runForm(JFrame x){
        defaultPage = x;
        defaultPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        defaultPage.setVisible(true);
    }
    
    static void switchForward(JFrame z){
        defaultPage.setVisible(false);
        x = z;
        z.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        z.setVisible(true);
    }
    
    static void switchBackward(){
        if(x == null) return;
        x.setVisible(false);
        x.dispose();
        runForm(defaultPage);
    }
    //void 
}
