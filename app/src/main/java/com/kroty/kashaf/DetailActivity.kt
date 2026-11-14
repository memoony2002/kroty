package com.kroty.kashaf

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.kroty.kashaf.data.Discovery
import com.kroty.kashaf.ui.DiscoveryViewModel

/**
 * شاشة التفاصيل: تعرض معلومات اكتشاف واحد مع خيار الحذف.
 */
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val discovery = intent.getParcelableExtra<Discovery>(EXTRA_DISCOVERY)
            ?: run {
                finish()
                return
            }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.detail_title)

        findViewById<TextView>(R.id.detailTitle).text = discovery.title
        findViewById<TextView>(R.id.detailCategory).text = discovery.category
        findViewById<TextView>(R.id.detailDate).text = discovery.date
        findViewById<TextView>(R.id.detailLocation).text =
            if (discovery.location.isNotEmpty()) discovery.location
            else getString(R.string.no_location)
        findViewById<TextView>(R.id.detailDescription).text =
            if (discovery.description.isNotEmpty()) discovery.description
            else getString(R.string.no_description)

        findViewById<MaterialButton>(R.id.buttonDelete).setOnClickListener {
            val viewModel = ViewModelProvider(this)[DiscoveryViewModel::class.java]
            viewModel.delete(discovery.id)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_DISCOVERY = "extra_discovery"
    }
}
