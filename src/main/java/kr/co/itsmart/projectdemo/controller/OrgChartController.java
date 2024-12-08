package kr.co.itsmart.profileMnt.controller;

import kr.co.itsmart.profileMnt.service.OrgChartService;
import kr.co.itsmart.profileMnt.vo.CommonVO;
import kr.co.itsmart.profileMnt.vo.ProfileVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OrgChartController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OrgChartService orgChartService;

    public OrgChartController(OrgChartService orgChartService) {
        this.orgChartService = orgChartService;
    }

    @GetMapping("/orgChart")
    public String getOrganization(Model model){
        List<CommonVO> orgChart = orgChartService.getOrgChart();
        List<ProfileVO> users = orgChartService.getOrgChartUserList();

        model.addAttribute("orgChart", orgChart);
        model.addAttribute("users", users);

        return "selectOrgChart";
    }
}
