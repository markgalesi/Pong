import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.MouseInfo;

import javax.swing.ImageIcon;
import java.util.Arrays;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener
{
    private int width = 710;
    private int height = 600;
    private int half = 355;
    private int quarter = 177;
    private int quarterThree = 533;
    private int delay = 15;
    private int portalDelayLeft = 5;
    private int portalDelayRight = -5;
    private int paddleLocation = width/2;
    private int paddleLocation2 = width/2;
    private int portalLocationLeft = 0;
    private int portalLocationRight = height - 120;
    private int ballX = 80;
    private int ballY = 80;
    private int slopeX = 10;
    private int slopeY = 10;
    private int bottomScore = 0;
    private int topScore = 0;
    private int random = 0;
    private int mouseX;
    private int pause = 1;
    private int count = 0;

    private boolean inGame = true;
    private boolean left1 = false;
    private boolean right1 = true;
    private boolean left2 = false;
    private boolean right2 = true;

    private Timer timer;
    private Timer portalTimer;
    private Image ball;
    private Image paddle;
    private Image paddle2;
    private Image portalRight;
    private Image portalLeft;
    private Image line;


    public Board()
    {
        addKeyListener(new Player1Adapter());
        setBackground(Color.white);
        setFocusable(true);

        setPreferredSize(new Dimension(width, height));
        loadImages();
        initGame();
    }

    private void loadImages()
    {

        ImageIcon icoBall = new ImageIcon("ball.png");
        ball = icoBall.getImage();

        ImageIcon icoPaddle = new ImageIcon("paddle.png");
        paddle = icoPaddle.getImage();

        ImageIcon icoPaddle2 = new ImageIcon("paddle.png");
        paddle2 = icoPaddle2.getImage();

        ImageIcon icoLine = new ImageIcon("Line.png");
        line = icoLine.getImage();

        ImageIcon icoPortalRight = new ImageIcon("portalRight.png");
       	portalRight = icoPortalRight.getImage();

        ImageIcon icoPortalLeft = new ImageIcon("portalLeft.png");
        portalLeft = icoPortalLeft.getImage();
    }

    private void initGame()
    {
        hideMouse();
        timer = new Timer(delay, this);
        timer.start();
    	ballX = width/2;
    	ballY = height/2;
        random = (int) (Math.random()* 4) + 1;
        if(random == 1)
        {
    		slopeX = 10;
        	slopeY = 10;
		}
		else if(random == 2)
		{
			slopeX = -10;
			slopeY = 10;
		}
		else if(random == 3)
		{
			slopeX = 10;
			slopeY = -10;
		}
		else
		{
			slopeX = -10;
			slopeY = -10;
		}
    }

    private void reinitGame()
    {

        hideMouse();
        timer.start();
    	portalDelayLeft = 5;
    	portalDelayRight = -5;
    	paddleLocation = width/2;
    	paddleLocation2 = width/2;
    	portalLocationLeft = 0;
    	portalLocationRight = height - 120;
    	ballX = width/2;
    	ballY = height/2;
        random = (int) (Math.random()* 4) + 1;
        if(random == 1)
        {
    		slopeX = 10;
        	slopeY = 10;
		}
		else if(random == 2)
		{
			slopeX = -10;
			slopeY = 10;
		}
		else if(random == 3)
		{
			slopeX = 10;
			slopeY = -10;
		}
		else
		{
			slopeX = -10;
			slopeY = -10;
		}

		if(topScore >= 7)
		{
			delay = 17;
			portalDelayLeft = 3;
			portalDelayRight = -3;
		}
		if(bottomScore >= 7)
		{
			delay = 17;
			portalDelayLeft = 3;
			portalDelayRight = -3;
		}
    }

    private void hideMouse()
    {
	        ImageIcon emptyIcon = new ImageIcon(new byte[0]);
	        Cursor invisibleCursor = getToolkit().createCustomCursor(
	        emptyIcon.getImage(), new Point(0,0), "Invisible");
	        this.setCursor(invisibleCursor);
    }


    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g)
    {

        if(inGame)
        {
              g.drawImage(line, 0, height/2, this);
              g.drawImage(paddle, paddleLocation, 580, this);
              g.drawImage(paddle2, paddleLocation2, 20, this);
              g.drawImage(portalRight, width - 15, portalLocationRight, this);
              g.drawImage(portalLeft, 0, portalLocationLeft, this);
              g.drawImage(ball, ballX, ballY, this);
              checkCollision();
              ballMove();
              paddleMove();
			  stats(g);
        }
        else
        {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g)
    {
        setBackground(Color.white);
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.black);
        g.setFont(small);
        g.drawString(msg, (width - metr.stringWidth(msg)) / 2, height / 2);
    }

    private void stats(Graphics g)
    {
        String msg = "Score:" + topScore + "||" + count;
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.black);
        g.setFont(small);
        g.drawString(msg, (width - metr.stringWidth(msg)) / 2, height / 2);

        String msg2 = "Score:" + bottomScore;
        g.setColor(Color.black);
        g.setFont(small);
    	g.drawString(msg2, (width - metr.stringWidth(msg2)) / 2,320);

    }


    private void ballMove()
    {
		ballX = ballX + slopeX;
		ballY = ballY + slopeY;
		portalLocationLeft = portalLocationLeft + portalDelayLeft;
		portalLocationRight = portalLocationRight + portalDelayRight;
    }
    private void paddleMove()
    {

	        paddleLocation = (MouseInfo.getPointerInfo().getLocation().x) - 630;

            if(paddleLocation < 0)
            {
				paddleLocation = 0;
			}

            if(paddleLocation > width - 120)
            {
				paddleLocation = width - 120;
			}
            if(paddleLocation2 < 0)
            {
				left2 = false;
				right2 = true;
			}
            if(paddleLocation2 > width - 120)
            {
				left2 = true;
				right2 = false;
			}

		//AI
		//|_|___
		if((ballX > 0) && (ballX < quarter) && (slopeX > 0))
		{
			if((paddleLocation2 < ballX) && (paddleLocation2 + 120 > ballX) && (ballY < 35))
			{
				right2 = false;
				left2 = false;
			}
			if(paddleLocation2 > quarter)
			{
				right2 = false;
				left2 = true;
			}
		}
		if((ballX > 0) && (ballX < quarter) && (slopeX < 0))
		{
			if((paddleLocation2 < ballX) && (paddleLocation2 + 120 > ballX) && (ballY < 60))
			{
				right2 = false;
				left2 = false;
			}
			if(paddleLocation2 > quarter)
			{
				right2 = false;
				left2 = true;
			}
		}
		//_|_|__
		if((ballX > quarter) && (ballX < half) && (slopeX > 0))
		{
			if((paddleLocation2 < ballX) && (paddleLocation2 + 120 > ballX) && (ballY < 60))
			{
				right2 = false;
				left2 = false;
			}
			if(paddleLocation2 < quarter)
			{
				right2 = true;
				left2 = false;
			}
			if(paddleLocation2 > half)
			{
				right2 = false;
				left2 = true;
			}
		}
		if((ballX > quarter) && (ballX < half) && (slopeX < 0))
		{
			if((paddleLocation2 < ballX) && (paddleLocation2 + 120 > ballX) && (ballY < 60))
			{
				right2 = false;
				left2 = false;
			}
			if(paddleLocation2 < quarter)
			{
				right2 = true;
				left2 = false;
			}
			if(paddleLocation2 > half)
			{
				right2 = false;
				left2 = true;
			}
		}
		//__|_|_
		if((ballX > half) && (ballX < quarterThree) && (slopeX > 0))
		{
			if((paddleLocation2 < ballX) && (paddleLocation2 + 120 > ballX) && (ballY < 60))
			{
				right2 = false;
				left2 = false;
			}
			if(paddleLocation2 < half)
			{
				right2 = true;
				left2 = false;
			}
			if(paddleLocation2 > quarterThree)
			{
				right2 = false;
				left2 = true;
			}
		}
		if((ballX > half) && (ballX < quarterThree) && (slopeX < 0))
		{
			if((paddleLocation2 < ballX) && (paddleLocation2 + 120 > ballX) && (ballY < 60))
			{
				right2 = false;
				left2 = false;
			}
			if(paddleLocation2 < half)
			{
				right2 = true;
				left2 = false;
			}
			if(paddleLocation2 > quarterThree)
			{
				right2 = false;
				left2 = true;
			}
		}
		//___|_|
		if((ballX > quarterThree) && (ballX < width) && (slopeX > 0))
		{
			if((paddleLocation2 < ballX) && (paddleLocation2 + 120 > ballX) && (ballY < 60))
			{
				right2 = false;
				left2 = false;
			}
			if(paddleLocation2 < quarterThree)
			{
				right2 = true;
				left2 = false;
			}
		}
		if((ballX > quarterThree) && (ballX < width) && (slopeX < 0))
		{
			if((paddleLocation2 < ballX) && (paddleLocation2 + 120 > ballX) && (ballY < 60))
			{
				right2 = false;
				left2 = false;
			}
			if(paddleLocation2 < quarterThree)
			{
				right2 = true;
				left2 = false;
			}
		}


		if(right1 == true)
			paddleLocation = paddleLocation + 30;

		if(left1 == true)
			paddleLocation = paddleLocation - 30;

		if(right2 == true)
			paddleLocation2 = paddleLocation2 + 30;

		if(left2 == true)
			paddleLocation2 = paddleLocation2 - 30;

			//paddleLocation2 = ballX - 50;
    }

    private void checkCollision()
    {

		//paddle1 |_|__
		/*if((ballX + 15 >= paddleLocation) && (ballX <= paddleLocation + 120) && (ballX <= paddleLocation + 40) && (ballY >= 558))
		{
			slopeY = (slopeY/-1) - 3;
			slopeX = (slopeX/-1) - 3;
			delay = delay - 5;
		}
		//paddle1 __|_|
		if((ballX >= paddleLocation) && (ballX + 15 <= paddleLocation + 120) && (ballX >= paddleLocation + 80)  && (ballX <= paddleLocation + 120) && (ballY >= 558))
		{
			slopeY = (slopeY/-1) - 3;
			slopeX = (slopeX/-1) - 3;
			delay = delay - 5;
		}
		//paddle1 _|_|_
		if((ballX >= paddleLocation) && (ballX + 15 <= paddleLocation + 120) && (ballX >= paddleLocation + 40) && (ballX <= paddleLocation + 80) && (ballY >= 558))
		{
			slopeY = (slopeY/-1) + 3;
			delay = delay + 5;
		}*/
		if((ballX + 15 >= paddleLocation) && (ballX <= paddleLocation + 120) && (ballY >= 558))
		{
			slopeY = (slopeY/-1) + 3;
		}
		if((ballX + 15 >= paddleLocation2) && (ballX <= paddleLocation2 + 120) && (ballY <= 35))
		{
			slopeY = (slopeY/-1) + 3;
		}

		//portals
		if((ballY + 15 > portalLocationLeft) && (ballY < portalLocationLeft + 120) && (ballX < 35))
		{
			ballX = 675;
			ballY = portalLocationRight + 60;
		}
		if((ballY + 15 > portalLocationRight) && (ballY < portalLocationRight + 120) && (ballX > 675))
		{
			ballX = 35;
			ballY = portalLocationLeft + 60;
		}


            if(portalLocationLeft > height - 120)
            {
				portalDelayLeft = portalDelayLeft/-1;
			}
            if(portalLocationLeft < 0)
            {
				portalDelayLeft = portalDelayLeft/-1;
			}
            if(portalLocationRight > height - 120)
            {
				portalDelayRight = portalDelayRight/-1;
			}
            if(portalLocationRight < 0)
            {
				portalDelayRight = portalDelayRight/-1;
			}

		if(ballX + 15 > width)
			slopeX = slopeX/-1;
		if(ballX < 0)
			slopeX = slopeX/-1;
		if(ballY < 0)
		{
			loadImages();
       		reinitGame();
       		inGame = true;
			bottomScore++;
		}
		if(ballY+ 15 > height)
		{
			loadImages();
       		reinitGame();
       		inGame = true;
			topScore++;
		}

    }


    public void actionPerformed(ActionEvent e)
    {
        repaint();
        count++;
    }

    private class Player1Adapter extends KeyAdapter
    {
        public void keyPressed(KeyEvent e)
        {

            int key = e.getKeyCode();
			//player1(bottom)
            if((key == KeyEvent.VK_LEFT) && (paddleLocation > 0))
            {
				//paddleLocation = paddleLocation - 50;
				left1 = true;
				right1 = false;
            }

            if((key == KeyEvent.VK_RIGHT) && (paddleLocation < width - 120))
            {
				//paddleLocation = paddleLocation + 50;
				right1 = true;
				left1 = false;
            }

            if((key == KeyEvent.VK_DOWN))
            {
				//paddleLocation = paddleLocation + 50;
				right1 = false;
				left1 = false;
            }
            //player2(top)
            if((key == KeyEvent.VK_A) && (paddleLocation2 > 0))
            {
				//paddleLocation2 = paddleLocation2 - 50;
				left2 = true;
				right2 = false;
            }

            if((key == KeyEvent.VK_D) && (paddleLocation2 < width - 120))
            {
				//paddleLocation2 = paddleLocation2 + 50;
				right2 = true;
				left2 = false;
            }

            if((key == KeyEvent.VK_S))
            {
				//paddleLocation = paddleLocation + 50;
				right2 = false;
				left2 = false;
            }

           	if ((key == KeyEvent.VK_ENTER))
            {
					 loadImages();
       				 reinitGame();
       				 inGame = true;
            }
          	if ((key == KeyEvent.VK_ESCAPE))
          	{
					 System.exit(0);
          	}
          	if ((key == KeyEvent.VK_P))
          	{
					 pause++;
          	}
          	if ((key == KeyEvent.VK_O))
          	{
					 //timer.start();
          	}
          	if ((key == KeyEvent.VK_R))
          	{
					 topScore = bottomScore = 0;
          	}
			if(pause % 2 == 0)
			{
				timer.stop();
			}
			if(pause % 2 != 0)
			{
				timer.start();
			}
		}
    }
}