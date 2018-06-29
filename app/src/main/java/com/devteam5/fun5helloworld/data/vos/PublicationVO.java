package com.devteam5.fun5helloworld.data.vos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by paikhantko on 6/9/18.
 */

public class PublicationVO {

    @SerializedName("publication-id")
    private String publicationId;

    @SerializedName("title")
    private String title;

    @SerializedName("logo")
    private String logo;

    public String getPublicationId() {
        return publicationId;
    }

    public String getTitle() {
        return title;
    }

    public String getLogo() {
        return logo;
    }
}
