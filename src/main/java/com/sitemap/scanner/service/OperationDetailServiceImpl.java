package com.sitemap.scanner.service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.sitemap.scanner.domain.DatabaseSequence;
import com.sitemap.scanner.domain.OperationDetail;
import com.sitemap.scanner.dto.OperationDetailDTO;
import com.sitemap.scanner.repository.OperationDetailRepository;

@Service
public class OperationDetailServiceImpl implements OperationDetailService {
	@Autowired
	private OperationDetailRepository opetailRepo;
	
	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public OperationDetailDTO save(OperationDetailDTO willBeSaved) {
		if (willBeSaved == null) {
			throw new IllegalArgumentException("SiteMapUrlEntryService->save has been tried with null param!");
		}
		Optional<OperationDetail> willBeSavedDom = Optional.empty();
		if (willBeSaved.getId() == null) {
			OperationDetail domain = toDomain(willBeSaved);
			domain.setId(generateSequence(OperationDetail.SEQUENCE_NAME));
			willBeSavedDom = Optional.of(domain);
		} else {
			willBeSavedDom = Optional.of(toDomain(willBeSaved));
		}
		OperationDetail saved = opetailRepo.save(willBeSavedDom.get());
		return toDto(saved);
	}

	@Override
	public OperationDetailDTO findById(BigInteger id) {
		Optional<OperationDetail> foundById = opetailRepo.findById(id);
		if (foundById.isEmpty()) {
			return null;
		} else {
			return toDto(foundById.get());
		}
	}

	@Override
	public List<OperationDetailDTO> findAll() {
		return opetailRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
	}

	@Override
	public List<OperationDetailDTO> findByDomainName(String domainName) {
		return opetailRepo.findByDomainName(domainName).stream().map(this::toDto).collect(Collectors.toList());
	}
	
	
	//--helper methods
	private OperationDetailDTO toDto(OperationDetail foundById) {
		if (foundById == null) {
			return null;
		}
		return OperationDetailDTO.builder()
				.id(foundById.getId())
				.domainName(foundById.getDomainName())
				.foundUrlCount(foundById.getFoundUrlCount())
				.operationDuration(foundById.getOperationDuration())
				.operationStartTime(foundById.getOperationStartTime())
				.operationEndTime(foundById.getOperationEndTime())
				.build();
		
	}

	private OperationDetail toDomain(OperationDetailDTO willBeSaved) {
		if (willBeSaved == null) {
			return null;
		}
		
		return OperationDetail.builder()
		.id(willBeSaved.getId())
		.domainName(willBeSaved.getDomainName())
		.foundUrlCount(willBeSaved.getFoundUrlCount())
		.operationDuration(willBeSaved.getOperationDuration())
		.operationStartTime(willBeSaved.getOperationStartTime())
		.operationEndTime(willBeSaved.getOperationEndTime())
		.build();
	}
	
	public BigInteger generateSequence(String seqName) {

        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : BigInteger.ONE;

    }

}
