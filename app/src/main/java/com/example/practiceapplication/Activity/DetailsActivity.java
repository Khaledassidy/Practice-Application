package com.example.practiceapplication.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.practiceapplication.Database.DatabasesAcces;
import com.example.practiceapplication.Model.Car;
import com.example.practiceapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class DetailsActivity extends AppCompatActivity {

    Toolbar toolbar_details;
    TextInputEditText model,color,dpl,description;
    ImageView imageView;
    public static final int ADD_CAR_RESULT_CODE=1;
    public static final int EDIT_CAR_RESULT_CODE=2;
    public static final int DELETE_CAR_RESULT_CODE=3;
    private int carId=-1;
    Uri imageuri;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> activityResultLauncher1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        toolbar_details=findViewById(R.id.toolbar_detials);
        model=findViewById(R.id.TextInputEditText_model);
        imageView=findViewById(R.id.image_details);
        color=findViewById(R.id.TextInputEditText_color);
        dpl=findViewById(R.id.TextInputEditText_dpl);
        description=findViewById(R.id.TextInputEditText_description);
        activityResultLauncher1=registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean o) {
                if(o){
                    opengallary();
                }else{
                    if(ActivityCompat.shouldShowRequestPermissionRationale(DetailsActivity.this,Manifest.permission.READ_MEDIA_IMAGES)){
                        Toast.makeText(DetailsActivity.this, "this permission is required", Toast.LENGTH_SHORT).show();

                    }else{
                        Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        Toast.makeText(DetailsActivity.this, "twice denied please acces in setting", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if(o.getData()!=null && o.getResultCode()==RESULT_OK){
                    imageuri=o.getData().getData();
                    imageView.setImageURI(o.getData().getData());
                }
            }
        });
        setSupportActionBar(toolbar_details);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        Intent intent=getIntent();
        carId=intent.getIntExtra(MainActivity.CAR_KEY,-1);
        if(carId==-1){
            //add screen
            enablefield();
            clearfield();
        }else{
            //show screen
            disablefield();
            DatabasesAcces.getInstance(this).open();
            Car c = DatabasesAcces.getInstance(this).GetCar(carId);
            DatabasesAcces.getInstance(this).close();
            if(c!=null){
                addfield(c);
            }
        }
        imageView.setOnClickListener(v->{
           checkhavecamerapermissioninnewandroid();


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        MenuItem check= menu.findItem(R.id.check);
        MenuItem edit= menu.findItem(R.id.edit);
        MenuItem delete= menu.findItem(R.id.delete);
        if(carId==-1){
            //add screen bade e5fe l icons l 5asa bel ta3del w l delete
           check.setVisible(true);
           edit.setVisible(false);
           delete.setVisible(false);
        }else{
            //show screen edit or delete
            check.setVisible(false);
            edit.setVisible(true);
            delete.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String data_model,data_color,data_description,dta_image;
        double data_dpi;
        Car car;
        boolean result;
       if(item.getItemId()==R.id.check){
           data_model=model.getText().toString();
           data_color=color.getText().toString();
           data_description=description.getText().toString();
           data_dpi=Double.parseDouble(dpl.getText().toString());
           if(imageuri!=null){
               dta_image=String.valueOf(imageuri);

           }else{
               dta_image=String.valueOf(R.color.DARKPRIMARYCOLOR);

           }
           car=new Car(carId,dta_image,data_model,data_color,data_dpi,data_description);
           DatabasesAcces.getInstance(this).open();
           if(carId==-1){
               DatabasesAcces.getInstance(this).insertCar(car);
               Toast.makeText(this, "Car added successfully", Toast.LENGTH_SHORT).show();
               setResult(ADD_CAR_RESULT_CODE,null);
               finish();
           }else{
                DatabasesAcces.getInstance(this).updateCar(car);
               Toast.makeText(this, "Car edit successfully", Toast.LENGTH_SHORT).show();
               setResult(EDIT_CAR_RESULT_CODE,null);
               finish();

           }
           DatabasesAcces.getInstance(this).close();

         return true;
       } else if (item.getItemId()==R.id.edit) {
           enablefield();
           MenuItem check= toolbar_details.getMenu().findItem(R.id.check);
           MenuItem edit= toolbar_details.getMenu().findItem(R.id.edit);
           MenuItem delete= toolbar_details.getMenu().findItem(R.id.delete);
           edit.setVisible(false);
           delete.setVisible(false);
           check.setVisible(true);
           return true;
       } else if (item.getItemId()==R.id.delete) {
           car=new Car(carId,null,null,null,0,null);
           DatabasesAcces.getInstance(this).open();
           result=DatabasesAcces.getInstance(this).DeleteCar(car);
           DatabasesAcces.getInstance(this).close();
           if(result){
               Toast.makeText(this, "Car delete succesfully", Toast.LENGTH_SHORT).show();
           }
           setResult(DELETE_CAR_RESULT_CODE,null);
           finish();
           return true;
       }
       return false;

    }

    private void disablefield(){
        imageView.setEnabled(false);
        model.setEnabled(false);
        color.setEnabled(false);
        dpl.setEnabled(false);
        description.setEnabled(false);

    }


    private void enablefield(){
        imageView.setEnabled(true);
        model.setEnabled(true);
        color.setEnabled(true);
        dpl.setEnabled(true);
        description.setEnabled(true);

    }


    private void clearfield(){
        imageView.setImageURI(null);
        model.setText(null);
        color.setText(null);
        dpl.setText(null);
        description.setText(null);

    }

    private void addfield(Car car){
        if(car!=null) {
            imageView.setImageURI(Uri.parse(car.getImage_car()));
            model.setText(car.getModel_car());
            color.setText(car.getColor_car());
            dpl.setText(String.valueOf(car.getDpl_car()));
            description.setText(car.getDescription_car());
        }


    }


    public void opengallary(){
        Intent intent1=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultLauncher.launch(intent1);


    }


    public void checkhavecamerapermissioninnewandroid(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                Log.d("khaled","khaled");
                opengallary();
            }else{
                activityResultLauncher1.launch(Manifest.permission.READ_MEDIA_IMAGES);
            }
        }else{
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            }else{
                activityResultLauncher1.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

            }
        }
    }




}