package demo;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverterOfMapToJson {
	
private ObjectMapper jackson;
	
	public ConverterOfMapToJson() {
		this.jackson = new ObjectMapper();
	}
	
	
	public String convertToDatabaseColumn(Map<String, Object> attribute) {
		try {
			return this.jackson.writeValueAsString(attribute);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Map<String, Object> convertToEntityAttribute(String dbData) {
		try {
			return this.jackson.readValue(dbData, Map.class);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
