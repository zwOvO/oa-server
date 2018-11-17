package com.oa.service.impl;

import com.oa.base.BaseServiceImpl;
import com.oa.entity.License;
import com.oa.entity.vo.LicenseVO;
import com.oa.mapper.LicenseMapper;
import com.oa.service.ILicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LicenseServiceImpl extends BaseServiceImpl<LicenseMapper, License> implements ILicenseService {
    @Autowired
    LicenseMapper licenseMapper;

    @Override
    public List<LicenseVO> selectLicenseVOList() {
        List<LicenseVO> leaveList= licenseMapper.selectLicenseVOList();
        return leaveList;
    }
}
