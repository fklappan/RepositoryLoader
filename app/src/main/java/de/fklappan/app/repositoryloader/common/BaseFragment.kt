package de.fklappan.app.repositoryloader.common

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import de.fklappan.app.repositoryloader.RepositoryLoaderApplication
import de.fklappan.app.repositoryloader.di.ControllerComponent
import de.fklappan.app.repositoryloader.di.ControllerModule
import de.fklappan.app.repositoryloader.domain.LOG_TAG

/**
 * Base class for all Fragments. Takes care of the cumbersome stuff needed for every Fragment.
 * Exposes the AppBarHeader and the Injector for Dependency Injection.
 */
open class BaseFragment : Fragment() {

    private lateinit var controllerComponent: ControllerComponent
    private lateinit var appBarHeader: AppBarHeader

    // upon attaching to the Activity, we get the DI module
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(LOG_TAG, "onAttach")

        if (context.applicationContext is RepositoryLoaderApplication) {
            val application: RepositoryLoaderApplication = context.applicationContext as RepositoryLoaderApplication
            controllerComponent = application.getApplicationComponent()
                .newControllerComponent(ControllerModule(context))
        }
        if (context is AppBarHeader) {
            appBarHeader = context
        }
    }

    fun getInjector() : ControllerComponent = controllerComponent

    fun getAppBarHeader() : AppBarHeader = appBarHeader
}