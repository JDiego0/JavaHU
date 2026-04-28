package com.riwi.legacy;

import java.util.Objects;

public class PerformanceReportPOJO {
    private int idEmployee;
    private double average;
    private String feedback;

    public PerformanceReportPOJO(int idEmployee, double average, String feedback) {
        this.idEmployee = idEmployee;
        this.average = average;
        this.feedback = feedback;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public double getAverage() {
        return average;
    }

    public String getFeedback() {
        return feedback;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof PerformanceReportPOJO other) {
            return idEmployee == other.idEmployee &&
                   average == other.average &&
                   feedback.equals(other.feedback);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(idEmployee, average, feedback);
    }

    @Override
    public String toString(){
        return "PerformanceReportPOJO{" +
                "idEmployee=" + idEmployee +
                ", average=" + average +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
