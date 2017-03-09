package com.mvp.app.ui.fragment.home;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mvp.app.R;
import com.mvp.app.data.network.model.DummyData;
import com.mvp.app.ui.base.BaseFragment;
import com.mvp.app.ui.adapter.DummyDataAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class DummyFragment extends BaseFragment implements DummyMvpContract.IDummyMvpView {

    @Inject
    DummyMvpContract.IDummyMvpPresenter<DummyMvpContract.IDummyMvpView> mPresenter;

    private String title;
    private int page;
    @BindView(R.id.recycler_view_dummy)
    RecyclerView mRecyclerView;

    @BindView(R.id.swiperefresh_dummy)
    SwipeRefreshLayout mSwipeRefreshLayout;

    DummyDataAdapter mDummyDataAdapter;
    private SearchView mSearchView;

    // newInstance constructor for creating fragment with arguments
    public static DummyFragment newInstance(int page, String title) {
        DummyFragment fragmentFirst = new DummyFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("page", 0);
        title = getArguments().getString("title");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_tab, container, false);
        ButterKnife.bind(this,view);
        setUp(view);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home, menu);
        try {
          /*  // Associate searchable configuration with the SearchView
            SearchManager searchManager =
                    (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
            mSearchView =
                    (SearchView) menu.findItem(R.id.search).getActionView();
            mSearchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getActivity().getComponentName()));*/
        }catch(Exception e){
            Timber.e("Error Search View",e);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        mSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        if(mSearchView != null) {

            // omit these lines, if you don't want it automatically expanding when search fragment is tabbed to
           /* mSearchView.setIconified(false);
            mSearchView.setIconifiedByDefault(false);*/

            // Add query listener
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(getContext(), String.format("Searching for %s", query), Toast.LENGTH_SHORT).show();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.search){
            if(mSearchView != null){
                mSearchView.onActionViewExpanded();
                mSearchView.requestFocus();
            }
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void setUp(View view) {
        getActivityComponent().inject(this);
        mPresenter.attachView(this);
        setHasOptionsMenu(true);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mPresenter.fetchNewDummyData();
        mDummyDataAdapter = new DummyDataAdapter(new ArrayList<>(),getActivity());
        mRecyclerView.setAdapter(mDummyDataAdapter);
        //initSwipe(mRecyclerView);  Check this implementation for swipe view
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(),"Refreshing",Toast.LENGTH_LONG).show();
                mPresenter.fetchNewDummyData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showProgress(boolean b) {

    }

    @Override
    public void onFetchNewDummyDataSuccessful(List<DummyData> dummyDatas) {
        mDummyDataAdapter.updateDataList(dummyDatas);
        mDummyDataAdapter.notifyDataSetChanged();
    }
}