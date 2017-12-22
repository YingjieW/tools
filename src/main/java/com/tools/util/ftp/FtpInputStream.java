package com.tools.util.ftp;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.InputStream;

public class FtpInputStream extends InputStream {
	
	private String file;
	
	private FTPClient ftp;
	
	private InputStream in;

	public FtpInputStream(String file, FTPClient ftp, InputStream in) {
		super();
		this.file = file;
		this.ftp = ftp;
		this.in = in;
	}

	@Override
	public int read() throws IOException {
		return in.read();
	}

	@Override
	public int read(byte[] b) throws IOException {
		return in.read(b);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return in.read(b, off, len);
	}



	@Override
	public void close() throws IOException {
		try {
			in.close();
			if (!ftp.completePendingCommand()) {
				throw new RuntimeException("下载文件失败:" + file + "," + ftp.getReplyString());
			}
		} finally {
			ftp.disconnect();
		}
	}

}
