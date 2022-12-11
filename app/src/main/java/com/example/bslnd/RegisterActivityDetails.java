package com.example.bslnd;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bslnd.http.HttpClient;
import com.example.bslnd.http.model.Sevadar;
import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivityDetails extends AppCompatActivity {


    private EditText profession;
    private EditText qualification;
    private EditText pathYear;
    private EditText knownLanguage;
    private EditText gotra;
    private EditText dob;
    private EditText tob;
    private EditText pob;
    private EditText nationality;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        setContentView(R.layout.register_activity_details);

        profession = findViewById(R.id.profession);
        qualification = findViewById(R.id.qualification);
        pathYear = findViewById(R.id.pathYear);
        knownLanguage = findViewById(R.id.knownLanguage);
        gotra = findViewById(R.id.gotra);
        dob = findViewById(R.id.dob);
        tob = findViewById(R.id.tob);
        pob = findViewById(R.id.pob);
        nationality = findViewById(R.id.nationality);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Sevadar sevadar = (Sevadar) intent.getExtras().getSerializable("sevadar");
                if(checkDataEntered()){
                    postData(new Sevadar(sevadar.getFullName(),
                            sevadar.getFatherName(),
                            sevadar.getGender(),
                            sevadar.getEmail(),
                            sevadar.getPhone(),
                            sevadar.getAddress(),
                            sevadar.getState(),
                            sevadar.getCity(),
                            sevadar.getPin(),
                            profession.getText().toString(),
                            qualification.getText().toString(),
                            pathYear.getText().toString(),
                            knownLanguage.getText().toString(),
                            dob.getText().toString(),
                            tob.getText().toString(),
                            pob.getText().toString(),
                            gotra.getText().toString(),
                            nationality.getText().toString()));
                }
            }
        });
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean checkDataEntered() {
        boolean valid = true;

        if (isEmpty(profession)) {
            profession.setError("Enter valid Profession!");
            valid = false;
        }
        if (isEmpty(qualification)) {
            qualification.setError("Enter valid Qualification!");
            valid = false;
        }
        if (isEmpty(pathYear)) {
            pathYear.setError("Enter valid Path Year!");
            valid = false;
        }
        if (isEmpty(knownLanguage)) {
            knownLanguage.setError("Enter valid Known Languages!");
            valid = false;
        }
        if (isEmpty(tob)) {
            tob.setError("Enter valid Time of Birth!");
            valid = false;
        }
        if (isEmpty(pob)) {
            pob.setError("Enter valid Place of Birth!");
            valid = false;
        }
        if (isEmpty(gotra)) {
            gotra.setError("Gotra is required!");
            valid = false;
        }
        if (isEmpty(nationality)) {
            nationality.setError("Nationality is required!");
            valid = false;
        }
        return valid;
    }

    private void postData(final Sevadar sevadar) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + getIntent().getExtras().getString("ipAddress") + ":" + getIntent().getExtras().getString("port"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HttpClient retrofitAPI = retrofit.create(HttpClient.class);
        //Call<Sevadar> call = retrofitAPI.createPost(sevadar);

        String json = new Gson().toJson(sevadar);
        RequestBody requestBodySevadar = RequestBody.create(MediaType.parse("multipart/form-data"), json.getBytes());

        MultipartBody.Part body = null;
        if(getIntent().getExtras().getByteArray("profilePic") != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),
                    getIntent().getExtras().getByteArray("profilePic"));
            body = MultipartBody.Part.createFormData("image", "profilePicBSLND.png", requestFile);
        }

        Call<Sevadar> call = retrofitAPI.createSevadar(requestBodySevadar, body);
        call.enqueue(new Callback<Sevadar>() {
            @Override
            public void onResponse(Call<Sevadar> call, Response<Sevadar> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(RegisterActivityDetails.this, "Registered!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivityDetails.this, RegisterActivity.class);
                    intent.putExtra("ipAddress", getIntent().getExtras().getString("ipAddress"));
                    intent.putExtra("port", getIntent().getExtras().getString("port"));
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivityDetails.this, "Error Registering!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Sevadar> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(RegisterActivityDetails.this, "Error Saving Data!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap image = (Bitmap) data.getExtras().get("data");
        profilePic.setImageBitmap(image);

        int imageSizeBeforeCompress = BitmapCompat.getAllocationByteCount(image);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        imageBytes = bos.toByteArray();
        int imageSizeAfterCompress = bos.size();
        Toast.makeText(RegisterActivityDetails.this, "Image Compressed from "+ imageSizeBeforeCompress +" to "+imageSizeAfterCompress, Toast.LENGTH_SHORT).show();
    }*/
}
