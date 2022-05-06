package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainController {

    private List<String> spamWords = Arrays.asList(
            "Free" , "win" , "receive" , "free message", "now!", "winner" , "you have been selected" , "prize" , "claim" , "cash" , "URGENT" , "ringtone" , "please" , "call us" , "congrats" , "trip" , "won" , "bonus" , "please call" , "contact us"
    );

    @FXML
    private Label etat;

    @FXML
    private Label nbrMots;

    @FXML
    private Label nbrMotsSpam;

    @FXML
    private WebView resultView;

    @FXML
    private TextArea textArea;

    @FXML
    private Button verifyBtn;

    @FXML
    void verifiyText(ActionEvent event) {
        String inputText = textArea.getText();
        nbrMots.setText(String.valueOf(countWords(inputText)));
        detectSpamWords(inputText);
    }

    private int countWords(String s){

        int wordCount = 0;
        boolean word = false;
        int endOfLine = s.length() - 1;

        for (int i = 0; i < s.length(); i++) {
            // Si le caractere est une lettre => word = true
            if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
                word = true;
            } else if (!Character.isLetter(s.charAt(i)) && word) {
                wordCount++;
                word = false;
            } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
                wordCount++;
            }
        }
        return wordCount;
    }

    private  long countingWord(ArrayList<String> list, String findWord)
    {
        int counter = 0;
        for(String i:list){
            if(i.equals(findWord)) counter++;
        }
        return counter;
    }

    private void detectSpamWords(String text){
        ArrayList<String> output = new ArrayList(Arrays.asList(text.toLowerCase().split("[\\s,]+")));
        int spamNbr = 0;
        for(int i = 0;i<spamWords.size();i++){
            String spamWord = spamWords.get(i).toLowerCase();
            spamNbr+= countingWord(output,spamWord);
            output.replaceAll(e -> e.equals(spamWord)?"<font Color='red'>"+spamWord+"</font>":e);
        }
        System.out.println(output);
        WebEngine engine = resultView.getEngine();
        engine.loadContent("<p>"+String.join(" ",output)+"<p/>");
        nbrMotsSpam.setText(String.valueOf(spamNbr));

        etat.setText(spamNbr>0?"Spam":"Secure");
        etat.setStyle(spamNbr>0?"-fx-text-fill: #ff4848":"-fx-text-fill: #48ff48");
    }


}