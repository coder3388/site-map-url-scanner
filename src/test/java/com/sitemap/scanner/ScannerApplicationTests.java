package com.sitemap.scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.xml.sax.SAXException;

import com.sitemap.scanner.domain.SiteMapUrlEntry;
import com.sitemap.scanner.dto.SiteMapUrlEntryDTO;
import com.sitemap.scanner.service.SiteMapUrlEntryService;
import com.sitemap.scanner.service.SiteMapUrlExtractorService;

@ContextConfiguration(classes = ScannerApplication.class)
@DataMongoTest
@ExtendWith(SpringExtension.class)
@DirtiesContext
class ScannerApplicationTests {
	
	@Autowired
	private SiteMapUrlEntryService urlEntryService;
	
	@Autowired
	private SiteMapUrlExtractorService siteMapUrlExtractorService;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	void contextLoads() {
		
	}
	
	@BeforeTestClass
	void beforeTests() {
		mongoTemplate.remove(SiteMapUrlEntry.class);
	}
	
//	@Test
	void testCrudServiceMethods_noErrorShouldThrown() {
		SiteMapUrlEntryDTO entry = SiteMapUrlEntryDTO.builder().domainName("hurriyet")
				.url("https://www.hurriyet.com.tr/gundem/usakta-hastaneyi-sarsan-skandal-doktor-tutuklandi-41983666")
				.build();
		SiteMapUrlEntryDTO saved = urlEntryService.save(entry);
		assertNotNull(saved.getId());
		SiteMapUrlEntryDTO foundById = urlEntryService.findById(saved.getId());
		assertNotNull(foundById);
		List<SiteMapUrlEntryDTO> foundsByDomainName = urlEntryService.findByDomainName(foundById.getDomainName());
		assertFalse(foundsByDomainName.isEmpty());;
	}
	
	@Test
	void parsexml_noErrorShouldThrown() throws SAXException, IOException, ParserConfigurationException {
		siteMapUrlExtractorService.extractUrls("https://www.hurriyet.com.tr/sitemaps/newssitemap.xml");
		assertTrue(true);
	}
	
	

}
