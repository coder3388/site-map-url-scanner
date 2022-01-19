package com.sitemap.scanner.domain;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "operation-detail")
public class OperationDetail {
	@Transient
    public static final String SEQUENCE_NAME = "operation-detail_sequence";
	@Id
	private BigInteger id;
	private String domainName;
	private BigInteger foundUrlCount;
	private LocalDateTime operationStartTime;
	private LocalDateTime operationEndTime;
	private Duration operationDuration;
}
