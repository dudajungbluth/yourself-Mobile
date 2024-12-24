package com.example.duda;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
 * Use the {@link FragmentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLogin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_EMAIL = "email";
    private static final String ARG_PASSWORD = "password";

    // TODO: Rename and change types of parameters

    private View fragmentView;
    Button buttonLogin;
    Button buttonRedirect;
    User user;
    public FragmentLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment FragmentLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLogin newInstance() {
        FragmentLogin fragment = new FragmentLogin();
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

        fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        buttonLogin = fragmentView.findViewById(R.id.buttonLogin);
        buttonRedirect = fragmentView.findViewById(R.id.buttonRedirect);


        // redireciona pro register
        buttonRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                FragmentRegister fragmentRegister = FragmentRegister.newInstance();
                fragmentTransaction.replace(R.id.fragmentContainerView, fragmentRegister);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        return fragmentView;
    }



    private void loginUser() {
        TextView etEmail = fragmentView.findViewById(R.id.etEmail);
        TextView etPassword = fragmentView.findViewById(R.id.etPassword);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("email", etEmail.getText().toString())
                .addFormDataPart("password", etPassword.getText().toString())
                .build();

        Call<User> call = RetrofitClient.getInstance().getMyApi().loginUser(requestBody);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();


                    if (user != null) {
                        boolean saved = ((MainActivity) getActivity()).savePreferences(user.name, user.email);
                        if (saved) {
                            Toast.makeText(requireContext(), "Sucesso ao salvar informações", Toast.LENGTH_SHORT).show();

                            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                            FragmentSunglasses fragmentSunglasses = new FragmentSunglasses().newInstance();
                            fragmentTransaction.replace(R.id.fragmentContainerView, fragmentSunglasses);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        } else {
                            Toast.makeText(requireContext(), "Erro ao salvar informações", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(requireContext(), "Erro ao obter informações do usuário", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Email ou senha incorreta", Toast.LENGTH_SHORT).show();
                }
            }



            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(requireContext(), "negado", Toast.LENGTH_SHORT).show();
            }
        });

    };
}