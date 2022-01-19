package com.sitemap.scanner.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SiteMapUrlEntryDTO {
	private BigInteger id;
	private String url;
	private String domainName;
	private LocalDateTime createDateTime;
}
