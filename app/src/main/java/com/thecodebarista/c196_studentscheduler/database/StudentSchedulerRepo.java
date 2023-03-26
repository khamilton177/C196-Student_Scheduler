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
        StudentSchedulerDb db = StudentSchedulerDb.getDatabase(application);
        termDao = db.termDao();
        courseDao = db.courseDao();
        assessmentDao = db.assessmentDao();
        instructorDao = db.instructorDao();
    }

    public void insert(Term term) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            termDao.insert(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(Course course) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            courseDao.insert(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(Assessment assessment) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            assessmentDao.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(Instructor instructor) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            instructorDao.insert(instructor);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Term term) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            termDao.update(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Course course) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            courseDao.update(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Assessment assessment) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            assessmentDao.update(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Instructor instructor) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            instructorDao.update(instructor);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Term term) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            termDao.delete(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Course course) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            courseDao.delete(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Assessment assessment) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            assessmentDao.delete(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Instructor instructor) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            instructorDao.delete(instructor);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Term> getAllTerms() {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            allTerms = termDao.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allTerms;
    }

    public List<Course> getAllCourses() {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            allCourses = courseDao.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allCourses;
    }

    public List<Assessment> getAllAssessments() {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            allAssessments = assessmentDao.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allAssessments;
    }

    public List<Instructor> getAllInstructors() {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            allInstructors = instructorDao.getAllInstructors();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allInstructors;
    }

    public List<Course> getAllTermCourses(int termID) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            allCourses = courseDao.getAllTermCourses(termID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allCourses;
    }

    public List<Course> getCoursesByStatus(Course.CourseStatus courseStatus) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            allCourses = courseDao.getCoursesByStatus(courseStatus);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allCourses;
    }

    public List<Assessment> getAssessmentsByType(Assessment.AssessmentType type) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            allAssessments = assessmentDao.getAssessmentsByType(type);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allAssessments;
    }

    public List<Assessment> getAllCourseAssessments(int courseID) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            allAssessments = assessmentDao.getAllCourseAssessments(courseID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allAssessments;
    }

/*    public List<Instructor> getInstructorByCourse(int courseID) {
        StudentSchedulerRepo.dbExecutorWriterSvc.execute(() -> {
            allInstructors = instructorDao.getInstructorByCourse(courseID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allInstructors;
    }*/

}
