package com.makeandbuild.vessl.sample.util;

public class UrlUtil {
	String protocol;
	String host;
	String port;
	String context;
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getBaseUrl() {
		String baseUrl = protocol + "://" + host + ":" + port + "/" + context;

		return baseUrl;
	}
	public String toUrl(String snippet){
		return getBaseUrl() + snippet;
	}
}
