package org.thanhan.ddd.application.service.event.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.thanhan.ddd.application.service.event.EventAppService;
import org.thanhan.ddd.domain.service.HiDomainService;

@Service
public class EventAppServiceImpl implements EventAppService {

    @Resource
    private HiDomainService hiDomainService;

    @Override
    public String sayHi(String who) {
        return hiDomainService.sayHiFromDomain(who);
    }
}
