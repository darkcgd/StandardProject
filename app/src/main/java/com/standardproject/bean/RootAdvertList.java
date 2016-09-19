package com.standardproject.bean;

import java.util.List;

public class RootAdvertList {

    /**
     * list : [{"id":2,"relation_id":null,"title":"222","image_url":"http://58.210.52.234:16101/2016/2/17/PIC20160217103536361.jpg","link_url":"http://www.baidu.com","relation_type":1,"relative_url":"/2016/2/17/PIC20160217103536361.jpg"},{"id":1,"relation_id":null,"title":"1111","image_url":"http://58.210.52.234:16101/2016/2/17/PIC20160217103454863.jpg","link_url":"http://www.baidu.com","relation_type":1,"relative_url":"/2016/2/17/PIC20160217103454863.jpg"}]
     * success : true
     */

    private boolean success;
    /**
     * id : 2
     * relation_id : null
     * title : 222
     * image_url : http://58.210.52.234:16101/2016/2/17/PIC20160217103536361.jpg
     * link_url : http://www.baidu.com
     * relation_type : 1
     * relative_url : /2016/2/17/PIC20160217103536361.jpg
     */

    private List<ListEntity> list;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<ListEntity> getList() {
        return list;
    }

    public static class ListEntity {
        private int id;
        private Object relation_id;
        private String title;
        private String image_url;
        private String link_url;
        private int relation_type;
        private String relative_url;

        public void setId(int id) {
            this.id = id;
        }

        public void setRelation_id(Object relation_id) {
            this.relation_id = relation_id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public void setLink_url(String link_url) {
            this.link_url = link_url;
        }

        public void setRelation_type(int relation_type) {
            this.relation_type = relation_type;
        }

        public void setRelative_url(String relative_url) {
            this.relative_url = relative_url;
        }

        public int getId() {
            return id;
        }

        public Object getRelation_id() {
            return relation_id;
        }

        public String getTitle() {
            return title;
        }

        public String getImage_url() {
            return image_url;
        }

        public String getLink_url() {
            return link_url;
        }

        public int getRelation_type() {
            return relation_type;
        }

        public String getRelative_url() {
            return relative_url;
        }
    }
}
