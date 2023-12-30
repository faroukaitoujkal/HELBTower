public abstract class Guardian extends NotCollectibleGameElement {

    public Guardian(int posX, int posY, String imagePaths ) {
        super(posX, posY, new String[]{imagePaths});
    }
     
    public abstract void moveGuardian(HELBTowerFXGame gameBoard);
     
}
