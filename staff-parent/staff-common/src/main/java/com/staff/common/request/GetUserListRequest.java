package com.staff.common.request;

import lombok.Data;

@Data
public class GetUserListRequest {
    // 页数
    private Integer pageNo;

    // 类型
    private String type;

    // 编号
    private String count;
}
