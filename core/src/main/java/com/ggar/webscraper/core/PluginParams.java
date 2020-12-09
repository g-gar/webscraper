package com.ggar.webscraper.core;

public abstract class PluginParams<T extends PluginOperations> {

	private final T operation;
	private final String value;

	public PluginParams(T operation, String value) {
		this.operation = operation;
		this.value = value;
	}

	public T getOperation() {
		return operation;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PluginParams [operation=").append(operation).append(", value=").append(value).append("]");
		return builder.toString();
	}
	
}
