package backcodes.com.buntykaghanti;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class rcvadapter extends RecyclerView.Adapter<rcvadapter.holder> {
    Context ctx;
    int i=0;
    MediaPlayer mediaPlayer;
    String w[]={"Aye Madarchod","Bunty to Chutiya Hai","Chatri Daldunga","Dekh Lega Madarchod","Mtlb Mera Lauda","Taboot","Avi baat kar raha hai"};
    int s[]={R.raw.ayemc,R.raw.buntytochutiahai,R.raw.chatri,R.raw.dekhlegamc,R.raw.mtlbmeral,R.raw.taboot,R.raw.avibaakarrahahainamai};
    rcvadapter(Context tc)
    {
        ctx=tc;

    }

    @Override
    public holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler,parent,false );

        return new holder(view);
    }

    @Override
    public void onBindViewHolder(holder holder, final int position) {
     holder.tvdialog.setText(w[position]);
     holder.cardView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(i>0)
             mediaPlayer.stop();
             play(s[position]);

         }
     });
     holder.tvdialog.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(i>0)
                 mediaPlayer.stop();
             play(s[position]);
         }
     });
    }


    @Override
    public int getItemCount() {
        return w.length;
    }
    public class holder extends RecyclerView.ViewHolder
    {
         TextView tvdialog;
         CardView cardView;
        public holder(View itemView) {
            super(itemView);
            tvdialog=(TextView)itemView.findViewById(R.id.tvdialog);
            cardView=(CardView)itemView.findViewById(R.id.card);
        }
    }
    public void play(int n)
    {
        i++;
        mediaPlayer=MediaPlayer.create(ctx,n);
        mediaPlayer.start();
    }
}
