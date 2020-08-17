package com.flowable.core.utils;

import com.github.pagehelper.Page;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class PagerModel<T> implements Serializable {
    private static final long serialVersionUID = 4804053559968742915L;
    private long total;
    private List<T> data = new ArrayList();
    private List<T> rows = new ArrayList();

    public PagerModel(List<T> list) {
        if (list instanceof Page) {
            Page<T> page = (Page)list;
            this.data = page;
            this.total = page.getTotal();
        } else if (list instanceof Collection) {
            this.data = list;
            this.total = (long)list.size();
        }

    }

    public PagerModel(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
        this.data = rows;
    }
}
