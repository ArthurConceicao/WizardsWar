package br.com.hellmets.motocerol;

import br.com.hellmets.motocerol.AndGraph.AGAccelerometer;
import br.com.hellmets.motocerol.AndGraph.AGGameManager;
import br.com.hellmets.motocerol.AndGraph.AGInputManager;
import br.com.hellmets.motocerol.AndGraph.AGScene;
import br.com.hellmets.motocerol.AndGraph.AGScreenManager;
import br.com.hellmets.motocerol.AndGraph.AGSprite;
import br.com.hellmets.motocerol.AndGraph.AGTouchScreen;

/**
 * Created by Arthur on 02/12/2017.
 */

public class CenaMenu extends AGScene {

    AGSprite title = null;
    AGSprite botaoJogar= null;
    AGSprite bg = null;

    public CenaMenu(AGGameManager manager){
        super(manager);
    }

    @Override
    public void init() {
        setSceneBackgroundColor(0,0,0);

        bg = createSprite(R.mipmap.bg1, 1 , 1);
        bg.setScreenPercent(100, 100);
        bg.vrPosition.setY(AGScreenManager.iScreenHeight/2);
        bg.vrPosition.setX(AGScreenManager.iScreenWidth/2);

        title = createSprite(R.mipmap.title, 1 , 1);
        title.setScreenPercent(80, 15);
        title.vrPosition.setY(AGScreenManager.iScreenHeight * 0.35f);
        title.vrPosition.setX(AGScreenManager.iScreenWidth/2);

        botaoJogar = createSprite(R.mipmap.botaonovojogo, 1 , 1);
        botaoJogar.setScreenPercent(100, 20);
        botaoJogar.vrPosition.setY(AGScreenManager.iScreenHeight * 0.15f);
        botaoJogar.vrPosition.setX(AGScreenManager.iScreenWidth/2);
    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {
        if (AGInputManager.vrTouchEvents.screenClicked()){
            if (botaoJogar.collide(AGInputManager.vrTouchEvents.getLastPosition())){
                vrGameManager.setCurrentScene(1);
                return;
            }
        }

    }
}
