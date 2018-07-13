package com.example.mayank.internshiptask;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileImageActivity extends AppCompatActivity {

    CircleImageView imageView;
    Button button, chose_img;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    private Bitmap bitmap;
    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image);


        imageView = findViewById(R.id.image);
        SharedpreferenceHelper sharedPreferenceHelper = SharedpreferenceHelper.getInstance(ProfileImageActivity.this);

        String s = sharedPreferenceHelper.getImage();
        if( s!=null){
            byte[] b = Base64.decode(s, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            imageView.setImageBitmap(bitmap);
        }




        button = findViewById(R.id.change_img);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        chose_img  = findViewById(R.id.choose_img);
        chose_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });



    }



    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            Picasso.get()
                    .load(filePath)
                    .into(imageView);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();


            SharedpreferenceHelper sharedPreferenceHelper = SharedpreferenceHelper.getInstance(ProfileImageActivity.this);
            storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference ref = storageReference.child("images/" +sharedPreferenceHelper.getName() );
            ref.putFile(filePath)
                  .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                      @Override
                      public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                          if(bitmap!=null){
                              ByteArrayOutputStream baos = new ByteArrayOutputStream();
                              bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                              byte[] b = baos.toByteArray();

                              String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                              SharedpreferenceHelper sharedPreferenceHelper = SharedpreferenceHelper.getInstance(ProfileImageActivity.this);
                              sharedPreferenceHelper.setImage(encodedImage);
                              progressDialog.dismiss();
                              startActivity(new Intent(ProfileImageActivity.this,DashboardActivity.class));
                              finish();
                          }

                      }
                  });
        }
    }
}
