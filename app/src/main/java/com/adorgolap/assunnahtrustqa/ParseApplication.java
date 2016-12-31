package com.adorgolap.assunnahtrustqa;

import android.app.Application;

import com.adorgolap.assunnahtrustqa.helper.Utils;

/**
 * Created by ifta on 12/17/16.
 */

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.initializeParse(this);
    }
}
