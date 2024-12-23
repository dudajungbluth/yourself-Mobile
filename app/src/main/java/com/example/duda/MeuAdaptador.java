package com.example.duda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MeuAdaptador extends RecyclerView.Adapter<MeuAdaptador.ViewHolder> {
    ArrayList<Oculos> oculos;
    Context context;
    int categoryId;


    public MeuAdaptador(ArrayList<Oculos> oculos, int categoryId) {
        this.oculos = oculos;
        this.categoryId = categoryId; // para armazenar o id da categoria
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView txtNome;
        final TextView txtPrice;
        final ImageView Ocphoto;

        public ViewHolder(View view) {
            super(view);
            txtNome = (TextView) view.findViewById(R.id.txtNome);
            txtPrice = (TextView) view.findViewById(R.id.txtPrice);
            Ocphoto = (ImageView) view.findViewById(R.id.Ocphoto);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Oculos oculo = oculos.get(position);

        context = holder.Ocphoto.getContext();

        holder.Ocphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                Description description = Description.newInstance(oculo.getId());
                fragmentTransaction.replace(R.id.fragmentContainerView, description);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        if (oculo.getCategories_id() == categoryId) {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }

        holder.txtNome.setText(oculo.name);
        holder.txtPrice.setText("R$ " + oculo.price);

        String urlImage = "http://192.168.1.10/yourself-project/" + oculo.getPath();
        Picasso.get().load(urlImage).into(holder.Ocphoto);


    }
    @Override
    public int getItemCount() {

        return oculos.size();
    }
    // Método para atualizar o filtro de categoria
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        notifyDataSetChanged(); // Notifica o adaptador para atualizar a lista com o novo filtro
    }
}
