package dev.byto.moly.utils

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

class LifecycleRecyclerView(private val recyclerView: RecyclerView) : DefaultLifecycleObserver {
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        recyclerView.adapter = null
    }
}