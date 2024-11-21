package kr.co.itsmart.profileMnt.controller;

import kr.co.itsmart.profileMnt.service.CommonService;
import kr.co.itsmart.profileMnt.service.ProfileMntService;
import kr.co.itsmart.profileMnt.service.ProjectMntService;
import kr.co.itsmart.profileMnt.service.WorkExperienceMntService;
import kr.co.itsmart.profileMnt.vo.CommonVO;
import kr.co.itsmart.profileMnt.vo.ProfileVO;
import kr.co.itsmart.profileMnt.vo.ProjectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/profile/modify")
public class ProfileMntController {
private static final Logger LOGGER = LoggerFactory.getLogger(ProjectMntController.class);
    private final ProfileMntService profileMntService;
    private final CommonService commonService;
    private final ProjectMntService projectMntService;
    private final WorkExperienceMntService workExperienceMntService;

    public ProfileMntController(ProfileMntService profileMntService,
                                CommonService commonService,
                                @Lazy ProjectMntService projectMntService,
                                @Lazy WorkExperienceMntService workExperienceMntService
    ) {
        this.profileMntService = profileMntService;
        this.commonService = commonService;
        this.projectMntService = projectMntService;
        this.workExperienceMntService = workExperienceMntService;
    }


    @PostMapping("/info/{user_id}")
    public String modifyUsrProfile(@ModelAttribute ProfileVO profile, Model model) {
        Map<String, String> params = new HashMap<>();
        params.put("code_group_id", "TASK");
        params.put("task_type", "lar");
        List<CommonVO> taskLarList = commonService.selectCodeList(params);

        params.clear();
        params.put("code_group_id", "TASK");
        List<CommonVO> taskMidList = commonService.selectCodeList(params);
        params.put("code_group_id", "ORG");
        List<CommonVO> orgList = commonService.selectCodeList(params);
        params.put("code_group_id", "PSIT");
        List<CommonVO> psitList = commonService.selectCodeList(params);
        params.put("code_group_id", "ROLE");
        List<CommonVO> roleList = commonService.selectCodeList(params);

        int pj_totalMonth = projectMntService.calcTotalMonth(profile.getUser_id());
        int wk_totalMonth = workExperienceMntService.calcTotalMonth(profile.getUser_id());

        model.addAttribute("profile", profile);
        model.addAttribute("orgList", orgList);
        model.addAttribute("psitList", psitList);
        model.addAttribute("roleList", roleList);
        model.addAttribute("taskLarList", taskLarList);
        model.addAttribute("taskMidList", taskMidList);
        model.addAttribute("pj_totalMonth", pj_totalMonth);
        model.addAttribute("wk_totalMonth", wk_totalMonth);

        return "selectProfileModify";
    }

    @PostMapping("/{user_id}")
    @ResponseBody
    public String updateUsrProfile(@PathVariable("user_id") String user_id, @ModelAttribute ProfileVO profile) {
        try {
            // hist_seq
            int hist_seq = profileMntService.selectMaxSeq(user_id);
            profile.setHist_seq(hist_seq);
            LOGGER.info("프로필 정보 hist_seq: hist_seq={}", hist_seq);

            // 프로필 정보 처리
            profileMntService.updateUsrProfileInfo(profile);
        }catch (Exception e){
            LOGGER.debug("프로필 정보 처리 실패: user_id={}", user_id, e.getMessage());
            return "FAIL";
        }
        return "SUCCESS";
    }

    @GetMapping("/skill/update")
    public String updateUsrSkill(
            @RequestParam("user_id") String user_id,
            @RequestParam("projecct_seq") int project_seq,
            Model model){
        ProjectVO tmpVO = new ProjectVO();
        tmpVO.setUser_id(user_id);
        tmpVO.setProject_seq(project_seq);
        return "";
    }
}
