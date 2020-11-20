package com.dwict.jfmc.client.com.model;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Paging {

	private int pageIndex;

	private int pageSize;

	private int startRow;

	private List<?> resultList;

	private int totalCount;

	public void handlePaging(int pageIndex, int pageSize) {
		this.pageIndex = pageIndex < 1 ? 1 : pageIndex;
		this.pageSize = pageSize;
		this.startRow = ((pageIndex-1) * pageSize);
	}

	public void handlePagingList(List<?> resultList, int totalCount) {
		this.resultList = resultList;
		this.totalCount = totalCount;
	}

}
