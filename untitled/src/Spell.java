public class Spell {
    public int r;
    public int t;
    public int type;
    public  int dd;
    public int x;
    public  int y;
    public String s;
    public int cost;
    public int attackSpeed;
    public  int command;
    public Spell(int type,int command, int x, int y) {
        if (type==1) {
            this.x=x;
            this.y=y;
            this.command=command;
            this.type=type;
            this.dd=400;
            this.r=100;
            this.t=40;
            this.s = "untitled//src//fireball.png";
            this.cost = 4;
            this.attackSpeed=40;
        }
    }
    public boolean canAttack(Unit unit) {
        return (int)Math.sqrt((this.x-unit.x)*(this.x-unit.x)+(this.y-unit.y)*(this.y-unit.y))<this.r;
    }
    public boolean isEnemy(Unit unit) {
        return this.command!=unit.command;
    }
}
