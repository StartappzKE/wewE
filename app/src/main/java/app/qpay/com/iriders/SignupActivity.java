package app.qpay.com.iriders;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import app.qpay.com.iriders.flagments.SignupFragment;

public class SignupActivity extends AppCompatActivity {

    Fragment currentFragment = null;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentFragment = new SignupFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.containerView, currentFragment);
        ft.commit();


    }

}
