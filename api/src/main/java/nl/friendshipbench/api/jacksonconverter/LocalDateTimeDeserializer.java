package nl.friendshipbench.api.jacksonconverter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Jan-Bert on 24-1-2018.
 */
@JsonComponent
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime>
{
	@Override
	public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException
	{
		String isoDateString = jsonParser.readValueAs(String.class);

		return LocalDateTime.parse(isoDateString, DateTimeFormatter.ISO_DATE_TIME);
	}
}
