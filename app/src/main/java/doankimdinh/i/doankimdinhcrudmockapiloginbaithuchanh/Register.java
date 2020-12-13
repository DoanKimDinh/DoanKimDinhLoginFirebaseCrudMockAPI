package doankimdinh.i.doankimdinhcrudmockapiloginbaithuchanh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText edtName, edtEmail, edtPassword,edtRepassword;
    FirebaseAuth fAuth;
    TextView tvLoginButton;
    Button btnRegister;
    ProgressBar progressBarRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtName = findViewById(R.id.edtName);
        btnRegister = findViewById(R.id.btnRegister);
        progressBarRegister = findViewById(R.id.progressBarRegister);
        tvLoginButton = findViewById(R.id.tvLoginButton);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtRepassword = findViewById(R.id.edtNhapLai);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String repassword = edtRepassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    edtEmail.setError("Email không để trống");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    edtPassword.setError("Mật khẩu không để trống");
                    return;
                }
                if(password.length() < 6){
                    edtPassword.setError("Mật khẩu phải >= 6 ký tự");
                    return;
                }
                if(TextUtils.isEmpty(repassword)){
                    edtRepassword.setError("Nhập lại mật khẩu không để trống");
                    return;
                }

                if(!repassword.equals(password)){
                    edtRepassword.setError("Mật khẩu không khớp");
                    return;
                }

                progressBarRegister.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "Đã thêm thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            Toast.makeText(Register.this, "Error !"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        tvLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}