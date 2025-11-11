package com.system.recruitwithease.modules.applicant.service;

import com.system.recruitwithease.common.PaginatedResponseDto;
import com.system.recruitwithease.modules.applicant.dto.ApplicantRequestDto;
import com.system.recruitwithease.modules.applicant.dto.ApplicantResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApplicantService {
  ApplicantResponseDto createApplicant(@Valid ApplicantRequestDto applicantRequestDto);

  PaginatedResponseDto<ApplicantResponseDto> getApplicantsWithFilters(String firstName, String lastName, List<String> skill, Integer minExperience, String filterType, Pageable pageable);
}
