import java.awt.*;

public class Unit {
    public Point[] upoints = new Point[20];
    public String s;
    public int value;
    public Unit GOAL;
    public boolean building;
    public int x;
    public int y;
    public int w;
    public int h;
    public int hp;
    public int maxhp;
    public int dd;
    public int speed;
    public int attackSpeed;
    public int r;
    public int type;
    public int command;
    public boolean fly;
    public boolean splash;
    public int k1;
    public int cost;
    public  int death;
    public boolean shell;
    public int l;
    /* l: Избранная цель.
    l=0: Земля
    l=1: Земля и воздух
    l=2: Королевская башня
    l=3: Воздух
     */
    public Unit(int type, int command, int x,int y) {
        this.x=x;
        this.y=y;
        this.type=type;
        this.command=command;
        this.k1=-1;
        //Гоблины
        if(type==1) {
            this.l=0;
            this.w=50;
            this.h=70;
            this.hp=200;
            this.maxhp=200;
            this.dd=46;
            this.speed=6;
            this.attackSpeed=25;
            this.r=10;
            this.fly=false;
            this.splash=false;
            this.cost=2;
            this.s="1front1";
            this.death=100;
            this.shell=false;
            this.building=false;
            this.value=4;
            this.upoints[0]=new Point(-20, -20);
            this.upoints[1]=new Point(-20, 20);
            this.upoints[2]=new Point(20, -20);
            this.upoints[3]=new Point(20, 20);
        }
        //Королевская башня
        else if(type==0) {
            this.l=1;
            this.w=150;
            this.h=150;
            this.hp=5000;
            this.maxhp=5000;
            this.dd=0;
            this.speed=1;
            this.attackSpeed=30;
            this.r=325;
            this.fly=false;
            this.splash=false;
            this.cost=0;
            this.shell=true;
            this.building=false;
        }
        //Лучник
        else if (type==2) {
            this.l=1;
            this.w=75;
            this.h=90;
            this.hp=1000;
            this.maxhp=1000;
            this.dd=0;
            this.speed=2;
            this.attackSpeed=30;
            this.r=250;
            this.fly=false;
            this.splash=false;
            this.cost=5;
            this.s="2front2";
            this.shell=true;
            this.building=false;
            this.death=100;
            this.value=1;
            this.upoints[0]=new Point(0, 0);
        }
        //Пушка
        /*else if(type==4) {
            this.l=0;
            this.w=100;
            this.h=100;
            this.speed=1;
            this.attackSpeed=30;
            this.shell=true;
            this.maxhp=500;
            this.r=260;
            this.fly=false;
            this.splash=false;
            this.hp=500;
            this.s="4front1";
            this.cost=3;
            this.dd=0;
            this.building=true;
            this.value=1;
            this.upoints[0]=new Point(0, 0);
        }*/
        //Летучие мыши
        else if (type==5) {
            this.l=1;
            this.w=75;
            this.h=75;
            this.speed=4;
            this.attackSpeed=30;
            this.shell=true;
            this.maxhp=500;
            this.r=60;
            this.fly=true;
            this.splash=false;
            this.hp=this.maxhp;
            this.s="5front1";
            this.cost=4;
            this.dd=0;
            this.building=false;
            this.death=50;
            this.value=2;
            this.upoints[0]=new Point(0, -20);
            this.upoints[1]=new Point(0, 20);
        }
        else {
            this.cost=0;
        }
    }
    public boolean isEnemy(Unit unit) {
        return this.command!=unit.command&&(!unit.fly&&this.l!=3||unit.fly&&(this.l==1||this.l==3));
    }
    public int getDistance(Unit unit) {
        return (int)Math.sqrt((this.x-unit.x)*(this.x-unit.x)+(this.y-unit.y)*(this.y-unit.y));
    }
    public int getPointDistance(Point point) {
        return (int)Math.sqrt((this.x-point.x)*(this.x-point.x)+(this.y-point.y)*(this.y-point.y));
    }
    public boolean drx(Unit goal, Point[] points, int k, int min) {
        double div;
        double drx;
        double dry;
        boolean b;
        //System.out.println(min);
        if ((goal.x>points[0].x-10&&this.x>points[0].x-10||goal.x<points[2].x+10&&this.x<points[2].x+10||this.GOAL!=null)||this.fly) {
            if (goal.y - this.y == 0)
                div = 1000;
            else
                div = Math.abs((goal.x - this.x) / (goal.y - this.y));
            //System.out.println(div);
            dry = this.speed / Math.sqrt(div * div + 1);
            //System.out.println("dry = "+dry);
            drx = dry * div;
            //System.out.println("drx = "+drx);
            if (goal.x - this.x < 0) {
                drx = -drx;
            }
            if (goal.y - this.y < 0) {
                dry = -dry;
            }
            if (this.r + this.w / 3 + goal.w / 3 < min) {
                if (!this.building) {
                    this.y += dry;
                    this.x += drx;
                }
                this.GOAL=null;
                b=true;
            }
            else {
                this.GOAL=goal;
                b=false;
            }
        }
        else {
            Point point = null;
            int minD = 5000;
            for (int i = 0; i < 4; i++) {
                if (this.getPointDistance(points[i])<minD) {
                    minD = this.getPointDistance(points[i]);
                    point = points[i];
                }
            }
            if (point.y - this.y == 0)
                div = 1000;
            else
                div = Math.abs((point.x - this.x) / (point.y - this.y));
            //System.out.println(div);
            dry = this.speed / Math.sqrt(div * div + 1);
            //System.out.println("dry = "+dry);
            drx = dry * div;
            //System.out.println("drx = "+drx);
            if (point.x - this.x < 0) {
                drx = -drx;
            }
            if (point.y - this.y < 0) {
                dry = -dry;
            }
            if (this.r + this.w / 3 + goal.w / 3 < min) {
                if (!this.building) {
                    this.y += dry;
                    this.x += drx;
                }
                this.GOAL=null;
                b=true;
            }
            else {
                b = false;
                this.GOAL=goal;
            }
        }
        if (this.type!=0) {
            if (dry > 0 && Math.abs(drx) < dry && (k % (120 / this.speed) >= 60 / this.speed && k % (120 / this.speed) < 90 / this.speed)) {
                s = this.type + "front2";
            } else if (dry > 0 && Math.abs(drx) < dry && k % (120 / this.speed) >= 30 / this.speed && k % (120 / this.speed) < 60 / this.speed) {
                s = this.type + "front1";
            }
            else if (dry > 0 && Math.abs(drx) < dry && (k % (120 / this.speed) >= 0 && (k % (120 / this.speed) < 30 / this.speed))) {
                s = this.type+"front4";
            }else if (dry > 0 && Math.abs(drx) < dry) {
                s = this.type + "front3";
            } else if (drx < 0 && Math.abs(dry) <= -drx &&  (k % (120 / this.speed) >= 60 / this.speed && k % (120 / this.speed) < 90 / this.speed)) {
                s = this.type + "left2";
            } else if (drx < 0 && Math.abs(dry) <= -drx && k % (120 / this.speed) >= 30 / this.speed && k % (120 / this.speed) < 60 / this.speed) {
                s = this.type + "left1";
            }
            else if (drx < 0 && Math.abs(dry) <= -drx && (k % (120 / this.speed) >= 0 && (k % (120 / this.speed) < 30 / this.speed))) {
                s = this.type+"left4";
            }else if (drx < 0 && Math.abs(dry) <= -drx) {
                s = this.type + "left3";
            } else if (dry <= 0 && Math.abs(drx) < -dry &&  (k % (120 / this.speed) >= 90 / this.speed && k % (120 / this.speed) < 90 / this.speed)) {
                s = this.type + "back2";
            } else if (dry <= 0 && Math.abs(drx) < -dry && k % (120 / this.speed) >= 30 / this.speed && k % (120 / this.speed) < 60 / this.speed) {
                s = this.type + "back1";
            }
            else if (dry <= 0 && Math.abs(drx) < -dry && (k % (120 / this.speed) >= 0 && (k % (120 / this.speed) < 30 / this.speed))) {
                s = this.type+"back4";
            }else if (dry <= 0 && Math.abs(drx) < -dry) {
                s = this.type + "back3";
            } else if (drx >= 0 && Math.abs(dry) <= drx &&  (k % (120 / this.speed) >= 60 / this.speed && k % (120 / this.speed) < 90 / this.speed)) {
                s = this.type + "right2";
            }
            else if (drx >= 0 && Math.abs(dry) <= drx && (k % (120 / this.speed) >= 0 && (k % (120 / this.speed) < 30 / this.speed))) {
                s = this.type + "right4";
            }else if (drx >= 0 && Math.abs(dry) <= drx && k % (120 / this.speed) >= 30 / this.speed && k % (120 / this.speed) < 60 / this.speed) {
                s = this.type + "right1";
            } else {
                s = this.type + "right3";
            }
            //System.out.println(this.s);
        }
        //System.out.println(b);
        return b;
    }
    public void attackImage(int k, int k1, String s) {
        if (this.type!=0) {
            //System.out.println(s);
            //System.out.println(substring);
            String thiss;
            if (s.charAt(1)!='a') {
                thiss = this.type + "a" + s.substring(1, s.length() - 1);
            }
            else {
                thiss=s.substring(0, s.length()-1);
            }
            if ((k+k1) % this.attackSpeed < this.attackSpeed / 4) {
                this.s = thiss + 1;
            } else if ((k+k1) % this.attackSpeed >= this.attackSpeed / 4 && (k+k1) % this.attackSpeed < this.attackSpeed / 2) {
                this.s = thiss + 2;
            } else if ((k+k1) % this.attackSpeed >= this.attackSpeed / 2 && (k+k1) % this.attackSpeed < this.attackSpeed * 3 / 4) {
                this.s = thiss + 3;
            } else {
                this.s = thiss + 4;
            }
        }
    }
    public void deathImage(String s) {
        if (this.type != 0) {
            String s0;
            if (s.charAt(1)=='a'&&s.charAt(s.length() - 2) != 'd') {
                s0 = s.substring(0, s.length() - 1) + "d";
                //System.out.println(s);
            }
            else if (s.charAt(1)!='a') {
                s0=this.type+"a"+s.substring(1, s.length() - 1)+"d";
            }
            else {
                s0 = s.substring(0, s.length() - 1);
            }
            if (this.death < 70) {
                this.s = s0 + 3;
            } else if (this.death >= 70 && this.death < 85) {
                this.s = s0 + 2;
            } else {
                this.s = s0 + 1;
            }
            this.death--;
            //System.out.println(this.s);
            //System.out.println(this.death);
        }
    }
}
