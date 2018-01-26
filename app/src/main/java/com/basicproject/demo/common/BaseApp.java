package com.basicproject.demo.common;

import android.support.multidex.MultiDexApplication;

import com.dvp.base.config.AppConfig;
import com.dvp.base.config.AppPreferenceConfig;
import com.dvp.base.config.AppPropertiesConfig;
import com.dvp.base.util.AppManager;

/**
 * 作者 薛
 * 时间 2018/1/24 0024
 * 功能描述:
 */

public class BaseApp extends MultiDexApplication {


    private static BaseApp app;
    private AppManager appManager;
    private AppConfig appConfig;
    public static final int PREFERENCECONFIG = 0;
    public static final int PROPERTIESCONFIG = 1;

    public BaseApp() {
    }

    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static BaseApp getInstance() {
        return app;
    }

    public AppManager getAppManager() {
        if(this.appManager == null) {
            this.appManager = AppManager.getInstance();
        }

        return this.appManager;
    }

    public AppConfig getPreferenceConfig() {
        return this.getAppConfig(0);
    }

    public AppConfig getPropertiesConfig() {
        return this.getAppConfig(1);
    }

    public AppConfig getAppConfig(int configType) {
        if(configType == 0) {
            this.appConfig = AppPreferenceConfig.getPreferenceConfig(this, this.getPackageName().split("\\.")[this.getPackageName().split("\\.").length - 1]);
        } else if(configType == 1) {
            this.appConfig = AppPropertiesConfig.getPropertiesConfig(this);
        } else {
            this.appConfig = AppPreferenceConfig.getPreferenceConfig(this, this.getPackageName().split("\\.")[this.getPackageName().split("\\.").length - 1]);
        }

        if(!this.appConfig.isLoadConfig().booleanValue()) {
            this.appConfig.loadConfig();
        }

        return this.appConfig;
    }

    public AppConfig getAppConfig() {
        if(this.appConfig == null) {
            this.getPreferenceConfig();
        }

        return this.appConfig;
    }

    public void exitApp(boolean isBackground) {
        this.appManager.AppExit(this, Boolean.valueOf(isBackground));
    }



}
