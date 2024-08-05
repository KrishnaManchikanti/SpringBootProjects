package com.CodingNinjas.LeaveXpress.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveDto {

    private String type;
    private String startDate;
    private String endDate;
    private String description;
}
