package com.staff.common.request;

import com.staff.common.pojo.Results;
import lombok.Data;

import java.util.List;

@Data
public class InputResultsRequest {

    private String scount;

    private List<Results> resultsList;
}
