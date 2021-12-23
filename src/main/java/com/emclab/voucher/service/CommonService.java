package com.emclab.voucher.service;

import com.emclab.voucher.service.dto.PaginationResponse;
import java.util.List;
import java.util.Map;

public interface CommonService {
    Map<String, Object> updateParam(Map<String, Object> param, int limit);

    PaginationResponse findWithPaging(Map<String, Object> param, int totalItems, List<Object> items);
}
