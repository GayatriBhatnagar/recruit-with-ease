package com.system.recruitwithease.modules.applicant.repository;

import com.system.recruitwithease.modules.applicant.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepo extends JpaRepository<Applicant, Integer> , JpaSpecificationExecutor<Applicant> {




}
