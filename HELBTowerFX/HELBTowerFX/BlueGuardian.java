public class BlueGuardian extends Guardian {
    
    public BlueGuardian(int posX, int posY) {
        super(posX, posY,"/img/blue_guardian.png");
    }
    
    @Override
    public void moveGuardian(HELBTowerFXGame gameBoard) {
        if (gameBoard.getCanBlueGuardianMove()) {
            
        }
    }

}
