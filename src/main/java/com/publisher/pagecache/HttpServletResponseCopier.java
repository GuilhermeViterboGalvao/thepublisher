package com.publisher.pagecache;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class HttpServletResponseCopier extends HttpServletResponseWrapper {

	private ServletOutputStream outputStream;
	
	private ServletOutputStreamCopier copier;
	
	private PrintWriter writer;

	public HttpServletResponseCopier(HttpServletResponse response) throws IOException {
		super(response);
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (writer != null) {
			throw new IllegalStateException("getWriter() has already been called on this response.");
		}
		if (outputStream == null) {
			outputStream = getResponse().getOutputStream();
			copier = new ServletOutputStreamCopier(outputStream);
		}
		return copier;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (outputStream != null) {
			throw new IllegalStateException("getOutputStream() has already been called on this response.");
		}
		if (writer == null) {
			copier = new ServletOutputStreamCopier(getResponse().getOutputStream());
			writer = new PrintWriter(new OutputStreamWriter(copier, getResponse().getCharacterEncoding()), true);
		}
		return writer;
	}
	
	@Override
	public void flushBuffer() throws IOException {
		if (writer != null) {
			writer.flush();
			writer.close();
		} else if (outputStream != null) {
			copier.flush();
			copier.close();
		}
	}

	public byte[] getCopy() {
		if (copier != null) {
			return copier.getCopy();
		} else {
			return new byte[0];
		}
	}
}