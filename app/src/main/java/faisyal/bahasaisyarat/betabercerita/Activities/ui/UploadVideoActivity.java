package faisyal.bahasaisyarat.betabercerita.Activities.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

import faisyal.bahasaisyarat.betabercerita.R;

public class UploadVideoActivity extends AppCompatActivity {


    private  static final  int PIC_VIDEO_REQUEST= 1;

    private Spinner spinner;
    private Button pilihvideobtn, uploadvidbtn;
    private VideoView videoView;
    private Uri videoUri;
    MediaController mediaController;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private EditText videojudul;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);


        pilihvideobtn = findViewById(R.id.pilihvideobtn);
        uploadvidbtn = findViewById(R.id.uploadVideoBtn);
        videoView = findViewById(R.id.upload_VideoAdmin);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        videojudul = findViewById(R.id.JudulVideo);
        spinner = (Spinner) findViewById(R.id.spinnerkategori);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UploadVideoActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ss));
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(adapter);


        mediaController = new MediaController(this);

        storageReference = FirebaseStorage.getInstance().getReference("videos");
        databaseReference = FirebaseDatabase.getInstance().getReference("videos");

        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();

        pilihvideobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pilihvideobtn();
            }
        });

        uploadvidbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UploadVideo();
            }
        });

    }
    private void pilihvideobtn() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PIC_VIDEO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PIC_VIDEO_REQUEST && resultCode == RESULT_OK
        && data != null && data.getData() !=null);

        videoUri = data.getData();

        videoView.setVideoURI(videoUri);

    }

    private String getfileExt(Uri videoUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(videoUri));

    }

    private  void  UploadVideo(){

        progressBar.setVisibility(View.VISIBLE);
        if (videoUri != null) {
            StorageReference reference = storageReference.child(System.currentTimeMillis()+
                    "."+getfileExt(videoUri));

            reference.putFile(videoUri)
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
        }



}
}