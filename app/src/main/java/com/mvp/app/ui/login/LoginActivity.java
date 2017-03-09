package com.mvp.app.ui.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.mvp.app.R;
import com.mvp.app.data.network.model.SignInResponse;
import com.mvp.app.ui.base.BaseActivity;
import com.mvp.app.ui.fragment.login.SignUpFragment;
import com.mvp.app.ui.home.HomeActivity;
import com.mvp.app.util.CommonUtils;
import com.mvp.app.util.DialogFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class LoginActivity extends BaseActivity implements LoginMvpContract.ILoginMvpView {

    private static final String EXTRA_POPUP_MESSAGE =
            "EXTRA_POPUP_MESSAGE";

    @Inject
    LoginMvpContract.ILoginMvpPresenter<LoginMvpContract.ILoginMvpView> mLoginPresenter;

    private boolean mShouldFinishOnStop;

    @BindView(R.id.login_button) Button mSignInButton;

    @BindView(R.id.email_layout)
    TextInputLayout mEmailTxtInputLayout;

    @BindView(R.id.password_layout)
    TextInputLayout mPwdTxtInputLayout;

    @BindView(R.id.input_email)
    AutoCompleteTextView mEmailTextView;

    @BindView(R.id.input_password)
    EditText mPwdEditText;


    public enum LoginFragmentType{
        SIGNUP("SIGN UP"),SIGNIN("SIGN IN");
        private String title;
        LoginFragmentType(String fragmentType) {
            title = fragmentType;
        }
    }

    @OnClick(R.id.login_goto_signup)
    public void signUpClicked(){
        switchFragment(LoginFragmentType.SIGNUP);
    }

    public static Intent getStartIntent(Context context, boolean clearPreviousActivities) {
        Intent intent = new Intent(context, LoginActivity.class);
        if (clearPreviousActivities) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        return intent;
    }
    // popUpMessage will show on a Dialog as soon as the Activity opens
    public static Intent getStartIntent(Context context, boolean clearPreviousActivities,
                                        String popUpMessage) {
        Intent intent = getStartIntent(context, clearPreviousActivities);
        intent.putExtra(EXTRA_POPUP_MESSAGE, popUpMessage);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShouldFinishOnStop = false;
        setContentView(R.layout.activity_login);
        activityComponent().inject(this);
        ActionBar supportActionBar=getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle("Login");
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
        setUpButterKnife();
        mLoginPresenter.attachView(this);
        setSignInButtonEnabled(true);
        String popUpMessage = getIntent().getStringExtra(EXTRA_POPUP_MESSAGE);
        if (popUpMessage != null) {
            DialogFactory.createSimpleOkErrorDialog(this, popUpMessage).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mShouldFinishOnStop) finish();
    }

    @Override
    protected void onDestroy() {
        mLoginPresenter.detachView();
        super.onDestroy();
    }

    @Override
    protected void setUp() {

    }

    @Override
    protected boolean isNetworkBroadcastEnabled() {
        return false;
    }

    @OnClick(R.id.login_button)
    void signIn() {
        if(validateSignIn()) {
            String emailId = mEmailTextView.getEditableText().toString().trim();
            String pwd = mPwdEditText.getEditableText().toString().trim();
            mLoginPresenter.signIn(emailId, pwd);
        }
    }


    @Override
    public void onSignInSuccessful(SignInResponse vendorSignInResponse) {
        Intent intent = HomeActivity.getStartIntent(LoginActivity.this, false);
        startActivity(intent);
        finish();
    }

    @Override
    public void showProgress(boolean show) {
    }

    @Override
    public void setSignInButtonEnabled(boolean enabled) {
        // mSignInButton.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showProfileNotFoundError(String accountName) {
        DialogFactory.createSimpleOkErrorDialog(this,
                getString(R.string.error_profile_not_found, accountName)).show();
    }

    @Override
    public void showGeneralSignInError() {
        DialogFactory.createSimpleOkErrorDialog(this, getString(R.string.error_sign_in)).show();
    }

    @Override
    public void onNetworkAvailable() {

    }

    @Override
    public void onNetworkUnavailable() {

    }


    @Override
    public void switchFragment(LoginFragmentType fragmentType) {
        // findViewById(R.id.login_activity).setBackground(null);
        // getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.fragment_header));
        //  getSupportActionBar().show();
        // getSupportActionBar().setTitle(fragmentType.title);//Setting Action Title as fragment type title by default
        try {
            Fragment fragment = null;
            switch (fragmentType) {
                case SIGNUP:
                    fragment = SignUpFragment.newInstance("Sign Up");
                    break;
                case SIGNIN:
                    fragment = SignUpFragment.newInstance("Sign Up");
                    break;
                default:
                    break;
            }
            if(fragment!=null && !isFinishing()) {
                findViewById(R.id.fm_lyt_login_container).setVisibility(View.VISIBLE);
                findViewById(R.id.rlv_lyt_login_options_container).setVisibility(View.GONE);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
               /* fragmentTransaction.setCustomAnimations(
                        R.anim.slide_left_enter,
                        R.anim.slide_left_exit,
                        R.anim.slide_right_enter,
                        R.anim.slide_right_exit);*/

                // fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.replace(R.id.fm_lyt_login_container, fragment);
                fragmentTransaction.addToBackStack(fragmentType.title);
                fragmentTransaction.commitAllowingStateLoss();
            }
        }catch (Exception e){
            Timber.e(e.getMessage(), e);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager=getSupportFragmentManager();
        if(fragmentManager!=null) {
            int backStackEntryCount = fragmentManager.getBackStackEntryCount();
            if (backStackEntryCount > 1) {
                fragmentManager.popBackStack();
                //restoreToolBarTitle(backStackEntryCount);
            } else if (backStackEntryCount == 1) {
                fragmentManager.popBackStack();
                restoreLoginScreen();
            } else {
                super.onBackPressed();
                Log.d("CDA", "onBackPressed Called");
                Intent setIntent = new Intent(Intent.ACTION_MAIN);
                setIntent.addCategory(Intent.CATEGORY_HOME);
                setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(setIntent);
                finish();
            }
        }
    }

    private void restoreLoginScreen() {
        try {
            findViewById(R.id.fm_lyt_login_container).setVisibility(View.GONE);
            findViewById(R.id.rlv_lyt_login_options_container).setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle("Login");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        }catch(Exception e){
            Timber.e(e.getMessage(), e);
        }
    }

    @Override
    public boolean validateSignIn() {
        boolean isValid=true;
        clearErrorLayout();
        String emailId= mEmailTextView.getEditableText().toString().trim();
        String password= mPwdEditText.getEditableText().toString().trim();
        List<EditText> errorFields=new ArrayList<EditText>();
        if (TextUtils.isEmpty(emailId)) {
            mEmailTxtInputLayout.setError("Email is empty");
            errorFields.add(mEmailTextView);
            isValid=false;
        } else if (!CommonUtils.checkEmail(emailId)) {
            mEmailTxtInputLayout.setError("Invalid email id");
            errorFields.add(mEmailTextView);
            isValid=false;
        }

        if (TextUtils.isEmpty(password)) {
            mPwdTxtInputLayout.setError("Password is empty");
            errorFields.add(mPwdEditText);
            isValid=false;
        }
        if(!errorFields.isEmpty() && errorFields.get(0)!=null){
            errorFields.get(0).requestFocus();
        }
        return isValid;
    }

    @Override
    public void clearErrorLayout(){
        mEmailTxtInputLayout.setErrorEnabled(false);
        mPwdTxtInputLayout.setErrorEnabled(false);
    }
}
