package com.coffeeandcookies.portal125;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.adammcneilly.ActionButton;
import com.adammcneilly.ActionCardView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ItemViewHolder>
{
    List<Post> items;

    AdapterUsuarioInterface adapterInterface;

    FragmentActivity contexto;

    public interface AdapterUsuarioInterface
    {
        public void onClickPressItem();

    }


    public PostsAdapter(List<Post> items, AdapterUsuarioInterface adapterInterface, FragmentActivity contexto)
    {
        this.items = items;
        this.contexto = contexto;
        this.adapterInterface = adapterInterface;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_post, viewGroup, false);
        ItemViewHolder pvh = new ItemViewHolder(v,adapterInterface,contexto);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder personViewHolder, int i) {

        personViewHolder.setItem(items.get(i));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        ActionCardView actionCardView;
        LinearLayout ll_root;
        FragmentActivity contexto;
        AdapterUsuarioInterface adapterInterface;

        ItemViewHolder(View itemView,AdapterUsuarioInterface adapterInterface, FragmentActivity contexto)
        {
            super(itemView);
            this.contexto = contexto;
            this.adapterInterface = adapterInterface;
            actionCardView = (ActionCardView) itemView.findViewById(R.id.action_card);

        }

        public void setItem(final Post item)
        {
            actionCardView.setTitle(Html.fromHtml(item.getTitle()));
            try
            {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
                Date d = df.parse(item.getDate());
                DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
                actionCardView.setDescription(df2.format(d));
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            actionCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(contexto,PostActivity.class);
                    intent.putExtra("id",item.getId());
                    contexto.startActivity(intent);
                }
            });

        }

    }
}