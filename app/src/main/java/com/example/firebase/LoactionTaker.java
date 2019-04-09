package com.example.firebase;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class LoactionTaker extends JobService {

    @Override
    public boolean onStartJob(final JobParameters params) {
        Log.d("job", "started");

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                return "Hello abhihsek";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
                jobFinished(params, false);
            }
        }.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
