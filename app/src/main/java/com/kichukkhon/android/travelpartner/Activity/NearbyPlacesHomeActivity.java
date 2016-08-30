package com.kichukkhon.android.travelpartner.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.Constants;
import com.kichukkhon.android.travelpartner.Util.PlaceTypes;

public class NearbyPlacesHomeActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_places_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarWithAppbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Important Places");

        InitCommonUIElements();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvTourList);
        PlaceTypeContentAdapter adapter = new PlaceTypeContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        //set padding for tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.place_type_item_tile, parent, false));

            picture = (ImageView) itemView.findViewById(R.id.place_img);
            name = (TextView) itemView.findViewById(R.id.place_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    PlaceTypes placeTypes = new PlaceTypes();
                    Intent intent = new Intent(context, Places_list.class);
                    String place = placeTypes.place_type_list[getAdapterPosition()];
                    intent.putExtra(Constants.PLACE_TYPE_ID_KEY, place);
                    context.startActivity(intent);
                }
            });
        }
    }

    public static class PlaceTypeContentAdapter extends RecyclerView.Adapter<ViewHolder> {

        PlaceTypes placeTypes;
        String colors[] = {"#ffb300", "#2196f3", "#0277bd", "#e65100", "#3f51b5", "#004d40", "#4caf50", "#ffc107", "#607d8b", "#e91e63", "#3f51b5", "#9c27b0", "#673ab7"};
        Context mContext;

        public PlaceTypeContentAdapter(Context context) {
            mContext = context;
            placeTypes = new PlaceTypes();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String place_type_id = placeTypes.place_type_list[position];
            String icon_id = placeTypes.placeTypes.get(place_type_id);

            holder.picture.setBackgroundColor(Color.parseColor(colors[position % 13]));
            if (icon_id != null) {
                Drawable drawable = mContext.getResources().getDrawable(getDrawable(mContext, icon_id));
                holder.picture.setImageDrawable(drawable);
            }

            if (place_type_id == "grocery_or_supermarket") {
                place_type_id = "supermarket";
            }
            holder.name.setText(place_type_id.toUpperCase().replace("_", " "));
        }

        public static int getDrawable(Context context, String name) {
            return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        }

        @Override
        public int getItemCount() {
            return placeTypes.place_type_list.length;
        }
    }


}
