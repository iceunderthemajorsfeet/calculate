package com.example.demo.repository;

import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import jakarta.persistence.*;
import java.util.Map;

@Entity
@Table(name = "staff")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Integer month;
    @Column(name = "amountvacationday")
    private Integer amountVacationDay;
    @Column(name = "vacationday")
    private Integer vacationDay;
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<Integer, Long> salary;

    public User(Long id, String name, String email, Map<Integer, Long> salary, Integer vacationDay, Integer amountVacationDay, Integer month) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.vacationDay = vacationDay;
        this.amountVacationDay = amountVacationDay;
        this.month = month;
    }

    public  User(){

    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Map<Integer, Long> getSalary() {
        return salary;
    }
    public void setSalary(Map<Integer, Long> salary) {
        this.salary = salary;
    }

    public Integer getVacationDay() {
        return vacationDay;
    }
    public void setVacationDay(Integer vacationDay) {
        this.vacationDay = vacationDay;
    }

    public Integer getAmountVacationDay() {
        return amountVacationDay;
    }
    public void setAmountVacationDay(Integer amountVacationDay) {
        this.amountVacationDay = amountVacationDay;
    }

    public Integer getMonth() {
        return month;
    }
    public void setMonth(Integer month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", vacationDay=" + vacationDay +
                ", month=" + month +
                ", salary=" + salary +
                '}';
    }
}
