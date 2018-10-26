package com.oa.service.impl;

import com.oa.base.BaseServiceImpl;
import com.oa.entity.License;
import com.oa.mapper.LicenseMapper;
import com.oa.service.ILicenseService;
import org.springframework.stereotype.Service;

@Service
public class LicenseServiceImpl extends BaseServiceImpl<LicenseMapper, License> implements ILicenseService {

}
