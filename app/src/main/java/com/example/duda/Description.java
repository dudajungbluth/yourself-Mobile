package com.example.duda;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Description#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Description extends Fragment {

    private static final String ARG_ID = "id";
    Oculos oculos;
    private int id;
    private View fragmentView;
    Button deleteBtn;
    public Description() {
        // Required empty public constructor
    }

    /**
     * Factory method para criar uma nova instância do fragmento Description.
     *
     * @param id ID do óculos clicado.
     * @return Uma nova instância do fragmento Description.
     */
    public static Description newInstance(int id) {
        Description fragment = new Description();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_description, container, false);
        deleteBtn = fragmentView.findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteOc();
            }
        });

        return fragmentView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        renderDescription();
    }

    private void renderDescription() {
        Call<List<Oculos>> call = RetrofitClient.getInstance().getMyApi().getProductById(id);
        call.enqueue(new Callback<List<Oculos>>() {
            @Override
            public void onResponse(Call<List<Oculos>> call, Response<List<Oculos>> response) {
                List<Oculos> oculos = response.body();
                Oculos oculo = oculos.get(0);

                TextView tvName = fragmentView.findViewById(R.id.nameTextView);
                TextView tvPrice = fragmentView.findViewById(R.id.priceTextView);
                TextView tvDescription = fragmentView.findViewById(R.id.descriptionTextView);
                ImageView ivPhoto = fragmentView.findViewById(R.id.ivPhoto);

                tvName.setText(oculo.getName());
                tvPrice.setText("R$ "+oculo.getPrice());
                tvDescription.setText(oculo.getDescription().toString());


                String urlImage = "http://192.168.1.10/youself-project/" + oculo.getPath();
                Picasso.get().load(urlImage).into(ivPhoto);

                Log.d("teste", "Dados carregados com sucesso: " + oculo.getPath());
            }

            @Override
            public void onFailure(Call<List<Oculos>> call, Throwable t) {
                Log.d("TESTE", t.toString());
            }

        });
    }

    private void deleteOc() {
        Call <Oculos> call = RetrofitClient.getInstance().getMyApi().deleteOc(id);
        call.enqueue(new Callback<Oculos>() {
            @Override
            public void onResponse(Call<Oculos> call, Response<Oculos> response) {
                if(response.isSuccessful()){

                    oculos = response.body();
                    Toast.makeText(requireContext(), "Sucesso ao deletar. Redirecionando...", Toast.LENGTH_SHORT).show();
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    FragmentSunglasses initialFragment = FragmentSunglasses.newInstance();
                    fragmentTransaction.replace(R.id.fragmentContainerView, initialFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else{
                    Toast.makeText(requireContext(), "Erro deletar...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Oculos> call, Throwable t) {

            }
        });
    }


}
