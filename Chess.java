import javax.swing.*;
import java.awt.*;
import java.lang.Math.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.TexturePaint;

class Site extends Rectangle2D.Double
{
   Board b;
   boolean Empty; 
   
   Site(Board b, double x, double y)       
   {                  
      this.x=x;       
      this.y=y;     
      this.width=100;  
      this.height=100; 
	  this.Empty=true;
   }     
   
   
}

class Board extends JPanel implements MouseListener
{	
	Site[][] Sites=new Site[8][8];
	Pawn[] WPawns = new Pawn[8];
	TexturePaint[] WPawnsT = new TexturePaint[8];
	Pawn[] BPawns = new Pawn[8];
	TexturePaint[] BPawnsT = new TexturePaint[8];
	
	String bpawn="BPawn.png";
	String wpawn="WPawn.png";
	BufferedImage BPawn;
	BufferedImage WPawn;

	Board()
	{
		addMouseListener(this);  
		for(int j=0; j<8; j++)
		{
			WPawns[j]=new Pawn(25+j*100, 625, this);
		}
		
		for(int j=0; j<8; j++)
		{
			BPawns[j]=new Pawn(25+j*100, 125, this);
		}
		
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				Sites[i][j]=new Site(this, j*100, i*100);
			}
		}
		
		try
		{
			File bp=new File(bpawn);
			BPawn=ImageIO.read(bp);
		}
		catch(IOException e)
		{
			System.err.println("Problem z plikiem");
		}
		
		try
		{
			File wp=new File(wpawn);
			WPawn=ImageIO.read(wp);
		}
		catch(IOException e)
		{
			System.err.println("Problem z plikiem");
		}
	}
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D)g; 
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				if(i%2==0 && j%2==0 || i%2!=0 && j%2!=0)
				{
					g2d.setPaint(new Color(102, 178, 255));
					g2d.fill(Sites[i][j]);
				}else{
					g2d.setPaint(new Color(225, 225, 225));
					g2d.fill(Sites[i][j]);
				}
			}
		}
		for(int j=0; j<8; j++)
		{
			WPawnsT[j]=new TexturePaint(WPawn, WPawns[j]);
			g2d.setPaint(WPawnsT[j]);
			g2d.fill(WPawns[j]);
		}
		
		for(int j=0; j<8; j++)
		{
			BPawnsT[j]=new TexturePaint(BPawn, BPawns[j]);
			g2d.setPaint(BPawnsT[j]);
			g2d.fill(BPawns[j]);
		}

	}
	
	public void mouseEntered(MouseEvent e){


	}
	
	public void mouseExited(MouseEvent e){


	}
	
	public void mouseClicked(MouseEvent e){


	}
	
	public void mousePressed(MouseEvent e){
		
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				if(Sites[i][j].contains(e.getPoint()))
				{
					for(int k=0; k<8; k++)
					{
						if(Sites[i][j].contains(BPawns[k]))
						{
							BPawns[k].onmove=true;
						}else if(Sites[i][j].contains(WPawns[k]))
						{
							WPawns[k].onmove=true;
						}
					}
				}
			}
			
		}
		repaint();	
	}
	
	public void mouseDragged(MouseEvent ee){
		
		
		repaint();	
	}
	
	public void mouseReleased(MouseEvent eee) {
            
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				if(Sites[i][j].contains(eee.getPoint()))
				{
					for(int k=0; k<8; k++)
					{
						if(Sites[i][j].contains(WPawns[k]) || Sites[i][j].contains(BPawns[k]))
						{
							Sites[i][j].Empty=false;
						}
							
						if(Sites[i][j].Empty && BPawns[k].onmove==true && Sites[i][j].y-BPawns[k].y==75 && BPawns[k].x-Sites[i][j].x<100 && BPawns[k].x-Sites[i][j].x+25>0 && BPawns[k].y<900)
						{
							BPawns[k].Move(100, 0);
							BPawns[k].onmove=false;
							
						}else if(Sites[i][j].Empty && BPawns[k].moves==0 && BPawns[k].onmove==true && Sites[i][j].y-BPawns[k].y==175 && BPawns[k].x-Sites[i][j].x<100 && BPawns[k].x-Sites[i][j].x+25>0 && BPawns[k].y<900)
						{
							BPawns[k].Move(200, 0);
							BPawns[k].onmove=false;
						}
						
						if(Sites[i][j].Empty && WPawns[k].onmove==true && WPawns[k].y-Sites[i][j].y==125 && WPawns[k].x-Sites[i][j].x+25>0 && WPawns[k].y>25)
						{
							WPawns[k].Move(-100, 0);
							WPawns[k].onmove=false;
							
						}else if(Sites[i][j].Empty && WPawns[k].moves==0 && WPawns[k].onmove==true && WPawns[k].y-Sites[i][j].y==225 && WPawns[k].x-Sites[i][j].x<100 && WPawns[k].x-Sites[i][j].x+25>0 && WPawns[k].y>25)
						{
							WPawns[k].Move(-200, 0);
							WPawns[k].onmove=false;
						}
					}
				}
			}
			
		}

    }

    
}
abstract class Bierka extends Rectangle2D.Double
{
	Board b;
	
	abstract void Move(int f, int g);
	
}

class Pawn extends Bierka
{	
	int moves=0;
	boolean onmove=false;
	
	Board b;
	Pawn(double x, double y, Board b)
	{
		this.height=75;
		this.width=50;
		this.x=x;
		this.y=y;
	}
	
	void Move(int f, int g)
	{
		y+=f;
		x+=g;
		moves+=1;
		b.repaint();
	}
	

}

public class Chess
{
		public static void main(String[] args)
		{
			javax.swing.SwingUtilities.invokeLater(new Runnable()    
			{                                                        
				public void run()                                     
				{                                                     
					Board b;                                         
					b=new Board();                                   
			
			
					JFrame jf=new JFrame();  		
					jf.add(b);
					jf.setTitle("Chess");                       
					jf.setSize(850, 850);                               
					jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
					jf.setVisible(true);                               
				}                                                     
			});        
		}
}
