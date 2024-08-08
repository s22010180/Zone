package com.s22010180.zone.utils;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.s22010180.zone.R;
import com.s22010180.zone.frag.CreateAccountFragment;
import com.s22010180.zone.frag.LoginFragment;

import org.w3c.dom.Comment;

public class FragRep extends AppCompatActivity {
    private FrameLayout frame ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag_rep);

        frame = findViewById(R.id.frame);

        setFragment(new LoginFragment());
    }

    public  void setFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        if(fragment instanceof CreateAccountFragment){
            fragmentTransaction.addToBackStack(null);
        }

        if (fragment instanceof Comment){

            String id = getIntent().getStringExtra("id");
            String uid = getIntent().getStringExtra("uid");

            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            bundle.putString("uid", uid);
            fragment.setArguments(bundle);
        }
        fragmentTransaction.replace(frame.getId(),fragment);
        fragmentTransaction.commit();

    }
}