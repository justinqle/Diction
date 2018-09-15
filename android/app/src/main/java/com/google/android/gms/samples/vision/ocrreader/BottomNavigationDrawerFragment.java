package com.google.android.gms.samples.vision.ocrreader;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class BottomNavigationDrawerFragment extends BottomSheetDialogFragment implements NavigationView.OnNavigationItemSelectedListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottomsheet, container, false);

        NavigationView navigationView = view.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        return view;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.read:
                Toast.makeText(getContext(), "Read Word Mode", Toast.LENGTH_LONG).show();
                break;
            case R.id.definition:
                Toast.makeText(getContext(), "Read Word & Definition Mode", Toast.LENGTH_LONG).show();
                break;
            case R.id.synonym:
                Toast.makeText(getContext(), "Find Synonyms Mode", Toast.LENGTH_LONG).show();
                break;

        }
        dismiss();
        return true;
    }

}
