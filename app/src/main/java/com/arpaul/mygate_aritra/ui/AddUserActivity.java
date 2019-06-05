package com.arpaul.mygate_aritra.ui;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arpaul.mygate_aritra.BuildConfig;
import com.arpaul.mygate_aritra.R;
import com.arpaul.mygate_aritra.constants.Constant;
import com.arpaul.mygate_aritra.models.User;
import com.arpaul.mygate_aritra.viewmodel.UserVM;
import com.arpaul.utilitieslib.CalendarUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddUserActivity extends AppCompatActivity {

    @BindView(R.id.civProfileImage)
    protected CircleImageView civProfileImage;
    @BindView(R.id.edtProfileName)
    protected EditText edtProfileName;
    @BindView(R.id.btnSubmit)
    protected Button btnSubmit;
    @BindView(R.id.pbLoading)
    protected ProgressBar pbLoading;
    private String imagePath;
    private UserVM userVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        ButterKnife.bind(this);
        userVM = ViewModelProviders.of(this).get(UserVM.class);
    }

    public void onTakePic(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constant.getCaptureImageActivityRequestCode());
    }

    public void onSubmit(View view) {
        if (imagePath != null && !TextUtils.isEmpty(edtProfileName.getText().toString())) {
            pbLoading.setVisibility(View.VISIBLE);
            String timeStamp = CalendarUtils.getDateinPattern(Constant.getDateTimeFormatUtcMilis());
            String pic_type = "images/" +
                    BuildConfig.VERSION_NAME + "_" +
                    timeStamp +
                    ".jpg";

            User user = new User();
            user.setImagePath(imagePath);
            user.setUserName(edtProfileName.getText().toString());
            userVM.insert(user);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pbLoading.setVisibility(View.GONE);
                    finish();
                }
            }, 1000);
        } else if(TextUtils.isEmpty(edtProfileName.getText().toString()))
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Please take picture first", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.getCaptureImageActivityRequestCode()) {
            if (resultCode == Activity.RESULT_OK) {

                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                // convert byte array to Bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                File file = new File(getFilesDir(), "Image" + new Random().nextInt() + ".jpeg");
                FileOutputStream out = null;
                try {
                    out = openFileOutput(file.getName(), Context.MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //get absolute path
                imagePath = file.getAbsolutePath();
                Log.e("Image path <<>> ", imagePath);
                civProfileImage.setImageBitmap(bitmap);
            }
        }
    }
}
