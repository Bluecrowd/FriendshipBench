package nl.friendshipbench.api.jpacustomconverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by Jan-Bert on 23-1-2018.
 */
@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date>
{

	@Override
	public Date convertToDatabaseColumn(LocalDate locDate) {
		return (locDate == null ? null : Date.valueOf(locDate));
	}

	@Override
	public LocalDate convertToEntityAttribute(Date sqlDate) {
		return (sqlDate == null ? null : sqlDate.toLocalDate());
	}
}