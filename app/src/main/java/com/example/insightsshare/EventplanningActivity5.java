package com.example.insightsshare;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

//import com.example.insightsshare.databinding.ActivityEventplanning5Binding;


public class EventplanningActivity5 extends AppCompatActivity {

    ListView listView;
    int images[]= {R.mipmap.ic_launcher_round,R.mipmap.ic_launcher};
    String mText[]= {"Name2", "Name1"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventplanning5);

        listView = findViewById(R.id.ListOfParticipants5);
        MyAdapter adapter= new MyAdapter(this, mText, images);
        listView.setAdapter(adapter);
    }


    //including row5 to eventplanningActivity5 (for the Listview to see the paticipants)

    //Adapter for ListView Element
    class MyAdapter extends ArrayAdapter <String>{
        Context context;
        int rImage[];
        String rText[];

        MyAdapter(Context c, String text[], int image[]){
           super(c, R.layout.row5, R.id.RowText5, text);
           this.context=c;
           this.rImage= image;
           this.rText=text;

        }
        @NonNull
        @Override

        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row5= layoutInflater.inflate(R.layout.row5, parent, false);
            ImageView myImage=row5.findViewById(R.id.ProfileImage5);
            TextView myText=row5.findViewById(R.id.RowText5);

            myImage.setImageResource(rImage[position]);
            myText.setText(rText[position]);

            return row5;
        }
    }
}