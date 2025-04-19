package com.example.demo.controller;


import com.example.demo.repository.User;
import com.example.demo.service.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "api/staff")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> finaAll(){
        return userService.findAll();
    }

    @PostMapping
    public User creat(@RequestBody User user){
        return userService.create(user);
    }

    @DeleteMapping(path = "{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

    @PutMapping(path = "{id}")
    public void update(
            @PathVariable Long id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) HashMap<Integer, Long> salary,
            @RequestParam(required = false) Integer vacationDay

    ){
        userService.update(id, email, name, salary, vacationDay);
    }
//    @PutMapping("{id}")
//    public User update(
//            @PathVariable Long id,
//            @RequestBody UserUpdateRequest request
//    ) {
//        return userService.update(
//                id,
//                request.getEmail(),
//                request.getName(),
//                request.getSalary(),
//                request.getVacationDay()
//        );
//    }
//
//        // DTO для запроса обновления
//        @Getter
//        @Setter
//        @NoArgsConstructor
//        @AllArgsConstructor
//        public static class UserUpdateRequest {
//            private String email;
//            private String name;
//            private HashMap<Integer, Long> salary;
//            private Integer vacationDay;
//        }
}
