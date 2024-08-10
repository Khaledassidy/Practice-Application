package com.example.practiceapplication.Addapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceapplication.Costumeclicklistner.OnRecyclerViewItemClickListener;
import com.example.practiceapplication.Model.Car;
import com.example.practiceapplication.R;

import java.util.ArrayList;

public class Caraddapter extends RecyclerView.Adapter<Caraddapter.Viewholder> {

    Context context;
    ArrayList<Car> arrayList;
    OnRecyclerViewItemClickListener listener;

    public Caraddapter(Context context,ArrayList<Car> arrayList,OnRecyclerViewItemClickListener listener){
        this.context=context;
        this.arrayList=arrayList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.costume_car_layout,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        if(arrayList.get(position)!=null){
            holder.imageView.setImageURI(Uri.parse(arrayList.get(position).getImage_car()));
            holder.tv_model.setText(arrayList.get(position).getModel_car());
            holder.tv_color.setText(arrayList.get(position).getColor_car());
            holder.tv_dpl.setText(String.valueOf(arrayList.get(position).getDpl_car()));
            holder.imageView.setTag(arrayList.get(position).getId());

        }
    }

    public ArrayList<Car> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Car> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        TextView tv_model,tv_color,tv_dpl;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.costume_card_imageview);
            tv_model=itemView.findViewById(R.id.costume_card_model);
            tv_color=itemView.findViewById(R.id.costume_card_color);
            tv_dpl=itemView.findViewById(R.id.costume_card_dpl);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int id=(Integer) imageView.getTag();
            listener.onItemClick(id);

        }
    }
}
