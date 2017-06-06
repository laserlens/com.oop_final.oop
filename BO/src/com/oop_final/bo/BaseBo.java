package com.oop_final.bo;

/**
 * Created by Adrian.Flak on 6/1/2017.
 */
public class BaseBo {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String overRideThisMethod() {
        return "override this string";
    }
}
