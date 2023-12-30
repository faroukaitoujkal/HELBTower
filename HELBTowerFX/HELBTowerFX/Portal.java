public class Portal extends NotCollectibleGameElement {

    private int destinationX;
    private int destinationY;
    private int index;

    public Portal(int posX, int posY, int destinationX, int destinationY, String color, String name) {
        super(posX, posY, new String[]{"/img/portal_blue.png", "/img/portal_green.png"}, name);
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        
        if (color == "blue") {
            this.index = 0;
        } else {
            this.index = 1;
        }
    }

    public int getDestX() {
        return this.destinationX;
    }

    public int getDestY() {
        return this.destinationY;
    }
    
    @Override
    public String getPathToImage() {
        return IMAGE_PATHS[index];
    }

}


















/*public class Portal extends GameElement {

    private int destX;
    private int destY;
    private int ROWS = 20;
    private int COLUMNS = 20;
    private int currentImageIndex;
    private final String portalImagePath;

    public Portal(int posX, int posY, int destX, int destY) {
        super(posX, posY, new String[]{"/img/portal_blue.png", 
        "/img/portal_green.png"}); 
        this.destX = destX;
        this.destY = destY;
        this.currentImageIndex = 0;

    }

    public void setCurrentImageIndex(int index) {
        if (index >= 0 && index < getPathToImageLen()) {
            currentImageIndex = index;
        } else {
            // Gérer une valeur incorrecte, vous pouvez lancer une exception ou choisir une valeur par défaut.
            System.out.println("Index incorrect. Utilisation de l'index par défaut.");
            currentImageIndex = 0;
        }
    }

    @Override
    public String getPathToImage() {
        return getPathToImage(currentImageIndex);
    }

    @Override
    public void triggerAction(HELBTowerFXGame gameBoard) {
    int rows = gameBoard.getRows();
    int columns = gameBoard.getColumns();

    // Vérifier si les coordonnées de destination sont valides
    if (destX >= 0 && destX < rows && destY >= 0 && destY < columns) {
        gameBoard.getHeroPosition().x = destX;
        gameBoard.getHeroPosition().y = destY;
    }
}

}*/

