package com.example.user.scannergency;


        import android.Manifest;
        import android.app.Activity;
        import android.content.ActivityNotFoundException;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.os.Environment;
        import android.os.StrictMode;
        import android.provider.MediaStore;
        import android.support.annotation.NonNull;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.util.DisplayMetrics;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.ImageView;
        import android.widget.Toast;

        import java.io.File;

public class TakeImage extends AppCompatActivity {

    ImageView imageView;
    Toolbar toolbar;
    File file;
    Uri uri;
    Intent CamIntent,GalIntent,CropIntent;
    final int RequestPermissionCode=1;
    DisplayMetrics displayMetrics;
    int width,height;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("SCANNERGENCY");
        setSupportActionBar(toolbar);

        imageView = (ImageView)findViewById(R.id.imageView);

        int permissionCheck = ContextCompat.checkSelfPermission(TakeImage.this, Manifest.permission.CAMERA);
        if(permissionCheck == PackageManager.PERMISSION_DENIED)
            RequestRuntimePermission();
    }

    private void RequestRuntimePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(TakeImage.this,Manifest.permission.CAMERA))
            Toast.makeText(this,"CAMERA permission allows us to access CAMERA app",Toast.LENGTH_SHORT).show();
        else
        {
            ActivityCompat.requestPermissions(TakeImage.this,new String[]{Manifest.permission.CAMERA},RequestPermissionCode);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.btn_camera)
            CameraOpen();
        else if(item.getItemId() == R.id.btn_gallery)
            GalleryOpen();
        return true;
    }


    private void GalleryOpen() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        GalIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(GalIntent,"Select Image from Gallery"),2);
    }

    private void CameraOpen() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        CamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(Environment.getExternalStorageDirectory(),
                "file"+String.valueOf(System.currentTimeMillis())+".jpg");
        uri = Uri.fromFile(file);
        CamIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        CamIntent.putExtra("return-data",true);
        startActivityForResult(CamIntent,0);
    }

