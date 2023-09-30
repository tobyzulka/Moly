package dev.byto.moly.ui.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.byto.moly.R
import dev.byto.moly.databinding.ActivityMainBinding
import dev.byto.moly.utils.doOnApplyWindowInsets
import dev.byto.moly.utils.updateMargin

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        updateMargins()
        updateBottomNavPaddings()
        setupBottomNavigationBar()

    }

    private fun setupBottomNavigationBar() {
        val navHost = supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        val controller = navHost.navController

        binding.navView.setupWithNavController(navHost.navController)
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            destination.id = R.id.navigation_home
        }

        controller.removeOnDestinationChangedListener(listener)
        controller.addOnDestinationChangedListener(listener)
    }

    private fun updateMargins() {
        binding.root.doOnApplyWindowInsets { _, insets, _ ->
            binding.mainFragmentContainer.updateMargin(top = insets.systemWindowInsetTop)

            insets
        }
    }

    private fun updateBottomNavPaddings() {
        binding.navView.doOnApplyWindowInsets { view, insets, _ ->
            view.updatePadding(bottom = 0)
            insets
        }
    }
}