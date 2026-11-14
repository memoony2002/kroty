package com.kroty.kashaf.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kroty.kashaf.data.Discovery
import com.kroty.kashaf.data.DiscoveryStore

/**
 * ViewModel يحوي قائمة الاكتشافات ويدعم البحث والفلترة.
 */
class DiscoveryViewModel(app: Application) : AndroidViewModel(app) {

    private val store = DiscoveryStore(app)

    private val _discoveries = MutableLiveData<List<Discovery>>(emptyList())
    val discoveries: LiveData<List<Discovery>> = _discoveries

    /** reload the list from storage. */
    fun refresh() {
        _discoveries.value = store.getAll().sortedByDescending { it.date }
    }

    fun add(discovery: Discovery) {
        store.add(discovery)
        refresh()
    }

    fun delete(id: String) {
        store.delete(id)
        refresh()
    }

    fun clearAll() {
        store.clear()
        refresh()
    }

    /** فلترة القائمة حسب نص البحث. */
    fun filtered(query: String): List<Discovery> {
        val q = query.trim().lowercase()
        val list = _discoveries.value ?: emptyList()
        return if (q.isEmpty()) list else list.filter {
            it.title.lowercase().contains(q) ||
                it.category.lowercase().contains(q) ||
                it.description.lowercase().contains(q) ||
                it.location.lowercase().contains(q)
        }
    }
}
