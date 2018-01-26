package nl.friendshipbench.api.jacksonconverter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Jan-Bert on 25-1-2018.
 */
@JsonComponent
public class OffsetDateTimeSerializer extends JsonSerializer<OffsetDateTime>
{
	@Override
	public void serialize(OffsetDateTime offsetDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException
	{
		jsonGenerator.writeString(offsetDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
	}
}

