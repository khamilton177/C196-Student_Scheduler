package com.thecodebarista.c196_studentscheduler.database;

import android.app.Application;

import com.thecodebarista.c196_studentscheduler.dao.AssessmentDao;
import com.thecodebarista.c196_studentscheduler.dao.CourseDao;
import com.thecodebarista.c196_studentscheduler.dao.InstructorDao;
import com.thecodebarista.c196_studentscheduler.dao.TermDao;
import com.thecodebarista.c196_studentscheduler.entities.Assessment;
import com.thecodebarista.c196_studentscheduler.entities.Course;
import com.thecodebarista.c196_studentscheduler.entities.Instructor;
import com.thecodebarista.c196_studentscheduler.entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StudentSchedulerRepo {
    private TermDao termDao;
    private CourseDao courseDao;
    private AssessmentDao assessmentDao;
    private InstructorDao instructorDao;

    private List<Term> allTerms;
    private List<Course> allCourses;
    private List<Assessment> allAssessments;
    private List<Instructor> allInstructors;

    /**
     * Allowed Thread Pool count.
     */
    private static int NUMBER_OF_THREADS = 4;

    /**
     * The database write executor service.
     */
    static final ExecutorService dbExecutorWriterSvc = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Instantiate the StudentSchedulerRepo Repository.
     * @param application the Student Scheduler Application.
     */
    public StudentSchedulerRepo(Application application) {
        StudentSchedulerDb db = StudentSchedulerDb.getInstance(application);
        termDao = db.termDao();
        courseDao = db.courseDao();
        assessmentDao = db.assessmentDao();
        instructorDao = db.instructorDao();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(Term term) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            termDao.insert(term);
        });
    }

    public void insert(Course course) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            courseDao.insert(course);
        });
    }

    public void insert(Assessment assessment) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            assessmentDao.insert(assessment);
        });
    }

    public void insert(Instructor instructor) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            instructorDao.insert(instructor);
        });
    }

    public void update(Term term) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            termDao.update(term);
        });
    }

    public void update(Course course) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            courseDao.update(course);
        });
    }

    public void update(Assessment assessment) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            assessmentDao.update(assessment);
        });
    }

    public void update(Instructor instructor) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            instructorDao.update(instructor);
        });
    }

    public void delete(Term term) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            termDao.insert(term);
        });
    }

    public void delete(Course course) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            courseDao.insert(course);
        });
    }

    public void delete(Assessment assessment) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            assessmentDao.insert(assessment);
        });
    }

    public void delete(Instructor instructor) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            instructorDao.insert(instructor);
        });
    }

    public List<Term> getAllTerms() {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            allTerms = termDao.getAllTerms();
        });
        return allTerms;
    }

    public List<Course> getAllCourses() {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            allCourses = courseDao.getAllCourses();
        });
        return allCourses;
    }

    public List<Assessment> getAllAssessments() {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            allAssessments = assessmentDao.getAllAssessments();
        });
        return allAssessments;
    }

    public List<Instructor> getAllInstructors() {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            allInstructors = instructorDao.getAllInstructors();
        });
        return allInstructors;
    }

    public List<Course> getAllTermCourses(int termID) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            allCourses = courseDao.getAllTermCourses(termID);
        });
        return allCourses;
    }

    public List<Course> getCoursesByStatus(Course.CourseStatus courseStatus) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            allCourses = courseDao.getCoursesByStatus(courseStatus);
        });
        return allCourses;
    }

    public List<Assessment> getAssessmentsByType(Assessment.AssessmentType type) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            allAssessments = assessmentDao.getAssessmentsByType(type);
        });
        return allAssessments;
    }

    public List<Assessment> getAllCourseAssessments(int courseID) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            allAssessments = assessmentDao.getAllCourseAssessments(courseID);
        });
        return allAssessments;
    }

    public List<Instructor> getInstructorByCourse(int courseID) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            allInstructors = instructorDao.getInstructorByCourse(courseID);
        });
        return allInstructors;
    }
}
