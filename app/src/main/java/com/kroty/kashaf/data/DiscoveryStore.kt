package com.kroty.kashaf.data

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

/**
 * طبقة تخزين محلية بسيطة تعتمد على SharedPreferences.
 * يتم حفظ قائمة الاكتشافات كـ JSON داخل ملفات التطبيق.
 */
class DiscoveryStore(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val lock = Any()

    /** جلب كل الاكتشافات المخزّنة. */
    fun getAll(): List<Discovery> {
        synchronized(lock) {
            val raw = prefs.getString(KEY_ITEMS, null) ?: return emptyList()
            return runCatching {
                val array = JSONArray(raw)
                (0 until array.length()).map { i ->
                    val obj = array.getJSONObject(i)
                    Discovery(
                        id = obj.getString("id"),
                        title = obj.getString("title"),
                        category = obj.getString("category"),
                        description = obj.getString("description"),
                        location = obj.optString("location", ""),
                        date = obj.optString("date", "")
                    )
                }
            }.getOrDefault(emptyList())
        }
    }

    /** إضافة اكتشاف جديد وحفظه. */
    fun add(discovery: Discovery) {
        synchronized(lock) {
            val items = getAll().toMutableList()
            items.add(discovery)
            save(items)
        }
    }

    /** حذف اكتشاف بحسب المعرف. */
    fun delete(id: String) {
        synchronized(lock) {
            save(getAll().filterNot { it.id == id })
        }
    }

    /** حذف كل الاكتشافات. */
    fun clear() {
        synchronized(lock) {
            prefs.edit().remove(KEY_ITEMS).apply()
        }
    }

    private fun save(items: List<Discovery>) {
        val array = JSONArray()
        items.forEach { d ->
            array.put(JSONObject().apply {
                put("id", d.id)
                put("title", d.title)
                put("category", d.category)
                put("description", d.description)
                put("location", d.location)
                put("date", d.date)
            })
        }
        prefs.edit().putString(KEY_ITEMS, array.toString()).apply()
    }

    /** إنشاء اكتشاف جديد بمعلومات مبدئية. */
    companion object {
        const val PREFS_NAME = "kashaf_prefs"
        const val KEY_ITEMS = "discoveries"

        val CATEGORIES = listOf(
            "مكان", "مكان طبيعي", "فكرة", "كتاب", "شخص", "شيء مميز", "أخرى"
        )

        fun newDiscovery(title: String, category: String, description: String, location: String): Discovery {
            val now = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            return Discovery(
                id = UUID.randomUUID().toString(),
                title = title.trim(),
                category = category,
                description = description.trim(),
                location = location.trim(),
                date = now
            )
        }
    }
}
