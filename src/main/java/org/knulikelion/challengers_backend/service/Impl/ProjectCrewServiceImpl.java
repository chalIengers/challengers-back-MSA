package org.knulikelion.challengers_backend.service.Impl;

import org.knulikelion.challengers_backend.data.dao.ProjectCrewDAO;
import org.knulikelion.challengers_backend.data.dao.ProjectDAO;
import org.knulikelion.challengers_backend.data.dto.request.ProjectCrewRequestDto;
import org.knulikelion.challengers_backend.data.dto.response.BaseResponseDto;
import org.knulikelion.challengers_backend.data.dto.response.ProjectCrewResponseDto;
import org.knulikelion.challengers_backend.data.entity.ProjectCrew;
import org.knulikelion.challengers_backend.data.repository.ProjectCrewRepository;
import org.knulikelion.challengers_backend.service.ProjectCrewService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectCrewServiceImpl implements ProjectCrewService {
    private final ProjectCrewDAO projectCrewDAO;


    public ProjectCrewServiceImpl(ProjectCrewDAO projectCrewDAO) {
        this.projectCrewDAO = projectCrewDAO;
    }

    @Override
    public Object getCrewsGroupedByPosition(Long crewId) {

        Map<String,List<ProjectCrewResponseDto>> crewsGroupedByPosition = projectCrewDAO.getCrews(crewId);

        if(crewsGroupedByPosition.isEmpty()) {
            BaseResponseDto responseDto = new BaseResponseDto();

            responseDto.setSuccess(false);
            responseDto.setMsg("팀원 조회 불가");

            return responseDto;
        }else {
            return crewsGroupedByPosition;
        }
    }

    @Override
    public Object getProjectCrewById(Long crewId) {
        if (projectCrewDAO.selectById(crewId).isEmpty()) {
            BaseResponseDto resultResponseDto = new BaseResponseDto();

            resultResponseDto.setSuccess(false);
            resultResponseDto.setMsg("팀원 조회 불가");

            return resultResponseDto;
        } else {
            ProjectCrew getProjectCrew = projectCrewDAO.selectById(crewId).get();
            ProjectCrewResponseDto projectCrewResponseDto = new ProjectCrewResponseDto();
            projectCrewResponseDto.setId(getProjectCrew.getId());
            projectCrewResponseDto.setName(getProjectCrew.getProjectCrewName());
            projectCrewResponseDto.setRole(getProjectCrew.getProjectCrewRole());
            projectCrewResponseDto.setPosition(getProjectCrew.getProjectCrewPosition());

            return projectCrewResponseDto;
        }
    }
    @Override
    public BaseResponseDto updateProjectCrew(Long crewId, ProjectCrewRequestDto projectCrewRequestDto) {

        Optional<ProjectCrew> projectCrewOptional = projectCrewDAO.selectById(crewId);
        if(projectCrewOptional.isEmpty()) {
            BaseResponseDto resultResponseDto = new BaseResponseDto();
            resultResponseDto.setSuccess(false);
            resultResponseDto.setMsg("팀원이 존재하지 않습니다.");
            return resultResponseDto;
        }
        ProjectCrew projectCrew = projectCrewOptional.get();
        projectCrew.setId(crewId);
        projectCrew.setProjectCrewName(projectCrewRequestDto.getName());
        projectCrew.setProjectCrewRole(projectCrewRequestDto.getRole());
        projectCrew.setProjectCrewPosition(projectCrew.getProjectCrewPosition());
        projectCrew.setUpdatedAt(LocalDateTime.now());

        projectCrewDAO.updateCrew(projectCrew);

        BaseResponseDto resultResponseDto = new BaseResponseDto();
        resultResponseDto.setSuccess(true);
        resultResponseDto.setMsg("팀원 수정 완료");

        return resultResponseDto;
    }

    @Override
    public BaseResponseDto removeProjectCrew(Long crewId) {
        BaseResponseDto resultResponseDto = new BaseResponseDto();

        if(projectCrewDAO.selectById(crewId).isEmpty()) {
            resultResponseDto.setSuccess(false);
            resultResponseDto.setMsg("팀원 조회 불가");
        }else {
            projectCrewDAO.removeCrew(crewId);
            resultResponseDto.setSuccess(true);
            resultResponseDto.setMsg("팀원 삭제 완료");
        }
        return resultResponseDto;
    }
}
