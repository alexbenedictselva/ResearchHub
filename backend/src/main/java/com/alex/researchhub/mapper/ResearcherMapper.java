package com.alex.researchhub.mapper;

// import com.alex.researchhub.researcher.dto.*;
import com.alex.researchhub.dto.researcher.ResearcherRequest;
import com.alex.researchhub.dto.researcher.ResearcherResponse;
import com.alex.researchhub.entity.Researcher;

public class ResearcherMapper {

    public static Researcher toEntity(ResearcherRequest request){

        return Researcher.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(request.getPassword())
                .university(request.getUniversity())
                .department(request.getDepartment())
                .country(request.getCountry())
                .specialization(request.getSpecialization())
                .bio(request.getBio())
                .build();
    }

    public static ResearcherResponse toResponse(Researcher researcher){

        return ResearcherResponse.builder()
                .id(researcher.getId())
                .fullName(researcher.getFullName())
                .email(researcher.getEmail())
                .university(researcher.getUniversity())
                .department(researcher.getDepartment())
                .country(researcher.getCountry())
                .specialization(researcher.getSpecialization())
                .bio(researcher.getBio())
                .build();
    }

}