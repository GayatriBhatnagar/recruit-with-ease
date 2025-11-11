package com.system.recruitwithease.modules.applicant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantRequestDto {

    Integer applicantId;
    String firstName;
    String lastName;
    String skills;
    Integer minExperience;
}
