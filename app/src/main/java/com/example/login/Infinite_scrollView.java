package com.example.login;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class Infinite_scrollView extends RecyclerView.OnScrollListener {

    private int visibleThreshold = 5;
    // The current offset index of data you have loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
   // private boolean isLastPage = false;
    // Sets the starting page index
    private int startingPageIndex = 0;

    RecyclerView.LayoutManager mLayoutManager;
   // LinearLayoutManager linearLayoutManager;

    public Infinite_scrollView(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {

        int lastVisibleItemPosition = 0;

        int totalItemCount = mLayoutManager.getItemCount();

        lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();

        Log.d("last position",""+lastVisibleItemPosition);
        Log.d("total count",""+totalItemCount);
        Log.d("previous total count",""+previousTotalItemCount);

        if (totalItemCount < previousTotalItemCount) {

            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }

        }

       /* if (loading && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >=
                    LtotalItemCount && firstVisibleItemPosition >= 0) {
               // loadMoreItems();
                currentPage++;
                onLoadMore(currentPage, totalItemCount, view);
                loading = true;
            }
        }*/

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        Log.d("last position",""+lastVisibleItemPosition);
        Log.d("total count",""+totalItemCount);
        Log.d("previous total count",""+previousTotalItemCount);
        Log.d("loading",""+loading);
        Log.d("threshold",""+visibleThreshold);

        if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {

            currentPage++;
            onLoadMore(currentPage, totalItemCount, view);
            loading = true;
        }

    }

   // protected abstract boolean isLastPage();

    public abstract void onLoadMore( int page, int totalItemsCount, RecyclerView view);
}


