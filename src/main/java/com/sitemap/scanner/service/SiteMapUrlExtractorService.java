package com.sitemap.scanner.service;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.sitemap.scanner.dto.OperationDetailDTO;

public interface SiteMapUrlExtractorService{
	public OperationDetailDTO extractUrls(String siteMapUrl) throws SAXException, IOException, ParserConfigurationException;
}
