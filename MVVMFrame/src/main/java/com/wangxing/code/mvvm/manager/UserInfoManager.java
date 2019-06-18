package com.wangxing.code.mvvm.manager;

import com.wangxing.code.mvvm.utils.ACache;

/**
 * Created by WangXing on 2019/6/18.
 * 可根据项目需要自行更改
 */
public class UserInfoManager {

    /**
     * 用户信息Key
     */
    private static final String USER_ID = "userId";

    private static final String USER_TOKEN = "userToken";

    private static final String USER_NAME = "userName";

    private static final String USER_NICK_NAME = "userNickName";

    private static final String USER_SEX = "userSex";

    private static final String USER_PHONE = "userPhone";

    private static final String USER_ICON = "userIcon";

    private static final String USER_ADDRESS = "userAddress";

    private static final String USER_LEVEL = "userLevel";

    private static final String USER_STATUS = "userStatus";

    private static final String USER_PASSWORD = "userPassword";

    private static volatile UserInfoManager sInstance;

    private UserInfoManager() {
    }

    public static UserInfoManager getInstance() {
        if (sInstance == null) {
            synchronized (UserInfoManager.class) {
                if (sInstance == null) {
                    sInstance = new UserInfoManager();
                }
            }
        }
        return sInstance;
    }

    public String getUserId() {
        return ACache.get().getAsString(USER_ID);
    }

    public void saveUserId(String userId) {
        ACache.get().put(USER_ID, userId);
    }

    public String getUserToken() {
        return ACache.get().getAsString(USER_TOKEN);
    }

    public void saveUserToken(String userToken) {
        ACache.get().put(USER_TOKEN, userToken);
    }

    public String getUserName() {
        return ACache.get().getAsString(USER_NAME);
    }

    public void saveUserName(String userName) {
        ACache.get().put(USER_NAME, userName);
    }

    public String getUserNickName() {
        return ACache.get().getAsString(USER_NICK_NAME);
    }

    public void saveUserNickName(String userNickName) {
        ACache.get().put(USER_NICK_NAME, userNickName);
    }

    public String getUserSex() {
        return ACache.get().getAsString(USER_SEX);
    }

    public void saveUserSex(String userSex) {
        ACache.get().put(USER_SEX, userSex);
    }

    public String getUserPhone() {
        return ACache.get().getAsString(USER_PHONE);
    }

    public void saveUserPhone(String userPhone) {
        ACache.get().put(USER_PHONE, userPhone);
    }

    public String getUserIcon() {
        return ACache.get().getAsString(USER_ICON);
    }

    public void saveUserIcon(String userIcon) {
        ACache.get().put(USER_ICON, userIcon);
    }

    public String getUserAddress() {
        return ACache.get().getAsString(USER_ADDRESS);
    }

    public void saveUserAddress(String userAddress) {
        ACache.get().put(USER_ADDRESS, userAddress);
    }

    public String getUserLevel() {
        return ACache.get().getAsString(USER_LEVEL);
    }

    public void saveUserLevel(String userLevel) {
        ACache.get().put(USER_LEVEL, userLevel);
    }

    public String getUserStatus() {
        return ACache.get().getAsString(USER_STATUS);
    }

    public void saveUserSatus(String userStatus) {
        ACache.get().put(USER_STATUS, userStatus);
    }

    public String getUserPassword() {
        return ACache.get().getAsString(USER_PASSWORD);
    }

    public void saveUserPassword(String userPassword) {
        ACache.get().put(USER_PASSWORD, userPassword);
    }
}
