package com.toan_itc.tn.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.toan_itc.tn.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.toan_itc.tn.Utils.Utils.isEmailValid;
import static com.toan_itc.tn.Utils.Utils.isPasswordValid;

/**
 * Created by vantoan on 2/11/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class RegisterActivity extends BaseActivity {
  @BindView(R.id.etEmail)
  EditText mEtEmail;
  @BindView(R.id.etName)
  EditText mEtName;
  @BindView(R.id.etPassword)
  EditText mEtPassword;
  @BindView(R.id.btnRegister)
  Button mBtnRegister;
  @BindView(R.id.layout_email)
  TextInputLayout mLayoutEmail;
  @BindView(R.id.layout_pass)
  TextInputLayout mLayoutPass;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    ButterKnife.bind(this);
  }
  @OnClick(R.id.btnRegister)
  void onBtnRegisterClick() {
    check_Register();
  }
  private void check_Register() {
    mLayoutEmail.setError(null);
    mLayoutPass.setError(null);
    String email = mEtEmail.getText().toString();
    String password = mEtPassword.getText().toString();

    boolean cancel = false;
    View focusView = null;
    if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
      mLayoutPass.setErrorEnabled(true);
      mLayoutPass.setError(getString(R.string.error_invalid_password));
      focusView = mLayoutPass;
      cancel = true;
    }
    if (TextUtils.isEmpty(email)) {
      mLayoutEmail.setErrorEnabled(true);
      mLayoutEmail.setError(getString(R.string.error_field_required));
      focusView = mLayoutEmail;
      cancel = true;
    } else if (!isEmailValid(email)) {
      mLayoutEmail.setErrorEnabled(true);
      mLayoutEmail.setError(getString(R.string.error_invalid_email));
      focusView = mLayoutEmail;
      cancel = true;
    }

    if (cancel) {
      focusView.requestFocus();
    } else {
      mLayoutEmail.setErrorEnabled(false);
      mLayoutPass.setErrorEnabled(false);
      //mRegisterPresenter.register(mEmailSignUp.getText().toString(), mPasswordSignUp.getText().toString(), Constant.SHOP_ID);
    }
  }
}
