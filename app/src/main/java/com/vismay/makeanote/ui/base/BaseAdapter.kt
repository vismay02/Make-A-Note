package com.vismay.makeanote.ui.base

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vismay.makeanote.ui.shownotes.ShowNotesFragment

abstract class BaseAdapter<T : Any, VH : BaseItemViewHolder<T, out BaseItemViewModel<T>>>(
    parentLifecycle: Lifecycle,
    private val dataList: ArrayList<T>
) : RecyclerView.Adapter<VH>() {

    private var recyclerView: RecyclerView? = null

    init {
        Log.d(ShowNotesFragment.TAG, "init dataList: $dataList")
        parentLifecycle.addObserver(object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onParentDestroyed() {
                recyclerView?.run {
                    for (i in 0 until childCount) {
                        getChildAt(i)?.let {
                            (getChildViewHolder(it) as BaseItemViewHolder<*, *>).run {
                                onDestroy()
//                                Needed to clear composite disposable of RxJava
//                                viewModel.onManuallyCleared()
                            }
                        }
                    }
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onParentStopped() {
                recyclerView?.run {
                    for (i in 0 until childCount) {
                        getChildAt(i)?.let {
                            (getChildViewHolder(it) as BaseItemViewHolder<*, *>).onStop()
                        }
                    }
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onParentStarted() {
                recyclerView?.run {
                    if (layoutManager is LinearLayoutManager) {
                        val first =
                            (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        val last =
                            (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                        if (first in 0..last) {
                            for (i in first..last) {
                                findViewHolderForAdapterPosition(i)?.let {
                                    (it as BaseItemViewHolder<*, *>).onStart()
                                }
                            }
                        }
                    }
                }
            }
        })
    }

    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        Log.d(ShowNotesFragment.TAG, "onViewAttachedToWindow: $holder")
        holder.onStart()
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        Log.d(ShowNotesFragment.TAG, "onViewDetachedFromWindow: $holder")
        holder.onStop()
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        Log.d(ShowNotesFragment.TAG, "onBindViewHolder: $holder")
        holder.bind(dataList[position])
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        Log.d(ShowNotesFragment.TAG, "onAttachedToRecyclerView: ")
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        Log.d(ShowNotesFragment.TAG, "onDetachedFromRecyclerView: ")
        this.recyclerView = null
    }

    fun appendData(dataList: List<T>) {
        val oldCount = itemCount
        this.dataList.addAll(dataList)
        val currentCount = itemCount
        if (oldCount == 0 && currentCount > 0)
            notifyDataSetChanged()
        else if (oldCount in 1 until currentCount)
            notifyItemRangeChanged(oldCount - 1, currentCount - oldCount)
    }

}