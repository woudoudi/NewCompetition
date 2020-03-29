package com.base.scanlistlibrary.scanlist;

import android.content.Context;
import android.graphics.Color;
//import android.support.annotation.NonNull;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.base.scanlistlibrary.R;
import com.base.scanlistlibrary.base.ScanBaseRecyclerViewAdapter;
import com.base.scanlistlibrary.base.ScanContact;
import com.base.scanlistlibrary.base.ScanRecyclerViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


/**
 * 视频列表view
 *
 * @author zhaolong
 */
public class ScanVideoListView<A> extends FrameLayout {

    private String TAG = ScanVideoListView.this.getClass().getName();
    private Context mContext;
    private ScanRecyclerView mScanRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ScanBaseRecyclerViewAdapter<A> mRecyclerViewAdapter;

    private View mEmptyView;

    /**
     * 数据是否到达最后一页
     */
    private boolean isEnd;

    //刷新数据listener
    private ScanContact.OnRefreshDataListener onRefreshDataListener;
    //页面选择回调
    private ScanContact.OnPageSelectListener mOnPageSelectListener;
    private ScanContact.OnPageSelectScrollListener mOnPageSelectScrollListener;
    private RecyclerView.RecyclerListener mRecyclerListener;
    /**
     * 判断是否处于加载数据的状态中
     */
    private boolean isLoadingData;
    /**
     * 当前选中位置
     */
    private int mCurrentPosition;
    /**
     * 正常滑动，上一个被暂停的位置
     */
    private int mLastStopPosition = -1;
    private final int loadingStatusRefresh = 1;
    private final int loadingStatusLoadMore = 2;
    private int currentLoadingStatus = loadingStatusRefresh;

    private ScanPagerLayoutManager mScanPagerLayoutManager;
    private GridPagerLayoutManager mGridPagerLayoutManager;
    private StagerPagerLayoutManager mStagerPagerLayoutManager;

    private boolean mIsLoadMoreEnable = true;


    public ScanVideoListView(@NonNull Context context, int column, int emptyLayoutId, String emptyMsg, boolean isStager, boolean isPaging) {
        super(context);
        this.mContext = context;
        init(isPaging, isStager, column, emptyLayoutId, emptyMsg,null);
    }

    public ScanVideoListView(@NonNull Context context, int column, int emptyLayoutId, String emptyMsg, boolean isStager, boolean isPaging,GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        super(context);
        this.mContext = context;
        init(isPaging, isStager, column, emptyLayoutId, emptyMsg,spanSizeLookup);
    }

    private void init(boolean isPaging, boolean isStager, int column, int emptyLayoutId, String emptyMsg,GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.zl_page_list_main_layout, this, true);
        mScanRecyclerView = view.findViewById(R.id.zl_page_list_main_recycler);
        mScanRecyclerView.setEmptyViewMsg(emptyMsg);
        mSwipeRefreshLayout = view.findViewById(R.id.zl_page_list_main_refresh_view);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (onRefreshDataListener != null) {
                    isLoadingData = true;
                    currentLoadingStatus = loadingStatusRefresh;
                    onRefreshDataListener.onRefresh();
                }
            }
        });
        if (this.mRecyclerListener != null) {
            mScanRecyclerView.setRecyclerListener(this.mRecyclerListener);
        }
        mScanRecyclerView.setHasFixedSize(true);
        //禁用默认加载动画
        ((SimpleItemAnimator) mScanRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        if (isStager) {
            mStagerPagerLayoutManager = new StagerPagerLayoutManager(mContext, column, false);
            mStagerPagerLayoutManager.setItemPrefetchEnabled(true);
            mStagerPagerLayoutManager.setOnViewPagerListener(mGridOnViewPagerListener);
            mScanRecyclerView.setLayoutManager(mStagerPagerLayoutManager);
        } else {
            if (column == 1) {
                mScanPagerLayoutManager = new ScanPagerLayoutManager(mContext, isPaging,false);
                mScanPagerLayoutManager.setItemPrefetchEnabled(true);
                mScanPagerLayoutManager.setOnViewPagerListener(mGridOnViewPagerListener);
                mScanRecyclerView.setLayoutManager(mScanPagerLayoutManager);

            } else {
                mGridPagerLayoutManager = new GridPagerLayoutManager(mContext, column, isPaging);
                mGridPagerLayoutManager.setItemPrefetchEnabled(true);
                mGridPagerLayoutManager.setOnViewPagerListener(mGridOnViewPagerListener);
                if(spanSizeLookup!=null){
                    mGridPagerLayoutManager.setSpanSizeLookup(spanSizeLookup);
                }
                mScanRecyclerView.setLayoutManager(mGridPagerLayoutManager);
            }
        }

        if (emptyLayoutId <= 0) {
            View emptyView = view.findViewById(R.id.zl_page_list_main_empty_view);
            mEmptyView = emptyView;
            mScanRecyclerView.setEmptyView(emptyView);
        } else {
            View emptyView = View.inflate(mContext, emptyLayoutId, null);
            FrameLayout rootEmptyView = view.findViewById(R.id.zl_page_list_main_empty_rootview);
            rootEmptyView.removeAllViews();
            rootEmptyView.addView(emptyView);
            mEmptyView = emptyView;
            mScanRecyclerView.setEmptyView(emptyView);
        }
        mEmptyView.setOnClickListener(mOnRefreshClickListener);
    }

    ScanContact.OnViewPagerListener mGridOnViewPagerListener = new ScanContact.OnViewPagerListener() {
        @Override
        public void onInitComplete() {
            int position = -1;
            if(mScanPagerLayoutManager!=null){
                position = mScanPagerLayoutManager.findFirstVisibleItemPosition();
            }else if(mGridPagerLayoutManager!=null){
                position = mGridPagerLayoutManager.findFirstVisibleItemPosition();
            }
            if (position != -1) {
                mCurrentPosition = position;
            }
            startPlay(mCurrentPosition);
            mLastStopPosition = -1;
            Log.e(TAG, "onInitComplete mCurrentPosition= " + mCurrentPosition);
        }

        @Override
        public void onPageRelease(boolean isNext, int position) {
            if (mCurrentPosition == position) {
                mLastStopPosition = position;
                stopPlay(position);
            }
        }

        @Override
        public void onPageSelected(int position, boolean b) {
            mCurrentPosition = position;
            int lLastVisibleItemPosition =0;
            if (mGridPagerLayoutManager != null) {
                lLastVisibleItemPosition = mGridPagerLayoutManager.findLastVisibleItemPosition();
            }else if (mScanPagerLayoutManager!=null){
                lLastVisibleItemPosition = mScanPagerLayoutManager.findLastVisibleItemPosition();
            }else if (mStagerPagerLayoutManager!=null){
                lLastVisibleItemPosition = mStagerPagerLayoutManager.findLastVisibleItemPosition();
            }

            int itemCount = mRecyclerViewAdapter.getItemCount();
            if (lLastVisibleItemPosition == itemCount - 1 && !isLoadingData && !isEnd) {
                // 正在加载中, 防止网络太慢或其他情况造成重复请求列表
                isLoadingData = true;
                loadMore();
            }
            startPlay(position);
        }
    };


    private void stopPlay(int position) {
        if (position < 0 || position >= list.size()) {
            return;
        }
        if (mOnPageSelectListener != null) {
            mOnPageSelectListener.onPageRelease(position, list.get(position), mRecyclerViewAdapter, (ScanRecyclerViewHolder) mScanRecyclerView.findViewHolderForAdapterPosition(position));
        }
        if (mOnPageSelectScrollListener != null) {
            mOnPageSelectScrollListener.onPageRelease(position, list.get(position), mRecyclerViewAdapter, (ScanRecyclerViewHolder) mScanRecyclerView.findViewHolderForAdapterPosition(position));
        }
    }

    private void startPlay(int firstPosition) {
        if (firstPosition < 0 || firstPosition >=list.size()) {
            return;
        }
        if (mOnPageSelectListener != null) {
            mOnPageSelectListener.onPageSelected(firstPosition, list.get(firstPosition), mRecyclerViewAdapter, (ScanRecyclerViewHolder) mScanRecyclerView.findViewHolderForAdapterPosition(firstPosition));
        }
        if (mOnPageSelectScrollListener != null) {
            mOnPageSelectScrollListener.onPageSelected(list, mRecyclerViewAdapter, mScanRecyclerView);
        }
    }

    /**
     * 加载更多
     */
    private void loadMore() {
        if(!mIsLoadMoreEnable){
            return;
        }
        if (onRefreshDataListener != null) {
            currentLoadingStatus = loadingStatusLoadMore;
            onRefreshDataListener.onLoadMore();
        }
    }

    /**
     * 刷新数据
     *
     * @param list 刷新数据
     */
    public void refreshData(List<A> list) {
        mScanRecyclerView.setLoadingStatus(1);
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
            // 加载完毕, 重置加载状态
        }
        isEnd = false;
        isLoadingData = false;
        mRecyclerViewAdapter.refreshData(list);
    }

    /**
     * 刷新数据，并播放指定位置的视频
     *
     * @param list     视频列表数据
     * @param position 刷新完成之后播放位置
     */
    public void refreshData(List<A> list, int position) {
        mScanRecyclerView.setLoadingStatus(1);
        int size = list.size();
        if (position < 0) {
            position = 0;
        }
        if (size <= position) {
            position = size - 1;
        }
        mCurrentPosition = position;
        refreshData(list);
        mScanRecyclerView.scrollToPosition(mCurrentPosition);
    }


    /**
     * 加载更多数据
     *
     * @param list
     */
    public void addMoreData(List<A> list) {
        mScanRecyclerView.setLoadingStatus(1);
        isLoadingData = false;
        if (mRecyclerViewAdapter != null) {
            mRecyclerViewAdapter.addMoreData(list);
        }
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    public void setOnRefreshDataListener(ScanContact.OnRefreshDataListener onRefreshDataListener) {
        this.onRefreshDataListener = onRefreshDataListener;
    }

    public void setOnPageSelectListener(ScanContact.OnPageSelectListener onPageSelectListener) {
        mOnPageSelectListener = onPageSelectListener;
    }

    public void setOnPageSelectScrollListener(ScanContact.OnPageSelectScrollListener onPageSelectScrollListener) {
        mOnPageSelectScrollListener = onPageSelectScrollListener;
    }

    public void setOnRecyclerListener(RecyclerView.RecyclerListener recyclerListener) {
        mRecyclerListener = recyclerListener;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /**
     * Activity生命周期onPause发生时调用,防止切换到后台视频继续播放的问题
     */
    public void onPause() {


    }

    /**
     * Activity生命周期onResume发生时调用,防止切换到后台视频继续播放的问题
     */
    public void onResume() {

    }


    /**
     * 设置adapter
     *
     * @param recyclerViewAdapter
     */
    public void setRecyclerViewAdapter(ScanBaseRecyclerViewAdapter<A> recyclerViewAdapter) {
        this.mRecyclerViewAdapter = recyclerViewAdapter;
        mScanRecyclerView.setAdapter(recyclerViewAdapter);
        this.list = recyclerViewAdapter.getData();
    }

    public void setRefreshColorSchemeColors(int ... colors) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeColors(colors);
        }
    }

    private List<A> list;

    private OnClickListener mOnRefreshClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mSwipeRefreshLayout != null) {
                if (!mSwipeRefreshLayout.isRefreshing()) {
                    if (onRefreshDataListener != null) {
                        startLoading(false);
                        onRefreshDataListener.onRefresh();
                    }
                }
            }
        }
    };

    public void startLoading(boolean flag) {
        if (mSwipeRefreshLayout != null && flag) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
        mScanRecyclerView.setLoadingStatus(0);
    }

    public void updatAllDataAndNoSelect(int pos) {
        mScanPagerLayoutManager.updatAllDataAndNoSelect(false);
    }

    public void setOnRefresh(boolean b) {
        if (mSwipeRefreshLayout != null){
            mSwipeRefreshLayout.setEnabled(b);
        }
    }

    public void setOnLoadMoreEnable(boolean b) {
        this.mIsLoadMoreEnable = b;
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    public int getCurrentLoadingStatus(){
        return currentLoadingStatus;
    }

    public void setCurrentLoadingStatus(int currentLoadingStatus){
        this.currentLoadingStatus = currentLoadingStatus;
    }

    public void setNestedScrollingEnabled(boolean enabled){
        if(mScanRecyclerView!=null){
            mScanRecyclerView.setNestedScrollingEnabled(enabled);
        }
    }

    public ScanRecyclerView getmScanRecyclerView() {
        return mScanRecyclerView;
    }
}
