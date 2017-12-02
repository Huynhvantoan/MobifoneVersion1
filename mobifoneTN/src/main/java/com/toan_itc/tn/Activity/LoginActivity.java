package com.toan_itc.tn.Activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class LoginActivity extends BaseActivity {
  @BindView(R.id.etEmail)
  EditText mTxtEmail;
  @BindView(R.id.etPassword)
  EditText mPassword;
  @BindView(R.id.checkboxRemember)
  CheckBox checkboxRemember;
  @BindView(R.id.btnLogin)
  Button btnLogin;
  @BindView(R.id.tvRestore)
  TextView tvRestore;
  @BindView(R.id.ll1)
  LinearLayout ll1;
  @BindView(R.id.tvRegister)
  TextView tvRegister;
  @BindView(R.id.layout_email)
  TextInputLayout layoutEmail;
  @BindView(R.id.layout_pass)
  TextInputLayout layoutPass;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);
    mTxtEmail.addTextChangedListener(textWatcher);
    mPassword.addTextChangedListener(textWatcher);
  }
  @OnClick(R.id.btnLogin)
  void onBtnLoginClick() {
    checkLogin();
  }
  private TextWatcher textWatcher = new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
      enableLoginBtn();
    }
  };

  private void enableLoginBtn() {
    btnLogin.setEnabled(mTxtEmail.length() != 0 && mPassword.getText().length() != 0);
  }

  private void checkLogin() {
    mTxtEmail.setError(null);
    mPassword.setError(null);
    String email = mTxtEmail.getText().toString();
    String password = mPassword.getText().toString();

    boolean cancel = false;
    View focusView = null;
    if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
      layoutPass.setErrorEnabled(true);
      layoutPass.setError(getString(R.string.error_invalid_password));
      focusView = layoutPass;
      cancel = true;
    }
    if (TextUtils.isEmpty(email)) {
      layoutEmail.setError(getString(R.string.error_field_required));
      focusView = layoutEmail;
      cancel = true;
    } else if (!isEmailValid(email)) {
      layoutEmail.setErrorEnabled(true);
      layoutEmail.setError(getString(R.string.error_invalid_email));
      focusView = layoutEmail;
      cancel = true;
    }
    if (cancel) {
      focusView.requestFocus();
    } else {
      layoutEmail.setErrorEnabled(false);
      layoutPass.setErrorEnabled(false);
     // mLoginPresenter.login(mTxtEmail.getText().toString(), mPassword.getText().toString(), Constant.SHOP_ID);
    }
  }
}
