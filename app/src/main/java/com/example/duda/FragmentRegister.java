package com.example.duda;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRegister#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRegister extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters

    private View fragmentView;
    Button buttonRegister;
    Button buttonRedirect;
    User user;
    public FragmentRegister() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment FragmentRegister.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRegister newInstance() {
        FragmentRegister fragment = new FragmentRegister();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView =  inflater.inflate(R.layout.fragment_register, container, false);
        buttonRegister = fragmentView.findViewById(R.id.buttonRegister);
        buttonRedirect = fragmentView.findViewById(R.id.buttonRedirect);

        buttonRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                FragmentLogin fragmentLogin = FragmentLogin.newInstance();
                fragmentTransaction.replace(R.id.fragmentContainerView, fragmentLogin);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
        return fragmentView;
    }

    private void createUser() {
        TextView etName = fragmentView.findViewById(R.id.etName);
        TextView etEmail = fragmentView.findViewById(R.id.etEmail);
        TextView etPassword = fragmentView.findViewById(R.id.etPassword);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name", etName.getText().toString())
                .addFormDataPart("email", etEmail.getText().toString())
                .addFormDataPart("password", etPassword.getText().toString())
                .build();

        Call<User> call = RetrofitClient.getInstance().getMyApi().createUser(requestBody);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    FragmentLogin fragmentLogin = FragmentLogin.newInstance();
                    fragmentTransaction.replace(R.id.fragmentContainerView, fragmentLogin);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else{
                    Toast.makeText(requireContext(), "Erro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

}