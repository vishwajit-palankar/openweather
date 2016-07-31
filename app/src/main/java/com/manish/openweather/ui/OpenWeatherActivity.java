package com.manish.openweather.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.manish.openweather.R;
import com.manish.openweather.adapter.WeatherAdapter;
import com.manish.openweather.data.WeatherInformation;
import com.manish.openweather.mvp.WeatherPresenter;
import com.manish.openweather.mvp.WeatherPresenterImpl;
import com.manish.openweather.mvp.WeatherView;
import com.manish.openweather.utility.Utility;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OpenWeatherActivity extends MyBaseActivity implements WeatherView,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int PERMISSIONS_REQUEST_READ_LOCATION = 1;
    private static final String TAG = OpenWeatherActivity.class.getSimpleName();
    private static final int REQUEST_GOOGLE_PLAY_SERVICES = 11;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private WeatherPresenter mWeatherPresenter;

    @Bind(R.id.toolbar)
    Toolbar tbToolbar;

    @Bind(R.id.city)
    TextView tvCityName;

    @Bind(R.id.warning_message)
    TextView tvWarningMessage;

    @Bind(R.id.main_content)
    View vListHolder;

    @Bind(R.id.weatherList)
    RecyclerView rvList;

    @Bind(R.id.search)
    FloatingActionButton fbSearch;

    @Bind(R.id.noDataFound)
    View vNoDataFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_weather);
        ButterKnife.bind(this);

        setSupportActionBar(tbToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        if (checkPlayServices()) {
            createGoogleClient();
        }
        mWeatherPresenter = new WeatherPresenterImpl(this);
    }

    @Override
    public void showProgress() {
        Utility.showProgressDialog();
    }

    @Override
    public void hideProgress() {
        Utility.hideProgressBar();
    }

    @Override
    public void displayWeatherList(WeatherInformation weatherInformation) {

        if (null != weatherInformation) {
            vNoDataFound.setVisibility(View.GONE);
            vListHolder.setVisibility(View.VISIBLE);

            tvCityName.setText(weatherInformation.getCity().getName());

            rvList.setLayoutManager(new LinearLayoutManager(this));
            rvList.setHasFixedSize(true);

            WeatherAdapter adapter = new WeatherAdapter(weatherInformation);
            rvList.setAdapter(adapter);


        } else {
            displayError();
        }
    }

    @Override
    public void displayError() {
        hideProgress();
        vListHolder.setVisibility(View.GONE);
        vNoDataFound.setVisibility(View.VISIBLE);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkLocationPermission();

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "Google connection suspended ");
        hideProgress();
        displayError();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Google connection failed " + connectionResult.getErrorMessage());
        hideProgress();
        displayError();
    }

    protected void onStart() {
        if (null != mGoogleApiClient) {
            mGoogleApiClient.connect();
        }
        super.onStart();
    }

    protected void onStop() {
        if (null != mGoogleApiClient) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mWeatherPresenter) {
            mWeatherPresenter.onDestroy();
        }
    }

    private void createGoogleClient() {


        if (mGoogleApiClient == null) {
            showProgress();
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        REQUEST_GOOGLE_PLAY_SERVICES).show();
            }

            return false;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode == Activity.RESULT_OK) {
                    createGoogleClient();
                }
                break;

            default:
                displayError();
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                Utility.showWarningDialog(getString(R.string.warning), getString(R.string.warning_enable_location));

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSIONS_REQUEST_READ_LOCATION);

            }

        } else {
            getLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    getLocation();

                } else {

                    Utility.showWarningDialog(getString(R.string.warning), getString(R.string.warning_enable_location));

                }

            }


        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (null != mLastLocation) {
            mWeatherPresenter.downloadWeatherInfo(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        } else {
            Log.e(TAG, "Last Location is null");
            displayError();
        }
    }


}
