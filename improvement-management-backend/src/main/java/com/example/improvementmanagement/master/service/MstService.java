package com.example.improvementmanagement.master.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.improvementmanagement.master.dto.DepartmentDto;
import com.example.improvementmanagement.master.dto.RoleDto;
import com.example.improvementmanagement.master.repository.MstSearchRepository;

@Service
public class MstService {

    private final MstSearchRepository mstSearchRepository;

    public MstService(MstSearchRepository mstSearchRepository){
        this.mstSearchRepository = mstSearchRepository;
    }

    public List<DepartmentDto> getDepartments(){
        return mstSearchRepository.DepartmentFindAll();
    }

    public List<RoleDto> getRoles(){
        return mstSearchRepository.RoleFindAll();
    }

}
