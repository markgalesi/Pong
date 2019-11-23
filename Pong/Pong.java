import java.awt.EventQueue;
import javax.swing.JFrame;


public class Pong extends JFrame
{
    public Pong()
    {
		Board b = new Board();
        add(new Board());
        setResizable(false);
        pack();
        setTitle("Pong");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                JFrame ex = new Pong();
                ex.setVisible(true);
            }
        });
    }
}