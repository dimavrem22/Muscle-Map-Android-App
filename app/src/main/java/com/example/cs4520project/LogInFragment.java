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

import java.util.Locale;

public class LogInFragment extends Fragment implements View.OnClickListener {
    public final static String FRAGMENT_TAG = "LogInFragment";

    private EditText edit_pass, edit_name, edit_email, edit_pass2;
    private ImageView back_arrow;
    private Button button_login, button_register, button_submit;
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

        edit_email = rootView.findViewById(R.id.edit_email);
        edit_name = rootView.findViewById(R.id.edit_name);
        edit_pass = rootView.findViewById(R.id.edit_pass);
        edit_pass2 = rootView.findViewById(R.id.edit_pass2);

        back_arrow = rootView.findViewById(R.id.back_button);
        back_arrow.setOnClickListener(this);
        button_login = rootView.findViewById(R.id.login_button);
        button_login.setOnClickListener(this);
        button_register = rootView.findViewById(R.id.register_button);
        button_register.setOnClickListener(this);
        button_submit = rootView.findViewById(R.id.submit_button);
        button_submit.setOnClickListener(this);

        getSelectionPage();

        return rootView;
    }

    public void onClick(View v) {
        if (v.getId() == button_register.getId()) {
            registering = true;
            getEditPage();
        } else if (v.getId() == button_login.getId()) {
            registering = false;
            getEditPage();
        } else if (v.getId() == back_arrow.getId()) {
            getSelectionPage();
        } else if (v.getId() == button_submit.getId()) {
            if (registering) {
                sendRegistrationRequest();
            } else {
                sendLoginRequest();
            }
        }
    }

    private void getSelectionPage() {
        button_login.setVisibility(View.VISIBLE);
        button_register.setVisibility(View.VISIBLE);

        edit_name.setVisibility(View.INVISIBLE);
        edit_email.setVisibility(View.INVISIBLE);
        edit_pass.setVisibility(View.INVISIBLE);
        edit_pass2.setVisibility(View.INVISIBLE);
        button_submit.setVisibility(View.INVISIBLE);
        back_arrow.setVisibility(View.INVISIBLE);

        button_login.setEnabled(true);
        button_register.setEnabled(true);

        edit_name.setEnabled(false);
        edit_email.setEnabled(false);
        edit_pass.setEnabled(false);
        edit_pass.setEnabled(false);
        button_submit.setEnabled(false);
        back_arrow.setEnabled(false);

        // resetting the text of editText objects
        edit_name.setText("");
        edit_email.setText("");
        edit_pass.setText("");
        edit_pass2.setText("");
    }

    private void getEditPage() {
        if (registering) {
            edit_name.setVisibility(View.VISIBLE);
            edit_name.setEnabled(true);
            edit_pass2.setVisibility(View.VISIBLE);
            edit_pass2.setEnabled(true);
            button_submit.setText(button_register.getText());
        } else {
            button_submit.setText(button_login.getText());
        }

        edit_email.setVisibility(View.VISIBLE);
        edit_pass.setVisibility(View.VISIBLE);
        button_submit.setVisibility(View.VISIBLE);
        back_arrow.setVisibility(View.VISIBLE);

        edit_email.setEnabled(true);
        edit_pass.setEnabled(true);
        button_submit.setEnabled(true);
        back_arrow.setEnabled(true);

        button_login.setEnabled(false);
        button_register.setEnabled(false);
        button_login.setVisibility(View.INVISIBLE);
        button_register.setVisibility(View.INVISIBLE);
    }

    private void sendLoginRequest() {
        String email = String.valueOf(edit_email.getText());
        String pass = String.valueOf(edit_pass.getText());

        email = email.toLowerCase();

        if (email.equals("")) {
            Toast.makeText(getContext(), R.string.no_email, Toast.LENGTH_SHORT).show();
            return;
        } else if (pass.equals("")) {
            Toast.makeText(getContext(), R.string.no_pass, Toast.LENGTH_SHORT).show();
            return;
        }

        LoginToMain context = (LoginToMain) getActivity();
        context.loginRequest(email, pass);
    }

    private void sendRegistrationRequest() {
        String name = String.valueOf(edit_name.getText());
        String email = String.valueOf(edit_email.getText());
        String pass = String.valueOf(edit_pass.getText());
        String pass2 = String.valueOf(edit_pass2.getText());

        email = email.toLowerCase();

        if (name.equals("")) {
            Toast.makeText(getContext(), R.string.no_name, Toast.LENGTH_SHORT).show();
            return;
        } else if (!name.matches(".*\\w.*")) {
            Toast.makeText(getContext(), R.string.name_ws, Toast.LENGTH_SHORT).show();
            return;
        } else if (email.equals("")) {
            Toast.makeText(getContext(), R.string.no_email, Toast.LENGTH_SHORT).show();
            return;
        } else if (!Utils.isValidEmail(email)) {
            Toast.makeText(getContext(), R.string.email_format, Toast.LENGTH_SHORT).show();
            return;
        } else if (pass.equals("")) {
            Toast.makeText(getContext(), R.string.no_pass, Toast.LENGTH_SHORT).show();
            return;
        } else if (pass.length() < 8) {
            Toast.makeText(getContext(), R.string.pass_length, Toast.LENGTH_SHORT).show();
            return;
        } else if (!pass.equals(pass2)) {
            Toast.makeText(getContext(), R.string.no_match, Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("final project", "log in");
        // no errors detected yet
        LoginToMain context = (LoginToMain) getActivity();
        context.registerRequest(name, email, pass);
    }

    public interface LoginToMain {
        void loginRequest(String email, String pass);

        void registerRequest(String name, String email, String pass);
    }
}