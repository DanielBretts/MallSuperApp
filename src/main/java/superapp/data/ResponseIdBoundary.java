package superapp.data;

public class ResponseIdBoundary {
	private String responseId;

	public ResponseIdBoundary() {
	}

	public ResponseIdBoundary(String responseId) {
		super();
		this.responseId = responseId;
	}

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	@Override
	public String toString() {
		return "ResponseIdBoundary [responseId=" + responseId + "]";
	}

}