package com.example.cs4520project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogInFragment extends Fragment  implements View.OnClickListener {

    private EditText edit_pass, edit_name, edit_email, edit_pass2;
    private ImageView back_arrow;
    private Button button_login, button_register,  button_submit;
    private boolean registering;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LogInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogInFragment newInstance(String param1, String param2) {
        LogInFragment fragment = new LogInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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


}