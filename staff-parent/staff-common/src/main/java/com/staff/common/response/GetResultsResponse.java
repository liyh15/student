package com.staff.common.response;

import com.staff.common.pojo.Results;
import lombok.Data;

import java.util.List;

/**
 * 获取成绩
 */
@Data
public class GetResultsResponse extends BaseResponse<GetResultsResponse> {

    private Integer pageTotal;

    private List<Results> resultsList;
}
