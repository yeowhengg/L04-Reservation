package sg.edu.rp.c346.id20042741.l04_reservation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
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



    @RequiresApi(api = Build.VERSION_CODES.M)
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
        timePicker.setHour(19);
        timePicker.setMinute(30);

        btnDate.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v){
                if(btnDate.isPressed() && datecount == 0){
                    datePicker.setVisibility(View.VISIBLE);
                    btnDate.setText("Confirm");
                    btnTime.setEnabled(false);
                    datecount++;
                }
                else{
                    datePicker.setVisibility(View.GONE);
                    datecount = 0;
                    btnDate.setText("Choose Date");
                    date = dateValidation(datePicker) ? String.format("%d/%d/%d", datePicker.getDayOfMonth(), datePicker.getMonth() + 1, datePicker.getYear()) : "Invalid Date";
                    datetimeformat = String.format("Date: %s : Time: %s",date,time);
                    datetimeOutput.setText(datetimeformat);
                }

                if(datecount == 0 && btnDate.isPressed()){
                    btnTime.setEnabled(false);
                    if(!dateValidation(datePicker)){
                        btnTime.setEnabled(false);
                        Toast.makeText(MainActivity.this, "Invalid Date",Toast.LENGTH_SHORT).show();
                        datetimeformat = String.format("Date: %s : Time: %s",date,"");
                        datetimeOutput.setText(datetimeformat);
                    }
                    else{
                        btnTime.setEnabled(true);
                    }
                }

            }
        });

        btnTime.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v){
                String[] hour_meridian = new String[2];
                if(btnTime.isPressed() && timecount == 0){
                    btnDate.setEnabled(false);
                    timePicker.setVisibility(View.VISIBLE);
                    btnTime.setText("Confirm");
                    timecount++;
                }
                else{
                    timePicker.setVisibility(View.GONE);
                    btnTime.setText("Choose Time");
                    btnDate.setEnabled(true);
                    timecount = 0;
                    hour_meridian = returnHourAndMeridiem(timePicker);

                        if(timeValidation(timePicker)){
                            time = String.format("%s:%02d %s", hour_meridian[0], timePicker.getCurrentMinute(), hour_meridian[1]);
                        }
                        else{
                            time = String.format("%s:%s %s", "8", "59", "PM");
                            timePicker.setHour(20);
                            timePicker.setMinute(59);
                            Toast.makeText(MainActivity.this,"Only 8am to 859pm",Toast.LENGTH_SHORT).show();
                        }
                    datetimeformat = String.format("Date: %s : Time: %s",date,time);
                    datetimeOutput.setText(datetimeformat);
                }
            }
        });

        btnCfm.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v){
                finalOutput.setText("");
                finalOutput.setTextColor(Color.argb(255,255,0,0));
                String getName = nameTxt.getText().toString();
                String getPhone = phoneNoTxt.getText().toString();
                String getGrpSize = groupSizeTxt.getText().toString();

                if (!inputValidation(getName, getPhone, getGrpSize)) {
                    Toast.makeText(MainActivity.this, "Please enter your details correctly",Toast.LENGTH_SHORT).show();
                }
                else if(date.isEmpty() || time.isEmpty()) {
                    finalOutput.setText("Date time fields efeff not filled up correctly");
                }

                else if(!timeValidation(timePicker) || !dateValidation(datePicker)){
                    finalOutput.setText("Date fields are not filled up correctly");
                }
                else{
                    Toast.makeText(MainActivity.this,String.format("Reservation booked! Your details are: \nName: %s \nNumber: %s \nGroup Size: %s \nSmoking Corner: %s \n%s",getName,getPhone,getGrpSize,cbSmoking.isChecked()?"Yes":"No",datetimeformat),Toast.LENGTH_LONG).show();
                    finalOutput.setText("");
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
                startActivity(getIntent());
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
        Log.println(Log.DEBUG,"debug",String.format("This is for the time picker: \nYear: %d\nMonth: %d\nDay :%d",date.getYear(),date.getMonth() + 1,date.getDayOfMonth()));
        Log.println(Log.DEBUG,"debug",String.format("\nThis is for localdate: \nYear: %d \nMonth: %d \nDay: %d",LocalDate.now().getYear(),LocalDate.now().getMonth().getValue(),LocalDate.now().getDayOfMonth()));
        if(date.getYear() == LocalDate.now().getYear()){
            if(date.getMonth() + 1 == LocalDate.now().getMonth().getValue()){
                if(date.getDayOfMonth() <= LocalDate.now().getDayOfMonth()){
                    Toast.makeText(MainActivity.this, "Please select a date that is not today or yesterday",Toast.LENGTH_LONG);
                    return false;
                }
            }
            else if(date.getMonth() + 1 <= LocalDate.now().getMonthValue()){
                return false;
            }
        }
        return true;

    }

    //no method to allow us to view AM or PM selected
    @RequiresApi(api = Build.VERSION_CODES.M)
    public String[] returnHourAndMeridiem(TimePicker time){
        String[] hourAndMeridiem = new String[2];
        hourAndMeridiem[0] = time.getCurrentHour() > 12 ? String.format("%d",time.getCurrentHour() - 12) : String.format("%d",time.getCurrentHour());
        hourAndMeridiem[1] = time.getCurrentHour() > 12 ? "PM" : "AM";
        return hourAndMeridiem;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean timeValidation(TimePicker time){
        return time.getCurrentHour() >= 8 && (time.getCurrentHour() <= 20 && time.getCurrentMinute() <= 59) ? true:false;
    }



}