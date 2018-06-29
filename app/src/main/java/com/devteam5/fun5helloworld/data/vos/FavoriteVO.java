package com.devteam5.fun5helloworld.data.vos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by paikhantko on 6/9/18.
 */

public class FavoriteVO {
    @SerializedName("favorite-id")
    private String favoriteId;

    @SerializedName("favorite-date")
    private String favoriteDate;

    @SerializedName("acted-user")
    private ActedUserVO actedUser;

    public String getFavoriteId() {
        return favoriteId;
    }

    public String getFavoriteDate() {
        return favoriteDate;
    }

    public ActedUserVO getActedUser() {
        return actedUser;
    }
}
