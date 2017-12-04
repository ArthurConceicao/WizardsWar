package br.com.hellmets.motocerol;

import java.util.ArrayList;

import br.com.hellmets.motocerol.AndGraph.AGGameManager;
import br.com.hellmets.motocerol.AndGraph.AGInputManager;
import br.com.hellmets.motocerol.AndGraph.AGScene;
import br.com.hellmets.motocerol.AndGraph.AGScreenManager;
import br.com.hellmets.motocerol.AndGraph.AGSoundManager;
import br.com.hellmets.motocerol.AndGraph.AGSprite;
import br.com.hellmets.motocerol.AndGraph.AGTimer;

/**
 * Created by Arthur on 13/11/2017.
 */

public class CenaJogo extends AGScene {

    ArrayList<AGSprite> listaSpritesChao = new ArrayList<>();
    ArrayList<AGSprite> listaSpritesPortal = new ArrayList<>();
    ArrayList<AGSprite> listaSpritesFireball = new ArrayList<>();
    AGSprite bg = null;
    AGSprite boss = null;
    AGSprite pauline = null;
    AGSprite wizard = null;
    AGSprite explosion = null;
    AGSprite som = null;
    AGSprite up, left, right, upOn, leftOn, rightOn = null;
    AGTimer timer, explosionTimer = null;
    int direcao = 5;
    int fireballSound = 0;
    float x = 0, y = 0;
    boolean estadoAnterior = false;

    public CenaJogo(AGGameManager manager){
        super(manager);
    }

    @Override
    public void init() {
        listaSpritesChao = new ArrayList<>();
        listaSpritesPortal = new ArrayList<>();
        listaSpritesFireball = new ArrayList<>();
        explosionTimer = null;
        AGSoundManager.vrMusic.loadMusic("bit_rush.mp3", true);
        AGSoundManager.vrMusic.play();
        criaCenario();
        criaPersonagens();
        criaSetas();
        criaSom();
        timer = new AGTimer(1000);
        fireballSound =  AGSoundManager.vrSoundEffects.loadSoundEffect("fireball.mp3");

    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {

        programaGravidade();
        programaSetas();
        timer.update();
        if (timer.isTimeEnded()){
            criaFireball();
            criaFireball();
            timer.restart();
        }
        if (explosionTimer != null){
            explosionTimer.update();
        }
        alternaSom();
        atualizaFireball();
        movimentaPrincesa();
        verificaFim();
    }

    //Método para reunir os métodos que criam o cenário
    public void criaCenario(){
        bg = createSprite(R.mipmap.bg1_transparent, 1, 1);
        bg.setScreenPercent(100, 100);
        bg.vrPosition.setY(AGScreenManager.iScreenHeight/2);
        bg.vrPosition.setX(AGScreenManager.iScreenWidth/2);

        criaChao(12, 60, 300);
        criaChao(8, 50, 580);
        criaChao(4, 300, 930);
        criaChao(12, 50, 1300);
        criaChao(4, 400, 1600);
        criaPortal(4, 200, 375);
        criaPortal(5, 400, 655);
        criaPortal(5, 600, 1005);
        criaPortal(4, 400, 1375);
    }

    public void criaSom(){
        som = createSprite(R.mipmap.sound_on, 1, 1);
        som.setScreenPercent(10,7);
        som.vrPosition.setX(AGScreenManager.iScreenWidth * 0.95f);
        som.vrPosition.setY(AGScreenManager.iScreenHeight * 0.95f);
    }

    //Método para alternar estado da música entre tocando ou pausada
    public void alternaSom(){
        if(AGInputManager.vrTouchEvents.screenClicked())
            if (som.collide(AGInputManager.vrTouchEvents.getLastPosition())){
                if (AGSoundManager.vrMusic.isPlaying()) {
                    AGSoundManager.vrMusic.pause();
                    AGSprite somDesligado = createSprite(R.mipmap.sound_off, 1, 1);
                    somDesligado.setScreenPercent(10, 7);
                    somDesligado.vrPosition.setX(AGScreenManager.iScreenWidth * 0.95f);
                    somDesligado.vrPosition.setY(AGScreenManager.iScreenHeight * 0.95f);
                    removeSprite(som);
                    som = somDesligado;
                }else{
                    AGSoundManager.vrMusic.play();
                    AGSprite somLigado = createSprite(R.mipmap.sound_on, 1, 1);
                    somLigado.setScreenPercent(10, 7);
                    somLigado.vrPosition.setX(AGScreenManager.iScreenWidth * 0.95f);
                    somLigado.vrPosition.setY(AGScreenManager.iScreenHeight * 0.95f);
                    removeSprite(som);
                    som = somLigado;
                }
            }
    }

    //Método para criar os personagens na tela
    public void criaPersonagens(){

        //Boss
        boss = createSprite(R.mipmap.boss, 4, 1);
        boss.addAnimation(1, true, 0,1);
        boss.vrPosition.setX(150);
        boss.vrPosition.setY(1430);

        //Mago
        wizard = createSprite(R.mipmap.wizard_walk, 3, 1);
        wizard.setScreenPercent(10, 7);
        wizard.vrPosition.setX(500);
        wizard.vrPosition.setY(350);
        wizard.addAnimation(1, true, 1);
        wizard.addAnimation(5, true, 0,1,2,1);

        //Princesa
        pauline = createSprite(R.mipmap.pauline, 4, 1);
        pauline.addAnimation(5, true, 2, 3);
        pauline.setScreenPercent(8, 8);
        pauline.vrPosition.setX(500);
        pauline.vrPosition.setY(1700);
    }

    //Método para movimentar a princesa de um lado para o outro
    public void movimentaPrincesa(){
        x = pauline.vrPosition.getX() + direcao;
        pauline.vrPosition.setX(x);
        if(x >= 650){
            direcao *= -1;
            pauline.iMirror = AGSprite.HORIZONTAL;
        }

        if(x <= 450){
            direcao *= -1;
            pauline.iMirror = AGSprite.NONE;
        }
    }

    //Método para criar os chãos do cenário
    public void criaChao(int tamanho, int x, int y){
        ArrayList<AGSprite> sprites = new ArrayList<>();
        for(int i = tamanho; i > 0; i--){
            AGSprite sprite = null;
            sprites.add(sprite);
        }

        for(AGSprite a : sprites){
            a = createSprite(R.mipmap.wood, 1, 1);
            a.setScreenPercent(9, 5);
            a.vrPosition.setX(x);
            a.vrPosition.setY(y);
            x += 95;
            listaSpritesChao.add(a);
        }

    }

    //Método para fazer com que o personagem seja afetado pela gravidade
    public void programaGravidade(){
        for(AGSprite a : listaSpritesChao){
            if (wizard.collide(a)){
                wizard.vrPosition.setY(a.vrPosition.getY() + 110);
            }else{
                wizard.vrPosition.setY(wizard.vrPosition.getY() - 1);
            }
        }
    }

    //Método para criar os portais do cenário
    public void criaPortal(int tamanho, int x, int y){
        ArrayList<AGSprite> sprites = new ArrayList<>();
        for(int i = tamanho; i > 0; i--){
            AGSprite sprite = null;
            sprites.add(sprite);
        }

        for(AGSprite a : sprites){
            a = createSprite(R.mipmap.portal, 2, 1);
            a.addAnimation(5, true, 0, 1);
            a.setScreenPercent(7, 5);
            a.vrPosition.setX(x);
            a.vrPosition.setY(y);
            y += 70;
            listaSpritesPortal.add(a);
        }
    }

    //Sobrescreve o método de renderização para que as setas sejam renderizadas por último
    @Override
    public void render(){
        super.render();
        left.render();
        up.render();
        right.render();
        leftOn.render();
        upOn.render();
        rightOn.render();

    }

    //Método para criar os botões
    public void criaSetas(){
        left = createSprite(R.mipmap.left_w, 1, 1);
        left.setScreenPercent(20, 10);
        left.vrPosition.setX(AGScreenManager.iScreenWidth/4 * 1);
        left.vrPosition.setY(AGScreenManager.iScreenHeight * 0.08f);
        left.bAutoRender = false;

        right = createSprite(R.mipmap.right_w, 1, 1);
        right.setScreenPercent(20, 10);
        right.vrPosition.setX(AGScreenManager.iScreenWidth/4 * 3);
        right.vrPosition.setY(AGScreenManager.iScreenHeight * 0.08f);
        right.bAutoRender = false;

        up = createSprite(R.mipmap.up_w, 1, 1);
        up.setScreenPercent(20, 10);
        up.vrPosition.setX(AGScreenManager.iScreenWidth/4 * 2);
        up.vrPosition.setY(AGScreenManager.iScreenHeight * 0.08f);
        up.bAutoRender = false;

        leftOn = createSprite(R.mipmap.left_b, 1, 1);
        leftOn.setScreenPercent(20, 10);
        leftOn.vrPosition.setX(AGScreenManager.iScreenWidth/4 * 1);
        leftOn.vrPosition.setY(AGScreenManager.iScreenHeight * 0.08f);
        leftOn.bVisible = false;
        leftOn.bAutoRender = false;

        rightOn = createSprite(R.mipmap.right_b, 1, 1);
        rightOn.setScreenPercent(20, 10);
        rightOn.vrPosition.setX(AGScreenManager.iScreenWidth/4 * 3);
        rightOn.vrPosition.setY(AGScreenManager.iScreenHeight * 0.08f);
        rightOn.bVisible = false;
        rightOn.bAutoRender = false;

        upOn = createSprite(R.mipmap.up_b, 1, 1);
        upOn.setScreenPercent(20, 10);
        upOn.vrPosition.setX(AGScreenManager.iScreenWidth/4 * 2);
        upOn.vrPosition.setY(AGScreenManager.iScreenHeight * 0.08f);
        upOn.bVisible = false;
        upOn.bAutoRender = false;
    }

    //Método para criar as fireballs na tela de maneira aleatória
    public void criaFireball() {
        for (AGSprite a : listaSpritesFireball) {
            if (a.bRecycled) {
                a.vrPosition.setY(AGScreenManager.iScreenHeight + 200);
                a.vrPosition.setX((int) Math.floor(Math.random() * AGScreenManager.iScreenWidth));
                a.bRecycled = false;
                AGSoundManager.vrSoundEffects.play(fireballSound);
                return;
            }
        }

        AGSprite fireball = createSprite(R.mipmap.magic_fire, 5, 1);
        fireball.addAnimation(5, true, 0, 4);
        fireball.setScreenPercent(7, 7);
        fireball.vrPosition.setX((int) Math.floor(Math.random() * AGScreenManager.iScreenWidth));
        fireball.vrPosition.setY(AGScreenManager.iScreenHeight + 200);
        fireball.bRecycled = false;
        listaSpritesFireball.add(fireball);
        AGSoundManager.vrSoundEffects.play(fireballSound);
    }

    //Método para fazer as fireballs caírem na tela
    public void atualizaFireball(){
        for(AGSprite a : listaSpritesFireball){
            a.vrPosition.setY(a.vrPosition.getY() - 20);
            if (a.vrPosition.getY() < 0){
                a.bRecycled = true;
            }
        }
    }

    //Método para fazer a animação do wizard andando
    public void atualizaWizard(boolean estado){
        //Verifica se deseja alterar de anxdando para parado ou de parado para andando
        if (estadoAnterior != estado){
            if (estado == true){//De parado para andando
                wizard.setCurrentAnimation(1);
            }else{//De andando para parado
                wizard.setCurrentAnimation(0);
            }

        }
        estadoAnterior = estado;
    }

    //Método para programar as ações quando as setas forem clicadas ou pressionadas
    public void programaSetas(){

        //Seta para a esquerda
        if (AGInputManager.vrTouchEvents.screenDown()){
            if(left.collide(AGInputManager.vrTouchEvents.getLastPosition())){
                atualizaWizard(true);
                wizard.iMirror = AGSprite.NONE;
                wizard.vrPosition.setX(wizard.vrPosition.getX() - 10);
                leftOn.bVisible = true;
            }
        }else{
            atualizaWizard(false);
            leftOn.bVisible = false;
        }


        //Seta para a direita
        if (AGInputManager.vrTouchEvents.screenDown()){
            if(right.collide(AGInputManager.vrTouchEvents.getLastPosition())){
                atualizaWizard(true);
                wizard.iMirror = AGSprite.HORIZONTAL;
                wizard.vrPosition.setX(wizard.vrPosition.getX() + 10);
                rightOn.bVisible = true;
            }
        }else{
            atualizaWizard(false);
            rightOn.bVisible = false;
        }

        //Seta para cima
        if (AGInputManager.vrTouchEvents.screenClicked()) {
            if (up.collide(AGInputManager.vrTouchEvents.getLastPosition())){
                for (AGSprite a : listaSpritesPortal) {
                    if (wizard.collide(a)) {
                        wizard.vrPosition.setY(a.vrPosition.getY() + AGScreenManager.iScreenHeight * 0.1f);
                    }
                }
                upOn.bVisible = true;
            }
        }else{
            upOn.bVisible = false;
        }
    }

    //Método para verificar as condições que finalizam o jogo
    public void verificaFim(){
        if (wizard.collide(pauline)) {
            vrGameManager.setCurrentScene(3);
            AGSoundManager.vrMusic.stop();
            return;
        }
        explodeFireball();
    }

    //Método para verificar se houve contato entre alguma fireball e o mago
    public void explodeFireball(){
        for (AGSprite a : listaSpritesFireball) {
            if (wizard.collide(a.vrPosition.getX(), a.vrPosition.getY() - 20) || wizard.collide(boss) || wizard.vrPosition.getY() < 0) {
                if (explosionTimer == null){
                    explosion = createSprite(R.mipmap.explosion, 3, 1);
                    explosion.setScreenPercent(15, 10);
                    explosion.vrPosition.setX(wizard.vrPosition.getX());
                    explosion.vrPosition.setY(wizard.vrPosition.getY());
                    explosion.addAnimation(2, true, 0, 2 );
                    explosionTimer = new AGTimer(3000);
                    wizard.fadeOut(2500);
                }
            }
        }
        if (explosionTimer != null){
            if (explosionTimer.isTimeEnded()){
                AGSoundManager.vrMusic.stop();
                vrGameManager.setCurrentScene(2);
                return;
            }
        }

    }
}
