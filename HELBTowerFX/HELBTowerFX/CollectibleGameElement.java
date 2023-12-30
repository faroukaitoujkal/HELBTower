public abstract class CollectibleGameElement {

    private int posX;
    private int posY;

    private String name;
    protected final String[] IMAGE_PATHS ;
    

    public CollectibleGameElement(int posX, int posY, String[] imagePaths){
        this.posX = posX ; 
        this.posY = posY ;
        this.IMAGE_PATHS = imagePaths;
    }

    public CollectibleGameElement(int posX, int posY, String[] imagePaths, String name) {
        this.posX = posX ; 
        this.posY = posY ;
        this.IMAGE_PATHS = imagePaths;
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getPosX(){return posX;}
    
    public void setPosX(int newPosX){posX = newPosX;}
    
    public int getPosY(){return posY;}
    
    public void setPosY(int newPosY){posY = newPosY;}
    
    public String getPathToImage(){return IMAGE_PATHS[0];}
    
    public String getPathToImage(int index){return IMAGE_PATHS[index];}
    
    public int getPathToImageLen(){return IMAGE_PATHS.length;}
    
    public abstract void triggerAction(HELBTowerFXGame gameBoard);
}






