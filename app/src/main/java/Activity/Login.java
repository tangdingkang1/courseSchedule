package Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.myapplication.R;
import FormHandle.Form;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private EditText userNameView;
    private EditText passwordView;
    private EditText verifyTextView;
    private ImageView verifyImage;
    private Button loginBtn;

    private Form form;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

    userNameView = findViewById(R.id.Edit_ID);
    passwordView = findViewById(R.id.Edit_PWD);
    verifyTextView = findViewById(R.id.Edit_Verify);
    verifyImage = findViewById(R.id.verifyImage);
    loginBtn = findViewById(R.id.loginButton);

    loginBtn.setOnClickListener(this::submitForm);   // submit onClick
    verifyImage.setOnClickListener( (view) -> freshForm() );
    }
    public void freshForm(){
        form = new Form();
        form.getVerifyCodeImage(this); //调用函数，获得验证码
    }
    public void submitForm(View view){
        Log.e("name",userNameView.getText().toString());
        Log.e("pass",passwordView.getText().toString());
        Log.e("code",verifyTextView.getText().toString());
            form.submit(this, userNameView.getText().toString(), passwordView.getText().toString(), verifyTextView.getText().toString());
    }
    public void loadImage(Bitmap bitMap){
        runOnUiThread( ()-> {
            if (bitMap == null){
                verifyImage.setImageBitmap(bitMap);
                Toast.makeText(getApplicationContext(),"网络错误", Toast.LENGTH_SHORT).show();
            }
            else  verifyImage.setImageBitmap(bitMap);
        });

    }
}
