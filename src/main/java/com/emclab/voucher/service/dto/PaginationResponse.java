package com.emclab.voucher.service.dto;

import java.util.ArrayList;
import java.util.List;

public class PaginationResponse {

    private int totalItems;
    private int itemPerPage;
    private int currentItemCount;

    private int totalPages;
    private int currentPage;

    private List<Object> items = new ArrayList<>();

    public PaginationResponse() {}

    public PaginationResponse(int totalItems, int itemPerPage, int currentItemCount, int totalPages, int currentPage, List<Object> items) {
        this.totalItems = totalItems;
        this.itemPerPage = itemPerPage;
        this.currentItemCount = currentItemCount;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.items = items;
    }

    public int getTotalItems() {
        return this.totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getItemPerPage() {
        return this.itemPerPage;
    }

    public void setItemPerPage(int itemPerPage) {
        this.itemPerPage = itemPerPage;
    }

    public int getCurrentItemCount() {
        return this.currentItemCount;
    }

    public void setCurrentItemCount(int currentItemCount) {
        this.currentItemCount = currentItemCount;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<Object> getItems() {
        return this.items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }
}
