package com.niketgoel.assignment102;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    boolean dualPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MasterFragment masterFragment = null;
        FrameLayout frameLayout =
                (FrameLayout)findViewById(R.id.frameLayout);
        if(frameLayout != null) {
            dualPane = false;
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            masterFragment = (MasterFragment) getSupportFragmentManager()
                    .findFragmentByTag("MASTER");

            if (masterFragment == null) {
                masterFragment = new MasterFragment();
                fragmentTransaction.add(R.id.frameLayout,
                        masterFragment, "MASTER");
            }
            DetailsFragment detailsFragment = (DetailsFragment)
                    getSupportFragmentManager().findFragmentById(R.id.frameLayout);
            if (detailsFragment != null) {
                fragmentTransaction.remove(detailsFragment);
            }
            fragmentTransaction.commit();
        }
        else {
            dualPane = true;
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            masterFragment = (MasterFragment)
                    getSupportFragmentManager().findFragmentById(R.id.frameLayout);

            if (masterFragment == null) {
                masterFragment = new MasterFragment();
                fragmentTransaction.add(R.id.frameLayout, masterFragment);

            }

            DetailsFragment detailsFragmenta = (DetailsFragment)
                    getSupportFragmentManager().findFragmentById(R.id.frameLayout);
            if (detailsFragmenta == null) {
                detailsFragmenta = new DetailsFragment();
                fragmentTransaction.add(R.id.frameLayout, detailsFragmenta);
            }
            fragmentTransaction.commit();
        }
        masterFragment.setOnMasterSelectedListener(new MasterFragment.OnMasterSelectedListener() {
            @Override
            public void onItemSelected(String layoutNumber) {
                sendLayoutNumber(layoutNumber);
            }
        });


    }

    //sendCLayoutNumber() method handles sending the layout number to DetailFragment

    private void sendLayoutNumber(String layoutNumber){
        DetailsFragment detailsFragment;
        if(dualPane){
            //Two pane layout
            detailsFragment = (DetailsFragment)
                    getSupportFragmentManager().findFragmentById(R.id.frameLayout);
            detailsFragment.showSelectedLayout(layoutNumber);
        }
        else{
            //Single pane layout
            detailsFragment = new DetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(DetailsFragment.KEY_LAYOUTS_NUMBER,layoutNumber);
            detailsFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout,detailsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}
