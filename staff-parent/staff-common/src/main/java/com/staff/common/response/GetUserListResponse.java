package com.staff.common.response;
import com.staff.common.pojo.User;
import lombok.Data;

import java.util.List;

@Data
public class GetUserListResponse extends BaseResponse<GetUserListResponse> {

    private Integer pageTotal;

    private List<User> userList;
}
