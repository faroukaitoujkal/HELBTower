public class Hero extends NotCollectibleGameElement {

    private int speed;
    
    public Hero(int posX, int posY) {
        super(posX, posY, new String[]{"/img/hero.png"});
        this.speed = 1;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
