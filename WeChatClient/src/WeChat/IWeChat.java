package wechat;

import java.util.List;

/**
 * Created by ek2zqun on 11/30/2016.
 */
public interface IWeChat {
    void append(String Message);
    void connectionFailed();
    void loginSuccessful(int userId);
    void setContactList(List<wechat.Contact> contactList, List<wechat.Contact> groupList);
    void receiveIndividualMessage(ChatMessage chatMessage);
    void receiveGroupMessage(ChatMessage chatMessage);
}
