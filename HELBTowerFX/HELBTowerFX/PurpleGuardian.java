public class PurpleGuardian extends Guardian {
    
    private int speed;
    private int timeCounter;

    public PurpleGuardian(int posX, int posY) {
        super(posX, posY,"/img/purple_guardian.png");
        this.speed = 1;
        this.timeCounter = 0;
    }
    
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    @Override
    public void moveGuardian(HELBTowerFXGame gameBoard) {
        if (gameBoard.getCanPurpleGuardianMove()) {
            if (timeCounter >= 20) {
                Wall targetTower =gameBoard.getRandomTower();                // Si le gardien a atteint la tour cible, choisir une nouvelle tour cible
                

                // Calculer la direction vers la tour
                int distanceX = targetTower.getPosX() - this.getPosX();
                int distanceY = targetTower.getPosY() - this.getPosY();

                // Normaliser la direction
                double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
                double directionX = distanceX / distance;
                double directionY = distanceY / distance;

                // Déplacer graduellement vers la tour
                this.setPosX(getPosX() + (int) (speed * directionX));
                this.setPosY(getPosY() + (int) (speed * directionY));

                timeCounter = 0;
                if (this.getPosX() == targetTower.getPosX() && this.getPosY() == targetTower.getPosY()) {
                    targetTower = gameBoard.getRandomTower();
                    System.out.println("zer");
                }
            }
            timeCounter++;
        }

        // Ajuster la vitesse en fonction du nombre de pièces ramassées par le joueur
        setSpeed(getSpeed() + gameBoard.getCollectedCoins());
    }
}