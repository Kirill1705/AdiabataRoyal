public class Shell {
    public int x;
    public int y;
    public  int w;
    public  int h;
    public int speed;
    public String s;
    public int type;
    public int dd;
    public int command;
    public Unit goal;
    public  boolean splash;
    public  int r;
    public int l;
    public Shell(int type, int command, int x, int y, Unit goal) {

        this.type = type;
        this.command = command;
        this.x = x;
        this.y = y;
        this.goal=goal;
        if (this.type==2) {
            this.dd = 400;
            this.speed = 60;
            this.w = 30;
            this.h = 30;
            this.s="";
            this.splash=false;
        }
        else if (this.type==0) {
            this.dd=100;
            this.speed = 60;
            this.w=45;
            this.h=45;
            this.splash=false;
        }
        else if (this.type==5) {
            this.dd=180;
            this.speed=30;
            this.w=30;
            this.h=30;
            this.splash=true;
            this.r=50;
            this.l=1;
        }
    }
    public boolean isEnemy(Unit unit) {
        return this.command!=unit.command&&(!unit.fly&&this.l!=3||unit.fly&&(this.l==1||this.l==3));
    }
    public boolean shelldr() {
        if (goal==null) {
            return false;
        }
        else if (Math.abs(this.x-goal.x)<30&&Math.abs(this.y-goal.y)<30) {
            goal.hp-=this.dd;
            return false;
        }
        else {
            double div;
            double dry;
            double drx;
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
            this.x += drx;
            if (goal.y - this.y < 0) {
                dry = -dry;
            }
            this.y += dry;
            if(drx>0&&Math.abs(dry)<2*drx) {
                if(drx>Math.abs(2*dry)) {
                    s=this.type+"right";
                }
                else if(dry>0) {
                    s=this.type+"rightdown";
                }
                else {
                    s=this.type+"topright";
                }
            }
            else if(drx<0&&Math.abs(dry)<-2*drx) {
                if(-drx>Math.abs(2*dry)) {
                    s=this.type+"left";
                }
                else if(dry>0) {
                    s=this.type+"downleft";
                }
                else {
                    s=this.type+"lefttop";
                }
            }
            else if(dry>0&&dry>Math.abs(2*drx)) {
                s=this.type+"top";
            }
            else {
                s=this.type+"down";
            }
            //System.out.println(this.x+" "+this.y);
            return true;
        }
    }
}
