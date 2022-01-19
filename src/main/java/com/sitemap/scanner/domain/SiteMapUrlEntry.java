package com.sitemap.scanner.domain;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "sitemap-urlentry")
public class SiteMapUrlEntry {
	
	@Transient
    public static final String SEQUENCE_NAME = "sitemap-urlentry_sequence";
	
	@Id
	private BigInteger id;
	private String url;
	private String domainName;
	private LocalDateTime createDateTime;
}
