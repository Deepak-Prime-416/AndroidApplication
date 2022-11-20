package com.example.androidapplication;

import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    Button showPopUpButton, closePopUpButton, createFragmentButton;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<Fragment>();
    RecyclerView fragmentRV;
    TextView textView;
    FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.fragmentCount);
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.pop_up, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        showPopUpButton = findViewById(R.id.addFragmentBtn);
        showPopUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onButtonShowPopupWindowClick(popupWindow,view);
            }
        });
        closePopUpButton = popupWindow.getContentView().findViewById(R.id.btnCancel);
        closePopUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissPopUp(popupWindow,view);
            }
        });

        createFragmentButton = popupWindow.getContentView().findViewById(R.id.btnCreate);
        createFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragmentToArraylist(popupWindow,view);
            }
        });

        fragmentArrayList.add(new Fragment("Fragment1",45,30));
        fragmentArrayList.add(new Fragment("Fragment2",50,30));
        fragmentArrayList.add(new Fragment("Fragment3",60,30));
        fragmentArrayList.add(new Fragment("Fragment4",70,30));

        fragmentAdapter = new FragmentAdapter(fragmentArrayList);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        fragmentRV = findViewById(R.id.recycleFragment);
        fragmentRV.setLayoutManager(linearLayoutManager);
        fragmentRV.setAdapter(fragmentAdapter);

        textView.setText("Total : " + String.valueOf(fragmentAdapter.getItemCount()) + " Fragments");
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