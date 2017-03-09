package com.mvp.app.ui.fragment.login;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mvp.app.R;
import com.mvp.app.ui.base.BaseActivity;
import com.mvp.app.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SignUpFragment extends BaseFragment implements SignUpMvpContract.ISignUpMvpView {

    @Inject
    SignUpMvpContract.ISignUpMvpPresenter<SignUpMvpContract.ISignUpMvpView> mPresenter;
    private String mPageTitle;

    // newInstance constructor for creating fragment with arguments
    public static SignUpFragment newInstance(String title) {
        SignUpFragment fragmentFirst = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString("mPageTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    /*@Override
    public void onDestroyView() {
        ((BaseActivity)getActivity()).getSupportActionBar().setTitle("");
        ((BaseActivity)getActivity()).getSupportActionBar().hide();
        super.onDestroyView();
    }
*/
    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageTitle = getArguments().getString("mPageTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this,view);
        setUp(view);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void setUp(View view) {
        getActivityComponent().inject(this);
        mPresenter.attachView(this);
        ActionBar supportActionBar=((BaseActivity)getActivity()).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(mPageTitle);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
            setHasOptionsMenu(false);
            supportActionBar.show();
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onSignUpSuccess() {

    }

    @Override
    public void onSignUpFailed() {

    }
}