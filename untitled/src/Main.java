import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {

    public int countOfClick = 0;
    Point point = new Point(0, 0);
    public Point pH = new Point(750, 25);
    public Point pL = new Point(750, 710);
    public  Point[] points = new Point[4];



    public int cX = 500;
    public Spell t1 = new Spell(1, 1, 0, 0);
    public int su = 0;
    public  int mouse = 0;
    public int cY = 400;
    public int r1 = 0;
    public int type = -1;
    public int r0 = 0;
    public double s1 = 5;
    public double s0 = 5;
    public int dragX = -10;
    public int dragY = -10;
    public int pressX = -10;
    public int pressY = -10;
    public int xRel = -10;
    public int yRel = -10;
    public int kUnit = 6;
    public int k = 0;
    public int kSpell = 0;
    public Unit[] units = new Unit[5000];
    public Spell[] spells = new Spell[1000];

    private MyPanel panel;
    public void start() {
        javax.swing.Timer timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                k++;

                if(k<6000) {
                    if (s0<10)
                        s0 += 0.01;
                    if (s1<10)
                        s1 += 0.01;
                }
                else {
                    if (s0<10)
                        s0 += 0.04;
                    if (s0<10)
                        s1 += 0.04;
                }
                for (int i = 0; i < kUnit; i++) {
                    if (units[i]!=null&&units[i].hp>0) {
                        int minD = 5000;
                        Unit goal = null;
                        for (int j = 0; j < kUnit; j++) {
                            if (units[j] != null &&units[j].hp>0&& i != j && units[i].getDistance(units[j]) < minD && units[i].isEnemy(units[j])) {
                                minD = units[i].getDistance(units[j]);
                                goal = units[j];
                            }
                        }



                            if (goal!=null&& units[i].r + units[i].w / 3 + goal.w / 3 < minD) {
                                units[i].drx(goal, points, k);

                            }


                             else if(goal!=null){
                                 units[i].attackImage(k, units[i].k1, units[i].s);
                                if (units[i].k1 == -1) {
                                    units[i].k1 = k % units[i].attackSpeed;
                                }
                                if ((k + units[i].k1) % units[i].attackSpeed == 0) {
                                    goal.hp -= units[i].dd;
                                }
                            }
                        if (units[i].type==0&&units[i].hp<=0) {
                            units[i]=null;
                        }
                    }
                    else if(units[i]!=null) {
                        if (units[i].type==0) {
                            units[i]=null;
                        }
                        else {
                            units[i].deathImage(units[i].s);
                            if (units[i].death <= 0 && units[i].type != 0) {
                                units[i] = null;
                            }
                        }

                    }
                }
                for (int i = 0; i < kSpell; i++) {
                    if (spells[i]!=null) {

                        for (int j = 0; j<kUnit; j++) {
                            if (units[j]!=null&&spells[i].canAttack(units[j])&&spells[i].isEnemy(units[j])&&spells[i].t%spells[i].attackSpeed==0) {
                                if (units[j].type!=0) {
                                    units[j].hp -= spells[i].dd;
                                }
                                else {
                                    units[j].hp-=spells[i].dd/2;
                                }
                            }
                        }
                        spells[i].t--;
                        if (spells[i].t<2)
                            spells[i]=null;
                    }
                }
                nextStep();
            }
        } );
        timer.start();
    }

    private void nextStep() {

        repaint();
    }

    public Main(String title) {
        super(title);
        setBounds(10, 50, 600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        setJMenuBar(menuBar);
        panel = new MyPanel(true);


        add(panel);
        setVisible(true);



        start();
    }

    private JMenu createFileMenu() {
        JMenu menuFile = new JMenu("Файл");
        JMenuItem open = new JMenuItem("Открыть...");
        JMenuItem exit = new JMenuItem("Выход");
        menuFile.add(open);
        menuFile.addSeparator();
        menuFile.add(exit);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
                //panel.tool = LINE_TOOL;
            }
        });
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Щелкнули по пункту Открыть...");
                System.out.println(panel.point);
            }
        });
        return menuFile;
    }

    public static void main(String[] args) {
        Main main = new Main("Заголовок окна");

    }

    class MyPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
        public Point point = null;

        public Point beginPoint = null;
        public Point endPoint = null;

        public MyPanel(boolean isDoubleBuffered) {
            super(isDoubleBuffered);
            this.isFocusable();
            this.setFocusable(true);
            this.setFocusTraversalKeysEnabled(true);
            addMouseListener(this);
            addMouseMotionListener(this);
            addKeyListener(this);

        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            points[0] = new Point(this.getWidth()/2-30, 40);
            points[1] = new Point(this.getWidth()/2-30, this.getHeight()-60);
            points[2] = new Point(this.getWidth()/2 +30, 40);
            points[3] = new Point(this.getWidth()/2+30, this.getHeight()-60);

                g.drawImage(new ImageIcon("untitled//src//river.jpg").getImage(), this.getWidth()/2-50, 100, 100, this.getHeight()-200, this);

            g.drawLine(r1, 0, r1, this.getHeight());
            g.drawImage(new ImageIcon("untitled//src//GoblinMain.jpg").getImage(), this.getWidth()-200, this.getHeight()-200, 200, 200, this);
            g.drawImage(new ImageIcon("untitled//src//fireball1.jpg").getImage(), this.getWidth()-200, this.getHeight()-400, 200, 200, this);
            g.drawImage(new ImageIcon("untitled//src//Archer.png").getImage(), this.getWidth()-200, this.getHeight()-600, 200, 200, this);
            //g.drawImage(new ImageIcon("untitled//src//background.png").getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
            // Теперь будем ТУТ всё рисовать
            if (point != null)
                g.fillOval(point.x - 5, point.y - 5, 9, 9);
            if (beginPoint != null)
                g.fillOval(beginPoint.x - 3, beginPoint.y - 3, 5, 5);
            if (endPoint != null && beginPoint != null)
                g.drawLine(beginPoint.x, beginPoint.y, endPoint.x, endPoint.y);
            g.drawString("Число кликов мыши: " + countOfClick, 100, 100);

                for (int i = 1; i <=10; i++) {
                    g.setColor(Color.CYAN);
                    g.drawRect(0, this.getHeight() * i / 10, 15, this.getHeight() / 10);
                    g.drawRect(this.getWidth() - 15, this.getHeight() * i / 10, 15, this.getHeight() / 10);
                }
                if (k==1) {
                    units[0] = new Unit(0, 0, this.getWidth() / 8, this.getHeight() / 9);
                    units[1] = new Unit(0, 0, this.getWidth() / 8, (int) (this.getHeight() / 9 * 6.5));
                    units[2] = new Unit(0, 1, this.getWidth()/8*7, (int)(this.getHeight()/9*6.5));
                    units[3] = new Unit(0, 1, this.getWidth()/8*7, this.getHeight()/9);
                }
            g.setColor(Color.GREEN);
            g.fillRect(1, this.getHeight()-(int)(this.getHeight()*s0/10), 13, (int)(this.getHeight()*s0/10));
            g.fillRect(this.getWidth()-14, this.getHeight()-(int)(this.getHeight()*s1/10), 13, (int)(this.getHeight()*s1/10));
            this.setFont( this.getFont().deriveFont( 20.0f ));
            g.drawString(Integer.toString((int)(s0)), 16, this.getHeight()-30);
            g.drawString(Integer.toString((int)(s1)), this.getWidth()-16, this.getHeight()-30);
            if (units[0]!=null) {
                units[0].x = this.getWidth() / 8;
                units[0].y = this.getHeight() / 9;
            }
            if (units[1]!=null) {
                units[1].x = this.getWidth()/8;
                units[1].y = (int)(this.getHeight()/9*6.5);
            }
            if (units[2]!=null) {
                units[2].x = this.getWidth()/8*6;
                units[2].y = (this.getHeight()/9);
            }
            if (units[3]!=null) {
                units[3].x = this.getWidth() / 8 * 6;
                units[3].y = (int) (this.getHeight() / 9 * 6.5);
            }

                for (int i = 0; i <= 5; i++) {
                    if (units[i]!=null) {
                        g.setColor(Color.RED);

                        g.fillRect(units[i].x-20, units[i].y-40, 150*units[i].hp/5000, 10);
                        g.drawString(Integer.toString(units[i].hp), units[i].x-21, units[i].y-41 );
                        g.drawRect(units[i].x-20, units[i].y-40, 150, 10);
                        //System.out.println(units[i].hp);

                        if (i==0||i==1)
                            g.drawImage(new ImageIcon("untitled//src//town.png").getImage(), units[i].x - 30, units[i].y - 30, units[i].w, units[i].h, this);
                        if (i==2||i==3)
                            g.drawImage(new ImageIcon("untitled//src//town1.png").getImage(), units[i].x-30, units[i].y - 30, units[i].w, units[i].h, this);
                    }

                }
            /*if (k<10||(k>=20&&k<30)) {
                g.drawImage(new ImageIcon("untitled//src//1front1.png").getImage(), 150, 645, 100, 100, this);
            }
            if (k>=10&&k<20) {
                g.drawImage(new ImageIcon("untitled//src//1front2.png").getImage(), 150, 45, 100, 100, this);
            }
            if (k>=30&&k<=40) {
                g.drawImage(new ImageIcon("untitled//src//1front3.png").getImage(), 150, 345, 100, 100, this);
            }

             */

            g.drawRect(cX-this.getWidth()/80, cY-this.getHeight()/40, this.getWidth()/40, this.getHeight()/20);


            for (int i = 6; i < kUnit; i++) {
                if (units[i]!=null) {
                    if (units[i].type == 1) {
                        g.drawImage(new ImageIcon("untitled//src//"+units[i].s+".png").getImage(), units[i].x, units[i].y, units[i].w, units[i].h, this);
                        if (units[i].hp!=200&&units[i].hp>0) {
                            g.setColor(Color.RED);
                            g.fillRect(units[i].x, units[i].y - units[i].h / 4, units[i].w * units[i].hp / 200, 4);
                            g.drawRect(units[i].x, units[i].y - units[i].h / 4, units[i].w, 4);
                        }
                    }
                }
            }
            for (int i = 0; i < kSpell; i++) {
                if (spells[i]!=null) {
                    g.drawImage(new ImageIcon(spells[i].s).getImage(), spells[i].x-spells[i].r, spells[i].y-spells[i].r, 2*spells[i].r, 2*spells[i].r, this);
                }
            }
            if (mouse==1) {
                g.drawOval(dragX-t1.r, dragY-t1.r, 2*t1.r, 2*t1.r);
            }
            if (su==1) {
                g.drawOval(cX-t1.r, cY-t1.r, 2*t1.r, 2*t1.r);
            }

        }






        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            point = mouseEvent.getPoint();
            countOfClick++;
            System.out.println(point);
            repaint();

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            pressX= mouseEvent.getX();
            pressY = mouseEvent.getY();
            if (pressX<this.getWidth()&&pressX>this.getWidth()-200&&pressY<this.getHeight()&&pressY>this.getHeight()-200&&s1>2) {
                r1 = this.getWidth()/2;
                mouse=0;
            }
            if (pressX<this.getWidth()&&pressX>this.getWidth()-200&&pressY<this.getHeight()-200&&pressY>this.getHeight()-400) {
                mouse=1;
                System.out.println(dragX);
            }
                repaint();
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            xRel = mouseEvent.getX();
            yRel = mouseEvent.getY();
            if (pressX<this.getWidth()&&pressX>this.getWidth()-200&&pressY<this.getHeight()&&pressY>this.getHeight()-200&&xRel<this.getWidth()-200&&s1>2) {
                kUnit+=4;
                if (xRel<this.getWidth()/2+20) {
                    xRel = this.getWidth()/2+20;
                }
                //int c = (int)(2*Math.random());
                s1-=2;

                units[kUnit - 4] = new Unit(1, 1, xRel+20, yRel-20);
                units[kUnit - 3] = new Unit(1, 1, xRel+20, yRel+20);
                units[kUnit - 2] = new Unit(1, 1, xRel-20, yRel-20);
                units[kUnit - 1] = new Unit(1, 1, xRel-20, yRel+20);
                //System.out.println(kUnit);
            } else if (pressX<this.getWidth()&&pressX>this.getWidth()-200&&pressY<this.getHeight()-200&&pressY>this.getHeight()-400&&xRel<this.getWidth()-200&&s1>4) {
                kSpell++;
                mouse=0;
                s1-=4;
                spells[kSpell-1] = new Spell(1, 1, xRel, yRel);
            }
            else if(pressX<this.getWidth()&&pressX>this.getWidth()-200&&pressY<this.getHeight()-400&&pressY>this.getHeight()-600&&xRel<this.getWidth()-200&&s1>5) {
                if (xRel<this.getWidth()/2+20) {
                    xRel = this.getWidth()/2+20;
                }
                mouse=0;
                kUnit++;
                s1-=5;
                units[kUnit-1] = new Unit(2, 1, xRel, yRel);
            }
            else {
                mouse=0;
            }
            repaint();
            xRel=-10;
            yRel=-10;
            r1=0;
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
        }

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            dragX = mouseEvent.getX();
            dragY = mouseEvent.getY();


        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
        }
        @Override
        public void keyPressed(KeyEvent keyEvent) {

            int key = keyEvent.getKeyCode();
            System.out.println(key);
            if (key==49) {
                su=0;
                type = 1;
                r0=this.getWidth()/2;
                if (cX>this.getWidth()/2-40) {
                    cX=this.getWidth()/2-40;
                }
            }
            if (key==50) {
                su=1;
                type = 1;
                r0 = this.getWidth()-200;
            }
            if (key==51) {
                su=0;
                type=2;
            }
            if (key==10) {
                if (type==1&&su==0&&s0>2) {
                    kUnit+=4;
                    s0-=2;

                    units[kUnit - 4] = new Unit(1, 0, cX+20, cY-20);
                    units[kUnit - 3] = new Unit(1, 0, cX+20, cY+20);
                    units[kUnit - 2] = new Unit(1, 0, cX-20, cY-20);
                    units[kUnit - 1] = new Unit(1, 0, cX-20, cY+20);
                }
                else if (type==1&&su==1&&s0>4) {
                    kSpell++;

                    spells[kSpell-1] = new Spell(1, 0, cX, cY);
                    s0-=spells[kSpell-1].cost;
                }
                else if(type==2&&su==0&&s0>5) {
                    kUnit++;
                    s0-=5;
                    units[kUnit-1] = new Unit(2, 0, cX, cY);
                }

            }
            if (key==37&&cX>200) {
                cX-=this.getWidth()/40;
            }
            if (key==38&&cY>this.getHeight()/18) {
                cY-=this.getHeight()/20;

            }
            if (key==39&&(cX<this.getWidth()/2-40||su==1&&cX<this.getWidth()-200)) {
                cX+=this.getWidth()/40;
            }
            if (key==40&&cY<this.getHeight()-this.getHeight()/20) {
                cY+=this.getHeight()/20;
            }
            repaint();
        }
        @Override
        public void keyReleased(KeyEvent e) {

        }
        @Override
        public void keyTyped(KeyEvent e) {

        }
    }
}