package com.example.demo.service;

import com.example.demo.repository.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User create(User user){
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            throw new IllegalStateException("пользователь с таким email уже существует");
        }

        if (user.getSalary() == null) {
            throw new IllegalArgumentException("Зарплата не указана");
        }

        if (user.getMonth() == null || user.getMonth() < 1 || user.getMonth() > 12) {
            throw new IllegalArgumentException("Текущий месяц должен быть от 1 до 12");
        }

        user.setAmountVacationDay((int) UserSalaryCalculate.calculate(
                user.getMonth(),
                user.getSalary(), // Теперь принимает Map
                user.getVacationDay()
        ));
        return userRepository.save(user);
    }


    public void delete(Long id){

        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            throw new IllegalStateException("пользователя с айди:" + id + " нет");
        }
        userRepository.deleteById(id);
    }

    public void update(Long id, String email, String name, Map<Integer, Long> salary, Integer vacationDay){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            throw new IllegalStateException("пользователя с айди:" + id + " нет");
        }
        User user = optionalUser.get();
        if (email != null && !email.equals(user.getEmail())) {
            Optional<User> foundByEmail = userRepository.findByEmail(email);
            if(foundByEmail.isPresent()){
                throw new IllegalStateException("пользователь с таким email уже существует");
            }
            user.setEmail(email);
        }
        if (name != null && !name.equals(user.getName())) {
            user.setName(name);
        }

        if (salary != null && !salary.equals(user.getSalary())) {
            Map<Integer, Long> salaryCopy = new HashMap<>(salary);
            user.setSalary(salaryCopy);
        }

        if (vacationDay != null && !vacationDay.equals(user.getVacationDay())) {
            user.setVacationDay(vacationDay);
        }
        userRepository.save(user);
    }
}
