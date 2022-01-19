package com.sitemap.scanner.controller;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xml.sax.SAXException;

import com.sitemap.scanner.dto.OperationDetailDTO;
import com.sitemap.scanner.dto.SiteMapUrlEntryDTO;

@RequestMapping(path = "sitemap-scanner/", produces = "application/json; charset=UTF-8")
public interface SiteMapScanController {
	@PostMapping("/extract-urls")
	public OperationDetailDTO extractUrls(@RequestBody String siteMapUrl) throws SAXException, IOException, ParserConfigurationException;
	
	@GetMapping("/find-site-map-entries-by-domain-name/domain-name/{domain-name}")
	public List<SiteMapUrlEntryDTO> findSiteMapUrlEntriesByDomainName(@PathVariable("domain-name") String domainName);
	
	@GetMapping("/find-all-operation-details")
	public List<OperationDetailDTO> findAllOperationDetails();
}
