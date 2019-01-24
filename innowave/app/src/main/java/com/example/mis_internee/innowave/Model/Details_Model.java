package com.example.mis_internee.innowave.Model;

/**
 * Created by MIS-Internee on 1/23/2019.
 */

public class Details_Model {

    String f_name;
    String f_avatar;

    //constructor
    public Details_Model(String F_NAME, String F_AVATAR) {
        this.f_name = F_NAME;
        this.f_avatar = F_AVATAR;
    }

    //getters


    public String getName() {
        return this.f_name;
    }

    public String getAvatar() {
        return this.f_avatar;
    }


}
