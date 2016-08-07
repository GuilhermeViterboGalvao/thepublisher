package com.publisher.pagecache;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

public class ServletOutputStreamCopier extends ServletOutputStream {

    private OutputStream outputStream;
    
    private ByteArrayOutputStream copy;

    public ServletOutputStreamCopier(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.copy = new ByteArrayOutputStream(1024);
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
        copy.write(b);
    }

    public byte[] getCopy() {
        return copy.toByteArray();
    }
    
    @Override
    public void close() throws IOException {
    	super.close();
    	outputStream.close();
    	copy.close();        
    }
    
    @Override
    public void flush() throws IOException {
    	super.flush();
    	outputStream.flush();
    	copy.flush();
    }
}