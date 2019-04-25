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
         System.in.read();
    }
}
