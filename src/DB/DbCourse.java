/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Model.AppFactory;
import Model.Course;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BaDlUcK
 */
public class DbCourse {

   
    DBConnection dBConnection = DBConnection.getInstance();
    Connection con = dBConnection.getCon();
    PreparedStatement ps;
    ResultSet rs;

    //Course Queries
    private final String CREATE_COURSE = "insert into courses (name,capacity,teacher_id) values (?,?,?)";
    private final String UPDATE_COURSE = "update courses set name = ? ,capacity = ?,teacher_id = ? where id = ?";
    private final String DELETE_COURSE = "delete from courses where id = ? ";
    private final String GET_ALL_COURSES = "select * from courses";

    private final String GET_COURSE = "select * from courses where id = ?";
    private final String GET_TEACHER_COURSES = "select * from courses where teacher_id = ?";
    private final String GET_COUNT_COURSES = "select count(*) from courses";

    private final String GET_COURSE_STUDENTS = "select * from users_courses where course_id = ?";

    // enroll user in courses queries
    private final String REGISTER_STUDENT_COURSE = "insert into users_courses (user_id,course_id,grade)values(?,?,?)";
    private final String SET_STUDENT_GRADE = "update users_courses set grade = ? where user_id = ? and course_id = ?";
    private final String DELETE_STUDENT_COURSE = "delete from users_courses where user_id = ? and course_id = ? ";

    private final String GET_STUDENT_COURSES = "select * from courses,users_courses where courses.id = users_courses.course_id and user_id =?; ";

    private final String GET_AVALIABLE_COURSES = "SELECT * FROM courses WHERE courses.id NOT IN ( SELECT course_id FROM users_courses INNER JOIN courses ON courses.id = users_courses.course_id AND user_id = ?)";

    //number of students queries
    private final String INC_NUM_OF_STUDENTS = "update courses set num_of_students = num_of_students+1 where id = ?";
    private final String DEC_NUM_OF_STUDENTS = "update courses set num_of_students = num_of_students-1 where id = ?";

    public int create_course(Course course) {
        int affectedRows = -1;
        try {
            ps = con.prepareStatement(CREATE_COURSE);
            ps.setString(1, course.getCourse_name());
            ps.setDouble(2, course.getCapacity());
            ps.setInt(3, course.getTeacher_id());
            affectedRows = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DbCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return affectedRows;
    }

    public Course get_course(int id) {
        try {
            ps = con.prepareStatement(GET_COURSE);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.first()) {
                return new Course(rs.getInt("id"), rs.getString("name"), rs.getInt("num_of_students"), rs.getInt("capacity"), rs.getInt("teacher_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        try {
            ps = con.prepareStatement(GET_ALL_COURSES);
            rs = ps.executeQuery();
            while (rs.next()) {
                courses.add(new Course(rs.getInt("id"), rs.getString("name"), rs.getInt("num_of_students"), rs.getInt("capacity"), rs.getInt("teacher_id")));

            }
        } catch (SQLException ex) {
            Logger.getLogger(DbCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return courses;
    }

    public int delete_course(int id) {
        int affectedRows = -1;
        try {
            ps = con.prepareStatement(DELETE_COURSE);
            ps.setInt(1, id);
            affectedRows = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DbCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return affectedRows;
    }

    public int update_course(Course course) {
        int affectedRows = -1;
        try {
            ps = con.prepareStatement(UPDATE_COURSE);
            ps.setString(1, course.getCourse_name());
            ps.setInt(2, course.getCapacity());
            ps.setInt(3, course.getTeacher_id());
            ps.setInt(4, course.getId());
            affectedRows = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DbCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return affectedRows;
    }

    public ArrayList<Course> getTeacherCourses(int id) {
        ArrayList<Course> courses = new ArrayList<>();
        try {
            ps = con.prepareStatement(GET_TEACHER_COURSES);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                courses.add(new Course(rs.getInt("id"), rs.getString("name"), rs.getInt("num_of_students"), rs.getInt("capacity"), rs.getInt("teacher_id")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return courses;
    }

    public ArrayList<User> getCourseStudents(Course course) {
        ArrayList<User> coursesStudents = new ArrayList<>();
        DbUser dbUser = new DbUser();
        try {
            ps = con.prepareStatement(GET_COURSE_STUDENTS);
            ps.setInt(1, course.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                User courseStudent = dbUser.getUser(rs.getInt("user_id"));
                courseStudent.setGrade(rs.getDouble("grade"));
                coursesStudents.add(courseStudent);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return coursesStudents;
    }

    public int get_count_courses() {
        try {
            ps = con.prepareStatement(GET_COUNT_COURSES);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int register_student_course(User student, Course course) {
        int affectedRows = -1;
        try {
            ps = con.prepareStatement(REGISTER_STUDENT_COURSE);
            ps.setInt(1, student.getId());
            ps.setInt(2, course.getId());
            ps.setDouble(3, 0);
            affectedRows = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DbUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return affectedRows;
    }

    public int set_student_grade(int student_id, Course course, double grade) {
        int affectedRows = -1;
        try {
            ps = con.prepareStatement(SET_STUDENT_GRADE);
            ps.setDouble(1, grade);
            ps.setInt(2, student_id);
            ps.setDouble(3, course.getId());
            affectedRows = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DbUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return affectedRows;
    }

    public int delete_student_course(int student_id, Course course) {
        int affectedRows = -1;
        try {
            ps = con.prepareStatement(DELETE_STUDENT_COURSE);
            ps.setInt(1, student_id);
            ps.setDouble(2, course.getId());
            affectedRows = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DbUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return affectedRows;
    }

    public ArrayList<Course> get_student_courses(User user) {
        ArrayList<Course> studentsCourses = new ArrayList<>();
        try {
            ps = con.prepareStatement(GET_STUDENT_COURSES);
            ps.setInt(1, user.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                studentsCourses.add(new Course(rs.getInt("id"), rs.getString("name"), rs.getInt("num_of_students"), rs.getInt("capacity"), rs.getInt("teacher_id")));

            }
        } catch (SQLException ex) {
            Logger.getLogger(DbCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return studentsCourses;
    }

    public HashMap<String, Double> getHashMapStudentCourses(User user) {
        HashMap<String, Double> studentCourses = new HashMap<>();
        try {
            ps = con.prepareStatement(GET_STUDENT_COURSES);
            ps.setInt(1, user.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                studentCourses.put(rs.getString("name"), rs.getDouble("grade"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return studentCourses;
    }

    public ArrayList<Course> get_avaliable_courses(User user) {
        ArrayList<Course> avaCourses = new ArrayList<>();
        try {
            ps = con.prepareStatement(GET_AVALIABLE_COURSES);
            ps.setInt(1, user.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                avaCourses.add(new Course(rs.getInt("id"), rs.getString("name"), rs.getInt("num_of_students"), rs.getInt("capacity"), rs.getInt("teacher_id")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return avaCourses;
    }

    public int inc_num_of_students(Course course) {
        int affectedRows = -1;
        try {
            ps = con.prepareStatement(INC_NUM_OF_STUDENTS);
            ps.setInt(1, course.getId());
            affectedRows = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DbCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return affectedRows;
    }

    public int dec_num_of_students(Course course) {
        int affectedRows = -1;
        try {
            ps = con.prepareStatement(DEC_NUM_OF_STUDENTS);
            ps.setInt(1, course.getId());
            affectedRows = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DbCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return affectedRows;
    }

}
