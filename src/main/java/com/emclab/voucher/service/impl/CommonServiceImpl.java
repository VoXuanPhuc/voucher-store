package com.emclab.voucher.service.impl;

import com.emclab.voucher.service.CommonService;
import com.emclab.voucher.service.dto.PaginationResponse;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl implements CommonService {

    @Override
    public Map<String, Object> updateParam(Map<String, Object> param, int limit) {
        if (!param.containsKey("page")) param.put("page", 1);

        if (!param.containsKey("limit")) param.put("limit", limit);

        // if(param.containsKey("sort_by")) {
        // String sql = param.get("sort_by").toString().replace(".", " ");
        // param.put("sort_by", sql);
        // }

        return param;
    }

    @Override
    public PaginationResponse findWithPaging(Map<String, Object> param, int totalItems, List<Object> items) {
        int page = Integer.parseInt(param.get("page").toString());
        int limit = Integer.parseInt(param.get("limit").toString());

        int totalPages = (int) (Math.ceil((double) totalItems / limit));

        PaginationResponse response = new PaginationResponse();

        response.setTotalItems(totalItems);
        response.setItemPerPage(limit);
        response.setCurrentItemCount(items.size());
        response.setCurrentPage(items.size());
        response.setTotalPages(totalPages);
        response.setCurrentPage(page);
        response.setItems(items);

        return response;
    }
}
