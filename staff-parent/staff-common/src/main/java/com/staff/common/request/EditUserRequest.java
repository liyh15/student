package com.staff.common.request;

import com.staff.common.pojo.StuCourse;
import com.staff.common.pojo.User;
import lombok.Data;

import java.util.List;

@Data
public class EditUserRequest extends User {

    private List<StuCourse> stuCourseList;
}
