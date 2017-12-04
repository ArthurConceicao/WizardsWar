package br.com.hellmets.motocerol;

import br.com.hellmets.motocerol.AndGraph.AGGameManager;
import br.com.hellmets.motocerol.AndGraph.AGInputManager;
import br.com.hellmets.motocerol.AndGraph.AGScene;
import br.com.hellmets.motocerol.AndGraph.AGScreenManager;
import br.com.hellmets.motocerol.AndGraph.AGSoundManager;
import br.com.hellmets.motocerol.AndGraph.AGSprite;
import br.com.hellmets.motocerol.AndGraph.AGTimer;

/**
 * Created by Arthur on 26/11/2017.
 */

public class CenaVitoria extends AGScene {

    AGSprite vitoria, voltar = null;
    AGTimer timer = null;

    public CenaVitoria(AGGameManager manager){
        super(manager);
    }

    @Override
    public void init() {
        setSceneBackgroundColor(0,1,0);
        AGSoundManager.vrMusic.loadMusic("victory.mp3", false);
        AGSoundManager.vrMusic.play();

        vitoria = createSprite(R.mipmap.vitoria, 1, 1);
        vitoria.setScreenPercent(100, 30);
        vitoria.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/2);

        voltar = createSprite(R.mipmap.botaomenuinicial, 1, 1);
        voltar.setScreenPercent(100, 20);
        voltar.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/8);

        timer = new AGTimer(1000);
    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {
        timer.update();
        if (timer.isTimeEnded()){
            if (AGInputManager.vrTouchEvents.screenClicked()){
                if (voltar.collide(AGInputManager.vrTouchEvents.getLastPosition())){
                    AGSoundManager.vrMusic.stop();
                    vrGameManager.setCurrentScene(0);
                    return;
                }
            }
        }

    }
}
