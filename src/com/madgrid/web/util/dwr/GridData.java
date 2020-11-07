package com.madgrid.web.util.dwr;

public class GridData{
	String gridHtml;
	String shortInfo;
	Boolean isInPartialWin;
	Integer partialWinSeconds;
	Integer partialWinUserId;
	Integer id;
	
	public Integer getPartialWinSeconds() {
		return partialWinSeconds;
	}
	public void setPartialWinSeconds(Integer partialWinSeconds) {
		this.partialWinSeconds = partialWinSeconds;
	}
	public String getGridHtml() {
		return gridHtml;
	}
	public void setGridHtml(String gridHtml) {
		this.gridHtml = gridHtml;
	}
	public String getShortInfo() {
		return shortInfo;
	}
	public void setShortInfo(String shortInfo) {
		this.shortInfo = shortInfo;
	}
	public Boolean getIsInPartialWin() {
		return isInPartialWin;
	}
	public void setIsInPartialWin(Boolean isInPartialWin) {
		this.isInPartialWin = isInPartialWin;
	}
	public Integer getPartialWinUserId() {
		return partialWinUserId;
	}
	public void setPartialWinUserId(Integer partialWinUserId) {
		this.partialWinUserId = partialWinUserId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}