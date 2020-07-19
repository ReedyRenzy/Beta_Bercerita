package faisyal.bahasaisyarat.betabercerita.Activities.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

import faisyal.bahasaisyarat.betabercerita.R;

public class UploadVideoActivity extends AppCompatActivity {


    private  static final  int PICK_VIDEO= 1;

    private Spinner spinner;
    private Button button;

    VideoView videoView;
    EditText editText;
    private Uri videoUri;
    MediaController mediaController;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    Member member;
    UploadTask uploadTask;
    ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);




        member = new Member();
        storageReference = FirebaseStorage.getInstance().getReference("videos");
        databaseReference = FirebaseDatabase.getInstance().getReference("videos");
        /*pilihvideobtn(); = findViewById(R.id.pilihvideobtn);*/

        mediaController = new MediaController(this);

        button = findViewById(R.id.uploadVideoBtn);
        videoView = findViewById(R.id.upload_VideoAdmin);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        editText = findViewById(R.id.JudulVideo);
        spinner = (Spinner) findViewById(R.id.spinnerkategori);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UploadVideoActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ss));
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(adapter);


        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UploadVideo();
            }
        });

    }


    public void pilihvideobtn (View view){

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_VIDEO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_VIDEO && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            videoUri = data.getData();
            videoView.setVideoURI(videoUri);

        }
    }

    private String getExt(Uri Uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(Uri));
    }

    public void ShowVideo(View view){

        Intent intent = new Intent(UploadVideoActivity.this,HomeActivity.class);
        startActivity(intent);
    }



    private  void  UploadVideo(){

        String videoName = editText.getText().toString();
        String search = editText.getText().toString().toLowerCase();
        if (videoUri != null) {

            progressBar.setVisibility(View.VISIBLE);
            final StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getExt(videoUri));
            uploadTask = reference.putFile(videoUri);


            /*StorageReference reference = storageReference.child(System.currentTimeMillis()+
                    "."+getfileExt(videoUri));
            uploadTask = reference.putFile(videoUri);*/

            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return reference.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {

                            if (task.isSuccessful()){

                                Uri downloadUrl = task.getResult();
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(UploadVideoActivity.this, "Upload Berhasil", Toast.LENGTH_SHORT).show();

                                member.setJudulVideo(videoName);
                                member.setVideoUrl(downloadUrl.toString());
                                member.setSearch(search);
                                String i = databaseReference.push().getKey();
                                databaseReference.child(i).setValue(member);

                            }else {
                                Toast.makeText(UploadVideoActivity.this,"Gagal Upload Video", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        }else {
            Toast.makeText(this, "Semua harus diisi", Toast.LENGTH_SHORT).show();
        }

           /* reference.putFile(videoUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(),"Upload Berhasil",Toast.LENGTH_SHORT).show();
                            Member member = new Member (videojudul.getText().toString().trim(),
                                    taskSnapshot.getUploadSessionUri().toString());
                            String upload = databaseReference.push().getKey();
                            databaseReference.child(upload).setValue(member);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            Toast.makeText(getApplicationContext(),"Tidak ada video yang dipilih",Toast.LENGTH_SHORT).show();
        }*/



    }
}