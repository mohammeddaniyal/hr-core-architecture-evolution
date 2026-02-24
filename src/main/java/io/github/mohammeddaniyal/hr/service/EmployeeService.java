package io.github.mohammeddaniyal.hr.service;

import io.github.mohammeddaniyal.hr.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    public EmployeeDTO add(EmployeeDTO employee);
    public EmployeeDTO getByEmployeeId(String employeeId);
    public List<EmployeeDTO> getAll();
    public EmployeeDTO update(String employeeId,EmployeeDTO employeeDTO);
    public void delete(String employeeId);
}
