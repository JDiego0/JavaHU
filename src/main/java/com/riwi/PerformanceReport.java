package com.riwi;

public record PerformanceReport(int idEmployee , double average, String feedback) {
    // Custom constructor for validation
    public PerformanceReport {
        if (idEmployee <= 0) {
            throw new IllegalArgumentException("Employee ID must be positive");
        }
        if (average < 0 || average > 100) {
            throw new IllegalArgumentException("Average must be between 0 and 100");
        }
        if (feedback == null || feedback.trim().isEmpty()) {
            throw new IllegalArgumentException("Feedback can not be null or empty");
        }
    }

    //Static method of factory for creating end of month reports
    public static PerformanceReport createEndOfMonthReport(int idEmployee, double Average, String feedback) {
        return new PerformanceReport(idEmployee, Average, "[END OF MONTH] " + feedback);
    }

    // Method for determining the level of performance
    public String getLevelPerformance() {
        if (average >= 90) return "Excelent";
        if (average >= 80) return "Good";
        if (average >= 70) return "Acceptable";
        return "Needs improvement";
    }
}
