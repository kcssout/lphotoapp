package com.example.myphotoapp.Logger;

import android.os.Binder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LogWrapper {

    private static final String TAG= "LogWrapper";
    private static final int LOG_FILE_SIZE_LIMIT = 512*1024;
    private static final int LOG_FILE_MAX_COUNT = 2;
    private static final String LOG_FILE_NAME = "FileLog%g.txt"; //%g 로 해야지 순차적으로 숫자가 올라간다.%g를 사용하지 않으면 "FileLog.txt.0"으로 생성된다.
    private static final SimpleDateFormat formatter =
            new SimpleDateFormat("MM-dd HH:mm:ss.SSS: ", Locale.getDefault());
    private static final Date date = new Date();
    private static Logger logger;
    private static FileHandler fileHandler;
    private static Date deleteDate;

    static {
        //객체 생성 없이 사용하기 위해 static method 만 정의
        try{
            //android.permission.WRITE_EXTERNAL_STORAGE
            //외부에 저장


            deleteDate = new Date();
            //두날짜 사이의 시간 차이(ms)를 하루 동안의 ms(24시*60분*60초*1000밀리초) 로 나눈다.
            long diffDay = (deleteDate.getTime() - date.getTime()) / (24*60*60*1000); // date를 sharedpreferernce 에 저장
            if(diffDay < 2){
                //삭제
            }

            fileHandler = new FileHandler(Environment.getExternalStorageDirectory() + File.separator +LOG_FILE_NAME,LOG_FILE_SIZE_LIMIT, LOG_FILE_MAX_COUNT, true);
            // directory 바꿔주기

            fileHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord logRecord) {
                    date.setTime(System.currentTimeMillis());

                    StringBuilder ret = new StringBuilder(90);
                    ret.append(formatter.format(date));
                    ret.append(logRecord.getMessage());
                    return ret.toString();
                }
            }); // 현재 시간

            logger = Logger.getLogger(LogWrapper.class.getName());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
            logger.setUseParentHandlers(false);
            Log.d(TAG, "init success");

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void i(String tag, String msg) {
        if (logger != null) {
            logger.log(Level.INFO, String.format("I/%s(%d): %s\n",tag, Binder.getCallingPid(), msg));
        }

        Log.v(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (logger != null) {
            logger.log(Level.WARNING, String.format("W/%s(%d): %s\n",tag, Binder.getCallingPid(), msg));
        }

        Log.v(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (logger != null) {
            logger.log(Level.parse("E"), String.format("V/%s(%d): %s\n",tag, Binder.getCallingPid(), msg));
        }

        Log.v(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (logger != null) {
            logger.log(Level.INFO, String.format("V/%s(%d): %s\n",tag, Binder.getCallingPid(), msg));
        }

        Log.v(tag, msg);
    }



}
