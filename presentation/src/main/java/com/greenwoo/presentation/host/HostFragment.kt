package com.greenwoo.presentation.host

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.IntRange
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseFragment
import com.greenwoo.presentation.common.observeEvent
import com.greenwoo.presentation.databinding.HostBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass


@AndroidEntryPoint
class HostFragment : BaseFragment<HostBinding, HostViewData, HostViewModel>() {

    override val layoutId = R.layout.host

    override val viewModelClass: KClass<HostViewModel> = HostViewModel::class

    override fun initView(
        binding: HostBinding,
        viewData: HostViewData,
        savedInstanceState: Bundle?,
        viewLifecycleOwner: LifecycleOwner
    ) {
        super.initView(binding, viewData, savedInstanceState, viewLifecycleOwner)
        val navController =
            childFragmentManager.findFragmentById(R.id.containerHost) as NavHostFragment
        binding.btmNavigation.setupWithNavController(navController.navController)
    }

    override fun bindViewModel(viewModel: HostViewModel, savedInstanceState: Bundle?) {
        super.bindViewModel(viewModel, savedInstanceState)
        viewModel.navigation.observeEvent(this, ::handleNavigation)
    }

    private fun handleNavigation(target: HostViewModel.NavigationTarget) {
        when (target) {
            is HostViewModel.NavigationTarget.ShowMessage -> {
                if(getConnectionType(requireContext()) == 0)
                    Toast.makeText(requireContext(), target.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    @IntRange(from = 0, to = 3)
    fun getConnectionType(context: Context): Int {
        var result = 0 // Returns connection type. 0: none; 1: mobile data; 2: wifi
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (cm != null) {
                val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        result = 2
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        result = 1
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                        result = 3
                    }
                }
            }
        } else {
            if (cm != null) {
                val activeNetwork = cm.activeNetworkInfo
                if (activeNetwork != null) {
                    // connected to the internet
                    if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                        result = 2
                    } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                        result = 1
                    } else if (activeNetwork.type == ConnectivityManager.TYPE_VPN) {
                        result = 3
                    }
                }
            }
        }
        return result
    }
}
