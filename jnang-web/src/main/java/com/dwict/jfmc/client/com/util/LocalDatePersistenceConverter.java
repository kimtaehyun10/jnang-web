package com.dwict.jfmc.client.com.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDatePersistenceConverter implements javax.persistence.AttributeConverter<LocalDate, Date> {

	@Override
	public Date convertToDatabaseColumn(LocalDate entityValue) {
		return Date.from(entityValue.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	@Override
	public LocalDate convertToEntityAttribute(Date databaseValue) {
		return Instant.ofEpochMilli(databaseValue.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

}
