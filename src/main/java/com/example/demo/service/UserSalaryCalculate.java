package com.example.demo.service;

import com.example.demo.CalculacteSalaryApplication;

import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class UserSalaryCalculate {
    private static final double AVERAGE_MONTH_DAYS = 29.3;

    /**
     * Рассчитывает сумму отпускных
     * @param month месяц расчета (1-12)
     * @param salary зарплата за 24 месяца (1-24)
     * @param vacationDay количество дней отпуска
     * @return сумма отпускных
     */
    public static double calculate(int month, HashMap<Integer, Long> salary, int vacationDay) {
        // Валидация входных данных
        validateInput(month, salary, vacationDay);

        // Получаем 12 месяцев для расчета
        Map<Integer, Long> calculationPeriod = getCalculationPeriod(month, salary);

        // Рассчитываем среднюю зарплату
        double averageSalary = calculateAverageSalary(calculationPeriod);

        // Рассчитываем сумму отпускных
        return calculatePayment(averageSalary, vacationDay);
    }


    private static void validateInput(int month, HashMap<Integer, Long> salary, int vacationDay) {
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

    private static Map<Integer, Long> getCalculationPeriod(int month, HashMap<Integer, Long> salary) {
        Map<Integer, Long> period = new HashMap<>();

        for (int i = 0; i < 12; i++) {
            int monthIndex = month - i;
            if (monthIndex < 1) {
                monthIndex += 12;
            }

            // Берем данные из второй половины (13-24 месяцы)
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




