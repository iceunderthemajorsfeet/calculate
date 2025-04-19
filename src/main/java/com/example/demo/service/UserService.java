package com.example.demo.service;

import com.example.demo.repository.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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

        user.setAmountVacationDay((int) UserSalaryCalculate.calculate(user.getMonth(), user.getSalary(), user.getVacationDay()));
        //user.setAge(Period.between(user.getBirthday(), LocalDate.now()).getYears());
        return userRepository.save(user);
    }

//public User create(UserRequest request) {
//    // Валидация
//    if (request.getSalary() == null || request.getSalary().size() != 24) {
//        throw new IllegalArgumentException("Должна быть указана зарплата за 24 месяца");
//    }
//
//    if (request.getCurrentMonth() == null || request.getCurrentMonth() < 1 || request.getCurrentMonth() > 12) {
//        throw new IllegalArgumentException("Текущий месяц должен быть от 1 до 12");
//    }
//
//    // Проверка email
//    userRepository.findByEmail(request.getEmail())
//            .ifPresent(u -> {
//                throw new IllegalStateException("Пользователь с таким email уже существует");
//            });
//
//    // Создание пользователя
//    User user = new User();
//    user.setName(request.getName());
//    user.setEmail(request.getEmail());
//    user.setMonth(request.getMonth());
//    user.setSalary(request.getSalary());
//
//    // Расчет отпускных
//    int vacationDays = VacationCalculator.calculate(
//            request.getCurrentMonth(),
//            request.getSalary()
//    );
//    user.setAmountVacationDay(vacationDays);
//
//    return userRepository.save(user);
//}

    public void delete(Long id){

        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            throw new IllegalStateException("пользователя с айди:" + id + " нет");
        }
        userRepository.deleteById(id);
    }


    public void update(Long id, String email, String name, HashMap<Integer, Long> salary, Integer vacationDay){
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

//        if (salary != null && !salary.equals(user.getSalary())) {
//            user.setSalary(salary);
//        }
        if (salary != null && !salary.equals(user.getSalary())) {
            // Создаем глубокую копию, чтобы избежать модификации переданного Map
            HashMap<Integer, Long> salaryCopy = new HashMap<>(salary);
            user.setSalary(salaryCopy);
        }

        if (vacationDay != null && !vacationDay.equals(user.getVacationDay())) {
            user.setVacationDay(vacationDay);
        }

        userRepository.save(user);

    }
}
