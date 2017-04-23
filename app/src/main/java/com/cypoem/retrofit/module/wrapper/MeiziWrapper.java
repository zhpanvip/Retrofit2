package com.cypoem.retrofit.module.wrapper;

import com.cypoem.retrofit.module.BasicResponse;
import com.cypoem.retrofit.module.bean.Meizi;
import java.util.List;

/**
 * Created by zhpan on 2017/4/20.
 */

public class MeiziWrapper extends BasicResponse {
    List<Meizi.ResultsBean> results;

    public List<Meizi.ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<Meizi.ResultsBean> results) {
        this.results = results;
    }
}
