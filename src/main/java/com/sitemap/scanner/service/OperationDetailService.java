package com.sitemap.scanner.service;

import java.math.BigInteger;
import java.util.List;

import com.sitemap.scanner.dto.OperationDetailDTO;

public interface OperationDetailService{
	public OperationDetailDTO save(OperationDetailDTO willBeSaved);
	
	public OperationDetailDTO findById(BigInteger id);
	
	public List<OperationDetailDTO> findAll();
	
	public List<OperationDetailDTO> findByDomainName(String domainName);
}
