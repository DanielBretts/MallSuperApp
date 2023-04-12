package superapp;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -2509731730564940439L;

	public UserNotFoundException() {
	}

	public UserNotFoundException(String message) {
		super(message);
	}

}