package com.woniu.service;

import com.woniu.entity.Worker;

import java.util.List;

public interface WorkerService {
    List<Worker> findDoctors();

    List<Worker> findNurses();
}
