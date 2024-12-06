package kr.co.itsmart.profileMnt.service;

import kr.co.itsmart.profileMnt.dao.ProfileDAO;
import kr.co.itsmart.profileMnt.vo.ProfileVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfileInfoServiceImpl implements ProfileInfoService{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileInfoServiceImpl.class);
    private final ProfileDAO profileDAO;
    private final PasswordEncoder passwordEncoder;

    @Value("${user.default-password}")
    private String defaultPassword;

    public ProfileInfoServiceImpl(ProfileDAO profileDAO, PasswordEncoder passwordEncoder){
        this.profileDAO = profileDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<ProfileVO> getUsrProfileInfoList(ProfileVO profile) {
        LOGGER.info("getUsrProfileInfoList를 조회합니다.");
        return profileDAO.getUsrProfileInfoList(profile);
    }

    @Override
    public int getUsrProfileInfoCnt(ProfileVO profile) {
        return profileDAO.getUsrProfileInfoCnt(profile);
    }

    @Override
    public boolean checkUsrExists(String user_id) {
        return profileDAO.checkUsrExists(user_id);
    }

    @Override
    @Transactional
    public void insertUsrProfile(ProfileVO profile) {
        // PASSWORD 암호화
        String encodedPassword = passwordEncoder.encode(defaultPassword);
        LOGGER.info("암호화 된 비밀번호: user_pw={}" , encodedPassword);
        profile.setUser_pw(encodedPassword);

        // INSERT
        profileDAO.insertUsrProfile(profile);
        LOGGER.info("신규 직원 정보를 생성했습니다: user_id={}" , profile.getUser_id());

        // CREATE HIST
        profileDAO.insertUsrProfileInfoHist(profile);
        LOGGER.info("프로필 정보 이력을 생성했습니다: user_id={}, hist_seq={}" , profile.getUser_id(), profile.getHist_seq());

    }

    @Override
    public int selectMaxHistSeq(String user_id) {
        return profileDAO.selectMaxHistSeq(user_id);
    }

}
