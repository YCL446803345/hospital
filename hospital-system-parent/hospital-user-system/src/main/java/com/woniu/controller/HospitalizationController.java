
package com.woniu.controller;

import com.github.pagehelper.PageInfo;
import com.woniu.entity.Bed;
import com.woniu.entity.Cost;
import com.woniu.entity.Patient;
import com.woniu.entity.PaymentRecord;
import com.woniu.service.HospitalizationBillServer;
import com.woniu.service.PatientService;
import com.woniu.service.UserService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HospitalizationController {

    @Autowired
    private UserService userService;

    @GetMapping("queryUserStatus")
    public ResponseEntity<String> queryUserStatus(String phone){
        String status = userService.queryUserStatus(phone);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }

}

