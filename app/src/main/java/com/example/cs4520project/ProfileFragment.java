package com.example.cs4520project;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    public static final String FRAGMENT_TAG = "PROFILE_FRAGMENT";
    private static final String ARG_PROFILE = "PROFILE";

    private Profile profile;
    private ImageView backButton;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param profile User profile.
     * @return A new instance of fragment ProfileFragment.
     */
    public static ProfileFragment newInstance(Profile profile) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PROFILE, profile);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            profile = (Profile) getArguments().getSerializable(ARG_PROFILE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView profileName = rootView.findViewById(R.id.textViewProfileName);
        TextView profileEmail = rootView.findViewById(R.id.textViewProfileEmail);
        Button logoutButton = rootView.findViewById(R.id.buttonLogout);

        profileName.setText(profile.getName());
        profileEmail.setText(profile.getEmail());

        this.backButton = rootView.findViewById(R.id.profile_backImage);
        this.backButton.setOnClickListener(this);

        logoutButton.setOnClickListener(v -> {
            FromProfileFragmentToMain context = (FromProfileFragmentToMain) this.getContext();
            context.logoutRequest();
        });

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == this.backButton.getId()){
            this.getActivity().getSupportFragmentManager()
                    .beginTransaction().remove(this)
                    .commit();
        }
    }

    public interface FromProfileFragmentToMain{
        void logoutRequest();
    }


}