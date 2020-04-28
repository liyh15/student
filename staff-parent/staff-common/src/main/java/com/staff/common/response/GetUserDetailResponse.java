package com.staff.common.response;

import com.staff.common.dto.GetUserDetailDTO;
import lombok.Data;

@Data
public class GetUserDetailResponse extends BaseResponse<GetUserDetailResponse> {

    private GetUserDetailDTO getUserDetailDTO;
}
