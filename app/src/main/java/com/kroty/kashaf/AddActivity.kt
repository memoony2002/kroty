package com.kroty.kashaf

import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.kroty.kashaf.data.DiscoveryStore
import com.kroty.kashaf.ui.DiscoveryViewModel

/**
 * شاشة إضافة اكتشاف جديد: إدخال العنوان والفئة والوصف والموقع.
 */
class AddActivity : AppCompatActivity() {

    private lateinit var viewModel: DiscoveryViewModel
    private lateinit var categoryField: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.add_title)

        viewModel = ViewModelProvider(this)[DiscoveryViewModel::class.java]

        categoryField = findViewById(R.id.categoryField)
        categoryField.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                DiscoveryStore.CATEGORIES
            )
        )

        val titleField: EditText = findViewById(R.id.titleField)
        val descriptionField: EditText = findViewById(R.id.descriptionField)
        val locationField: EditText = findViewById(R.id.locationField)

        findViewById<MaterialButton>(R.id.buttonSave).setOnClickListener {
            val title = titleField.text.toString()
            if (title.isBlank()) {
                titleField.error = getString(R.string.title_required)
                return@setOnClickListener
            }
            val category = categoryField.text.toString().ifEmpty {
                DiscoveryStore.CATEGORIES.first()
            }
            val discovery = DiscoveryStore.newDiscovery(
                title = title,
                category = category,
                description = descriptionField.text.toString(),
                location = locationField.text.toString()
            )
            viewModel.add(discovery)
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
}
