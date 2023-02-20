package com.baeldung.drools.optaplanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import java.util.Arrays;

public class OptaPlannerUnitTest {

    static CourseSchedule unsolvedCourseSchedule;

    @BeforeAll
    public static void setUp() {

        unsolvedCourseSchedule = new CourseSchedule();

        for(int i = 0; i < 10; i++){
            unsolvedCourseSchedule.getLectureList().add(new Lecture());
        }

        unsolvedCourseSchedule.getPeriodList().addAll(Arrays.asList(new Integer[] { 1, 2, 3 }));
        unsolvedCourseSchedule.getRoomList().addAll(Arrays.asList(new Integer[] { 1, 2 }));
    }

    @Test
    public void test_whenCustomJavaSolver() {

        SolverFactory<CourseSchedule> solverFactory = SolverFactory.createFromXmlResource("courseScheduleSolverConfiguration.xml");
        Solver<CourseSchedule> solver = solverFactory.buildSolver();
        CourseSchedule solvedCourseSchedule = solver.solve(unsolvedCourseSchedule);

        Assertions.assertNotNull(solvedCourseSchedule.getScore());
        Assertions.assertEquals(-4, solvedCourseSchedule.getScore().getHardScore());
    }

    @Test
    public void test_whenDroolsSolver() {

        SolverFactory<CourseSchedule> solverFactory = SolverFactory.createFromXmlResource("courseScheduleSolverConfigDrools.xml");
        Solver<CourseSchedule> solver = solverFactory.buildSolver();
        CourseSchedule solvedCourseSchedule = solver.solve(unsolvedCourseSchedule);

        Assertions.assertNotNull(solvedCourseSchedule.getScore());
        Assertions.assertEquals(0, solvedCourseSchedule.getScore().getHardScore());
    }
}
