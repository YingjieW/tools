package com.tools.util.ftp;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.OutputStream;

public class FtpOutputStream extends OutputStream {
	
	private String file;
	
	private FTPClient ftp;
	
	private OutputStream out;

	public FtpOutputStream(String file, FTPClient ftp, OutputStream out) {
		super();
		this.file = file;
		this.ftp = ftp;
		this.out = out;
	}

	@Override
	public void write(int b) throws IOException {
		out.write(b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		out.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		out.write(b, off, len);
	}

	@Override
	public void close() throws IOException {
		try {
			out.close();
			if (!ftp.completePendingCommand()) {
				throw new RuntimeException("上传文件失败:" + file + "," + ftp.getReplyString());
			}
		} finally {
			ftp.disconnect();
		}
	}

}
