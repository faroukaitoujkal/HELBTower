package com.example.helbtower;

public class Coin extends GameElement {
    
    public Coin(int posX, int posY){
        super(posX, posY, new String[]{"/img/ic_coin.png"});
    }
    
    @Override
    public void triggerAction(HELBTowerFXGame gameBoard){
    
        gameBoard.decreaseCoinCounter();
        if (gameBoard.getCoinCounter() == 0){
            gameBoard.setGameOver();
        }
        setPosX(gameBoard.getVoidX()) ; 
        setPosY(gameBoard.getVoidY()) ;
    }
}





