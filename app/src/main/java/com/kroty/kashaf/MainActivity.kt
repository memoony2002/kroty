package com.kroty.kashaf

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.materialswitch.MaterialSwitch
import com.kroty.kashaf.data.Discovery
import com.kroty.kashaf.ui.DiscoveryAdapter
import com.kroty.kashaf.ui.DiscoveryViewModel

/**
 * الشاشة الرئيسية لتطبيق "كشاف":
 * تعرض قائمة الاكتشافات مع بحث فوري وإمكانية الإضافة والحذف.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: DiscoveryViewModel
    private lateinit var adapter: DiscoveryAdapter
    private lateinit var listView: RecyclerView
    private lateinit var emptyView: TextView

    private val searchQuery = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[DiscoveryViewModel::class.java]

        listView = findViewById(R.id.recyclerView)
        emptyView = findViewById(R.id.emptyView)
        adapter = DiscoveryAdapter { discovery -> openDetail(discovery) }
        listView.layoutManager = LinearLayoutManager(this)
        listView.adapter = adapter

        val searchBox: EditText = findViewById(R.id.searchBox)
        searchBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchQuery.clear()
                searchQuery.append(s)
                render()
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })

        findViewById<MaterialSwitch>(R.id.searchToggle).setOnCheckedChangeListener { _, checked ->
            searchBox.visibility = if (checked) View.VISIBLE else View.GONE
            if (!checked) {
                searchBox.text?.clear()
                searchQuery.clear()
            }
            render()
        }

        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }

        viewModel.discoveries.observe(this) { _ -> render() }
        viewModel.refresh()
    }

    private fun render() {
        val list = viewModel.filtered(searchQuery.toString())
        adapter.submitList(list)
        emptyView.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        listView.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun openDetail(discovery: Discovery) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_DISCOVERY, discovery)
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_refresh -> {
            viewModel.refresh()
            true
        }
        R.id.action_clear -> {
            viewModel.clearAll()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
