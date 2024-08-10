package com.example.practiceapplication.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
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
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceapplication.Addapters.Caraddapter;
import com.example.practiceapplication.Costumeclicklistner.OnRecyclerViewItemClickListener;
import com.example.practiceapplication.Database.DatabasesAcces;
import com.example.practiceapplication.Model.Car;
import com.example.practiceapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final String CAR_KEY="car_key";
    Toolbar toolbar;
    RecyclerView recyclerView_main;
    FloatingActionButton floatingActionButton_main;
    Caraddapter caraddapter;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> activityResultLauncher1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar_main);
        floatingActionButton_main=findViewById(R.id.floating_btn_main);
        recyclerView_main=findViewById(R.id.main_recyleview);
        setSupportActionBar(toolbar);
        activityResultLauncher1=registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean o) {
                if(o){
                    Toast.makeText(MainActivity.this, "camera permission acces", Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(MainActivity.this, "camera permission are required", Toast.LENGTH_SHORT).show();


                }
            }
        });



        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if(o.getResultCode()==DetailsActivity.ADD_CAR_RESULT_CODE || o.getResultCode()==DetailsActivity.EDIT_CAR_RESULT_CODE || o.getResultCode()==DetailsActivity.DELETE_CAR_RESULT_CODE){
                    DatabasesAcces.getInstance(getApplicationContext()).open();
                    ArrayList<Car> arrayList= DatabasesAcces.getInstance(getApplicationContext()).GetAllCars();
                    DatabasesAcces.getInstance(getApplicationContext()).close();
                    caraddapter.setArrayList(arrayList);
                    caraddapter.notifyDataSetChanged();
                }
            }
        });
        addaptercar();
        checkhavecamerapermissioninnewandroid();
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }




        floatingActionButton_main.setOnClickListener(v->{
            Intent intent=new Intent(getApplicationContext(),DetailsActivity.class);
            activityResultLauncher.launch(intent);


        });



    }


    public void addaptercar(){
        DatabasesAcces.getInstance(this).open();
        ArrayList<Car> arrayList= DatabasesAcces.getInstance(this).GetAllCars();
        DatabasesAcces.getInstance(this).close();
        caraddapter=new Caraddapter(getApplicationContext(),arrayList, new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int car_id) {
                Intent intent=new Intent(getApplicationContext(),DetailsActivity.class);
                intent.putExtra(CAR_KEY,car_id);
                activityResultLauncher.launch(intent);
            }
        });
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView_main.setLayoutManager(layoutManager);
        recyclerView_main.setAdapter(caraddapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        SearchView searchView=(SearchView) menu.findItem(R.id.search_menue).getActionView();
        if(searchView!=null) {
            searchView.setQueryHint("Search Car");
            searchView.setSubmitButtonEnabled(true);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    DatabasesAcces.getInstance(getApplicationContext()).open();
                    ArrayList<Car> arrayList=DatabasesAcces.getInstance(getApplicationContext()).SearchCars(query);
                    DatabasesAcces.getInstance(getApplicationContext()).close();
                    caraddapter.setArrayList(arrayList);
                    caraddapter.notifyDataSetChanged();

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    DatabasesAcces.getInstance(getApplicationContext()).open();
                    ArrayList<Car> arrayList=DatabasesAcces.getInstance(getApplicationContext()).SearchCars(newText);
                    DatabasesAcces.getInstance(getApplicationContext()).close();
                    caraddapter.setArrayList(arrayList);
                    caraddapter.notifyDataSetChanged();
                    return true;
                }
            });

            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    DatabasesAcces.getInstance(getApplicationContext()).open();
                    ArrayList<Car> arrayList=DatabasesAcces.getInstance(getApplicationContext()).GetAllCars();
                    DatabasesAcces.getInstance(getApplicationContext()).close();
                    caraddapter.setArrayList(arrayList);
                    caraddapter.notifyDataSetChanged();
                    return true;
                }
            });
        }

        return true;
    }
    public void checkhavecamerapermissioninnewandroid(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "you have camera permission", Toast.LENGTH_SHORT).show();
            }else{
                activityResultLauncher1.launch(Manifest.permission.READ_MEDIA_IMAGES);
            }
        }else{
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "you have camera permission", Toast.LENGTH_SHORT).show();
            }else{
                activityResultLauncher1.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

            }
        }
    }


}