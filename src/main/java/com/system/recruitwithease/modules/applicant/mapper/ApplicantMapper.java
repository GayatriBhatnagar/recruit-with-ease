package com.system.recruitwithease.modules.applicant.mapper;

import com.system.recruitwithease.modules.applicant.dto.ApplicantRequestDto;
import com.system.recruitwithease.modules.applicant.dto.ApplicantResponseDto;
import com.system.recruitwithease.modules.applicant.entity.Applicant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicantMapper {
    
    public Applicant toApplicant(ApplicantRequestDto applicantRequestDto) ;

    ApplicantResponseDto toApplicantDto(Applicant postApplicant);
}
