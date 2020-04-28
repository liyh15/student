package com.staff.common.dto;

import com.staff.common.pojo.Course;
import com.staff.common.pojo.StuCourse;
import com.staff.common.pojo.User;
import lombok.Data;

import java.util.List;

@Data
public class GetUserDetailDTO extends User {

    private List<StuCourse> stuCourseList;
}
