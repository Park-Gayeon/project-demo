package kr.co.itsmart.projectdemo.service;

import kr.co.itsmart.projectdemo.dao.ProfileDAO;
import kr.co.itsmart.projectdemo.vo.ProfileVO;
import kr.co.itsmart.projectdemo.vo.ProjectVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{
    private final ProfileDAO profileDAO;

    @Override
    public ProfileVO selectDetailInfo(String user_id){
        return profileDAO.selectDetailInfo(user_id);
    }

    @Override
    public ProjectVO selectUsrSkillList(ProjectVO tmpVO) {
        return profileDAO.selectUsrSkillList(tmpVO);
    }
}
