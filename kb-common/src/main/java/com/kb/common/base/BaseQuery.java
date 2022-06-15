package com.kb.common.base;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-06-14 - 21:25
 */
public class BaseQuery {
    /**
     * 当前页,默认1
     */
    private Integer page=1;
    /**
     * 每页显示的条数,默认10
     */
    private Integer limit=10;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
