package fr.cejuba.stardew.main;

import java.io.*;

public class Config {
    private final GamePanel gamePanel;

    public Config(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void saveConfig(){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("config.txt"));

            // Full Screen
            if(gamePanel.fullScreenOn){
                bufferedWriter.write("ON");
            }
            else{
                bufferedWriter.write("OFF");
            }
            bufferedWriter.newLine();

            // Music Volume
            bufferedWriter.write(Integer.toString(gamePanel.music.getVolumeScale()));
            bufferedWriter.newLine();

            // SoundEffect Volume
            bufferedWriter.write(Integer.toString(gamePanel.soundEffect.getVolumeScale()));
            bufferedWriter.newLine();

            bufferedWriter.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("config.txt"));

            String s = bufferedReader.readLine();

            // Full Screen
            gamePanel.fullScreenOn = s.equals("ON");

            // Music Volume
            s = bufferedReader.readLine();
            gamePanel.music.setVolumeScale(Integer.parseInt(s));

            // SoundEffect Volume
            s = bufferedReader.readLine();
            gamePanel.soundEffect.setVolumeScale(Integer.parseInt(s));

            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
