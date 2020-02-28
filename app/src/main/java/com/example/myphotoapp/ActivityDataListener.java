package com.example.myphotoapp;

import android.os.Bundle;

/**
 * Created by Shim on 2015-05-13.
 * Activity 와 Fragment 간 통신을 위한 interface
 * Activity -> Fragment 로 화면 및 데이터 처리를 요청
 */
public interface ActivityDataListener {
    void onActivityMessageReceived(int actionCode, Bundle data);
}
