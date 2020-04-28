package com.staff.common.response;

import com.staff.common.pojo.Course;
import lombok.Data;

import java.util.List;

@Data
public class GetCourseListResponse extends BaseResponse<GetCourseListResponse> {

    private List<Course> courseList;
}
