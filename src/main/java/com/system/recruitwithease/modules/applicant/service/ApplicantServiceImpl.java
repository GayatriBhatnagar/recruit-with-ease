package com.system.recruitwithease.modules.applicant.service;

import com.system.recruitwithease.common.PaginatedResponseDto;
import com.system.recruitwithease.common.ApplicantSpecification;
import com.system.recruitwithease.modules.applicant.dto.ApplicantRequestDto;
import com.system.recruitwithease.modules.applicant.dto.ApplicantResponseDto;
import com.system.recruitwithease.modules.applicant.entity.Applicant;
import com.system.recruitwithease.modules.applicant.mapper.ApplicantMapper;
import com.system.recruitwithease.modules.applicant.repository.ApplicantRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicantServiceImpl implements ApplicantService {
    private final ApplicantRepo applicantRepo;
    private final ApplicantMapper applicantMapper;

    public ApplicantServiceImpl(ApplicantRepo applicantRepo, ApplicantMapper applicantMapper) {
        this.applicantRepo = applicantRepo;
        this.applicantMapper = applicantMapper;
    }

    @Override
    public ApplicantResponseDto createApplicant(ApplicantRequestDto applicantRequestDto) {
        Applicant applicant = applicantMapper.toApplicant(applicantRequestDto);
        Applicant postApplicant= applicantRepo.save(applicant);
        return applicantMapper.toApplicantDto(postApplicant);
    }

    @Override
    public PaginatedResponseDto<ApplicantResponseDto> getApplicantsWithFilters(String firstName, String lastName, List<String> skill, Integer minExperience, String filterType, Pageable pageable) {
        Specification<Applicant> specification = null;
        List<Specification<Applicant>> filters = new ArrayList<>();
        if(firstName!=null && !firstName.isEmpty()){
            filters.add(ApplicantSpecification.hasFirstName(firstName));
        }
        if(lastName!=null && !lastName.isEmpty()){
            filters.add(ApplicantSpecification.hasLastName(lastName));
        }
        if(minExperience!=null){
            filters.add(ApplicantSpecification.hasMinExperience(minExperience));
        }
        if(skill!=null && !skill.isEmpty()){
            for(String sk :skill)
            {
            filters.add(ApplicantSpecification.hasSkills(sk));
            }
        }
        specification= filters.stream().reduce("or".equalsIgnoreCase(filterType) ? Specification::or : Specification::and)
                .orElse(ApplicantSpecification.matchALL());

        Page<Applicant> pageableResponse = applicantRepo.findAll(specification, pageable);
        Page<ApplicantResponseDto> mappedResponseDto = pageableResponse.map(applicantMapper::toApplicantDto);

       return new PaginatedResponseDto<>(
               mappedResponseDto.getContent(),
               mappedResponseDto.getNumber(),
               mappedResponseDto.getSize(),
               mappedResponseDto.getTotalElements());

    }
}
