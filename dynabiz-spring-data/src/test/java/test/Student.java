package test;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Student {
    public Student() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long studentID;
    private String fullName;
    private boolean gender;

    public Student(long studentID, String fullName, boolean gender) {
        this.studentID = studentID;
        this.fullName = fullName;
        this.gender = gender;
    }

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
}
