package com.example.mushedriver.mushedriver;


import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Time_dates extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener,SearchManager.OnCancelListener, DialogInterface.OnCancelListener, com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener {

    private int hour,year,day,minute,month;

    Button timeCheck,btnProhibited,requestDriver;
    RadioButton private_,bakkie_,truck_,trailor_;
    Button  payment_menthod ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_dates);
        requestDriver =(Button)findViewById(R.id.confirm_amount_pick_up);


        private_ =(RadioButton)findViewById(R.id.private_car);
        bakkie_ =(RadioButton)findViewById(R.id.bakkie_car);
        truck_ =(RadioButton)findViewById(R.id.Truck_car);
        trailor_ =(RadioButton)findViewById(R.id.Trailor_car);
        final Calculations_amount calculations_amount = new Calculations_amount();


        payment_menthod =(Button)findViewById(R.id.payment_menthod);
        payment_menthod .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  startActivity(new Intent(getApplicationContext(),CardPayment.class));
            }
        });


        private_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDriver.setText("CONFIRM R"+calculations_amount.bakkiecar);
            }
        });
        bakkie_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDriver.setText("CONFIRM R"+calculations_amount.bakkiecar);
            }
        });
        truck_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDriver.setText("CONFIRM R"+calculations_amount.bakkiecar);
            }
        });
        trailor_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDriver.setText("CONFIRM R"+calculations_amount.bakkiecar);
            }
        });




        btnProhibited =(Button)findViewById(R.id.btn_prohibited);
        btnProhibited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Prohibited_Items.class));
            }
        });



          timeCheck =(Button)findViewById(R.id.timeCheck);
          timeCheck.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  schedulePickUp(v);
              }
          });


          requestDriver.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {



                      if(get_if_clicked_condition()){
                          requestDriver.setText("Waiting for parcel....");
                          requestDriver.setBackgroundResource(R.color.design_default_color_primary);

                      }else{
                          Snackbar mySnackbar = Snackbar.make(findViewById(R.id.errors),
                                  "Read prohibited items and accept condition", Snackbar.LENGTH_SHORT);
                          mySnackbar.show();
                      }
                  if(timeCheck.getText().toString().equals("Schedule time")){
                      Snackbar mySnackbar = Snackbar.make(findViewById(R.id.errors),
                              "Please select date", Snackbar.LENGTH_SHORT);
                      mySnackbar.show();
                      return;
                  }

              }
          });

    }


    private boolean get_if_clicked_condition() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String m = bundle.getString("con");
            return true;
        }
        return false;
    }

    public void schedulePickUp(View view){
        initDateTimeData();
        Calendar cDefault = Calendar.getInstance();

        cDefault.set(year,month,day);

        com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance
                (this,
                cDefault.get(Calendar.YEAR),
                cDefault.get(Calendar.MONTH),
                cDefault.get(Calendar.DAY_OF_MONTH)

                );
        Calendar cMin =Calendar.getInstance();
        Calendar cMax = Calendar.getInstance();
        cMax.set(cMax.get( Calendar.YEAR),11,31);
        datePickerDialog.setMinDate(cMin);
        datePickerDialog.setMinDate(cMax);

        List<Calendar> daysList = new LinkedList<>();
        Calendar [] daysArray;
        Calendar cAux = Calendar.getInstance();

        while (cAux.getTimeInMillis() <= cMax.getTimeInMillis()){
            if(cAux.get(Calendar.DAY_OF_WEEK) != 1 && cAux.get(Calendar.DAY_OF_WEEK) != 7){
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(cAux.getTimeInMillis());
                daysList.add(c);
            }
            cAux.setTimeInMillis(cAux.getTimeInMillis()+(24*60*60*100));

        }
        daysArray = new Calendar[daysList.size()];
       for(int i =0; i< daysArray.length;i++){
           daysArray[i] = daysList.get(i);
       }

       datePickerDialog.setSelectableDays(daysArray);
       datePickerDialog.setOnCancelListener(Time_dates.this);
       datePickerDialog.show(getFragmentManager(),"DatePickeDialog");


    }

    private  void initDateTimeData(){

        if(year ==0 ){

            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
        }
    }



    @Override
    public void onCancel() {
        hour=year=day=minute=month=0;
        //TODO add some textView
    }



    @Override
    public void onDateSet(DatePickerDialog view, int year_, int monthOfYear, int dayOfMonth) {

        Calendar tDefault = Calendar.getInstance();
        tDefault.set(year,month,day,hour,minute);

        year = year_;
        month = monthOfYear;
        day = dayOfMonth;

        com.wdullaer.materialdatetimepicker.time.TimePickerDialog timePickerDialog = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(
                this,
                tDefault.get(Calendar.HOUR_OF_DAY),
                tDefault.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.setOnCancelListener((DialogInterface.OnCancelListener) this);
        timePickerDialog.show(getFragmentManager(),"TimePickeDialog");
        timePickerDialog.setTitle("Pick up time");
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute_) {


        if(hourOfDay < 9 && hourOfDay>18){

            onDateSet(null,year,month,day);
            Toast.makeText(getApplicationContext(),"You entered between less 9 or > 18",Toast.LENGTH_LONG).show();
            return;
        }
        hour = hourOfDay;
        minute = minute_;

        timeCheck.setText((day<10 ? "0"+day:day)+"/"+(month+1 <10?"0"+(month+1):month+1)+"/"+
                year+"  "+(hour<10?"0"+hour:hour)+"h"+(minute<10?"0"+minute:minute));
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        hour=year=day=minute=month=0;
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute_) {
        if(hourOfDay < 9 && hourOfDay>18){

            onDateSet(null,year,month,day);
            Toast.makeText(getApplicationContext(),"You entered between less 9 or > 18",Toast.LENGTH_LONG).show();
            return;
        }
        hour = hourOfDay;
        minute = minute_;

        //Todo
        timeCheck.setText((day<10 ? "0"+day:day)+"/"+(month+1 <10?"0"+(month+1):month+1)+"/"+
                year+"  "+(hour<10?"0"+hour:hour)+"h"+(minute<10?"0"+minute:minute));
    }
}
