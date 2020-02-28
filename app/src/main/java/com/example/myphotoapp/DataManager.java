package com.example.myphotoapp;

/**
 * Created by Shim on 2015-08-31.
 * 싱글톤 패턴 클래스
 * CentralActivity 내에 포함되는 Fragment들을 관리하기 위한 클래스
 */
public class DataManager  {

    private static DataManager dataManager;

    public static DataManager getInstance() {
        if (dataManager == null) {
            dataManager = new DataManager();
        }
        return dataManager;
    }
}
