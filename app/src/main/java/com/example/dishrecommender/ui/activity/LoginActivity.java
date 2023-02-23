package com.example.dishrecommender.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dishrecommender.MainActivity;
import com.example.dishrecommender.R;
import com.example.dishrecommender.logic.model.BaseBean;
import com.example.dishrecommender.logic.model.RestaurantRepository;
import com.example.dishrecommender.logic.net.RestaurantInterface;
import com.example.dishrecommender.ui.base.BaseActivity;
import com.example.dishrecommender.ui.fragment.RegisterDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    private Button mBtnLogin;
    private TextInputEditText mEtAccount;
    private TextInputEditText mEtPassword;
    private TextView mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEtAccount = findViewById(R.id.login_et_username);
        mEtPassword = findViewById(R.id.login_et_password);
        mBtnLogin = findViewById(R.id.login_btn_login);
        mBtnRegister = findViewById(R.id.login_btn_register);

        // DialogFragment
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterDialogFragment registerDialogFragment = new RegisterDialogFragment();
                registerDialogFragment.show(getSupportFragmentManager(), "Register");
            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable account = mEtAccount.getText();
                Editable password = mEtPassword.getText();
                if (TextUtils.isEmpty(account)) {
                    Toast.makeText(LoginActivity.this, "Please input account", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Please input password", Toast.LENGTH_SHORT).show();
                } else {
                    RestaurantInterface mRestaurantService = RestaurantRepository.getSingleton().mRestaurantService;
                    mRestaurantService.login(account.toString(), password.toString()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<BaseBean>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        }

                        @Override
                        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull BaseBean baseBean) {
                            Toast.makeText(LoginActivity.this, baseBean.msg, Toast.LENGTH_SHORT).show();
                            if (baseBean.ok) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }
}