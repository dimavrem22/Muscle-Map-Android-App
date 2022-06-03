package com.example.cs4520project;

import java.io.Serializable;
import java.util.Map;

// TODO: proof of concept?
public class MuscleGroupActivityMap implements Serializable {
    private final Map<MuscleGroup, MuscleActivity> map;

    public MuscleGroupActivityMap(Map<MuscleGroup, MuscleActivity> muscleActivity) {
        map = muscleActivity;
    }

    public MuscleActivity get(MuscleGroup group) {
        return map.get(group);
    }

    // TODO: very very hacky, use XML parser?
    public String generateSvg() {
        String result = "<vector xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                "    android:width=\"210dp\"\n" +
                "    android:height=\"297dp\"\n" +
                "    android:viewportWidth=\"210\"\n" +
                "    android:viewportHeight=\"297\">\n";

        String shoulderColor = get(MuscleGroup.SHOULDERS).hexColor();
        result += String.format(
                "<path android:name=\"rightShoulder\"" +
                        " android:pathData=\"m28.97,141.79 l2.44,-15.55 14.03,-0.61z\"" +
                        " android:fillColor=\"%s\"/>" +
                        "<path android:name=\"leftShoulder\"" +
                        " android:pathData=\"m92.09,141.49 l-2.44,-15.55 -14.03,-0.61z\"" +
                        " android:fillColor=\"%s\"/>"
                , shoulderColor, shoulderColor);

        String bicepColor = get(MuscleGroup.BICEPS).hexColor();
        result += String.format("<path android:name=\"rightBicep\"" +
                " android:pathData=\"m39.03,132.95 l-2.74,25.61 -9.45,6.4 -0.3,-21.04z\"" +
                " android:fillColor=\"%s\"/>" +
                "<path android:name=\"leftBicep\"" +
                " android:pathData=\"m82.03,132.64 l2.74,25.61 9.45,6.4 0.3,-21.04z\"" +
                " android:fillColor=\"#%s\"/>", bicepColor, bicepColor);

        String forearmColor = get(MuscleGroup.FOREARMS).hexColor();
        result += String.format(
                "<path android:name=\"rightForearm\"" +
                        " android:pathData=\"m24.7,158.56 l1.83,7.01 7.62,-4.57 -0.3,14.94 -10.37,14.64 -4.88,-0.3 3.05,-25z\"" +
                        " android:fillColor=\"%s\"/>" +
                        "<path android:name=\"leftForearm\"" +
                        " android:pathData=\"m96.36,158.26 l-1.83,7.01 -7.62,-4.57 0.3,14.94 10.37,14.64 4.88,-0.3 -3.05,-25z\"" +
                        " android:fillColor=\"%s\"/>", forearmColor, forearmColor);

        String trapColor = get(MuscleGroup.TRAPS).hexColor();
        result += String.format(
                "<path android:name=\"rightTrap\"" +
                        " android:pathData=\"m42.69,124.72 l10.37,-10.06 3.35,11.28z\"" +
                        " android:fillColor=\"%s\"/>" +
                        "<path android:name=\"leftTrap\"" +
                        " android:pathData=\"m78.37,124.41 l-10.37,-10.06 -3.35,11.28z\"" +
                        " android:fillColor=\"%s\"/>", trapColor, trapColor);

        String chestColor = get(MuscleGroup.CHEST).hexColor();
        result += String.format(


                "<path android:name=\"rightChest\"" +
                        " android:pathData=\"m39.95,135.69 l7.01,8.54 12.81,-1.83 -0.3,-11.28 -5.18,-3.96 -7.32,-0.91z\"" +
                        " android:fillColor=\"%s\"/>" +
                        "<path android:name=\"leftChest\"" +
                        " android:pathData=\"m81.11,135.39 l-7.01,8.54 -12.81,-1.83 0.3,-11.28 5.18,-3.96 7.32,-0.91z\"" +
                        " android:fillColor=\"%s\"/>", chestColor, chestColor);

        String absColor = get(MuscleGroup.ABS).hexColor();
        result += String.format(
                "<path android:name=\"abs\"" +
                        " android:pathData=\"m53.97,145.45c6.4,-1.52 6.4,-1.52 6.4,-1.52l6.71,1.52 0.61,11.28 -2.74,10.98H57.02l-4.27,-10.67z\"" +
                        " android:fillColor=\"%s\"/>", absColor);

        return result +
                "</vector>";
    }
}
