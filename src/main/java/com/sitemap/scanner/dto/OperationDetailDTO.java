package com.sitemap.scanner.dto;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperationDetailDTO {
	private BigInteger id;
	private String domainName;
	private BigInteger foundUrlCount;
	private LocalDateTime operationStartTime;
	private LocalDateTime operationEndTime;
	private Duration operationDuration;
}
