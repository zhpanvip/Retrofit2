package com.cypoem.retrofit.module;

import java.util.List;

/**
 * Created by edianzu on 2017/4/17.
 */

public class ListData {
    /**
     * {"list":[{"id":1,"psw":"www","user":"???"},{"id":2,"psw":"SSS","user":"????"},{"id":4,"psw":"32432","user":"??"},{"id":5,"psw":"123","user":"??"},{"id":6,"psw":"h123","user":"houzi"},{"id":15,"psw":"h123","user":"houzi"},{"id":16,"psw":"h123","user":"houzi"}]}
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1
         * psw : www
         * user : ???
         */

        private int id;
        private String psw;
        private String user;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPsw() {
            return psw;
        }

        public void setPsw(String psw) {
            this.psw = psw;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }
}
