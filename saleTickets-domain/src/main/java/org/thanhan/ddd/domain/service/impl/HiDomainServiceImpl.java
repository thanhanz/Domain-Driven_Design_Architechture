package org.thanhan.ddd.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thanhan.ddd.domain.repository.HiDomainRepository;
import org.thanhan.ddd.domain.service.HiDomainService;

@Service
public class HiDomainServiceImpl implements HiDomainService {

    @Autowired
    private HiDomainRepository hiDomainRepository;


    @Override
    public String sayHiFromDomain(String who) {
        return hiDomainRepository.sayHiFromRepository(who);
    }
}
