public class Potion extends CollectibleGameElement {

    private int currentImageIndex;
    private int typePotion;
    private int duration; 

    public Potion(int posX, int posY, int typePotion, String name) { 
        super(posX, posY, new String[]{"/img/yellow_potion.png", "/img/orange_potion.png", "/img/red_potion.png"}, name);
        this.typePotion = typePotion;

        if (typePotion == 0) {
            this.currentImageIndex  = 0;
            this.duration = 3;
        } else if (typePotion == 1) {
            this.currentImageIndex  = 1;
            this.duration = 5;
        } else {
            this.currentImageIndex  = 2;
            this.duration = 10;
        }
    }
    
    @Override
    public String getPathToImage(){
        return IMAGE_PATHS[currentImageIndex];
    }

    @Override
    public void triggerAction(HELBTowerFXGame gameBoard){

    if (typePotion == 0) {
        System.out.println("potion jaune manger" + duration);
        gameBoard.applySpeedBoost(duration);
    } else if (typePotion  == 1) {
        System.out.println("potion orange manger" + duration);
        gameBoard.applySpeedBoost(duration);
    } else {
        System.out.println("potion rouge manger" + duration);
        gameBoard.applySpeedBoost(duration);
    }

    setPosX(gameBoard.getVoidX()); 
    setPosY(gameBoard.getVoidY());
    }
}
