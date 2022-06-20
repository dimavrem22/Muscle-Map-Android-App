package com.example.cs4520project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Pattern;


public class LogInFragment extends Fragment  implements View.OnClickListener {

    public final static String  FRAGMENT_TAG = "LogInFragment";

    private EditText edit_pass, edit_name, edit_email, edit_pass2;
    private ImageView back_arrow;
    private Button button_login, button_register,  button_submit;
    private boolean registering;

    public LogInFragment() {
        // Required empty public constructor
    }


    public static LogInFragment newInstance() {
        LogInFragment fragment = new LogInFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_log_in, container, false);


        this.edit_email = rootView.findViewById(R.id.edit_email);
        this.edit_name = rootView.findViewById(R.id.edit_name);
        this.edit_pass = rootView.findViewById(R.id.edit_pass);
        this.edit_pass2 = rootView.findViewById(R.id.edit_pass2);

        this.back_arrow = rootView.findViewById(R.id.back_button);
        this.back_arrow.setOnClickListener(this);
        this.button_login = rootView.findViewById(R.id.login_button);
        this.button_login.setOnClickListener(this);
        this.button_register = rootView.findViewById(R.id.register_button);
        this.button_register.setOnClickListener(this);
        this.button_submit = rootView.findViewById(R.id.submit_button);
        this.button_submit.setOnClickListener(this);

        this.getSelectionPage();

        return rootView;

    }

    public void onClick(View v) {
        if (v.getId() == this.button_register.getId()){
            this.registering = true;
            this.getEditPage();
        }
        else if (v.getId() == this.button_login.getId()){
            this.registering = false;
            this.getEditPage();
        }
        else if (v.getId() == this.back_arrow.getId()){
            this.getSelectionPage();
        }
        else if (v.getId() == this.button_submit.getId()){
            if (this.registering){
                this.sendRegistrationRequest();
            } else {
                this.sendLoginRequest();
            }
        }

    }

    private void getSelectionPage() {

        this.button_login.setVisibility(View.VISIBLE);
        this.button_register.setVisibility(View.VISIBLE);

        this.edit_name.setVisibility(View.INVISIBLE);
        this.edit_email.setVisibility(View.INVISIBLE);
        this.edit_pass.setVisibility(View.INVISIBLE);
        this.edit_pass2.setVisibility(View.INVISIBLE);
        this.button_submit.setVisibility(View.INVISIBLE);
        this.back_arrow.setVisibility(View.INVISIBLE);


        this.button_login.setEnabled(true);
        this.button_register.setEnabled(true);

        this.edit_name.setEnabled(false);
        this.edit_email.setEnabled(false);
        this.edit_pass.setEnabled(false);
        this.edit_pass.setEnabled(false);
        this.button_submit.setEnabled(false);
        this.back_arrow.setEnabled(false);

        // resetting the text of editText objects
        this.edit_name.setText("");
        this.edit_email.setText("");
        this.edit_pass.setText("");
        this.edit_pass2.setText("");
    }

    private void getEditPage(){

        if (this.registering){
            this.edit_name.setVisibility(View.VISIBLE);
            this.edit_name.setEnabled(true);
            this.edit_pass2.setVisibility(View.VISIBLE);
            this.edit_pass2.setEnabled(true);
            this.button_submit.setText(this.button_register.getText());
        } else {
            this.button_submit.setText(this.button_login.getText());
        }

        this.edit_email.setVisibility(View.VISIBLE);
        this.edit_pass.setVisibility(View.VISIBLE);
        this.button_submit.setVisibility(View.VISIBLE);
        this.back_arrow.setVisibility(View.VISIBLE);

        this.edit_email.setEnabled(true);
        this.edit_pass.setEnabled(true);
        this.button_submit.setEnabled(true);
        this.back_arrow.setEnabled(true);

        this.button_login.setEnabled(false);
        this.button_register.setEnabled(false);
        this.button_login.setVisibility(View.INVISIBLE);
        this.button_register.setVisibility(View.INVISIBLE);
    }

    private void sendLoginRequest(){
        String email = String.valueOf(this.edit_email.getText());
        String pass = String.valueOf(this.edit_pass.getText());

        if (email.equals("")){
            Toast.makeText(this.getContext(), R.string.no_email, Toast.LENGTH_SHORT).show();
            return;
        }
        else if (pass.equals("")){
            Toast.makeText(this.getContext(), R.string.no_pass, Toast.LENGTH_SHORT).show();
            return;
        }

        LoginToMain context = (LoginToMain)this.getActivity();
        context.loginRequest(email, pass);

    }

    private void sendRegistrationRequest(){

        String name = String.valueOf(this.edit_name.getText());
        String email = String.valueOf(this.edit_email.getText());
        String pass = String.valueOf(this.edit_pass.getText());
        String pass2 = String.valueOf(this.edit_pass2.getText());

        if (name.equals("")){
            Toast.makeText(this.getContext(), R.string.no_name, Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!String.valueOf(this.edit_name.getText()).matches(".*\\w.*")){
            Toast.makeText(this.getContext(), R.string.name_ws, Toast.LENGTH_SHORT).show();
            return;
        }
        else if (email.equals("")){
            Toast.makeText(this.getContext(), R.string.no_email, Toast.LENGTH_SHORT).show();
            return;
        }
        else if (!checkEmailFormat()){
            Toast.makeText(this.getContext(), R.string.email_format, Toast.LENGTH_SHORT).show();
            return;
        }
        else if (pass.equals("")){
            Toast.makeText(this.getContext(), R.string.no_pass, Toast.LENGTH_SHORT).show();
            return;
        }
        else if (pass.length() < 8){
            Toast.makeText(this.getContext(), R.string.pass_length, Toast.LENGTH_SHORT).show();
            return;
        }
        else if (!pass.equals(pass2)){
            Toast.makeText(this.getContext(), R.string.no_match, Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("final project", "log in");
        // no errors detected yet
        LoginToMain context = (LoginToMain)this.getActivity();
        context.registerRequest(name, email, pass);
    }

    private boolean checkEmailFormat() {
        String pattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+" +
                "(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(pattern)
                .matcher(String.valueOf(edit_email.getText())).matches();
    }



    public interface LoginToMain {
        void loginRequest(String email, String pass);
        void registerRequest(String name, String email, String pass);
    }



}