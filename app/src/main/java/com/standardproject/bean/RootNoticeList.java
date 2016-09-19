package com.standardproject.bean;

import java.util.List;

public class RootNoticeList {


    /**
     * lastPage : true
     * pageSize : 20
     * pageNumber : 1
     * list : [{"id":1,"title":"eee","updated":null,"created":"2016-02-15 13:26:40","status":1,"is_top":0,"context":"eeeee<img src=\"http://127.0.0.1:16101/editor/2016/2/15/PIC20160215132637531.jpg\" alt=\"\" />","last_update_user_id":null,"status_str":"正常","create_dept_id":1,"create_user_id":1}]
     * firstPage : true
     * totalRow : 1
     * totalPage : 1
     */

    private PageEntity page;
    /**
     * page : {"lastPage":true,"pageSize":20,"pageNumber":1,"list":[{"id":1,"title":"eee","updated":null,"created":"2016-02-15 13:26:40","status":1,"is_top":0,"context":"eeeee<img src=\"http://127.0.0.1:16101/editor/2016/2/15/PIC20160215132637531.jpg\" alt=\"\" />","last_update_user_id":null,"status_str":"正常","create_dept_id":1,"create_user_id":1}],"firstPage":true,"totalRow":1,"totalPage":1}
     * success : true
     */

    private boolean success;

    public void setPage(PageEntity page) {
        this.page = page;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public PageEntity getPage() {
        return page;
    }

    public boolean isSuccess() {
        return success;
    }

    public static class PageEntity {
        private boolean lastPage;
        private int pageSize;
        private int pageNumber;
        private boolean firstPage;
        private int totalRow;
        private int totalPage;
        /**
         * id : 1
         * title : eee
         * updated : null
         * created : 2016-02-15 13:26:40
         * status : 1
         * is_top : 0
         * context : eeeee<img src="http://127.0.0.1:16101/editor/2016/2/15/PIC20160215132637531.jpg" alt="" />
         * last_update_user_id : null
         * status_str : 正常
         * create_dept_id : 1
         * create_user_id : 1
         */

        private List<ListEntity> list;

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public void setFirstPage(boolean firstPage) {
            this.firstPage = firstPage;
        }

        public void setTotalRow(int totalRow) {
            this.totalRow = totalRow;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public boolean isLastPage() {
            return lastPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public boolean isFirstPage() {
            return firstPage;
        }

        public int getTotalRow() {
            return totalRow;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity {
            private int id;
            private String title;
            private String updated;
            private String created;
            private int status;
            private int is_top;
            private String context;
            private String last_update_user_id;
            private String status_str;
            private int create_dept_id;
            private int create_user_id;

            public void setId(int id) {
                this.id = id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setUpdated(String updated) {
                this.updated = updated;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public void setIs_top(int is_top) {
                this.is_top = is_top;
            }

            public void setContext(String context) {
                this.context = context;
            }

            public void setLast_update_user_id(String last_update_user_id) {
                this.last_update_user_id = last_update_user_id;
            }

            public void setStatus_str(String status_str) {
                this.status_str = status_str;
            }

            public void setCreate_dept_id(int create_dept_id) {
                this.create_dept_id = create_dept_id;
            }

            public void setCreate_user_id(int create_user_id) {
                this.create_user_id = create_user_id;
            }

            public int getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getUpdated() {
                return updated;
            }

            public String getCreated() {
                return created;
            }

            public int getStatus() {
                return status;
            }

            public int getIs_top() {
                return is_top;
            }

            public String getContext() {
                return context;
            }

            public String getLast_update_user_id() {
                return last_update_user_id;
            }

            public String getStatus_str() {
                return status_str;
            }

            public int getCreate_dept_id() {
                return create_dept_id;
            }

            public int getCreate_user_id() {
                return create_user_id;
            }
        }
    }

}
