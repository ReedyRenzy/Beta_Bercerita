package faisyal.bahasaisyarat.betabercerita.Activities.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import faisyal.bahasaisyarat.betabercerita.R;

public class RegisterActivity extends AppCompatActivity {

    ImageView ImgUserPhoto;
    static int PreqCode = 1 ;
    static int REQUESCODE = 1 ;
    Uri pickedImgUri;
    Uri ImageUri;

    private EditText userEmail,userPassword,userPassword2,username;
    private ProgressBar loadingProgress;
    private Button regBtn, backBtn;
    private DatabaseReference rootref;
    private DatabaseReference Users;
    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // ini views
        userEmail = findViewById(R.id.regMail);
        userPassword = findViewById(R.id.regPassword);
        userPassword2 = findViewById(R.id.regPassword2);
        username = findViewById(R.id.regName);
        loadingProgress = findViewById(R.id.regProgressBar);
        regBtn = findViewById(R.id.regBtn);
        loadingProgress.setVisibility(View.INVISIBLE);
        backBtn = findViewById(R.id.backBtn);

        mAuth = FirebaseAuth.getInstance();
        rootref = FirebaseDatabase.getInstance().getReference();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivity = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(loginActivity);
                finish();


            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                regBtn.setVisibility(View.INVISIBLE);
                loadingProgress.setVisibility(View.VISIBLE);
                final String email = userEmail.getText().toString();
                final String password = userPassword.getText().toString();
                final String password2 = userPassword2.getText().toString();
                final String name = username.getText().toString();
//                String userid = mAuth.getCurrentUser().getUid();





                if( email.isEmpty() || name.isEmpty()  || password.isEmpty() || !password.equals(password2))  {

                    // something goes wrong
                    // we need to display an error message
                    showMessage("Please Verify all fields") ;
                    regBtn.setVisibility(View.VISIBLE);
                    loadingProgress.setVisibility(View.INVISIBLE);


                }
                else {
                    // everything is ok and all fields are filled now we can start creating user account
                    // CreateUserAccount method will try to create the user if the email is vaid
                    CreaterUserAccount(email,name,password);



                }




            }
        });

        ImgUserPhoto = findViewById(R.id.regUserPhoto) ;

        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (Build.VERSION.SDK_INT >= 29) {
//
//                    checkAndRequestForPermission();
//
//                }
//                else
//                {
//                    openGallery();
//                }

        CropImageBro();


            }
        });

    }

    private void CropImageBro() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    private void CreaterUserAccount(String email, final String name, String password) {


        // this method create user account with specific email and password

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
// user account created successfully
                            showMessage("Account created");
                            // afterwe created user account we need to update his profile picture and name
                            updateUserInfo(name, pickedImgUri, mAuth.getCurrentUser());

                            String userid = mAuth.getCurrentUser().getUid();
                            HashMap<String, Object> simpan = new HashMap<>();
                            simpan.put("userid", userid);
                            simpan.put("email", email);
                            simpan.put("password", password);
                            simpan.put("username", name);
                            simpan.put("email", email);

                            rootref.child(userid).setValue(simpan).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });


                        }
                        else
                        {

                            // account creation failed
                            showMessage("account creation failed" + task.getException().getMessage().toString());
                            regBtn.setVisibility(View.VISIBLE);
                            loadingProgress.setVisibility((View.INVISIBLE));

                        }
                    }
                });
    }

    // update user photo and name
    private void updateUserInfo(final String name, Uri pickedImgUri, final FirebaseUser currentUser) {

        // first we need to upload user photo to firebase storage and get url
        //Ngambil foto pake ImageUri jangan pake pickedimage, image uri udh jadigobal jadi bisa dipake di function manapun
        //Upload berhasil, autentifikasi coba di clear meh teu banyak akun
        //Terus mulai langsung bikin realtime database nyimpen informasi user,supaya dapet userid biar dapet session di aplikasi

        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child("foto.jpg");
        imageFilePath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // image uploaded successfully
                // now we can get our image url

                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        // uri contain user image url

                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();

                        currentUser.updateProfile(profileUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            //user info update successfully
                                            showMessage("Register Complete");
                                            updateUI();
                                        }

                                    }
                                });




                    }
                });





            }
        });

    }

    private void updateUI() {

        Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeActivity);
        finish();
    }


    // simple method to show toast message
    private void showMessage(String message) {


        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

    }

    private void openGallery() {
        //TODO: open gallery intent and wait for user to pick an image !

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("Image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }

    private void checkAndRequestForPermission() {

        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(RegisterActivity.this, "Please accept for required permission",Toast.LENGTH_SHORT).show();
            }

            else
            {
                ActivityCompat.requestPermissions(RegisterActivity.this,
                                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                                        PreqCode);
            }
        }

        else
                openGallery();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (data != null) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);//Ngambil hasil crop foto
            ImageUri=result.getUri(); //Tempat nyimpen sementara foto
            ImgUserPhoto.setImageURI(ImageUri); //Pasang hasil foto di image view
        }
        else {
            Toast.makeText(this, "Pilih gambar", Toast.LENGTH_SHORT).show();
        }

    }
}
