package nl.friendshipbench.api.jacksonconverter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Jan-Bert on 24-1-2018.
 */
@JsonComponent
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime>
{
	@Override
	public void serialize(LocalDateTime offsetDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException
	{
		jsonGenerator.writeString(offsetDateTime.format(DateTimeFormatter.ISO_DATE_TIME));
	}
}
