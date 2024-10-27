package com.example.myapplication.ui.profile

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.myapplication.datastore.SettingPreferences
import com.example.myapplication.datastore.datastore
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.utils.ProfileViewModelFactory
import com.example.worker.MyWorker
import java.util.concurrent.TimeUnit

class ProfileFragment : Fragment(), View.OnClickListener {

    // work manager
    private lateinit var workManager: WorkManager
    private lateinit var periodicWorkRequest: PeriodicWorkRequest

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // request permission
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){isGranted: Boolean ->
        if (isGranted){
            Toast.makeText(requireActivity(), "Notifications permission granted", Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(requireActivity(), "Notifications permission rejected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= 33){
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        val switchTheme = binding.switch2

        val pref = SettingPreferences.getInstance(requireContext().datastore)
        val profileViewModel = ViewModelProvider(requireActivity(), ProfileViewModelFactory(pref))[ProfileViewModel::class.java]

        profileViewModel.getThemeSettings().observe(viewLifecycleOwner){ isDarkModeActive : Boolean ->
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
                binding.imageView6.setImageResource(R.drawable.githublogo)
            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener{ _: CompoundButton?, isChecked: Boolean ->
            profileViewModel.saveThemeSetting(isChecked)
        }

        binding.link.setOnClickListener {
            val web = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/Tanzz08")
            )
            startActivity(web)
        }

        // notification
        workManager = WorkManager.getInstance(requireActivity())
        binding.button.setOnClickListener (this)

        binding.button2.isEnabled = false
        binding.button2.setOnClickListener(this)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button -> starPeriodicTask()
            R.id.button2 -> cancelPeriodicTask()
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun starPeriodicTask() {
        val data = Data.Builder()
            .build()
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        periodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 1, TimeUnit.DAYS)
            .setInputData(data)
            .setConstraints(constraints)
            .addTag("PERIODIC_TASK_TAG")
            .build()
        workManager.enqueue(periodicWorkRequest)
        workManager.getWorkInfoByIdLiveData(periodicWorkRequest.id)
            .observe(viewLifecycleOwner) { workInfo ->
                binding.button2.isEnabled = false
                if (workInfo.state == WorkInfo.State.ENQUEUED){
                    binding.button2.isEnabled = true
                }
            }
    }

    private fun cancelPeriodicTask() {
        Toast.makeText(requireActivity(), "Notifikasi Dibatalkan", Toast.LENGTH_SHORT).show()
        workManager.cancelAllWorkByTag("PERIODIC_TASK_TAG")

    }



}