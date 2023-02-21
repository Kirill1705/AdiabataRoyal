import java.awt.*;

public class Unit {
    public String s;
    public int x;
    public int y;
    public int w;
    public int h;
    public int hp;
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
    public Unit(int type, int command, int x,int y) {
        if(type==1) {
            this.x=x-25;
            this.y=y-25;
            this.w=50;
            this.h=70;
            this.hp=200;
            this.dd=46;
            this.speed=6;
            this.attackSpeed=25;
            this.r=10;
            this.type=1;
            this.command=command;
            this.fly=false;
            this.k1=-1;
            this.splash=false;
            this.cost=2;
            this.s="1front1";
            this.death=100;
        }
        if(type==0) {
            this.x=x;
            this.y=y;
            this.w=150;
            this.h=150;
            this.hp=5000;
            this.dd=100;
            this.speed=0;
            this.attackSpeed=40;
            this.r=400;
            this.type=0;
            this.command=command;
            this.fly=false;
            this.k1=-1;
            this.splash=false;
            this.cost=0;
        }

    }
    public boolean isEnemy(Unit unit) {
        return this.command!=unit.command;
    }
    public int getDistance(Unit unit) {
        return (int)Math.sqrt((this.x-unit.x)*(this.x-unit.x)+(this.y-unit.y)*(this.y-unit.y));
    }
    public int getPointDistance(Point point) {
        return (int)Math.sqrt((this.x-point.x)*(this.x-point.x)+(this.y-point.y)*(this.y-point.y));
    }
    public void drx(Unit goal, Point[] points, int k) {
        double div;
        double drx;
        double dry;
        if (goal.x>points[0].x-10&&this.x>points[0].x-10||goal.x<points[2].x+10&&this.x<points[2].x+10) {
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
            this.x+=drx;
            if (goal.y - this.y < 0) {
                dry = -dry;
            }
            this.y+=dry;
        }
        else {
            //int minD = 5000;
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
            this.x+=drx;
            if (point.y - this.y < 0) {
                dry = -dry;
            }
            this.y+=dry;
        }
        if (this.type!=0) {
            if (dry > 0 && Math.abs(drx) <= dry && (k % (360 / this.speed) >= 0 && (k % (360 / this.speed) < 90 / this.speed) || (k % (360 / this.speed) >= 180 / this.speed && k % (360 / this.speed) < 270 / this.speed))) {
                s = this.type + "front1";
            } else if (dry > 0 && Math.abs(drx) <= dry && k % (360 / this.speed) >= 90 / this.speed && k % (360 / this.speed) < 180 / this.speed) {
                s = this.type + "front2";
            } else if (dry > 0 && Math.abs(drx) <= dry) {
                s = this.type + "front3";
            } else if (drx < 0 && Math.abs(dry) <= -drx && (k % (360 / this.speed) >= 0 && (k % (360 / this.speed) < 90 / this.speed) || (k % (360 / this.speed) >= 180 / this.speed && k % (360 / this.speed) < 270 / this.speed))) {
                s = this.type + "left1";
            } else if (drx < 0 && Math.abs(dry) <= -drx && k % (360 / this.speed) >= 90 / this.speed && k % (360 / this.speed) < 180 / this.speed) {
                s = this.type + "left2";
            } else if (drx < 0 && Math.abs(dry) <= -drx) {
                s = this.type + "left3";
            } else if (dry <= 0 && Math.abs(drx) < -dry && (k % (360 / this.speed) >= 0 && (k % (360 / this.speed) < 90 / this.speed) || (k % (360 / this.speed) >= 180 / this.speed && k % (360 / this.speed) < 270 / this.speed))) {
                s = this.type + "back1";
            } else if (dry <= 0 && Math.abs(drx) < -dry && k % (360 / this.speed) >= 90 / this.speed && k % (360 / this.speed) < 180 / this.speed) {
                s = this.type + "back2";
            } else if (dry <= 0 && Math.abs(drx) < -dry) {
                s = this.type + "back3";
            } else if (drx >= 0 && Math.abs(dry) < drx && (k % (360 / this.speed) >= 0 && (k % (360 / this.speed) < 90 / this.speed) || (k % (360 / this.speed) >= 180 / this.speed && k % (360 / this.speed) < 270 / this.speed))) {
                s = this.type + "right1";
            } else if (drx >= 0 && Math.abs(dry) < drx && k % (360 / this.speed) >= 90 / this.speed && k % (360 / this.speed) < 180 / this.speed) {
                s = this.type + "right2";
            } else {
                s = this.type + "right3";
            }
        }
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
            if (k % this.attackSpeed+k1 < this.attackSpeed / 4) {
                this.s = thiss + 1;
            } else if (k % this.attackSpeed+k1 >= this.attackSpeed / 4 && k % this.attackSpeed+k1 < this.attackSpeed / 2) {
                this.s = thiss + 2;
            } else if (k % this.attackSpeed+k1 >= this.attackSpeed / 2 && k % this.attackSpeed+k1 < this.attackSpeed * 3 / 4) {
                this.s = thiss + 3;
            } else {
                this.s = thiss + 4;
            }
            //System.out.println(this.s);
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
        }
    }
}
