package main.GUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.WindowEvent;
import main.dataBaseHelper.DBExam;
import main.dataBaseHelper.DBQustion;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import main.Main;


public class ExamController implements Initializable {
    ArrayList<DBQustion> Qlist ;
    DBQustion dbQ ;
    int currIndex , mxIndex ;
    @FXML
    JFXTextArea questionTxt ;
    @FXML
    JFXCheckBox choice1 ,choice2, choice3 , choice4 ;
    @FXML
    Label qNo, timeLeft ;
    @FXML
    JFXButton startBtn ;
    public int TotalS ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       dbQ  = new DBQustion();
       Qlist  = new ArrayList<>(dbQ.getByExamId("z"));
       mxIndex = Qlist.size() ;
       currIndex = 0;
    }

    @FXML
    public void startTimer(ActionEvent event){
        choice1.setDisable(true);
        choice2.setDisable(true);
        choice3.setDisable(true);
        choice4.setDisable(true);
        SetQuestion(Qlist.get(currIndex));
        qNo.setText("1");
        DBExam e = new DBExam();
        timeLeft.setText(e.getById("z").durationTime);
        String [] time = e.getById("z").durationTime.split(":");
        int H = Integer.parseInt(time[0]) , M = Integer.parseInt(time[1]) , S = Integer.parseInt(time[2]);
        TotalS = (M * 60) + (H * 60 * 60) + S ;
        long delay = TotalS * 1000;
        startBtn.setDisable(true);
        Timer timert = new Timer();
        timert.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                Platform.runLater(() ->{
                    int hours = TotalS / (60 * 60);
                    int minutes = TotalS / 60;
                    int seconds = TotalS % 60;
                    String T = hours + ":" + minutes + ":" + seconds ;
                    timeLeft.setText(T);
                    if(TotalS<0)
                        timert.cancel();
                    TotalS = TotalS - 1;
                });
            }
        }, 0, 1000);
    }

    @FXML
    public void Next(ActionEvent e){
        if(currIndex+1 < mxIndex) {
            ResetChoices();
            currIndex++;
            qNo.setText(String.valueOf(currIndex + 1));
            SetQuestion(Qlist.get(currIndex));
        }
    }

    @FXML
    public void Previous(ActionEvent e){
        if(currIndex > 0) {
            currIndex--;
            qNo.setText(String.valueOf(currIndex + 1));
            SetQuestion(Qlist.get(currIndex));
        }
    }
    @FXML
    private void Choice1(ActionEvent event){
        if(choice1.isSelected()){
            choice2.setSelected(false);
            choice3.setSelected(false);
            choice4.setSelected(false);
        }
    }
    @FXML
    private void Choice2(ActionEvent event){
        if(choice2.isSelected()){
            choice1.setSelected(false);
            choice3.setSelected(false);
            choice4.setSelected(false);
        }
    }
    @FXML
    private void Choice3(ActionEvent event){
        if(choice3.isSelected()){
            choice2.setSelected(false);
            choice1.setSelected(false);
            choice4.setSelected(false);
        }
    }
    @FXML
    private void Choice4(ActionEvent event){
        if(choice4.isSelected()){
            choice2.setSelected(false);
            choice3.setSelected(false);
            choice1.setSelected(false);
        }
    }
    @FXML
    private void ResetChoices(){
        choice2.setSelected(false);
        choice3.setSelected(false);
        choice1.setSelected(false);
        choice4.setSelected(false);
    }

    @FXML
    public void SetQuestion(DBQustion qustion){
        questionTxt.setText(qustion.Question);
        choice1.setText(qustion.Choice1);
        choice2.setText(qustion.Choice2);
        choice3.setText(qustion.Choice3);
        choice4.setText(qustion.Choice4);
    }

    @FXML
    public void Submit(ActionEvent event){

    }
}