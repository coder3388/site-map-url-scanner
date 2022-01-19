package com.sitemap.scanner.service;

import java.math.BigInteger;
import java.util.List;

import com.sitemap.scanner.dto.SiteMapUrlEntryDTO;

public interface SiteMapUrlEntryService{
	public SiteMapUrlEntryDTO save(SiteMapUrlEntryDTO willBeSaved);
	
	public SiteMapUrlEntryDTO findById(BigInteger id);
	
	public List<SiteMapUrlEntryDTO> findAll();
	
	public List<SiteMapUrlEntryDTO> findByDomainName(String domainName);
}
