package com.example.joan.lafosca.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joan on 17/3/15.
 */
public class ModelState {
    @Expose
    private String state;
    @Expose
    private Integer flag;
    @Expose
    private Integer happiness;
    @Expose
    private Integer dirtiness;
    @SerializedName("kids")
    private List<ModelKid> kids = new ArrayList<ModelKid>();

    /**
     *
     * @return
     * The state
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     * The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     * The flag
     */
    public Integer getFlag() {
        return flag;
    }

    /**
     *
     * @param flag
     * The flag
     */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    /**
     *
     * @return
     * The happiness
     */
    public Integer getHappiness() {
        return happiness;
    }

    /**
     *
     * @param happiness
     * The happiness
     */
    public void setHappiness(Integer happiness) {
        this.happiness = happiness;
    }

    /**
     *
     * @return
     * The dirtiness
     */
    public Integer getDirtiness() {
        return dirtiness;
    }

    /**
     *
     * @param dirtiness
     * The dirtiness
     */
    public void setDirtiness(Integer dirtiness) {
        this.dirtiness = dirtiness;
    }

    /**
     *
     * @return
     * The kids
     */
    public List<ModelKid> getKids() {
        return kids;
    }

    /**
     *
     * @param kids
     * The kids
     */
    public void setKids(List<ModelKid> kids) {
        this.kids = kids;
    }
}
