package com.example.ppmtool.services;

import com.example.ppmtool.domain.Backlog;
import com.example.ppmtool.domain.ProjectTask;
import com.example.ppmtool.repositories.BacklogRepository;
import com.example.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){
        //Pts to be added to a specific project, project !=null,backlog exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        //set the backlog to pt;
        projectTask.setBacklog(backlog);
        //project sequence like : IDPRO-1, IDPRO-2 ...
        Integer BacklogSequence = backlog.getPTSequence();
        //Update the backlog sequence
        BacklogSequence++;
        backlog.setPTSequence(BacklogSequence);

        //Add sequence to ProjectTask
        projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        //initial priority when priority is null
        if(projectTask.getPriority()==null){
            projectTask.setPriority(3);
        }
        //initial state when state is null
        if(projectTask.getStatus() =="" || projectTask.getStatus() ==null){
            projectTask.setStatus("TO_DO");
        }
        return projectTaskRepository.save(projectTask);

    }
    public Iterable<ProjectTask> findBacklogById(String id){
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);

    }
}
