package sg.edu.rp.c346.id20042741.l04_reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText nameTxt;
    EditText phoneNoTxt;
    EditText groupSizeTxt;
    Button btnDate;
    Button btnTime;
    Button btnCfm;
    Button btnReset;
    DatePicker datePicker;
    TimePicker timePicker;
    CheckBox cbSmoking;
    TextView datetimeOutput;
    TextView finalOutput;
    int datecount;
    int timecount;
    String datetimeformat;
    String date;
    String time;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameTxt = findViewById(R.id.txtEditName);
        phoneNoTxt = findViewById(R.id.txtEditNumber);
        groupSizeTxt = findViewById(R.id.txtEditGroupSize);
        btnDate = findViewById(R.id.btnDate);
        btnTime = findViewById(R.id.btnTime);
        btnCfm = findViewById(R.id.btnCfm);
        btnReset = findViewById(R.id.btnReset);
        datePicker = findViewById(R.id.dateId);
        timePicker = findViewById(R.id.timeId);
        cbSmoking = findViewById(R.id.cbSmoke);
        datetimeOutput = findViewById(R.id.displayDateTime);
        finalOutput = findViewById(R.id.outputDisplay);
        datecount = 0;
        timecount = 0;
        date = "";
        time = "";

        btnDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                if(btnDate.isPressed() && datecount == 0){
                    datePicker.setVisibility(View.VISIBLE);
                    datecount++;
                }
                else{
                    datePicker.setVisibility(View.GONE);
                    datecount = 0;
                    date = String.format("%d/%d/%d", datePicker.getDayOfMonth(), datePicker.getMonth() + 1, datePicker.getYear());
                    datetimeformat = String.format("Date: %s | Time: %s",date,time);
                }

                if(!date.isEmpty()){
                    datetimeOutput.setText(datetimeformat);
                }
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(btnTime.isPressed() && timecount == 0){
                    timePicker.setVisibility(View.VISIBLE);
                    timecount++;
                }
                else{
                    timePicker.setVisibility(View.GONE);
                    timecount = 0;
                    time = String.format("%d-%02d", timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                    datetimeOutput.setText(datetimeformat);
                    datetimeformat = String.format("Date: %s | Time: %s",date,time);
                }

                if(!time.isEmpty()){
                    datetimeOutput.setText(datetimeformat);
                }
            }
        });

        btnCfm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String getName = nameTxt.getText().toString();
                String getPhone = phoneNoTxt.getText().toString();
                String getGrpSize = groupSizeTxt.getText().toString();

                if (!inputValidation(getName, getPhone, getGrpSize)) {
                    finalOutput.setText("Please enter the inputs correctly");
                } else {
                    finalOutput.setText(String.format("Reservation booked! Your details are: \n Name: %s \n Number: %s \n"));
                }
            }
        });
    }


    private boolean inputValidation(String name, String phone, String grpSize){
        String namePattern = "^([a-zA-Z]+\\s)*[a-zA-Z]+$";
        String phonePattern = "(8|9)[0-9]{7}";
        String grpPattern = "[0-9]";

        return Pattern.matches(namePattern,name) && Pattern.matches(phonePattern,phone) && Pattern.matches(grpPattern, grpSize);
    }
}