package com.zhaobaoge.buy.ui.user.login;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.zhaobaoge.buy.R;
import com.zhaobaoge.buy.utils.RegexUtils;
import com.zhaobaoge.mvp.view.AppDelegate;
import com.zhaobaoge.widget.CleanableEditText;

/**
 * Created by shikewei on 2018/11/1.
 */

public class LoginDelegate extends AppDelegate {
    private Boolean isPhone = false;
    private TextView tvLoginSwitch,
            tvForgetPass,
            tvPhoneError;
    private LinearLayout linearPass,
            linearPhone,
            linearCode;
    private CleanableEditText edtPhoneNum, edtPass;
    private ToggleButton tgLoginPassVisible;


    @Override
    public int getRootLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initWidget() {
        tvLoginSwitch = get(R.id.tv_login_type_switch);
        tvForgetPass = get(R.id.tv_login_forget);
        tvPhoneError = get(R.id.tv_phone_error);

        linearPass = get(R.id.linear_us_pass);
        linearPhone = get(R.id.linear_us_phone);
        linearCode = get(R.id.linear_us_verification_code);
        UsPass();

        edtPhoneNum = get(R.id.edt_login_phone_num);
        edtPhoneNum.addTextChangedListener(new TextWatcherImpl());
        edtPass = get(R.id.edt_login_pass);

        tgLoginPassVisible = get(R.id.tg_login_pass_visible);
        tgLoginPassVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tgLoginPassVisible.isChecked()) {
                    edtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    edtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }

    public void Switch() {
        if (isPhone)
            UsPass();
        else
            UsPhone();
    }

    public void UsPass() {
        isPhone = false;
        tvLoginSwitch.setText("验证码登录");
        tvLoginSwitch.setVisibility(View.VISIBLE);
        tvForgetPass.setVisibility(View.VISIBLE);
        linearPass.setVisibility(View.VISIBLE);
        linearCode.setVisibility(View.GONE);
    }

    public void UsPhone() {
        isPhone = true;
        tvLoginSwitch.setText("密码登录");
        tvLoginSwitch.setVisibility(View.VISIBLE);
        tvForgetPass.setVisibility(View.GONE);
        linearPass.setVisibility(View.GONE);
        linearCode.setVisibility(View.VISIBLE);
    }

    private class TextWatcherImpl implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!RegexUtils.isMobileSimple(charSequence)) {
                tvPhoneError.setVisibility(View.VISIBLE);
            } else {
                tvPhoneError.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
