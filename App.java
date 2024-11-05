import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
      int boardwidth=600;
      int boardheight=boardwidth;
        JFrame f=new JFrame("Snake");
      f.setVisible(true);
      f.setSize(boardwidth,boardheight);
      f.setLocationRelativeTo(null);
      f.setFocusable(true);
      f.setResizable(false);
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Snakes s=new Snakes(boardheight,boardheight);
      f.add(s);
      f.pack();
      s.requestFocus();
      
    }
}
