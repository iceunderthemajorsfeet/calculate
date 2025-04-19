package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

public class UserSalaryCalculate {
    private static final double AVERAGE_MONTH_DAYS = 29.3;

    public static double calculate(int month, Map<Integer, Long> salary, int vacationDay) {

        validateInput(month, salary, vacationDay);
        Map<Integer, Long> calculationPeriod = getCalculationPeriod(month, salary);
        double averageSalary = calculateAverageSalary(calculationPeriod);
        return calculatePayment(averageSalary, vacationDay);
    }


    private static void validateInput(int month, Map<Integer, Long> salary, int vacationDay) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Месяц должен быть от 1 до 12");
        }

        if (salary == null || salary.size() != 24) {
            throw new IllegalArgumentException("Необходимо предоставить зарплату за 24 месяца");
        }

        if (vacationDay <= 0) {
            throw new IllegalArgumentException("Количество дней отпуска должно быть положительным");
        }
    }

    private static Map<Integer, Long> getCalculationPeriod(int month, Map<Integer, Long> salary) {
        Map<Integer, Long> period = new HashMap<>();

        for (int i = 0; i < 12; i++) {
            int monthIndex = month - i;
            if (monthIndex < 1) {
                monthIndex += 12;
            }

            int salaryKey = monthIndex + 12;
            Long salaryValue = salary.get(salaryKey);

            if (salaryValue == null) {
                throw new IllegalStateException("Отсутствует зарплата за месяц " + salaryKey);
            }

            period.put(monthIndex, salaryValue);
        }

        return period;
    }

    private static double calculateAverageSalary(Map<Integer, Long> period) {
        return period.values().stream()
                .mapToLong(Long::longValue)
                .average()
                .orElseThrow(() -> new IllegalStateException("Ошибка расчета средней зарплаты"));
    }

    private static double calculatePayment(double averageSalary, int vacationDay) {
        return (averageSalary / AVERAGE_MONTH_DAYS) * vacationDay;
    }
}




