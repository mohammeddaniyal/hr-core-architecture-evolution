package io.github.mohammeddaniyal.hr.service;

import io.github.mohammeddaniyal.hr.dto.DesignationDTO;

import java.util.List;

public interface DesignationService {
    public DesignationDTO add(DesignationDTO designationDTO);
    public List<DesignationDTO> getAll();
    public DesignationDTO update(int code,DesignationDTO designationDTO);
    public void deleteByCode(int code);
    public DesignationDTO getByCode(int code);
}
