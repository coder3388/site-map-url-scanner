package com.sitemap.scanner.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sitemap.scanner.domain.OperationDetail;

@Repository
public interface OperationDetailRepository extends MongoRepository<OperationDetail, BigInteger>{

	List<OperationDetail> findByDomainName(String domainName);
}
