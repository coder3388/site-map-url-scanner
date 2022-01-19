package com.sitemap.scanner.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.sitemap.scanner.dto.SiteMapUrlEntryDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NewsUrlHandler extends DefaultHandler {
	private final SiteMapUrlEntryService urlEntryService;
	private final String domainName;
	private final List<CompletableFuture> asyncOperationList = new ArrayList<>();
	private BigInteger foundUrlCount = BigInteger.ZERO;
	
	private LocalDateTime start;
	private LocalDateTime end;
	
	private boolean isLoc = false;
	
	public NewsUrlHandler(SiteMapUrlEntryService urlEntryService, String domainName) {
		this.urlEntryService = urlEntryService;
		this.domainName = domainName;
	}
	
	public LocalDateTime getStart() {
		return start;
	}
	
	public LocalDateTime getEnd() {
		return end;
	}

	@Override
	public void startDocument() throws SAXException {
		start = LocalDateTime.now();
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("loc")) {
			isLoc = true;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (isLoc) {
			final String newsUrl = new String(ch, start, length).trim();
			log.info("Loc : {}", newsUrl);
			
			CompletableFuture<SiteMapUrlEntryDTO> asyncSaveEntry = CompletableFuture.supplyAsync(() -> {
				return createAndSaveNewsEntry(newsUrl);
			});
			asyncOperationList.add(asyncSaveEntry);
			foundUrlCount = getFoundUrlCount().add(BigInteger.ONE);
			isLoc = false;
		}
	}

	private SiteMapUrlEntryDTO createAndSaveNewsEntry(String newsUrl) {
		SiteMapUrlEntryDTO createdEntry = SiteMapUrlEntryDTO.builder().domainName(domainName).url(newsUrl).build();
		return urlEntryService.save(createdEntry);
	}
	
	@Override
	public void endDocument() throws SAXException {
		CompletableFuture.allOf(asyncOperationList.toArray(new CompletableFuture[asyncOperationList.size()])).join();
		end = LocalDateTime.now();
		super.endDocument();
	}

	public BigInteger getFoundUrlCount() {
		return foundUrlCount;
	}

}
