package com.example.dishrecommender.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.dishrecommender.R;
import com.example.dishrecommender.logic.model.BaseBean;
import com.example.dishrecommender.logic.net.RestaurantInterface;
import com.example.dishrecommender.logic.net.RetrofitServiceManager;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

// Register uses Retrofit and RxJava to save the account to server
public class RegisterDialogFragment extends DialogFragment {
    private Button mBtnRegister;
    private TextInputEditText mEtAccount;
    private TextInputEditText mEtPassword;
    private TextInputEditText mEtRepeatPassword;

    public RegisterDialogFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnRegister = view.findViewById(R.id.register_btn_register);
        mEtAccount = view.findViewById(R.id.register_et_username);
        mEtPassword = view.findViewById(R.id.register_et_password);
        mEtRepeatPassword = view.findViewById(R.id.register_et_repeat_password);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable account = mEtAccount.getText();
                Editable password = mEtPassword.getText();
                Editable rePassword = mEtRepeatPassword.getText();
                if (TextUtils.isEmpty(account)) {
                    Toast.makeText(getContext(), "Please input account", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password) && TextUtils.isEmpty(rePassword)) {
                    Toast.makeText(getContext(), "Please input password", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.equals(password, rePassword)) {
                    Toast.makeText(getContext(), "The two passwords do not match!", Toast.LENGTH_SHORT).show();

                } else if (password.length() < 6) {
                    Toast.makeText(getContext(), "Passwords need at least 6 characters!", Toast.LENGTH_SHORT).show();
                }
                else {
                    RestaurantInterface mRestaurantService = RetrofitServiceManager.getInstance().create(RestaurantInterface.class);
                    mRestaurantService.register(account.toString(), password.toString()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<BaseBean>() {

                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull BaseBean baseBean) {
                            Toast.makeText(getContext(), baseBean.msg, Toast.LENGTH_SHORT).show();
                            if (baseBean.ok) {
                                dismiss();
                            }
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        super.onResume();
    }
}
