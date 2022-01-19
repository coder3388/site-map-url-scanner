package com.sitemap.scanner.service;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.sitemap.scanner.dto.OperationDetailDTO;

@Service
public class SiteMapUrlExtractorServiceImpl implements SiteMapUrlExtractorService {
	@Autowired
	private SiteMapUrlEntryService siteMapUrlEntryService;
	
	@Autowired
	private OperationDetailService opetailService;
	
	@Override
	public OperationDetailDTO extractUrls(String siteMapUrl) throws SAXException, IOException, ParserConfigurationException {
		if (siteMapUrl==null) {
			throw new IllegalArgumentException("siteMap url should not be empty!");
		}
		
		URL url = new URL(siteMapUrl);
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        
        NewsUrlHandler elementHandler = new NewsUrlHandler(siteMapUrlEntryService, url.getHost());
		saxParser.parse(siteMapUrl, elementHandler);   
		
		System.err.println("Parsing an save ok -> siteMapUrl");
		System.err.println(Duration.between(elementHandler.getStart(), elementHandler.getEnd()).toMillis());
		
		OperationDetailDTO willBeSaved = OperationDetailDTO.builder().domainName(url.getHost())
			.foundUrlCount(elementHandler.getFoundUrlCount())
			.operationStartTime(elementHandler.getStart())
			.operationEndTime(elementHandler.getEnd())
			.operationDuration(Duration.between(elementHandler.getStart(), elementHandler.getEnd()))
			.build();
		
		return opetailService.save(willBeSaved);
	}


}
