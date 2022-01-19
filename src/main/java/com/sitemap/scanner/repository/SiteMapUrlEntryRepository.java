package com.sitemap.scanner.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sitemap.scanner.domain.SiteMapUrlEntry;

@Repository
public interface SiteMapUrlEntryRepository extends MongoRepository<SiteMapUrlEntry, BigInteger>{

	List<SiteMapUrlEntry> findByDomainName(String domainName);
}
