package org.example.model;

import java.util.List;

public class GetIngredientsResponse {
    private Boolean success;
    private List<DataBurger> data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<DataBurger> getData() {
        return data;
    }

    public void setData(List<DataBurger> data) {
        this.data = data;
    }
}
