package com.CodingNinjas.LeaveXpress.repository;

import com.CodingNinjas.LeaveXpress.model.LeaveModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveModel, Long> {

}
