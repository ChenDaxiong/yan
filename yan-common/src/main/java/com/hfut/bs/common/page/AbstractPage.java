package com.hfut.bs.common.page;

import com.hfut.bs.common.utils.BeanUtil;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public abstract class AbstractPage<E> implements Page<E> {

//	默认的起始页为1
    public static final int DEFAULT_FIRST_PAGE_NUM = 1;
//    默认每页大小为10
    public static final int DEFAULT_PAGE_SIZE = 10;

    protected int pageSize = DEFAULT_PAGE_SIZE;

    protected int pageNum = DEFAULT_FIRST_PAGE_NUM;
    
    protected int itemsTotalCount = 0;//总记录数
    protected int pageTotalCount = 0;//总页数
    protected List<E> items;
    protected boolean firstPage;//是否是第一页,boolean类型系统默认值是false;
    protected boolean lastPage;//是否是最后一页,boolean类型系统默认值是false;
    protected int startIndex;
    
    private String sortField="update_time";//排序
	private String sortDirection = "DESC";//排序方向
    
	
    @Override
    public int getFirstPageNum() {
        return DEFAULT_FIRST_PAGE_NUM;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        if (pageNum < DEFAULT_FIRST_PAGE_NUM) pageNum = DEFAULT_FIRST_PAGE_NUM;
        this.pageNum = pageNum;
    }

    @Override
    public List<E> getItems() {
        return items;
    }

    public void setItems(Collection<E> items) {
        if (items == null) items = Collections.emptyList();
        this.items = new ArrayList<E>(items);
        this.lastPage = this.pageNum == this.pageTotalCount;
        this.firstPage = this.pageNum == DEFAULT_FIRST_PAGE_NUM;
    }

    @Override
    public boolean isFirstPage() {
    	firstPage = (getPageNum() <= getFirstPageNum());
    	return firstPage;
    }

    @Override
    public boolean isLastPage() {
        return lastPage;
    }

    public int getPrePageNum() {
        return isFirstPage() ? getFirstPageNum() : getPageNum() - 1;
    }

    public int getNextPageNum() {
        return isLastPage() ? getPageNum() : getPageNum() + 1;
    }

    @Override
    public Iterator<E> iterator() {
        return this.items.iterator();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }


	public void setItemsTotalCount(int itemsTotalCount) {
		this.itemsTotalCount = itemsTotalCount;
		if (itemsTotalCount % this.pageSize == 0) {
			this.pageTotalCount = itemsTotalCount / this.pageSize;
		} else {
			this.pageTotalCount = itemsTotalCount / this.pageSize + 1;
		}
		if (this.pageNum > this.pageTotalCount) {
			this.pageNum = DEFAULT_FIRST_PAGE_NUM;
		}
		if (pageNum == DEFAULT_FIRST_PAGE_NUM) {
			firstPage = true;
		}
		if (this.itemsTotalCount <= this.pageSize || pageNum == pageTotalCount) {
			this.lastPage = true;
		}
	}
	
	@Override
	public int getItemsTotalCount() {
		return itemsTotalCount;
	}
    
	@Override
	public int getLastPageNum() {
		return itemsTotalCount;
	}
	
	public int getStartIndex() {
		this.startIndex = (this.pageNum - 1) * this.pageSize;
		if(this.startIndex <= 0){
			this.startIndex = 0;
		}
		return this.startIndex;
	}
	
	/**
	 * 按照sortField升序
	 * @param sortField：指java bean中的属性
	 */
	public void ascSortField(String sortField) {
		if(StringUtils.isNotEmpty(sortField)){
			this.sortField = BeanUtil.fieldToColumn(sortField);
			this.sortDirection = " ASC ";
		}
	}
	
	/**
	 * 按照sortField降序
	 * @param sortField ：指java bean中的属性
	 */
	public void descSortField(String sortField) {
		if(StringUtils.isNotEmpty(sortField)){
			this.sortField = BeanUtil.fieldToColumn(sortField);
			this.sortDirection = " DESC ";
		}
	}
	
	public String getSortDirection() {
		return sortDirection;
	}
	
	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}
	
	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	@Override
    public String toString() {
        return "Page[" + this.getPageNum() + "]:" + items.toString();
    }

	
}
