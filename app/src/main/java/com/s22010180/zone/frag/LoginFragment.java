package com.s22010180.zone.frag;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.s22010180.zone.utils.FragRep;
import com.s22010180.zone.utils.MainActivity;
import com.s22010180.zone.R;

public class LoginFragment extends Fragment {
    private EditText Email, Password;
    private TextView SignUp, ForgotPassword;
    private Button LoginBtn ;
    public static final String Email_Pattern = "^(.+)@(.+)$";
    private FirebaseAuth auth;

    public LoginFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        clickListener();

    }

    private void init(View view) {

        Email = view.findViewById(R.id.email);
        Password = view.findViewById(R.id.password);
        LoginBtn = view.findViewById(R.id.SignIn);
        SignUp = view.findViewById(R.id.ToSignup);
        ForgotPassword = view.findViewById(R.id.forgotpassword);
        auth = FirebaseAuth.getInstance();

    }

    private void clickListener() {
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = Email.getText().toString();
                String password = Password.getText().toString();

                if (email.isEmpty() || !email.matches(Email_Pattern)) {
                    Email.setError("Input valid email");
                    return;
                }

                if (password.isEmpty() || password.length() < 6) {
                    Password.setError("Input 6 digit valid password ");
                    return;
                }

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    FirebaseUser user = auth.getCurrentUser();

                                    if (!user.isEmailVerified()) {
                                        Toast.makeText(getContext(), "Please verify your email", Toast.LENGTH_SHORT).show();
                                    }

                                    sendUserToMainActivity();

                                } else {
                                    String exception = "Error: " + task.getException().getMessage();
                                    Toast.makeText(getContext(), exception, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragRep) getActivity()).setFragment(new CreateAccountFragment());
            }
        });
    }
    private void sendUserToMainActivity() {

        if (getActivity() == null)
            return;

        startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
        getActivity().finish();

    }

}