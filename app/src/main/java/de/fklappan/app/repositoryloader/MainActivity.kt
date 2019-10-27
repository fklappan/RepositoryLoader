package de.fklappan.app.repositoryloader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import de.fklappan.app.repositoryloader.common.AppBarHeader
import kotlinx.android.synthetic.main.activity_main.*

/**
 * The main Activity class which holds the NavHostFragment
 */
class MainActivity : AppCompatActivity(), AppBarHeader {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // AppBarHeader impl

    override fun setHeaderText(text: String) {
        toolbar.title = text
    }

    override fun setHeaderText(resource: Int) {
        toolbar.title = getString(resource)
    }
}
