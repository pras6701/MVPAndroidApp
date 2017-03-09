package com.mvp.app.ui.fragment.home;

import com.mvp.app.data.network.model.DummyData;
import com.mvp.app.injection.PerActivity;
import com.mvp.app.ui.base.intf.IMvpPresenter;
import com.mvp.app.ui.base.intf.IMvpView;

import java.util.List;

public class DummyMvpContract {

    @PerActivity
    public interface IDummyMvpPresenter<V extends IDummyMvpView> extends IMvpPresenter<V> {
        void fetchNewDummyData();
    }

    public interface IDummyMvpView extends IMvpView {

        void showProgress(boolean b);

        void onFetchNewDummyDataSuccessful(List<DummyData> dummyDatas);
    }

}
