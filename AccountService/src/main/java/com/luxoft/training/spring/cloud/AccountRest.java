package com.luxoft.training.spring.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountRest {
    @Autowired
    private AccountDAO dao;
    @Autowired
    private AccountRepository repo;

    @RequestMapping("/create")
    public Account create(@RequestParam Integer clientId) {
        return dao.create(clientId);
    }

    @RequestMapping("/fund/{id}")
    public ResponseEntity fund(@PathVariable Integer id, @RequestParam BigDecimal sum) {
        if (dao.addBalance(id, sum.abs())) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping("/checkout/{id}")
    public ResponseEntity checkout(@PathVariable Integer id, @RequestParam BigDecimal sum) {
        if (dao.addBalance(id, sum.abs().negate())) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping("/get")
    public List<? extends Account> get() {
        return repo.findAll();
    }

    @RequestMapping("/get/{id}")
    public List<? extends Account> get(@PathVariable Integer id) {
        return repo.findByClientId(id);
    }
}
