package com.sitemap.scanner.service;

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
import com.sitemap.scanner.domain.SiteMapUrlEntry;
import com.sitemap.scanner.dto.SiteMapUrlEntryDTO;
import com.sitemap.scanner.repository.SiteMapUrlEntryRepository;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SiteMapUrlEntryServiceImpl implements SiteMapUrlEntryService {
	@Autowired
	private SiteMapUrlEntryRepository siteMapUrlEntryRepo;
	
	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public SiteMapUrlEntryDTO save(SiteMapUrlEntryDTO willBeSaved) {
		if (willBeSaved == null) {
			throw new IllegalArgumentException("SiteMapUrlEntryService->save has been tried with null param!");
		}
		Optional<SiteMapUrlEntry> willBeSavedDom = Optional.empty();
		if (willBeSaved.getId() == null) {
			willBeSavedDom = Optional.of(SiteMapUrlEntry.builder().domainName(willBeSaved.getDomainName())
					.url(willBeSaved.getUrl()).id(generateSequence(SiteMapUrlEntry.SEQUENCE_NAME)).build());
		} else {
			willBeSavedDom = Optional.of(toDomain(willBeSaved));
		}
		SiteMapUrlEntry saved = siteMapUrlEntryRepo.save(willBeSavedDom.get());
		return toDto(saved);
	}

	@Override
	public SiteMapUrlEntryDTO findById(BigInteger id) {
		Optional<SiteMapUrlEntry> foundById = siteMapUrlEntryRepo.findById(id);
		if (foundById.isEmpty()) {
			return null;
		} else {
			return toDto(foundById.get());
		}
	}

	@Override
	public List<SiteMapUrlEntryDTO> findAll() {
		return siteMapUrlEntryRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
	}

	@Override
	public List<SiteMapUrlEntryDTO> findByDomainName(String domainName) {
		return siteMapUrlEntryRepo.findByDomainName(domainName).stream().map(this::toDto).collect(Collectors.toList());
	}
	
	
	//--helper methods
	private SiteMapUrlEntryDTO toDto(SiteMapUrlEntry foundById) {
		if (foundById == null) {
			return null;
		}
		return SiteMapUrlEntryDTO.builder().createDateTime(foundById.getCreateDateTime())
				.domainName(foundById.getDomainName()).id(foundById.getId()).url(foundById.getUrl()).build();
	}

	private SiteMapUrlEntry toDomain(SiteMapUrlEntryDTO willBeSaved) {
		if (willBeSaved == null) {
			return null;
		}
		return SiteMapUrlEntry.builder().id(willBeSaved.getId()).createDateTime(willBeSaved.getCreateDateTime())
				.domainName(willBeSaved.getDomainName()).url(willBeSaved.getUrl()).build();
	}
	
	public BigInteger generateSequence(String seqName) {

        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : BigInteger.ONE;

    }

}
