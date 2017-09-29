package com.cypoem.retrofit.module.wrapper;

import com.cypoem.retrofit.module.BasicResponse;
import com.cypoem.retrofit.module.bean.Results;
import java.util.List;

/**
 * Created by zhpan on 2017/4/20.
 */

public class MeiziWrapper extends BasicResponse {
    List<Results.ResultsBean> results;

    public List<Results.ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<Results.ResultsBean> results) {
        this.results = results;
    }
}
