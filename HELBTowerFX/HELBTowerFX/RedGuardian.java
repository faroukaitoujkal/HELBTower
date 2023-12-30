public class RedGuardian extends Guardian {

    private int lastHeroPosX;
    private int lastHeroPosY;

    public RedGuardian(int posX, int posY) {
        super(posX, posY,"/img/red_guardian.png");
    }
    
    @Override
    public void moveGuardian(HELBTowerFXGame gameBoard) {
        if (gameBoard.getCanRedGuardianMove()) {
            if (this.getPosX() < gameBoard.getHero().getPosX()) {
                if ((!gameBoard.isWall(this.getPosX() + 1, this.getPosY())) && !gameBoard.isTower(this.getPosX() + 1, this.getPosY())) {
                    if ((this.getPosX() < gameBoard.view.getRows() - 1)) {
                        this.setPosX(this.getPosX() + 1); // Se déplace vers la droite
                        lastHeroPosX = gameBoard.getHero().getPosX();
                        lastHeroPosY = gameBoard.getHero().getPosY();
                    }
                }
                
            } else if (this.getPosX() > gameBoard.getHero().getPosX()) {
                if ((!gameBoard.isWall(this.getPosX() - 1, this.getPosY())) && !gameBoard.isTower(this.getPosX() - 1, this.getPosY()) ) {
                    if ((this.getPosX() > 0)) {
                        this.setPosX(this.getPosX() - 1); // Se déplace vers la gauche
                        lastHeroPosX = gameBoard.getHero().getPosX();
                        lastHeroPosY = gameBoard.getHero().getPosY();
                    }
                    
                }
                
            } else if (this.getPosY() < gameBoard.getHero().getPosY()) {
                if ((!gameBoard.isWall(this.getPosX(), this.getPosY() - 1)) && !gameBoard.isTower(this.getPosX(), this.getPosY() - 1)) {
                    if ((this.getPosY() < gameBoard.view.getColumns() - 1)) {
                        this.setPosY(this.getPosY() + 1); // Se déplace vers le bas
                        lastHeroPosX = gameBoard.getHero().getPosX();
                        lastHeroPosY = gameBoard.getHero().getPosY();
                    }
                }
                
            } else if (this.getPosY() > gameBoard.getHero().getPosY()) {
                if ((!gameBoard.isWall(this.getPosX(), this.getPosY() + 1)) && !gameBoard.isTower(this.getPosX(), this.getPosY() + 1)) {
                    if ((this.getPosY() > 0)) {
                        this.setPosY(this.getPosY() - 1); // Se déplace vers le haut
                        lastHeroPosX = gameBoard.getHero().getPosX();
                        lastHeroPosY = gameBoard.getHero().getPosY();
                    }      
                }
                
            } /*else {

                if (lastHeroPosX < this.getPosX() && (!gameBoard.isWall(this.getPosX() + 1, this.getPosY())) && (this.getPosX() < gameBoard.view.getRows() - 1)) {
                    this.setPosX(this.getPosX() - 1);
                } else if (lastHeroPosX > this.getPosX() && (!gameBoard.isWall(this.getPosX() - 1, this.getPosY())) && (this.getPosX() > 0)) {
                    this.setPosX(this.getPosX() + 1);
                } else if (lastHeroPosY < this.getPosY() && (!gameBoard.isWall(this.getPosX(), this.getPosY() - 1)) && (this.getPosY() < gameBoard.view.getColumns() - 1)) {
                    this.setPosY(this.getPosY() - 1);
                } else if (lastHeroPosY > this.getPosY() && (!gameBoard.isWall(this.getPosX(), this.getPosY() + 1)) && (this.getPosY() > 0)) {
                    this.setPosY(this.getPosY() + 1);
                }
            }*/
        }
    }
    
}
