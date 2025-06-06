package org.thanhan.ddd.infrastructure.persistence.repository;

import org.springframework.stereotype.Repository;
import org.thanhan.ddd.domain.repository.HiDomainRepository;

@Repository
public class HiInfraRepositoryImpl implements HiDomainRepository {
    @Override
    public String sayHiFromRepository(String who) {
        return "Say hi from Infrastructure Repository: " + who;
    }


}
