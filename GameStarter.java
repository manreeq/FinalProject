


public class GameStarter {
    
    public static void main(String[] args) {
        GameFrame gf = new GameFrame();
        gf.connectToServer();
        gf.setUpGUI();
        gf.addKeyBindings();
    }

    
}
