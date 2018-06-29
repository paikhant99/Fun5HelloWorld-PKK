package com.devteam5.fun5helloworld.data.vos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by paikhantko on 6/9/18.
 */

public class SendToVO {

    @SerializedName("send-to-id")
    private String sendToId;

    @SerializedName("send-date")
    private String sendDate;

    @SerializedName("acted-user")
    private ActedUserVO actedUser;

    @SerializedName("received-user")
    private ActedUserVO receivedUser;

    public String getSendToId() {
        return sendToId;
    }

    public String getSendDate() {
        return sendDate;
    }

    public ActedUserVO getActedUser() {
        return actedUser;
    }

    public ActedUserVO getReceivedUser() {
        return receivedUser;
    }
}
