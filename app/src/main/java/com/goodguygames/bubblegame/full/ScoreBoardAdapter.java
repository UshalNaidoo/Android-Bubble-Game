package com.goodguygames.bubblegame.full;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.goodguygames.bubblegame.full.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ScoreBoardAdapter extends ArrayAdapter<ScoreBoard>{

    Context context; 
    int layoutResourceId;    
    ScoreBoard data[] = null;
	
public ScoreBoardAdapter(Context context, int layoutResourceId, ScoreBoard[] data) {
    super(context, layoutResourceId, data);
	this.layoutResourceId = layoutResourceId;
	this.context = context;
	this.data = data;
}
 @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ScoreBoardHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new ScoreBoardHolder();
            holder.name = (TextView)row.findViewById(R.id.nameTV);
            holder.score = (TextView)row.findViewById(R.id.scoreTV);
            row.setTag(holder);
        }
        else
        {
            holder = (ScoreBoardHolder)row.getTag();
        }
        
        ScoreBoard att = data[position];
        holder.name.setText(att.name);
        holder.score.setText(Integer.toString(att.score));
        try {
        	ImageView imageView = (ImageView) row.findViewById(R.id.imageIV);
    		String UserImageURL = "https://graph.facebook.com/"+att.url.toString()+"/picture?type=square";
    		Log.d("FB", "UserImageURL : " + UserImageURL);
    		URL userurl = new URL(UserImageURL);
			Bitmap User_Icon_val = BitmapFactory.decodeStream(userurl.openConnection().getInputStream()); 
			imageView.setImageBitmap(User_Icon_val);
        	
        	
    	} catch (MalformedURLException e) {
    		Log.d("FB", "Error : " + e.toString());
		} catch (IOException e) {
			Log.d("FB", "Error : " + e.toString());;
		} 
        
        RelativeLayout layout = (RelativeLayout)row.findViewById(R.id.barlayout);
        
        if(att.isMe.equals("0")){
	    	   layout.setBackgroundResource(R.drawable.buttons_background);
	       }
        else if (att.isMe.equals("1")){
	    	   layout.setBackgroundResource(R.drawable.darkbuttons_background);
	       }
        
        return row;
    }
    
    static class ScoreBoardHolder
    {
        TextView name;
        TextView score;
        TextView url;
    }
   
}