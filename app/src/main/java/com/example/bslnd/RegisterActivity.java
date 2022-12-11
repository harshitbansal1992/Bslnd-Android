package com.example.bslnd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.BitmapCompat;

import com.example.bslnd.http.HttpClient;
import com.example.bslnd.http.model.Sevadar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private ImageView profilePic;
    private EditText fullName;
    private EditText fatherName;
    private RadioGroup genderGroup;
    private RadioButton gender;
    private EditText email;
    private EditText phone;
    private EditText address;
    private EditText state;
    private EditText city;
    private EditText pin;
    private Button nextButton;
    private byte[] imageBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        setContentView(R.layout.register_activity);

        profilePic = findViewById(R.id.profilePic);
        fullName = findViewById(R.id.fullName);
        fatherName = findViewById(R.id.fatherName);
        genderGroup=(RadioGroup)findViewById(R.id.genderGroup);

        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        state = findViewById(R.id.state);
        city = findViewById(R.id.city);
        pin = findViewById(R.id.pin);

        nextButton = findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = genderGroup.getCheckedRadioButtonId();
                gender = (RadioButton) findViewById(selectedId);
                if(checkDataEntered()) {
                    Sevadar sevadar = new Sevadar(fullName.getText().toString(),
                            fatherName.getText().toString(),
                            gender.getText().toString(),
                            email.getText().toString(),
                            phone.getText().toString(),
                            address.getText().toString(),
                            state.getText().toString(),
                            city.getText().toString(),
                            pin.getText().toString(),
                            null,null,null,null,null,null,null,null,null);

                    Intent intent = new Intent(RegisterActivity.this, RegisterActivityDetails.class);
                    intent.putExtra("ipAddress", getIntent().getExtras().getString("ipAddress"));
                    intent.putExtra("port", getIntent().getExtras().getString("port"));
                    intent.putExtra("sevadar", sevadar);
                    intent.putExtra("profilePic", imageBytes);
                    startActivity(intent);
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 0);
            }
        });
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean checkDataEntered() {
        boolean valid = true;
        if (isEmpty(fullName)) {
            fullName.setError("Name is required!");
            valid = false;
        }

        if (isEmpty(fatherName)) {
            fatherName.setError("Father/Spouse name is required!");
            valid = false;
        }

        if (gender == null) {
            Toast.makeText(RegisterActivity.this, "Choose Gender!", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (isEmpty(address)) {
            address.setError("Address is required!");
            valid = false;
        }

        if (isEmail(email) == false) {
            email.setError("Enter valid email!");
              valid = false;
        }

        if (isEmpty(state)) {
            state.setError("Enter valid State!");
            valid = false;
        }

        if (isEmpty(city)) {
            city.setError("Enter valid City!");
            valid = false;
        }

        if (isEmpty(pin)) {
            pin.setError("Enter valid Pin!");
            valid = false;
        }
        return valid;
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap image = (Bitmap) data.getExtras().get("data");
        profilePic.setImageBitmap(image);

        int imageSizeBeforeCompress = BitmapCompat.getAllocationByteCount(image);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        imageBytes = bos.toByteArray();
        int imageSizeAfterCompress = bos.size();
        Toast.makeText(RegisterActivity.this, "Image Compressed from "+ imageSizeBeforeCompress +" to "+imageSizeAfterCompress, Toast.LENGTH_SHORT).show();
    }
}
