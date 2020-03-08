package com.epam.brest.courses.service_api.dto;

import com.epam.brest.courses.model.Rent;
import com.epam.brest.courses.model.dto.RentDto;

import java.time.LocalDate;
import java.util.List;

public interface RentDtoService {

    /**
     * Find rents with dress name for the specified period.
     *
     * @param dateFrom period start date.
     * @param dateTo period finish date.
     * @return rents list with dress name for the specified period.
     */
    List<RentDto> findAllWIthDressNameByDate(LocalDate dateFrom, LocalDate dateTo);
}