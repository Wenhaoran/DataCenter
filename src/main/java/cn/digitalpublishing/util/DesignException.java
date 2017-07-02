package cn.digitalpublishing.util;

public class DesignException extends Exception {
	
	private static final long serialVersionUID = -81469461416709765L;
	
	private String prompt;

	public DesignException() {
	}

	public DesignException(String prompt) {
		this.prompt = prompt;
	}

	public DesignException(String prompt, Throwable cause) {
		super(cause);
		this.prompt = prompt;
	}

	public String getPrompt() {
		return this.prompt;
	}
}