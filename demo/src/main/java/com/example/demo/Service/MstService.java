package com.example.demo.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.DepartmentDto;
import com.example.demo.Dto.RoleDto;
import com.example.demo.Repository.MstSearchRepository;

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
