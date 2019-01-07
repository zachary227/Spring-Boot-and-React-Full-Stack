package com.example.ppmtool.services;

import com.example.ppmtool.domain.Project;
import com.example.ppmtool.exceptions.ProjectIdException;
import com.example.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }
        catch( Exception e){
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
        }
    }

    public Project findProjectByIdentifier(String ProjectId){
        Project project = projectRepository.findByProjectIdentifier(ProjectId.toUpperCase());
        if (project == null){
            throw new ProjectIdException("Project ID '"+ProjectId+"' does not exists");
        }
        return project;

    }
    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectid){
        Project project = projectRepository.findByProjectIdentifier(projectid.toUpperCase());
        if(project ==null){
            throw new ProjectIdException("Cannot Delete Project with ID '"+projectid+"'. This project does not exist");
        }
        projectRepository.delete(project);
    }
}

