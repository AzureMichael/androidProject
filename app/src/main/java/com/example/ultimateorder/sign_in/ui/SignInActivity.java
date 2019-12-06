package com.example.ultimateorder.sign_in.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ultimateorder.R;
import com.example.ultimateorder.model.Employee;
import com.example.ultimateorder.waiter.WaiterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "Sign In Activity";
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Button signInButton = findViewById(R.id.buttonSignIn);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText)findViewById(R.id.emailField)).getText().toString().trim();
                String password = ((EditText)findViewById(R.id.passwordField)).getText().toString().trim();
                signIn(email, password);
            }
        });
    }




    private void signIn(String email, String password){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Log.d(TAG, "signInWithEmail:success");
                            Log.d(TAG, "Logged in user: " + Objects.requireNonNull(user).getEmail());

                            AtomicReference<String> type = new AtomicReference<>("");
                            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                            firestore.collection("employees").get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            List<Employee> employees = queryDocumentSnapshots.toObjects(Employee.class);
                                            employees.forEach(e -> {
                                                if(e.getUsername().equals(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail())){
                                                    type.set(e.getType());
                                                }
                                            });
                                            Log.d(TAG, "Employee type: " + type.get());
                                            if(type.get().equals("waiter")) {
                                                Intent intent = new Intent(getBaseContext(), WaiterActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                        }
                        else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
