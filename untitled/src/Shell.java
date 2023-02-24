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
    public Shell(int type, int command, int x, int y) {

        this.type = type;
        this.command = command;
        this.x = x;
        this.y = y;
        if (this.type==2) {
            this.dd = 400;
            this.speed = 15;
            this.w = 5;
            this.h = 5;
        }
        else if (this.type==0) {
            this.dd=100;
            this.speed = 15;
            this.w=5;
            this.h=5;
        }
    }
}
