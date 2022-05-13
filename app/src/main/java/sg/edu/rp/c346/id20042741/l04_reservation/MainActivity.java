package sg.edu.rp.c346.id20042741.l04_reservation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.timepicker.TimeFormat;

import org.w3c.dom.Text;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v){

                if(btnDate.isPressed() && datecount == 0){
                    datePicker.setVisibility(View.VISIBLE);
                    btnDate.setText("Confirm");
                    datecount++;
                }
                else{
                    datePicker.setVisibility(View.GONE);
                    datecount = 0;
                    btnDate.setText("Choose Date");
                    date = String.format("%d/%d/%d", datePicker.getDayOfMonth(), datePicker.getMonth() + 1, datePicker.getYear());
                    datetimeformat =  dateValidation(datePicker) ? String.format("Date: %s | Time: %s",date,time) : String.format("Date: %s | Time: %s","Invalid Date",time);
                }

                if(!date.isEmpty()){
                    datetimeOutput.setText(datetimeformat);
                }

                if(dateValidation(datePicker)){
                    btnTime.setEnabled(true);
                }else{
                    btnTime.setEnabled(false);
                }
            }
        });


        btnTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(btnTime.isPressed() && timecount == 0){
                    timePicker.setVisibility(View.VISIBLE);
                    btnTime.setText("Confirm");
                    timecount++;
                }
                else{
                    timePicker.setVisibility(View.GONE);
                    btnTime.setText("Choose Time");
                    timecount = 0;
                    time = String.format("%d-%02d", timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                    timeValidation(timePicker);
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
                }
                else{
                    finalOutput.setText(String.format("Reservation booked! Your details are: \nName: %s \nNumber: %s \nGroup Size: %s \nSmoking Corner: %s \nDate and time: %s",getName,getPhone,getGrpSize,cbSmoking.isChecked()?"Wants smoking corner":"Normal table area",datetimeformat));
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean dateValidation(DatePicker date){
        Log.println(Log.DEBUG,"debug",String.format("Year: %d",LocalDate.now().getYear()));
        Log.println(Log.DEBUG,"debug",String.format("Month: %d",LocalDate.now().getMonthValue()));

        if(date.getYear() == LocalDate.now().getYear()){
            if(date.getMonth() + 1 == LocalDate.now().getMonth().getValue()){
                if(date.getDayOfMonth() <= LocalDate.now().getDayOfMonth()){
                    Toast.makeText(MainActivity.this, "Please select a date that is not today or yesterday",Toast.LENGTH_LONG);
                    return false;
                }

            }
        }
        return true;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean timeValidation(TimePicker time){


        return true;
    }



}