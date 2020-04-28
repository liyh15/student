package com.staff.common.request;

import lombok.Data;

@Data
public class GetResultsRequest {

    private Integer pageNo;

    // 课程编号
    private String ccount;

    // 学号
    private String scount;
}
