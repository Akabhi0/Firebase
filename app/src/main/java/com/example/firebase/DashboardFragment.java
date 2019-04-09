package com.example.firebase;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.firebase.databinding.FragmentdashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentdashboardBinding binding;
    private static final int JOB_ID = 101;
    private JobScheduler jobScheduler;
    private JobInfo jobInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmentdashboard, container, false);
        return binding.getRoot();
    }

    private void Job_Scheduler() {
        ComponentName componentName = new ComponentName(getActivity(), LoactionTaker.class);
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, componentName);
        builder.setPeriodic(5000);
        builder.setRequiredNetworkType(jobInfo.NETWORK_TYPE_ANY);
        builder.setPersisted(true);

        jobInfo = builder.build();
        jobScheduler = (JobScheduler) getActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);

//        binding.buttonOne.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                jobScheduler.schedule(jobInfo);
//                Toast.makeText(getContext(), "Job schedule", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        binding.buttonTwo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                jobScheduler.cancel(JOB_ID);
//                Toast.makeText(getContext(), "Job cancel", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
