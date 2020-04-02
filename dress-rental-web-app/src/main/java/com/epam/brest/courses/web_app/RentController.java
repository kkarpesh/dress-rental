package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Dress;
import com.epam.brest.courses.model.Rent;
import com.epam.brest.courses.model.dto.RentDto;
import com.epam.brest.courses.service.RentServiceImpl;
import com.epam.brest.courses.service.dto.RentDtoServiceImpl;
import com.epam.brest.courses.service_api.DressService;
import com.epam.brest.courses.web_app.validators.RentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Rent controller.
 */
@Controller
public class RentController {

    /**
     * Default logger for current class.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(RentController.class);

    /**
     * Service layer object to get information of rentDto.
     */
    @Autowired
    private RentDtoServiceImpl rentDtoService;

    /**
     * Service layer object to get information of rent.
     */
    @Autowired
    private RentServiceImpl rentService;

    /**
     * Service layer object to get information of dress.
     */
    @Autowired
    private DressService dressService;

    /**
     * Object to validate rent.
     */
    @Autowired
    private RentValidator rentValidator;


    /**
     * Goto list of rents by date page.
     *
     * @param dateFrom period start date.
     * @param dateTo   period finish date.
     * @param model    model to storage information for view rendering.
     * @return view name.
     */
    @GetMapping("/rents")
    public final String getRents(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "dateFrom", required = false)
                    LocalDate dateFrom,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "dateTo", required = false)
                    LocalDate dateTo,
            ModelMap model) {
        LOGGER.debug("Get all rents from {} to {}", dateFrom, dateTo);

        List<RentDto> rents
                = rentDtoService.findAllWIthDressNameByDate(dateFrom, dateTo);

        model.addAttribute("rents", rents);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        return "rents";
    }

    /**
     * Goto add rent page.
     *
     * @param model model to storage information for view rendering.
     * @return view name.
     */
    @GetMapping("/rent")
    public final String gotoAddRentPage(Model model) {
        LOGGER.debug("Goto add rent page {}", model);
        model.addAttribute("isNew", true);
        model.addAttribute("rent", new Rent());
        List<Dress> dresses = dressService.findAll();
        model.addAttribute("dresses", dresses);
        return "rent";
    }

    /**
     * Goto edit rent page.
     *
     * @param id    rent ID.
     * @param model model to storage information for view rendering.
     * @return view name.
     */
    @GetMapping("/rent/{id}")
    public final String gotoEditDressPage(@PathVariable Integer id,
                                          Model model) {
        Optional<Rent> rent = rentService.findById(id);
        if (rent.isPresent()) {
            model.addAttribute("rent", rent.get());
            List<Dress> dresses = dressService.findAll();
            model.addAttribute("dresses", dresses);
            return "rent";
        } else {
            return "redirect:/rents";
        }
    }

    /**
     * Update or create new rent.
     *
     * @param rent   rent.
     * @param result binding result
     * @param model  to storage information for view rendering.
     * @return view name.
     */
    @PostMapping("/rent")
    public final String createOrUpdate(@Valid Rent rent,
                                       BindingResult result,
                                       Model model) {
        rentValidator.validate(rent, result);
        List<Dress> dresses = dressService.findAll();
        model.addAttribute("dresses", dresses);
        if (rent.getRentId() == null) {
            LOGGER.debug("Create new rent {}, {}", rent, result);
            if (result.hasErrors()) {
                model.addAttribute("isNew", true);
                return "rent";
            } else {
                rentService.create(rent);
                return "redirect:/rents";
            }
        } else {
            LOGGER.debug("Update rent {}, {}", rent, result);
            if (result.hasErrors()) {
                return "rent";
            } else {
                rentService.update(rent);
                return "redirect:/rents";
            }
        }
    }

    /**
     * Delete rent by ID.
     *
     * @param id rent ID.
     * @return view name.
     */
    @GetMapping("/rent/delete/{id}")
    public final String delete(@PathVariable Integer id) {
        rentService.delete(id);
        return "redirect:/rents";
    }
}



