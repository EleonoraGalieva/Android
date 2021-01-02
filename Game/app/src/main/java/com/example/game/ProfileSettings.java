package com.example.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.game.Models.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileSettings extends AppCompatActivity {

    CircleImageView profilePicture;
    EditText changeUsername;
    Button save;

    DatabaseReference databaseReference;
    FirebaseUser currentUser;

    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        profilePicture = findViewById(R.id.profilePicture);
        changeUsername = findViewById(R.id.changeUsername);
        save = findViewById(R.id.save);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                changeUsername.setHint(user.getUsername());
                if (user.getImageURL().equals("default")) {
                    profilePicture.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(ProfileSettings.this).load(user.getImageURL()).into(profilePicture);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        profilePicture.setOnClickListener(view -> openImage());
    }

    public void save(View view) {
        if (changeUsername.getText().toString().isEmpty()) {
            Toast.makeText(ProfileSettings.this, "Username can't be empty!", Toast.LENGTH_LONG).show();
        } else {
            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid()).child("username");
            databaseReference.setValue(changeUsername.getText().toString());
            Toast.makeText(ProfileSettings.this, "Changes saved!", Toast.LENGTH_LONG).show();
        }
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(ProfileSettings.this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        if (imageUri != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask((Continuation<UploadTask.TaskSnapshot, Task<Uri>>) task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                return fileReference.getDownloadUrl();
            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String mUri = downloadUri.toString();

                    databaseReference = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("imageURL", mUri);
                    databaseReference.updateChildren(map);

                } else {
                    Toast.makeText(ProfileSettings.this, "Failed uploading", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }).addOnFailureListener(e -> {
                Toast.makeText(ProfileSettings.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            });
        } else {
            Toast.makeText(ProfileSettings.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(ProfileSettings.this, "Upload is in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }
}