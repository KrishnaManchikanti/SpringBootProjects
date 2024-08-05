package com.CodingNinjas.LeaveXpress.service;

import com.CodingNinjas.LeaveXpress.dto.LeaveDto;
import com.CodingNinjas.LeaveXpress.exception.LeaveNotFoundException;
import com.CodingNinjas.LeaveXpress.model.LeaveModel;
import com.CodingNinjas.LeaveXpress.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    public LeaveModel getLeaveById(Long id) {
        return leaveRepository.findById(id).orElseThrow(() -> new LeaveNotFoundException("Leave not found with id: " + id));
    }

    public List<LeaveModel> getAllLeaves() {
        return leaveRepository.findAll();
    }

    public void updateLeave(Long id, LeaveDto updatedLeave) {
        LeaveModel existingLeave = getLeaveById(id);
        existingLeave.setType(updatedLeave.getType());
        existingLeave.setEndDate(updatedLeave.getEndDate());
        existingLeave.setStartDate(updatedLeave.getStartDate());
        leaveRepository.save(existingLeave);
    }

    public void deleteLeave(Long id) {
        leaveRepository.deleteById(id);
    }

    public void applyForLeave(LeaveDto leaveRequest) {
        LeaveModel leave = LeaveModel.builder().type(leaveRequest.getType())
                .description(leaveRequest.getDescription())
                .startDate(leaveRequest.getStartDate()).endDate(leaveRequest.getEndDate()).build();
        leaveRepository.save(leave);
    }

    public void acceptLeave(Long id) {
        LeaveModel leave = getLeaveById(id);
        leave.setAccepted(true);
        leaveRepository.save(leave);
    }

    public void rejectLeave(Long id) {
        LeaveModel leave = getLeaveById(id);
        leave.setAccepted(false);
        leaveRepository.save(leave);
    }

    public List<LeaveModel> getAcceptedLeaves() {
        List<LeaveModel> leaves = getAllLeaves();
        List<LeaveModel> acceptedLeaves = new ArrayList<>();
        for(LeaveModel leave: leaves){
            if(leave.isAccepted()) {
                acceptedLeaves.add(leave);
            }
        }
        return acceptedLeaves;
    }

    public List<LeaveModel> getRejectedLeaves() {
        List<LeaveModel> leaves = getAllLeaves();
        List<LeaveModel> rejectedLeaves = new ArrayList<>();
        for(LeaveModel leave: leaves){
            if(!leave.isAccepted()) {
                rejectedLeaves.add(leave);
            }
        }
        return rejectedLeaves;
    }
}
