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

public class CenaDerrota extends AGScene {

    AGSprite derrota, voltar = null;
    AGTimer timer = null;

    public CenaDerrota(AGGameManager manager){
        super(manager);
    }

    @Override
    public void init() {
        setSceneBackgroundColor(1,0,0);
        derrota = createSprite(R.mipmap.derrota, 1, 1);
        derrota.setScreenPercent(100, 30);
        derrota.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/2);

        AGSoundManager.vrMusic.loadMusic("derrota.mp3", false);
        AGSoundManager.vrMusic.play();

        timer = new AGTimer(15000);

        voltar = createSprite(R.mipmap.botaomenuinicial, 1, 1);
        voltar.setScreenPercent(100, 20);
        voltar.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/8);
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
            if (voltar.collide(AGInputManager.vrTouchEvents.getLastPosition())){
                AGSoundManager.vrMusic.stop();
                vrGameManager.setCurrentScene(0);
                return;
            }
        }
        timer.update();
        if (timer.isTimeEnded()){
            vrGameManager.setCurrentScene(0);
            return;
        }
    }
}
