package com.sitemap.scanner.controller;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.sitemap.scanner.dto.OperationDetailDTO;
import com.sitemap.scanner.dto.SiteMapUrlEntryDTO;
import com.sitemap.scanner.service.OperationDetailService;
import com.sitemap.scanner.service.SiteMapUrlEntryService;
import com.sitemap.scanner.service.SiteMapUrlExtractorService;

@RestController
public class SiteMapScanControllerImpl implements SiteMapScanController {
	
	@Autowired
	private SiteMapUrlEntryService siteMapUrlEntryService;
	
	@Autowired
	private SiteMapUrlExtractorService siteMapUrlExtractService;
	
	@Autowired
	private OperationDetailService operationDetailService;
	

	@Override
	public OperationDetailDTO extractUrls(String siteMapUrl)
			throws SAXException, IOException, ParserConfigurationException {
		return siteMapUrlExtractService.extractUrls(siteMapUrl);
	}

	@Override
	public List<SiteMapUrlEntryDTO> findSiteMapUrlEntriesByDomainName(String domainName) {
		return siteMapUrlEntryService.findByDomainName(domainName);
	}

	@Override
	public List<OperationDetailDTO> findAllOperationDetails() {
		return operationDetailService.findAll();
	}

}
