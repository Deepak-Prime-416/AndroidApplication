package com.example.androidapplication;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplication.model.Fragment;
import com.example.androidapplication.model.FragmentAdapter;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    Button showPopUpButton, closePopUpButton, createFragmentButton;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<Fragment>();
    RecyclerView fragmentRV;
    TextView textView;
    FragmentAdapter fragmentAdapter;
    Button playButton;
    Iterator itr;
    View timerLayoutView;
    PopupWindow timerLayout;
    CountDownTimer startTimer;
    CountDownTimer restTimer;
    int activeCounter;
    int restCounter;
    boolean timerRunning = false;
    TextView countdown;
    TextView currentActivity;
    TextView nextActivity;
    Button pauseButton;
    Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentArrayList.add(new Fragment("Fragment1", 10, 5));
        fragmentArrayList.add(new Fragment("Fragment2", 10, 5));
        fragmentArrayList.add(new Fragment("Fragment3",10,5));
        fragmentArrayList.add(new Fragment("Fragment4",10,5));

        textView = findViewById(R.id.fragmentCount);
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.pop_up, null);
        timerLayoutView = inflater.inflate(R.layout.timer_layout,null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        timerLayout = new PopupWindow(timerLayoutView, width, height, false);

        showPopUpButton = findViewById(R.id.addFragmentBtn);
        showPopUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonShowPopupWindowClick(popupWindow, view);
            }
        });
        closePopUpButton = popupWindow.getContentView().findViewById(R.id.btnCancel);
        closePopUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissPopUp(popupWindow, view);
            }
        });

        createFragmentButton = popupWindow.getContentView().findViewById(R.id.btnCreate);
        createFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragmentToArraylist(popupWindow, view);
            }
        });

        playButton = findViewById(R.id.btnPlay);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonShowPopupWindowClick(timerLayout,view);
                onButtonClickPlayTimer(fragmentArrayList);
            }
        });

        fragmentAdapter = new FragmentAdapter(fragmentArrayList);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        fragmentRV = findViewById(R.id.recycleFragment);
        fragmentRV.setLayoutManager(linearLayoutManager);
        fragmentRV.setAdapter(fragmentAdapter);

        textView.setText("Total : " + String.valueOf(fragmentAdapter.getItemCount()) + " Fragments");

        countdown = timerLayout.getContentView().findViewById(R.id.txtTime);
        currentActivity = timerLayout.getContentView().findViewById(R.id.txtRunningActivity);
        nextActivity = timerLayout.getContentView().findViewById(R.id.txtUpcomingActivity);
        pauseButton = timerLayout.getContentView().findViewById(R.id.btnPause);
        resetButton = timerLayout.getContentView().findViewById(R.id.btnReset);

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!timerRunning) {
                    activeTimerStart();
                    pauseButton.setText("Pause");
                    timerRunning=true;
                }else{
                    pauseButton.setText("Play");
                    startTimer.cancel();
                    timerRunning=false;
                }
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer.cancel();
                itr = fragmentArrayList.iterator();
                countdown.setText("00");
                timerRunning=false;
                StartTimer((Fragment) itr.next());
            }
        });
    }

    private void onButtonClickPlayTimer(ArrayList<Fragment> fragmentArrayList) {
        itr = fragmentArrayList.iterator();
        StartTimer((Fragment) itr.next());
    }

    private void StartTimer(Fragment fragment) {
        activeCounter = fragment.getActiveTime();
        restCounter = fragment.getRestTime();
        currentActivity.setText(fragment.getTitle());
        if(itr.hasNext())
            nextActivity.setText(fragmentArrayList.get(fragmentArrayList.indexOf(fragment) + 1).getTitle());
        else
            nextActivity.setText("Last Activity");
        if(timerRunning)
            activeTimerStart();
    }

    private void activeTimerStart(){
        startTimer = new CountDownTimer(1000 * activeCounter, 1000) {
            @Override
            public void onTick(long l) {
                activeCounter = (int)(l+1000)/1000;
                countdown.setText(String.valueOf(activeCounter));
            }

            @Override
            public void onFinish() {
                currentActivity.setText("Take Rest");
                restTimerStart();
            }
        }.start();
    }

    private void restTimerStart(){
        restTimer = new CountDownTimer(1000 * restCounter, 1000) {
            @Override
            public void onTick(long l) {
                restCounter = (int)(l+1000)/1000;
                countdown.setText(String.valueOf(restCounter));
            }

            @Override
            public void onFinish() {
                if (itr.hasNext())
                    StartTimer((Fragment) itr.next());
                else
                    timerLayout.dismiss();
            }
        }.start();
    }

    private void addFragmentToArraylist(PopupWindow popupWindow, View view) {
        EditText edtTitle = popupWindow.getContentView().findViewById(R.id.edtTxtName);
        EditText edtActiveTime = popupWindow.getContentView().findViewById(R.id.edtTxtActiveTime);
        EditText edtRestTime = popupWindow.getContentView().findViewById(R.id.edtTxtRestTime);
        fragmentAdapter.setFragmentArrayList(new Fragment(edtTitle.getText().toString(),
                Integer.parseInt(edtActiveTime.getText().toString()),
                Integer.parseInt(edtRestTime.getText().toString())));
        Toast.makeText(this, "new Fragment Created ", Toast.LENGTH_SHORT).show();
        textView.setText("Total : " + String.valueOf(fragmentAdapter.getItemCount()) + " Fragments");
    }

    private void dismissPopUp(PopupWindow popupWindow, View view) {
        popupWindow.dismiss();
    }

    public void onButtonShowPopupWindowClick(PopupWindow popupWindow, View view) {
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}