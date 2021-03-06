package ru.gdcn.beastmaster64revelations.GameFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import ru.gdcn.beastmaster64revelations.GameActivities.CharacterCreation.Gender;
import ru.gdcn.beastmaster64revelations.GameActivities.InLocation.InLocationActivity;
import ru.gdcn.beastmaster64revelations.GameInterface.Character.NPC.Player;
import ru.gdcn.beastmaster64revelations.R;

public class StatsFragment extends Fragment {

    private InLocationActivity activity;

    private Player player;

    private Button strPlus;
    private Button agiPlus;
    private Button intPlus;

    private TextView strText;
    private TextView agiText;
    private TextView intText;

    private TextView pointsText;

    public StatsFragment() {
        // Required empty public constructor
    }

    public static StatsFragment newInstance() {
        StatsFragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = (InLocationActivity) getActivity();

        //noinspection ConstantConditions
        player = this.activity.getPlayer();

        if (player == null)
            return;

        strPlus = ((InLocationActivity) activity).findViewById(R.id.fragment_stats_plus_str);
        agiPlus = ((InLocationActivity) activity).findViewById(R.id.fragment_stats_plus_agi);
        intPlus = ((InLocationActivity) activity).findViewById(R.id.fragment_stats_plus_int);

        strText = ((InLocationActivity) activity).findViewById(R.id.fragment_stats_strengthText);
        agiText = ((InLocationActivity) activity).findViewById(R.id.fragment_stats_agilityText);
        intText = ((InLocationActivity) activity).findViewById(R.id.fragment_stats_intellectText);

        TextView name = ((InLocationActivity) activity).findViewById(R.id.fragment_stats_char_name);
        name.setText(player.getName());
        ImageView face = ((InLocationActivity) activity).findViewById(R.id.fragment_stats_char_face);
        if (player.getGender() == Gender.MALE)
            face.setImageDrawable(face.getResources().getDrawable(R.drawable.char_face_man));
        else
            face.setImageDrawable(face.getResources().getDrawable(R.drawable.char_face_woman));

        pointsText = ((InLocationActivity) activity).findViewById(R.id.fragment_stats_points);

        strPlus.setOnClickListener(v -> {
            this.activity.decreaseUpgradePoints();
            player.gainStrength(1);
            updateContent();
        });
        agiPlus.setOnClickListener(v -> {
            this.activity.decreaseUpgradePoints();
            player.gainAgility(1);
            updateContent();
        });
        intPlus.setOnClickListener(v -> {
            this.activity.decreaseUpgradePoints();
            player.gainIntellect(1);
            updateContent();
        });

        updateContent();
    }



    private void updateContent() {
        if (activity.getUpgradePoints() > 0){
            enableButton(strPlus);
            enableButton(agiPlus);
            enableButton(intPlus);
        } else {
            disableButton(strPlus);
            disableButton(agiPlus);
            disableButton(intPlus);
        }

        pointsText.setText(String.valueOf(activity.getUpgradePoints()));
        String text = getResources().getString(R.string.character_stats_text_strength) + player.getStrength() ;
        strText.setText(text);
        text = getResources().getString(R.string.character_stats_text_agility) + player.getAgility() ;
        agiText.setText(text);
        text = getResources().getString(R.string.character_stats_text_intellect) + player.getIntellect() ;
        intText.setText(text);

    }

    private void disableButton(Button button) {
        button.setEnabled(false);
    }

    private void enableButton(Button button) {
        button.setEnabled(true);
    }


}
