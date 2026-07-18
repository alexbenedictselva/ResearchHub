package com.alex.researchhub.service.researcher;

import com.alex.researchhub.dto.researcher.ResearcherRequest;
import com.alex.researchhub.dto.researcher.ResearcherResponse;
import com.alex.researchhub.entity.Researcher;
import com.alex.researchhub.mapper.ResearcherMapper;
import com.alex.researchhub.repository.ResearcherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResearcherServiceImpl
        implements ResearcherService {

    private final ResearcherRepository repository;

    @Override
    public ResearcherResponse createResearcher(
            ResearcherRequest request) {

        Researcher researcher =
                ResearcherMapper.toEntity(request);

        repository.save(researcher);

        return ResearcherMapper.toResponse(researcher);
    }

    @Override
    public ResearcherResponse getResearcher(Long id) {

        Researcher researcher =
                repository.findById(id)
                        .orElseThrow();

        return ResearcherMapper.toResponse(researcher);
    }

    @Override
    public List<ResearcherResponse> getAllResearchers() {

        return repository.findAll()
                .stream()
                .map(ResearcherMapper::toResponse)
                .toList();
    }

    @Override
    public ResearcherResponse updateResearcher(Long id,
                                               ResearcherRequest request) {

        Researcher researcher =
                repository.findById(id)
                        .orElseThrow();

        researcher.setFullName(request.getFullName());
        researcher.setUniversity(request.getUniversity());
        researcher.setDepartment(request.getDepartment());
        researcher.setCountry(request.getCountry());
        researcher.setSpecialization(request.getSpecialization());
        researcher.setBio(request.getBio());

        repository.save(researcher);

        return ResearcherMapper.toResponse(researcher);
    }

    @Override
    public void deleteResearcher(Long id) {

        repository.deleteById(id);

    }

}