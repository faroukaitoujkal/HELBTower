public class MagicCape extends CollectibleGameElement {

    public MagicCape(int posX, int posY){
        super(posX, posY, new String[]{"/img/magic_cape.png"});
    }

    @Override
    public void triggerAction(HELBTowerFXGame gameBoard){

        gameBoard.activateMagicCape();
        
        setPosX(gameBoard.getVoidX()) ; 
        setPosY(gameBoard.getVoidY()) ;
    }
}
