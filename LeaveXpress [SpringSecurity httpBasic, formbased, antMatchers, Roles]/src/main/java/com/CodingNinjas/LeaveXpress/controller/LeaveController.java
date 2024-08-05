package com.CodingNinjas.LeaveXpress.controller;

import com.CodingNinjas.LeaveXpress.dto.LeaveDto;
import com.CodingNinjas.LeaveXpress.model.LeaveModel;
import com.CodingNinjas.LeaveXpress.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public LeaveModel getLeaveById(@PathVariable Long id) {
        return leaveService.getLeaveById(id);
    }

    //manager
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public List<LeaveModel> getAllLeaves() {
        return leaveService.getAllLeaves();
    }

    //get Accepted leaves
    @GetMapping("/accepted")
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public List<LeaveModel> getAcceptedLeaves() {
        return leaveService.getAcceptedLeaves();
    }

    //get rejected leaves
    @GetMapping("/rejected")
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public List<LeaveModel> getRejectedLeaves() {
        return leaveService.getRejectedLeaves();
    }

    // check particular leave is accepted/ rejected (status)
    @GetMapping("/status/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public boolean getLeaveStatus(@PathVariable Long id){
        return leaveService.getLeaveById(id).isAccepted();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public void updateLeave(@PathVariable Long id, @RequestBody LeaveDto updatedLeave) {
        leaveService.updateLeave(id, updatedLeave);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLeave(@PathVariable Long id) {
        leaveService.deleteLeave(id);
    }

    @PostMapping("/apply")
    @PreAuthorize("hasAnyRole('EMPLOYEE','MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public void applyForLeave(@RequestBody LeaveDto leaveRequest) {
        leaveService.applyForLeave(leaveRequest);
    }

    // manager
    @PostMapping("/accept/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void acceptLeave(@PathVariable Long id) {
        leaveService.acceptLeave(id);
    }

    //manager
    @PostMapping("/reject/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public void rejectLeave(@PathVariable Long id) {
        leaveService.rejectLeave(id);
    }
}
