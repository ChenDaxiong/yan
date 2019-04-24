package com.hfut.bs.course;

import com.hfut.bs.course.domain.Course;
import com.hfut.bs.course.domain.CourseSection;
import com.hfut.bs.course.model.CourseInfoModel;
import com.hfut.bs.course.model.CourseQueryParam;
import com.hfut.bs.course.service.CourseSectionServiceImpl;
import com.hfut.bs.course.service.CourseServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class CourseProvider
{
    public static void main( String[] args ) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.start();
        CourseServiceImpl courseService = (CourseServiceImpl) context.getBean("courseService");
        CourseSectionServiceImpl courseSectionService = (CourseSectionServiceImpl) context.getBean("courseSectionService");
        CourseQueryParam param = new CourseQueryParam();
//        CourseInfoModel course = courseService.getById(1);
        List<Course> result = courseService.queryList(param);
//        CourseSection courseSection = courseSectionService.getById(1);
        System.in.read();
    }
}
