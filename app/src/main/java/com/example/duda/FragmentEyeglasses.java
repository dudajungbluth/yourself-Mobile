package com.example.duda;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentEyeglasses extends Fragment {

    ArrayList<Oculos> oculos = new ArrayList<Oculos>();
    private RecyclerView rvoculos;
    private MeuAdaptador meuAdaptador;


    public FragmentEyeglasses() {
        // Required empty public constructor
    }

    public static FragmentSunglasses newInstance() {
        FragmentSunglasses fragment = new FragmentSunglasses();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eyeglasses, container, false);

    }

    @Override
    public void onResume() {
        super.onResume();

        rvoculos = getActivity().findViewById(R.id.rvoculos);

        meuAdaptador = new MeuAdaptador(oculos, 2);
        RecyclerView.LayoutManager layout =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvoculos.setLayoutManager(layout);
        rvoculos.setAdapter(meuAdaptador);
        getOculos();
    }

    private void getOculos() {
        Call<List<Oculos>> call = RetrofitClient.getInstance().getMyApi().getProducts();
        call.enqueue(new Callback<List<Oculos>>() {
            @Override
            public void onResponse(Call<List<Oculos>> call, Response<List<Oculos>> response) {
                oculos.clear();
                oculos.addAll(response.body());
                meuAdaptador.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Oculos>> call, Throwable t) {
                Log.d("TESTE", "onFailure: " + t.toString());
                Toast.makeText(getContext(), "Falha na comunicação com o servidor: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
