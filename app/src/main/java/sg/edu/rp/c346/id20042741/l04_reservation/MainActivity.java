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

import org.w3c.dom.Text;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText nameTxt;
    EditText phoneNoTxt;
    EditText groupSizeTxt;
    Button btnDateTime;
    Button btnCfm;
    Button btnReset;
    DatePicker datePicker;
    TimePicker timePicker;
    CheckBox cbSmoking;
    TextView datetimeOutput;
    TextView finalOutput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameTxt = findViewById(R.id.txtEditName);
        phoneNoTxt = findViewById(R.id.txtEditNumber);
        groupSizeTxt = findViewById(R.id.txtEditGroupSize);
        btnDateTime = findViewById(R.id.btnDateTime);
        btnCfm = findViewById(R.id.btnCfm);
        btnReset = findViewById(R.id.btnReset);
        datePicker = findViewById(R.id.dateId);
        timePicker = findViewById(R.id.timeId);
        cbSmoking = findViewById(R.id.cbSmoke);
        datetimeOutput = findViewById(R.id.displayDateTime);
        finalOutput = findViewById(R.id.outputDisplay);

        btnCfm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String getName = nameTxt.getText().toString();
                String getPhone = phoneNoTxt.getText().toString();
                String getGrpSize = groupSizeTxt.getText().toString();

                if(!inputValidation(getName, getPhone, getGrpSize)){
                    finalOutput.setText("Please enter the inputs correctly");
                }


            }
        });
    }


    private boolean inputValidation(String name, String phone, String grpSize){
        String namePattern = "^([a-zA-Z]+\\s)*[a-zA-Z]+$";
        String phonePattern = "[8|9](0-9){7}";
        String grpPattern = "[0-9]";

        return Pattern.matches(namePattern,name) || Pattern.matches(phonePattern,phone) || Pattern.matches(grpPattern, grpSize);
    }
}