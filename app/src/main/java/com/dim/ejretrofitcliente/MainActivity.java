package com.dim.ejretrofitcliente;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dim.ejretrofitcliente.data.model.Post;
import com.dim.ejretrofitcliente.data.remote.APIService;
import com.dim.ejretrofitcliente.data.remote.ApiUtils;

public class MainActivity extends AppCompatActivity {
    private TextView mResponseTv;
    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText titleEt = (EditText)findViewById(R.id.et_title);
        final EditText bodyEt = (EditText)findViewById(R.id.et_body);
        Button submitBtn = (Button)findViewById(R.id.btn_submit);
        mResponseTv = (TextView)findViewById(R.id.tv_response);
        mAPIService = ApiUtils.getAPIService();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEt.getText().toString().trim();
                String body = bodyEt.getText().toString().trim();
                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(body)){
                    sendPost(title, body);
                }
            }
        });
    }

    public void sendPost(String title, String body){
        // Utilizando RbJava
/*        mAPIService.savePost(title, body, 1).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Post>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Post post) {
                        showResponse(post.toString());
                    }
                });*/
        mAPIService.savePost(title, body, 1).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    showResponse(response.body().toString());
                    muestraMensaje("Post agregado a API. " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                muestraMensaje("No se pudo enviar la petición a la API.");
            }
        });
    }

    public void muestraMensaje(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    public void showResponse(String response){
        if(mResponseTv.getVisibility() == View.GONE){
            mResponseTv.setVisibility(View.VISIBLE);
        }
        mResponseTv.setText(response);
    }

}