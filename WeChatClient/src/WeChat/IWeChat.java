package wechat;

/**
 * Created by ek2zqun on 11/30/2016.
 */
public interface IWeChat {
    void append(String Message);
    void connectionFailed();
    void loginSuccessful();
}
