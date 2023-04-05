package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MoimTest {
    @Test
    public void isNotFull() {
        Moim moim = new Moim();
        moim.maxNumberOfAttendees = 100;
        moim.numberOfEnrollment = 10;
        Assertions.assertFalse(moim.isEnrollmentFull());
    }

    @Test
    public void isEmpty() {
        Moim moim = new Moim();
        moim.maxNumberOfAttendees = 0;
        moim.numberOfEnrollment = 0;
        Assertions.assertFalse(moim.isEnrollmentFull());
    }

    @Test
    public void isFull() {
        Moim moim = new Moim();
        moim.maxNumberOfAttendees = 10;
        moim.numberOfEnrollment = 10;
        Assertions.assertTrue(moim.isEnrollmentFull());
    }
}