package com.example.demo.repository;


import jakarta.persistence.*;
//import javax.persistence.*;
import java.util.HashMap;

@Entity
@Table(name = "staff")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(name = "name")
    private String name;
    private String email;
    //private Integer salary;
    private Integer month;
    @Column(name = "amountvacationday")
    private Integer amountVacationDay;
    @Column(name = "vacationday")
    private Integer vacationDay;

    @Column(columnDefinition = "JSON")
    @Convert(converter = HashMapToJsonConverter.class)
    private HashMap<Integer, Long> salary;


    public User(Long id, String name, String email, HashMap<Integer, Long> salary, Integer vacationDay, Integer amountVacationDay, Integer month) {
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

    public HashMap<Integer, Long> getSalary() {
        return salary;
    }
    public void setSalary(HashMap<Integer, Long> salary) {
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
                ", amountVacationDay=" + amountVacationDay +
                '}';
    }
}
