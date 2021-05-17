package org.example.web.util.page;

import com.baomidou.mybatisplus.core.metadata.IPage;

public class PageUtils {

    /**
     * 获取过滤字段的值
     * @param filterName
     * @return
     */
    public static String getColumnFilterValue(PageRequest pageRequest,
                                              String filterName) {
        String value = null;
        ColumnFilter columnFilter = pageRequest.getColumnFilter(filterName);
        if(columnFilter != null) {
            value = columnFilter.getValue();
        }
        return value;
    }

    //IPAGE转换为返回的PageResult
    public static PageResult getPageResult (IPage iPage) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNum((int)iPage.getCurrent());
        pageResult.setPageSize((int)iPage.getSize());
        pageResult.setTotalSize(iPage.getTotal());
        pageResult.setTotalPages((int)iPage.getPages());
        pageResult.setContent(iPage.getRecords());
        return pageResult;

    }

}
