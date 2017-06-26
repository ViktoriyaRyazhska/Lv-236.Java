package com.inva.hipstertest.freemarker.controllers;

import com.inva.hipstertest.service.ScheduleService;
import com.inva.hipstertest.service.dto.ScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class Pagination {


    @Autowired
    private ScheduleService scheduleService;

    public Pagination(){
    }
    /**
     * GET  /page : get a page of ScheduleDTOs.
     *
     * @param pageable the pagination information
     * @param model for add attributes
     * @return page pageble (FTL)
     */
    @RequestMapping(value = "freemarker/page", method = RequestMethod.GET)
    public String getAllPage(Model model, Pageable pageable) {
        Page<ScheduleDTO> page = scheduleService.findAll(pageable);
        model.addAttribute("schedules", page.getContent());
        model.addAttribute("sizes", pageable.getPageSize());
        model.addAttribute("current", pageable.getPageNumber());
        model.addAttribute("longs", pages(pageable.getPageSize()));
        return "pageble";
    }



    public long pages(int size){
        long all = scheduleService.countAllSchedule();
        long realPage = all/size;
        if(all % size == 0){
            return realPage;
        }
        return realPage + 1;
    }

}
