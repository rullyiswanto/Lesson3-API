package info.androidhive.materialtabs.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialtabs.R;
import info.androidhive.materialtabs.adapters.CityListAdapter;
import info.androidhive.materialtabs.listeners.RecyclerViewItemClickListener;
import info.androidhive.materialtabs.model.City;
import info.androidhive.materialtabs.model.CityResponse;
import info.androidhive.materialtabs.networks.MovieService;
import info.androidhive.materialtabs.util.DividerItemDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;


public class OneFragment extends Fragment implements RecyclerViewItemClickListener{

    private RecyclerView rvCity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<City> daftarKota = new ArrayList<>() ;
    private final static String API_KEY = "7e96bc9650c0ba99f9c458a2d9aa11d8";

    private CityListAdapter mAdapter ;


    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_one, container, false);
        rvCity = (RecyclerView) view.findViewById(R.id.rv_city);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);

        mAdapter = new CityListAdapter(daftarKota);
        mAdapter.setOnItemClickListener(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvCity.setLayoutManager(mLayoutManager);
        rvCity.setItemAnimator(new DefaultItemAnimator());
        rvCity.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rvCity.setAdapter(mAdapter);
        // Inflate the layout for this fragment
        return view;
    }
    private void loadData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Load data...");
        progressDialog.show();

        if (swipeRefreshLayout != null)
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });

        MovieService movieService = MovieService
                .retrofit.create(MovieService.class);
        Call<CityResponse> call = movieService.getCity(API_KEY);
        call.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                progressDialog.dismiss();

                CityResponse cityResponse = response.body();

                if (cityResponse != null) {
                    Log.d("status", cityResponse.getStatus());
                    daftarKota.addAll(cityResponse.getData());
                    mAdapter.notifyDataSetChanged();

                    if (swipeRefreshLayout != null)
                        swipeRefreshLayout.setRefreshing(false);

                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                if (swipeRefreshLayout != null)
                    swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    @Override
    public void onItemClick(int pos, View view) {

    }
}
