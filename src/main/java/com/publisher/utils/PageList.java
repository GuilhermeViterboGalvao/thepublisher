package com.publisher.utils;

import org.apache.struts2.util.Counter;

public class PageList extends Counter {

	private static final long serialVersionUID = 425538468091499126L;

	public PageList() {
		update();
	}
	
    private int selectedPage = 1;
    
    private int numberOfPages = 100;
    
    private int pagesToShow = 11;

    private void update() {
        int f = getSelectedPage() - (getPagesToShow() - 1) / 2;
        if (f <= 0) {
        	f = 1;
        }
        int l = f + getPagesToShow();
        if (l > getNumberOfPages()) {
            l = getNumberOfPages();
            f = getNumberOfPages() - getPagesToShow();
            if (f <= 0) {
            	f = 1;
            }
        }
        this.setFirst(f);
        this.setLast(l);
    }

    public int getSelectedPage() {
        return selectedPage;
    }

    public void setSelectedPage(int selectedPage) {
        this.selectedPage = selectedPage;
        update();
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
        update();
    }

    public int getPagesToShow() {
        return pagesToShow;
    }

    public void setPagesToShow(int pagesToShow) {
        this.pagesToShow = pagesToShow;
        update();
    } 
}