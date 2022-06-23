package com.example.cs4520project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseAnalysisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseAnalysisFragment extends Fragment implements View.OnClickListener {


    private static final String ARG_EMAIL = "email";
    private String email;

    private boolean lookingFront;


    private ImageView flipImage;

    private ImageView frontView, trapsFront, pecsFront, absFront, quadsFront, calvesFront,
            shouldersFront, bicepsFront, forearmsFront;

    private ArrayList<ImageView> frontMuscleImages;

    private ImageView backView, upperTrapsBack, trapsBack, shouldersBack, trisBack, forearmsBack,
            latsBack, lowerBackBack, glutsBack, hamsBack, calvesBack;

    private ArrayList<ImageView> backMuscleImages;


    public ExerciseAnalysisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ExerciseAnalysisFragment.
     */
    public static ExerciseAnalysisFragment newInstance(String email) {
        ExerciseAnalysisFragment fragment = new ExerciseAnalysisFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.email = getArguments().getString(ARG_EMAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exercise_analysis, container, false);

        this.lookingFront = true;

        this.flipImage = rootView.findViewById(R.id.flipViewButton);
        this.flipImage.setOnClickListener(this);

        this.frontMuscleImages = new ArrayList<ImageView>();


        this.frontView = rootView.findViewById(R.id.frontViewImage);
        this.bicepsFront = rootView.findViewById(R.id.bicepsFrontImage);
        this.frontMuscleImages.add(bicepsFront);
        this.pecsFront = rootView.findViewById(R.id.pecsFrontImage);
        this.frontMuscleImages.add(pecsFront);
        this.calvesFront = rootView.findViewById(R.id.calvesFrontImage);
        this.frontMuscleImages.add(calvesFront);
        this.quadsFront = rootView.findViewById(R.id.quadsFrontImage);
        this.frontMuscleImages.add(quadsFront);
        this.forearmsFront = rootView.findViewById(R.id.forearmsFrontImage);
        this.frontMuscleImages.add(forearmsFront);
        this.absFront = rootView.findViewById(R.id.absFrontImage);
        this.frontMuscleImages.add(absFront);
        this.shouldersFront = rootView.findViewById(R.id.shouldersFrontImage);
        this.frontMuscleImages.add(shouldersFront);
        this.trapsFront = rootView.findViewById(R.id.trapsFrontImage);
        this.frontMuscleImages.add(trapsFront);

        for (ImageView i : this.frontMuscleImages){
            i.setVisibility(View.INVISIBLE);
        }


        this.backMuscleImages = new ArrayList<ImageView>();
        this.backView = rootView.findViewById(R.id.backViewImage);
        this.glutsBack = rootView.findViewById(R.id.glutesBackImage);
        this.backMuscleImages.add(this.glutsBack);
        this.calvesBack = rootView.findViewById(R.id.calvesBackImage);
        this.backMuscleImages.add(this.calvesBack);
        this.hamsBack = rootView.findViewById(R.id.hamsBackImage);
        this.backMuscleImages.add(this.hamsBack);
        this.lowerBackBack = rootView.findViewById(R.id.lowerBackImage);
        this.backMuscleImages.add(this.lowerBackBack);
        this.latsBack = rootView.findViewById(R.id.latsBackImage);
        this.backMuscleImages.add(this.latsBack);
        this.trapsBack = rootView.findViewById(R.id.trapsBackImage);
        this.backMuscleImages.add(this.trapsBack);
        this.upperTrapsBack = rootView.findViewById(R.id.upperTrapsBackImage);
        this.backMuscleImages.add(this.upperTrapsBack);
        this.forearmsBack = rootView.findViewById(R.id.forearmsBackImage);
        this.backMuscleImages.add(this.forearmsBack);
        this.trisBack = rootView.findViewById(R.id.trisBackImage);
        this.backMuscleImages.add(this.trisBack);
        this.shouldersBack = rootView.findViewById(R.id.shouldersBackImage);
        this.backMuscleImages.add(this.shouldersBack);

        for (ImageView i : this.backMuscleImages){
            i.setVisibility(View.INVISIBLE);
        }
        this.backView.setVisibility(View.INVISIBLE);

        this.shouldersBack.getDrawable().setAlpha(72);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == this.flipImage.getId()){
            if (this.lookingFront){
                for (ImageView i : this.backMuscleImages){
                    i.setVisibility(View.VISIBLE);
                }
                for (ImageView i : this.frontMuscleImages){
                    i.setVisibility(View.INVISIBLE);
                }
                this.frontView.setVisibility(View.INVISIBLE);
                this.backView.setVisibility(View.VISIBLE);
            }
            else {
                for (ImageView i : this.backMuscleImages){
                    i.setVisibility(View.INVISIBLE);
                }
                for (ImageView i : this.frontMuscleImages){
                    i.setVisibility(View.VISIBLE);
                }
                this.frontView.setVisibility(View.VISIBLE);
                this.backView.setVisibility(View.INVISIBLE);
            }
            this.lookingFront = !this.lookingFront;
        }
    }
}
