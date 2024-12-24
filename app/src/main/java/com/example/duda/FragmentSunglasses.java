package com.example.duda;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSunglasses extends Fragment {

    // Instancia os elementos
    ArrayList<Oculos> oculos = new ArrayList<Oculos>();
    private RecyclerView rvoculos;
    private MeuAdaptador meuAdaptador;
    private Button btnOculosGrau;
    private Button btnAcessories;



    public FragmentSunglasses() {
        // Required empty public constructor
    }

    // criar novas instâncias de um fragmento
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

        View view = inflater.inflate(R.layout.fragment_sunglasses, container, false);
        //View view2 = inflater.inflate(R.layout.fragment_acessories, container, false);


        btnOculosGrau = view.findViewById(R.id.oculosgrau);
        btnAcessories = view.findViewById(R.id.acessorios);

        btnAcessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragmentAcessories fragmentAcessories = new FragmentAcessories();
                fragmentTransaction.replace(R.id.fragmentContainerView, fragmentAcessories);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        btnOculosGrau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragmentEyeglasses fragmentEyeglasses = new FragmentEyeglasses();
                fragmentTransaction.replace(R.id.fragmentContainerView, fragmentEyeglasses);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }


    @Override
public void onResume() {
    super.onResume();

    rvoculos = getActivity().findViewById(R.id.rvoculos);

    meuAdaptador = new MeuAdaptador(oculos, 1);
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
