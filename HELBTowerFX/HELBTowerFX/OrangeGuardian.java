import java.util.Random;

public class OrangeGuardian extends Guardian {

    public OrangeGuardian(int posX, int posY) {
        super(posX, posY,"/img/orange_guardian.png");
    }
    
    @Override
    public void moveGuardian(HELBTowerFXGame gameBoard) {

        if (gameBoard.getCanOrangeGuardianMove()) {
            Random rand = new Random();
            int randDirection = rand.nextInt(4);

            // la condition vérifie si la prochain position en x ou en y est en mur ou pas et aussi elle check pour que le gardien ne dépasse pas les bordures
            if (randDirection == 0 && !gameBoard.isWall(this.getPosX() + 1, this.getPosY()) && !gameBoard.isTower(this.getPosX() + 1, this.getPosY()) && this.getPosX() < gameBoard.view.getRows() - 1) {
                    this.setPosX(this.getPosX() + 1); // droite      
            } else if (randDirection == 1 && !gameBoard.isWall(this.getPosX() - 1, this.getPosY()) && !gameBoard.isTower(this.getPosX() - 1, this.getPosY()) &&this.getPosX() > 0) {
                    this.setPosX(this.getPosX() - 1); // gauche        
            } else if (randDirection == 2 && !gameBoard.isWall(this.getPosX(), this.getPosY() - 1) && !gameBoard.isTower(this.getPosX(), this.getPosY() - 1) && this.getPosY() > 0) {
                    this.setPosY(this.getPosY() - 1); //en haut            
            } else {
                if (!gameBoard.isWall(this.getPosX(), this.getPosY() + 1) && !gameBoard.isTower(this.getPosX(), this.getPosY() + 1) && this.getPosY() < gameBoard.view.getColumns() -1) {
                    this.setPosY(this.getPosY() + 1); // en bas
                }             
            }
        } 
    }

}
