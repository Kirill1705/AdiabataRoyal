import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {

    public int countOfClick = 0;
    public int[] iconsM = new int[16];
    public  int[] a = new int[16];

    public  Point[] points = new Point[4];
    public Shell[] shells = new Shell[1000];
    public int cX = 500;
    public  int slotM;
    public  int slotC;
    public Spell t1 = new Spell(1, 1, 0, 0);
    public  int end = 0;
    public  int over = 0;
    public int su = 0;
    public  int mouse = 0;
    public int cY = 400;
    public int r1 = 0;
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
    public  int kShell = 0;
    public int k = 0;
    public int kSpell = 0;
    public Unit[] units = new Unit[5000];
    public Spell[] spells = new Spell[1000];

    private MyPanel panel;
    public boolean b;
    public void start() {
        javax.swing.Timer timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                k++;

                points[0].x=panel.getWidth()/2-30;
                points[0].y=70;
                points[1].x=panel.getWidth()/2-30;
                points[1].y=panel.getHeight()-80;
                points[2].x=panel.getWidth()/2+30;
                points[2].y=70;
                points[3].x=panel.getWidth()/2+30;
                points[3].y=panel.getHeight()-80;
                if (k>7500) {
                    for (int i = 0; i < 6; i++) {
                        if (units[i]!=null) {
                            units[i].hp-=10;
                        }
                    }
                }
                if (k==7500||end==1||over!=0) {
                    for (int i = 6; i < kUnit; i++) {
                        units[i]=null;
                    }
                    for (int i = 0; i < kSpell; i++) {
                        spells[i]=null;
                    }
                    for (int i = 0; i < kShell; i++) {
                        shells[i]=null;
                    }
                }
                if (end>0) {
                    end++;
                }
                if(k<3000) {
                    if (s0<10)
                        s0 += 0.01;
                    if (s1<10)
                        s1 += 0.01;
                }
                else {
                    if (s0<10)
                        s0 += 0.02;
                    if (s1<10)
                        s1 += 0.02;
                }
                for (int i = 0; i <kShell; i++) {
                    if (shells[i]!=null&&shells[i].goal==null) {
                        shells[i]=null;
                        System.out.println("+");
                    }
                    if (shells[i]!=null&&!shells[i].shelldr()) {
                        if (shells[i].splash) {
                            for (int j = 0; j < kUnit; j++) {
                                if (units[j]!=null&&units[j]!=shells[i].goal&&shells[i].isEnemy(units[j])&&shells[i].goal.getDistance(units[j])<=shells[i].r+Math.max(units[j].w, units[j].h)) {
                                    units[j].hp-=shells[i].dd;
                                }
                            }
                        }
                        shells[i]=null;
                    }
                }
                for (int i = 0; i < kUnit; i++) {
                    if (units[i]!=null&&units[i].hp>0) {
                        int minD = 5000;
                        Unit goal = null;
                        if (units[i].GOAL==null||units[i].GOAL.hp<=0) {
                            //System.out.println(units[i].hp);
                            for (int j = 0; j < kUnit; j++) {
                                if (units[j] != null && units[j].hp > 0 && i != j && units[i].getDistance(units[j]) < minD && units[i].isEnemy(units[j])) {
                                    minD = units[i].getDistance(units[j]);
                                    goal = units[j];
                                }
                            }
                        }
                        else {
                            goal=units[i].GOAL;
                            minD=units[i].getDistance(units[i].GOAL);
                        }



                            if (goal!=null) {
                                b=units[i].drx(goal, points, k, minD);
                            }


                              if(units[i].GOAL!=null&&!b){
                                 units[i].attackImage(k, units[i].k1, units[i].s);
                                if (units[i].k1 == -1) {
                                    units[i].k1 = k % units[i].attackSpeed;
                                }
                                if ((k + units[i].k1) % units[i].attackSpeed == 0) {
                                    units[i].GOAL.hp -= units[i].dd;
                                    if (units[i].shell) {
                                        shells[kShell] = new Shell(units[i].type, units[i].command, (int)units[i].x, (int)units[i].y, units[i].GOAL);
                                        kShell++;
                                    }
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
        boolean f = false;
        for (int i = 0; i < a.length/2; i ++) {
            a[i]=i;
        }
        for (int i = a.length/2; i < a.length; i++) {
            a[i]=i-a.length/2;
        }
        //iconsM[0]=(int)((a.length/2)*Math.random());
        //a[iconsM[0]]=-1;
        //iconsM[iconsM.length/2]=(int)((a.length/2)*Math.random());
        //a[iconsM[iconsM.length/2]]=-1;
        for (int i = 0; i < iconsM.length/2; i++) {
            while (!f) {
                iconsM[i] = (int) ((a.length/2) * Math.random());
                //System.out.println(iconsM[i]);
                for (int j = 0; j < iconsM.length/2; j++) {
                    if (iconsM[i]==a[j]) {
                        f = true;
                        a[j]=-1;
                    }
                }
            }
            //System.out.println("iconsM[i]= "+iconsM[i]);
            f=false;
        }
        for (int i = iconsM.length/2; i < iconsM.length; i++) {
            while (!f) {
                iconsM[i] = (int) ((a.length/2) * Math.random());
                //System.out.println(iconsM[i]);
                for (int j = iconsM.length/2; j < iconsM.length; j++) {
                    if (iconsM[i]==a[j]) {
                        f = true;
                        a[j]=-1;
                    }
                }
            }
            f=false;
        }
        for (int i = 0; i < iconsM.length; i++) {
            System.out.println(iconsM[i]);
        }
        Unit unit;
        Spell spell;
        for (int i = 0; i < iconsM.length; i++) {
            if (iconsM[i]==5) {
                spell=new Spell(iconsM[i]+1, 1, -500, -500);
                a[i]=spell.cost;
            }
            else {
                unit = new Unit(iconsM[i] + 1, 1, -500, -500);
                a[i] = unit.cost;
            }
        }
        unit=null;
        spell=null;
        System.out.println("break");
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



        public MyPanel(boolean isDoubleBuffered) {
            super(isDoubleBuffered);
            this.isFocusable();
            this.setFocusable(true);
            this.setFocusTraversalKeysEnabled(true);
            addMouseListener(this);
            addMouseMotionListener(this);
            addKeyListener(this);
            for (int i = 0; i < 4; i++) {
                points[i]=new Point(0, 0);
            }
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(Color.BLACK);

                g.drawImage(new ImageIcon("untitled//src//background.png").getImage(), 0, 0, this.getWidth(), this.getHeight(), this);

                g.drawImage(new ImageIcon("untitled//src//river.jpg").getImage(), points[0].x, points[0].y, points[2].x-points[0].x, points[1].y-points[0].y, this);
            g.drawLine(this.getWidth()/2, 0, this.getWidth()/2, points[2].y);
            g.drawLine(points[2].x, points[2].y, points[2].x, points[3].y);
            g.drawLine(this.getWidth()/2, points[3].y, this.getWidth()/2, this.getHeight());
            g.fillRect(this.getWidth()-200, 0, 200, this.getHeight());
            g.fillRect(0, 0, 200, this.getHeight());
            g.drawImage(new ImageIcon("untitled//src//"+(iconsM[0]+1)+".jpg").getImage(), this.getWidth()-190, this.getHeight()*3/4+10, 180, this.getHeight()/4-20, this);
            g.drawImage(new ImageIcon("untitled//src//"+(iconsM[1]+1)+".jpg").getImage(), this.getWidth()-190, this.getHeight()/2+10, 180, this.getHeight()/4-20, this);
            g.drawImage(new ImageIcon("untitled//src//"+(iconsM[2]+1)+".jpg").getImage(), this.getWidth()-190, this.getHeight()/4+10, 180, this.getHeight()/4-20, this);
            g.drawImage(new ImageIcon("untitled//src//"+(iconsM[3]+1)+".jpg").getImage(), this.getWidth()-190, 10, 180, this.getHeight()/4-20, this);
            g.drawImage(new ImageIcon("untitled//src//"+(iconsM[8]+1)+".jpg").getImage(), 10, this.getHeight()*3/4+10, 180, this.getHeight()/4-20, this);
            g.drawImage(new ImageIcon("untitled//src//"+(iconsM[9]+1)+".jpg").getImage(), 10, this.getHeight()/2+10, 180, this.getHeight()/4-20, this);
            g.drawImage(new ImageIcon("untitled//src//"+(iconsM[10]+1)+".jpg").getImage(), 10, this.getHeight()/4+10, 180, this.getHeight()/4-20, this);
            g.drawImage(new ImageIcon("untitled//src//"+(iconsM[11]+1)+".jpg").getImage(), 10, 10, 180, this.getHeight()/4-20, this);
            //g.drawImage(new ImageIcon("untitled//src//background.png").getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
            g.drawImage(new ImageIcon("untitled//src//n"+a[0]+".jpg").getImage(), this.getWidth()-200, this.getHeight()*3/4, 50, 50, this);
            g.drawImage(new ImageIcon("untitled//src//n"+a[1]+".jpg").getImage(), this.getWidth()-200, this.getHeight()/2, 50, 50, this);
            g.drawImage(new ImageIcon("untitled//src//n"+a[2]+".jpg").getImage(), this.getWidth()-200, this.getHeight()/4, 50, 50, this);
            g.drawImage(new ImageIcon("untitled//src//n"+a[3]+".jpg").getImage(), this.getWidth()-200, 0, 50, 50, this);
            g.drawImage(new ImageIcon("untitled//src//n"+a[8]+".jpg").getImage(), 150, this.getHeight()*3/4, 50, 50, this);
            g.drawImage(new ImageIcon("untitled//src//n"+a[9]+".jpg").getImage(), 150, this.getHeight()/2, 50, 50, this);
            g.drawImage(new ImageIcon("untitled//src//n"+a[10]+".jpg").getImage(), 150, this.getHeight()/4, 50, 50, this);
            g.drawImage(new ImageIcon("untitled//src//n"+a[11]+".jpg").getImage(), 150, 0, 50, 50, this);


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
            g.drawImage(new ImageIcon("untitled//src//n"+(int)s0+".jpg").getImage(), 10, this.getHeight()-20, 20, 20, this);
            g.drawImage(new ImageIcon("untitled//src//n"+(int)s1+".jpg").getImage(), this.getWidth()-30 , this.getHeight()-20, 20, 20, this);
            if (units[0]!=null) {
                units[0].x = this.getWidth() / 5;
                units[0].y = this.getHeight() / 7;
            }
            if (units[1]!=null) {
                units[1].x = this.getWidth()/5;
                units[1].y =this.getHeight()/7*6;
            }
            if (units[2]!=null) {
                units[2].x = this.getWidth()/5*4;
                units[2].y = (this.getHeight()/7);
            }
            if (units[3]!=null) {
                units[3].x = this.getWidth() / 5 * 4;
                units[3].y = this.getHeight() / 7 * 6;
            }

                for (int i = 0; i <= 5; i++) {
                    if (units[i]!=null) {
                        g.setColor(Color.RED);
                        g.fillRect((int)(units[i].x-units[i].w/2), (int)(units[i].y-units[i].h/4*3+20), 150*units[i].hp/5000, 10);
                        g.drawString(Integer.toString(units[i].hp), (int)(units[i].x-units[i].w/2), (int)(units[i].y-units[i].h/4*3+19 ));
                        g.drawRect((int)(units[i].x-units[i].w/2), (int)(units[i].y-units[i].h/4*3+20), 150, 10);
                        //System.out.println(units[i].hp);

                        if (i==0||i==1)
                            g.drawImage(new ImageIcon("untitled//src//town.png").getImage(), (int)(units[i].x - units[i].w/2), (int)(units[i].y - units[i].h/2), units[i].w, units[i].h, this);
                        if (i==2||i==3)
                            g.drawImage(new ImageIcon("untitled//src//town1.png").getImage(), (int)(units[i].x-units[i].w/2), (int)(units[i].y - units[i].h/2), units[i].w, units[i].h, this);
                        //g.drawOval(units[i].x-units[i].r, units[i].y-units[i].r, 2*units[i].r, 2*units[i].r);
                    }

                }
            /*if (k<10||(k>=20&&k<30)) {
                g.drawImage(new ImageIcon("untitled//src//1front2.png").getImage(), 150, 645, 100, 100, this);
            }
            if (k>=10&&k<20) {
                g.drawImage(new ImageIcon("untitled//src//1front1.png").getImage(), 150, 45, 100, 100, this);
            }
            if (k>=30&&k<=40) {
                g.drawImage(new ImageIcon("untitled//src//1front3.png").getImage(), 150, 345, 100, 100, this);
            }

             */

            g.drawRect(cX-this.getWidth()/80, cY-this.getHeight()/40, this.getWidth()/40, this.getHeight()/20);
            //g.drawOval(cX, cY, 2, 2);


            for (int i = 6; i < kUnit; i++) {
                if (units[i]!=null) {

                        g.drawImage(new ImageIcon("untitled//src//"+units[i].s+".png").getImage(), (int)(units[i].x-units[i].w/2), (int)(units[i].y-units[i].h/2), units[i].w, units[i].h, this);
                        //g.drawOval(units[i].x, units[i].y, 2, 2);
                        if (units[i].hp!=units[i].maxhp&&units[i].hp>0) {
                            g.setColor(Color.RED);
                            g.fillRect((int)(units[i].x-units[i].w/2), (int)(units[i].y - units[i].h / 3*2), units[i].w * units[i].hp / units[i].maxhp, 4);
                            g.drawRect((int)(units[i].x-units[i].w/2), (int)(units[i].y - units[i].h / 3*2), units[i].w, 4);
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
            for (int i = 0; i < kShell; i++) {
                if (shells[i]!=null) {
                    g.drawImage(new ImageIcon("untitled//src//"+shells[i].s+".png").getImage(), shells[i].x-shells[i].w/2, shells[i].y-shells[i].h/2, shells[i].w, shells[i].h, this);
                }
            }
            int c1=0;
            int c0=0;
            if (units[0]!=null)
                c0++;
            if (units[1]!=null)
                c0++;
            if (units[2]!=null)
                c1++;
            if (units[3]!=null)
                c1++;
            if (k>10) {
                if (c1 > c0 && k > 4500 || over == 1 || c0 == 0) {
                    g.drawImage(new ImageIcon("untitled//src//victory.png").getImage(), this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight(), this);
                    g.setColor(Color.RED);
                    g.fillRect(0, 0, this.getWidth() / 2, this.getHeight());
                    g.drawImage(new ImageIcon("untitled//src//defeat.png").getImage(), 100, 200, this.getWidth() / 2 - 100, this.getHeight() - 400, this);
                    if (end==0)
                        end = 1;
                    over = 1;
                    System.out.println(end);
                    if (end > 150)
                        System.exit(0);
                } else if (c1 < c0 && over == 0 && k > 4500 || over == 2 || c1 == 0) {
                    g.drawImage(new ImageIcon("untitled//src//victory.png").getImage(), 0, 0, this.getWidth() / 2, this.getHeight(), this);
                    g.setColor(Color.RED);
                    g.fillRect(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
                    g.drawImage(new ImageIcon("untitled//src//defeat.png").getImage(), this.getWidth() / 2 + 100, 200, this.getWidth()/2 - 100, this.getHeight() - 400, this);
                    if (end==0)
                        end = 1;
                    over = 2;
                    if (end > 150) {
                        System.exit(0);
                    }
                }
            }
            if (k<4500) {
                g.setColor(Color.WHITE);
                g.fillRect(this.getWidth()/2-20, this.getHeight()/2-10, 45, 20);
                g.setColor(Color.RED);
                g.drawString(2-k/1500+": "+(59-(k%1500)/25), this.getWidth() / 2-20, this.getHeight() / 2+5);
            }

            if (k>=4500&&k<=7500) {
                g.setColor(Color.BLACK);
                g.fillRect(this.getWidth()/2-60, this.getHeight()/2-35, 120, 40);
                g.setColor(Color.RED);
                g.drawString("Доп. время", this.getWidth()/2-50, this.getHeight()/2-20);
                g.drawString((1-(k-4500)/1500)+": "+(59-(k%1500)/25), this.getWidth()/2-30, this.getHeight()/2);
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
            }
                repaint();
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            xRel = mouseEvent.getX();
            yRel = mouseEvent.getY();
            if (pressX<this.getWidth()&&pressX>this.getWidth()-200&&xRel<this.getWidth()-200) {
                if (pressY<this.getHeight()&&pressY>this.getHeight()*3/4) {
                    slotM=0;
                }
                else if(pressY<this.getHeight()*3/4&&pressY>this.getHeight()/2) {
                    slotM=1;
                }
                else if(pressY<this.getHeight()/2&&pressY>this.getHeight()/4) {
                    slotM=2;
                }
                else if(pressY<this.getHeight()/4&&pressY>0) {
                    slotM=3;
                }
                if (xRel<this.getWidth()/2&&iconsM[slotM]!=5) {
                    if ((yRel<points[0].y||yRel>points[3].y)) {
                        xRel=this.getWidth()/2;
                    }
                    else {
                        xRel=points[2].x+30;
                    }
                }
                if (iconsM[slotM]==5) {
                    spells[kSpell] = new Spell(iconsM[slotM]+1, 1, xRel, yRel);
                    if (s1<spells[kSpell].cost) {
                        spells[kSpell]=null;
                    }
                    else {
                        kSpell++;
                        s1-=spells[kSpell-1].cost;
                        int g = iconsM[slotM];
                        iconsM[slotM]=iconsM[4];
                        iconsM[4]=iconsM[5];
                        iconsM[5]=iconsM[6];
                        iconsM[6]=iconsM[7];
                        iconsM[7]=g;
                        g = a[slotM];
                        a[slotM]=a[4];
                        a[4]=a[5];
                        a[5]=a[6];
                        a[6]=a[7];
                        a[7]=g;
                    }
                }
                else {
                    Unit ex = new Unit(iconsM[slotM]+1, 1, xRel, yRel);
                    if (s1 >= ex.cost&&((yRel<points[2].y||yRel>points[3].y)&&xRel>this.getWidth()/2||xRel>points[2].x+20)) {
                        s1-=ex.cost;
                        for (int i = 0; i < ex.value; i++) {
                            units[kUnit] = new Unit(iconsM[slotM]+1, 1, xRel + ex.upoints[i].x, yRel + ex.upoints[i].y);
                            kUnit++;
                        }
                        int g = iconsM[slotM];
                        iconsM[slotM]=iconsM[4];
                        iconsM[4]=iconsM[5];
                        iconsM[5]=iconsM[6];
                        iconsM[6]=iconsM[7];
                        iconsM[7]=g;
                        g = a[slotM];
                        a[slotM]=a[4];
                        a[4]=a[5];
                        a[5]=a[6];
                        a[6]=a[7];
                        a[7]=g;
                    }
                }
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
                slotC=8;
                r0=this.getWidth()/2;
            }
            if (key==50) {
                slotC=9;
                r0 = this.getWidth()-200;
            }
            if (key==51) {
                slotC=10;
                r0=this.getWidth()/2;

            }
            if (key==52) {
                slotC=11;
                r0=this.getWidth()/2;
            }
            if (key==10) {
                if (iconsM[slotC]==5) {
                    spells[kSpell] = new Spell(iconsM[slotC]+1, 0, cX, cY);
                    if (s0<4) {
                        spells[kSpell]=null;
                    }
                    else {
                        kSpell++;
                        s0-=spells[kSpell-1].cost;
                        int g = iconsM[slotC];
                        iconsM[slotC]=iconsM[12];
                        iconsM[12]=iconsM[13];
                        iconsM[13]=iconsM[14];
                        iconsM[14]=iconsM[15];
                        iconsM[15]=g;
                        g = a[slotC];
                        a[slotC]=a[12];
                        a[12]=a[13];
                        a[13]=a[14];
                        a[14]=a[15];
                        a[15]=g;
                    }
                }
                else {
                    if (cX>this.getWidth()/2-40) {
                        cX=this.getWidth()/2-40;
                    }
                    Unit ex = new Unit(iconsM[slotC]+1, 1, xRel, yRel);
                    if (s0 >= ex.cost) {
                        s0-=ex.cost;
                        for (int i = 0; i < ex.value; i++) {
                            units[kUnit] = new Unit(iconsM[slotC]+1, 0, cX + ex.upoints[i].x, cY + ex.upoints[i].y);
                            kUnit++;
                        }
                        int g = iconsM[slotC];
                        iconsM[slotC]=iconsM[12];
                        iconsM[12]=iconsM[13];
                        iconsM[13]=iconsM[14];
                        iconsM[14]=iconsM[15];
                        iconsM[15]=g;
                        g = a[slotC];
                        a[slotC]=a[12];
                        a[12]=a[13];
                        a[13]=a[14];
                        a[14]=a[15];
                        a[15]=g;
                    }
                }
            }
            if (key==37&&cX>200) {
                cX-=this.getWidth()/40;
            }
            if (key==38&&cY>this.getHeight()/18) {
                cY-=this.getHeight()/20;

            }
            if (key==39&&cX<this.getWidth()) {
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