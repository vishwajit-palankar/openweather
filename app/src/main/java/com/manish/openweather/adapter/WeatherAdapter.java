package com.manish.openweather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.manish.openweather.R;
import com.manish.openweather.data.WeatherInformation;
import com.manish.openweather.data.WeatherList;
import com.manish.openweather.utility.Constant;
import com.manish.openweather.utility.Utility;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by manishp on 05/07/16.
 */
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private static final String TAG = WeatherAdapter.class.getSimpleName();
    private List<WeatherList> mWeatherList;
    private WeatherInformation mWeatherInformation;

    public WeatherAdapter(WeatherInformation weatherInformation) {
        mWeatherInformation = weatherInformation;

        if (null != mWeatherInformation) {
            mWeatherList = mWeatherInformation.getWeatherList();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (null != mWeatherList) {
            WeatherList weatherList = mWeatherList.get(position);

            if (null != weatherList.getWeather() && weatherList.getWeather().size() > 0) {
                Picasso.with(holder.ivIcon.getContext())
                        .load(String.format(Constant.IMAGE_URL, weatherList.getWeather().get(0).getIcon())).error(R.drawable.ic_cloud).placeholder(R.drawable.ic_cloud)
                        .into(holder.ivIcon);
            }

            holder.tvTemp.setText(new DecimalFormat("##.##").format(weatherList.getTemp().getDay()) + "78" + (char) 0x00B0 + " C");
            holder.tvPressure.setText(String.format(Utility.getString(R.string.pressure), weatherList.getPressure()));
            holder.tvHumidity.setText(String.format(Utility.getString(R.string.humidity), weatherList.getHumidity()) + "%");

        }
    }

    @Override
    public int getItemCount() {
        if (null != mWeatherInformation) {
            return mWeatherInformation.getCnt();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.icon)
        ImageView ivIcon;

        @Bind(R.id.temp)
        TextView tvTemp;

        @Bind(R.id.pressure)
        TextView tvPressure;

        @Bind(R.id.humidity)
        TextView tvHumidity;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }


}
