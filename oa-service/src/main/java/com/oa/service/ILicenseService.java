package com.oa.service;

import com.oa.base.BaseService;
import com.oa.entity.License;
import com.oa.entity.vo.LicenseVO;

import java.util.List;

public interface ILicenseService extends BaseService<License> {
    List<LicenseVO> selectLicenseVOList();
}
