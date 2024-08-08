package com.s22010180.zone.frag;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.s22010180.zone.utils.FragRep;
import com.s22010180.zone.utils.MainActivity;
import com.s22010180.zone.R;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountFragment extends Fragment {

    private EditText name, email, password;
    private TextView login;
    private Button signupBtn;
    private FirebaseAuth auth;
    public static final String Email_Pattern = "^(.+)@(.+)$";


    public CreateAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        click();
    }
    private void init(View view){
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        login = view.findViewById(R.id.ToLogin);
        signupBtn = view.findViewById(R.id.Signup);
        auth = FirebaseAuth.getInstance();

    }
    private void click(){
       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ((FragRep) getActivity()).setFragment(new LoginFragment());
           }
       });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Name = name.getText().toString();
                String Password = password.getText().toString();
                String Email = email.getText().toString();

                if (Name.isEmpty() || Name.equals(" ")) {
                    name.setError("Please Enter Valid Name");
                    return;
                }
                if (Email.isEmpty() || !Email.matches(Email_Pattern)) {
                    name.setError("Please Enter Valid Email");
                    return;
                }
                if (Password.isEmpty() || Password.length() < 6 ){
                    name.setError("Please Enter Valid Password");
                    return;
                }
                createAccount(Name, Email, Password);
            }
        });
    }
    private void createAccount(String name, String email, String password){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();

                    user.sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Email verification link send", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    uploadUser(user, name, email);

                }else{

                    String exception = task.getException().getMessage();
                    Toast.makeText(getContext(), "Error"+ exception, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private  void uploadUser(FirebaseUser user, String name, String email){
        Map<String, Object> map = new HashMap<>();

        map.put("name", name);
        map.put(email , email);
        map.put("profileImage", " ");
        map.put("uid", user.getUid());

        FirebaseFirestore.getInstance().collection("Users").document(user.getUid())
                .set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            assert getContext() != null;
                            startActivity(new Intent(getContext().getApplicationContext(), MainActivity.class));
                            getActivity().finish();
                        }else {
                            Toast.makeText(getContext(), "Error"+ task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}