//Aplication package
package br.com.hellmets.motocerol;

//Used Packages
import android.os.Bundle;
import br.com.hellmets.motocerol.AndGraph.AGActivityGame;
import br.com.hellmets.motocerol.AndGraph.AGGameManager;


public class Game extends AGActivityGame
{
    public void onCreate(Bundle saved)
    {
        super.onCreate(saved);

        this.vrManager = new AGGameManager(this, false);

        CenaMenu menu = new CenaMenu(vrManager);
        CenaJogo jogo = new CenaJogo(vrManager);
        CenaDerrota derrota = new CenaDerrota(vrManager);
        CenaVitoria vitoria = new CenaVitoria(vrManager);

        vrManager.addScene(menu);
        vrManager.addScene(jogo);
        vrManager.addScene(derrota);
        vrManager.addScene(vitoria);
    }
}
