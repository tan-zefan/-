package lifegame;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Start extends Applet implements Runnable,MouseListener,MouseMotionListener,KeyListener { 
    private Thread animator;
    private int delay;//延迟 
    private Button bt1 = new Button("开始/暂停");
    private Button bt2 = new Button("  清空   ");
    private Button bt3 = new Button("\n" + "下一步" + "\n");
    private boolean running;//flag。标识线程的运行状况，正在运行则running为true，被用户中断，running为false。

    @Override public void run() {
        long tm = System.currentTimeMillis();
        while (Thread.currentThread() == animator) {
            if (running == true) {
                WorldUtils.getNeighbors();
                WorldUtils.nextWorld();
                repaint();
            } 
            try {
                tm += delay;
                System.out.println(System.currentTimeMillis());
                Thread.sleep(Math.max(0, tm - System.currentTimeMillis()));
            } catch (InterruptedException e) {
                break;
            }
        } 
    } // run

    /**
     * applet 生命周期方法
     */
    @Override public void init()  {
        resize(650, 600);
        setLayout(new BorderLayout());
        animator = new Thread(this);
        delay = 100;
        running = false;
        //setBackground(Color.yellow);
        setBackground(new Color(0, 0, 0));//Color(199,237,204));
        add(bt1, BorderLayout.EAST);
        add(bt2, BorderLayout.WEST);
        add(bt3, BorderLayout.SOUTH);
        bt1.addActionListener(new Bt1());
        bt2.addActionListener(new Bt2());
        bt3.addActionListener(new Bt3());
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
    }

    class Bt1 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            running = !running;
            repaint();
        }
    }

    class Bt2 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            WorldUtils.table = new boolean[WorldUtils.SIZE][WorldUtils.SIZE];
            if(running = true) running = !running;
            repaint();
            WorldUtils.getNeighbors();
            WorldUtils.nextWorld();
            repaint();
        }
    }

    class Bt3 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            WorldUtils.getNeighbors();
            WorldUtils.nextWorld();
            repaint();
        }
    }

    @Override public void start() {        
        animator.start();       
    }

    @Override public void stop()    {
        animator = null;    
    }

    @Override public void paint(Graphics g) {
        update(g);
    }

    @Override public void update (Graphics g) {
        for (int x = 3; x < WorldUtils.SIZE; x++)
            for (int y = 0; y < WorldUtils.SIZE; y++) {
                System.out.print(WorldUtils.table[x][y]);
                g.setColor(WorldUtils.table[x][y] ? WorldUtils.cell : WorldUtils.space);
                g.fillRect(x * WorldUtils.CELL_Size, y * WorldUtils.CELL_Size, WorldUtils.CELL_Size - 1, WorldUtils.CELL_Size - 1);
            }
    }

    /**
     * event handler 
     */
    @Override
    public void mouseClicked(MouseEvent e){ }

    @Override
    public void mousePressed(MouseEvent e){
        int cellX = e.getX()/WorldUtils.CELL_Size;
        int cellY = e.getY()/WorldUtils.CELL_Size;
        WorldUtils.table[cellX][cellY] = !e.isControlDown();
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e){}

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent e){}

    @Override
    public void mouseDragged(MouseEvent e){
        this.mousePressed(e); 
    }

    @Override
    public void mouseMoved(MouseEvent e){}

    @Override
    public void keyTyped(KeyEvent e){}

    @Override
    public void keyPressed(KeyEvent e){}

    @Override
    public void keyReleased(KeyEvent e){}
}