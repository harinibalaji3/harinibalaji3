import java.awt.*;
import java.util.ArrayList;
import java.util.RandomAccess;
import java.awt.Event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;


public class Snakes extends JPanel implements ActionListener, KeyListener{
    private JButton resetButton;
    private int highScore = 0; 
    private class tile{
    int x=5;
    int y=5;
    tile(int x,int y)
       {
        this.x=x;
        this.y=y;
       }
    }
    int boardwidth;
    int boardheight;
    int tilesize=25;
    tile snakehead;
    ArrayList<tile> snakebody;
    tile food;
    Random rand;
    Timer gameLoop;
    int velocityx;
    int velocityy;
    boolean gameover;
    Snakes(int boardwidth,int boardheight)
    {
        this.boardwidth=boardwidth;
        this.boardheight=boardheight;
        setPreferredSize(new Dimension(this.boardwidth,this.boardheight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakehead=new tile(5,5);
        snakebody=new ArrayList<tile>();

        food=new tile(10,10);

        rand=new Random();
        placefood();

        gameLoop = new Timer(100,this);
        gameLoop.start();

        velocityx=0;
        velocityy=0;

         // Initialize reset button
         resetButton = new JButton("Reset");
         resetButton.setBounds(boardwidth / 2 - 50, boardheight - 50, 100, 30);
         resetButton.addActionListener(e -> resetGame());
         this.setLayout(null);
         this.add(resetButton);

    }
    private void resetGame() 
    {
        gameLoop.stop();
        snakehead = new tile(5, 5);
        snakebody.clear();
        velocityx = 0;
        velocityy = 0;
        gameover = false;
        placefood();
        gameLoop.start();
        repaint();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g); 
        draw(g);
    }
    public void draw(Graphics g)
    {
        //grid
        for(int i=0;i<boardwidth/tilesize;i++){
        //x1,y1,x2,y2
        g.drawLine(i*tilesize,0,i*tilesize,boardheight);
        g.drawLine(0, i*tilesize, boardwidth,i*tilesize);
        }
        //snakehead
        g.setColor(Color.green);
        g.fillRect(snakehead.x*tilesize,snakehead.y*tilesize,tilesize,tilesize);

        //snakebody
        for(int i=0;i<snakebody.size();i++)
        {
            tile snakepart=snakebody.get(i);
            g.fillRect(snakepart.x*tilesize,snakepart.y*tilesize , tilesize, tilesize);
        }
        //food
        g.setColor(Color.red);
        g.fillRect(food.x*tilesize,food.y*tilesize,tilesize,tilesize);

        //score
        g.setFont(new Font("Arial",Font.PLAIN,16));
        if(gameover){
            g.setColor(Color.red);
            g.drawString("game over:"+String.valueOf(snakebody.size()),tilesize-16,tilesize);
        }
        else{
            g.drawString("score:"+String.valueOf(snakebody.size()),tilesize-16,tilesize);
  
        }
        if (snakebody.size() > highScore)
        {
            highScore = snakebody.size();
        }
        g.setColor(Color.white);
        g.drawString("High Score: " + highScore, boardwidth - 120, tilesize);
    }
    public void placefood()
    {
        food.x=rand.nextInt(boardwidth/tilesize);//random for 600/25=24
        food.y=rand.nextInt(boardheight/tilesize);
    }
    public boolean collision(tile tile1,tile tile2){
        return tile1.x==tile2.x&&tile1.y==tile2.y;
    }
    public void move()
    {
        //eat food
        if(collision((snakehead), food))
        {
            snakebody.add(new tile(food.x, food.y));
            placefood(); 
        }
        if (snakebody.size() > highScore) {
            highScore = snakebody.size();
        }
        //snakehead

        for(int i=snakebody.size()-1;i>=0;i--){
           tile snakepart=snakebody.get(i);
           if(i==0){
            snakepart.x=snakehead.x;
            snakepart.y=snakehead.y;
           }
           else{
            tile prevsnakepart=snakebody.get(i-1);
            snakepart.x=prevsnakepart.x;
            snakepart.y=prevsnakepart.y;
           } 
        }
        snakehead.x +=velocityx;
        snakehead.y +=velocityy;

        //gameover condition
        for(int i=0;i<snakebody.size();i++){
            tile snakepart=snakebody.get(i);
            //collide with snakehead
            if(collision(snakehead, snakepart))
            {
                gameover=true;
            }
        }
        if(snakehead.x*tilesize<0 || snakehead.x*tilesize > boardwidth||
        snakehead.y*tilesize<0 || snakehead.y*tilesize > boardheight ){
            gameover=true;
        }
        
    }
    


    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(!gameover)
        {
            move();
            repaint();
        }
    }
   
       
        
        @Override
        public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP)
         {
            velocityx=0;
            velocityy=-1;
         }
         else if(e.getKeyCode() == KeyEvent.VK_DOWN)
         {
            
                velocityx=0;
                velocityy=1;
             
         }
         else if(e.getKeyCode() == KeyEvent.VK_LEFT)
         {
            
                velocityx=-1;
                velocityy=0;
             
         }
         else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
         {
            
                velocityx=1;
                velocityy=0;
         }
        }
        @Override
        public void keyTyped(KeyEvent e) {}
        @Override
        public void keyReleased(KeyEvent e) {}
}
