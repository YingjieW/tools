package com.tools.util.ftp;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class FtpUtils {

	/**
	 * 使用类似 ftp://user:password@127.0.0.1:21/file/path 的路径创建FTPClient
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static FTPClient buildFtpClient(URL url) throws IOException {
		if (!url.getProtocol().equals("ftp")) {
			throw new RuntimeException("不是ftp协议");
		}

		int port = url.getPort();
		if (port == -1) {
			port = url.getDefaultPort();
		}

		String username = "";
		String password = "";
		if (url.getUserInfo() != null) {
			String[] t = url.getUserInfo().split(":");
			username = URLDecoder.decode(t[0], "utf-8");
			password = URLDecoder.decode(t[1], "utf-8");
//			password= AESUtil.decrypt(password, CheckFlowConfigManager.encrypt_key);
		}

		FTPClient client = new FTPClient();
		try {
			client.connect(url.getHost(), port);
			if (!client.login(username, password)) {
				throw new IOException("ftp登录失败:" + username);
			}

		} catch (IOException e) {
			client.disconnect();
			throw e;
		}

		return client;
	}

	public static File tempFile() {
		return tempFile(null);
	}

	/**
	 * 返回一个临时文件
	 * 
	 * @return
	 */
	public static File tempFile(String suffix) {
		String s = new SimpleDateFormat("yyyyMMdd").format(new Date());
		try {
			return File.createTempFile(s, suffix);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static File downloadGZIP(URL url) {
		File temp = downloadFile(url);
		try {
			return ungzip(temp);
		} finally {
			temp.delete();
		}
	}

	/**
	 * 从ftp服务器上下载一个文件到临时目录
	 * 
	 * @return
	 */
	public static File downloadFile(URL url) {
		FileOutputStream output =null ;
		InputStream input = null;
		try {
			input = retrieve(url);
			File temp = tempFile();
			output=new FileOutputStream(temp);
			FtpInputStream fi=(FtpInputStream)input;
			IOUtils.copy(input, output);
			return temp;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(input);
		}
	}

	public static void storeGZIP(URL url, File file, boolean overwrite) {

		FileInputStream input = null;
		try {
			input = new FileInputStream(file);
			storeGZIP(url, input, overwrite);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(input);
		}
	}

	public static void storeGZIP(URL url, InputStream input, boolean overwrite) {
		OutputStream out = null;
		try {
			out = store(url, overwrite);
			GZIPOutputStream gzip = new GZIPOutputStream(out);
			IOUtils.copy(input, gzip);
			gzip.finish();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	public static void storeFile(URL url, File file, boolean overwrite) {

		FileInputStream input = null;
		try {
			input = new FileInputStream(file);
			storeFile(url, input, overwrite);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(input);
		}
	}

	public static void storeFile(URL url, InputStream input, boolean overwrite) {

		OutputStream output = null;
		try {
			output = store(url, overwrite);
			IOUtils.copy(input, output);
		} catch (IOException e) {
			throw new RuntimeException("上传文件失败:" + url, e);
		} finally {
			IOUtils.closeQuietly(output);
		}
	}

	public static OutputStream store(URL url, boolean overwrite) {

		checkUrl(url);

		FTPClient ftp = null;
		OutputStream out = null;
		File file = null;
		try {
			ftp = FtpUtils.buildFtpClient(url);
			ftp.enterLocalPassiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);

			file = new File(url.getPath().substring(1));

			String[] names = null;
			if (file.getParent() == null) {
				names = ftp.listNames();
			} else {
				names = ftp.listNames(file.getParent());
			}
			ftp.listNames(url.getPath());
			if (!overwrite && Arrays.asList(names).contains(file.toString())) {
				throw new RuntimeException("文件已存在:" + file);
			}

			// 创建目录
			if (file.getParentFile() != null) {
				mkdirs(ftp, file.getParent());
			}

			out = ftp.storeFileStream(file.toString());
			if (out == null) {
				throw new IOException(ftp.getReplyString());
			}
		} catch (IOException e) {
			throw new RuntimeException("上传文件失败:" + url, e);
		} finally {
			if (out == null) {
				try {
					ftp.disconnect();
				} catch (Exception e) {
				}
			}
		}

		return new FtpOutputStream(file.toString(), ftp, out);
	}

	public static InputStream retrieveGZIP(URL url) {
		InputStream in = retrieve(url);
		try {
			return new GZIPInputStream(in);
		} catch (IOException e) {
			IOUtils.closeQuietly(in);
			throw new RuntimeException(e);
		}
	}

	public static InputStream retrieve(URL url) {

		checkUrl(url);

		FTPClient ftp = null;
		InputStream in = null;
		String filepath = null;
		try {
			ftp = FtpUtils.buildFtpClient(url);
			ftp.enterLocalPassiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			filepath = url.getPath().substring(1);
			in = ftp.retrieveFileStream(filepath);
			if (in == null) {
				throw new IOException(ftp.getReplyString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (in == null) {
				try {
					ftp.disconnect();
				} catch (Exception e) {
				}
			}
		}

		return new FtpInputStream(filepath, ftp, in);
	}

	private static void mkdirs(FTPClient client, String path) throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		for (String s : path.split("/")) {
			if (!StringUtils.isEmpty(s)) {
				list.add(s);
				String t = StringUtils.join(list, '/');
				if (!client.makeDirectory(t)) {
					// 可能是目录已存在
				}
			}
		}
	}

	private static File ungzip(File file) {
		FileInputStream input = null;
		try {
			input = new FileInputStream(file);
			return ungzip(input);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(input);
		}
	}

	private static File ungzip(InputStream input) {
		File file = tempFile();
		FileOutputStream output = null;
		try {
			output = new FileOutputStream(file);
			GZIPInputStream gzip = new GZIPInputStream(input);
			IOUtils.copy(gzip, output);
			output.flush();
			return file;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(output);
		}
	}


	public static void deleteFile(URL url) {
		FTPClient ftp = null;
		try {
			ftp = FtpUtils.buildFtpClient(url);
			ftp.deleteFile(url.getPath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				ftp.disconnect();
			} catch (Exception e) {
			}
		}
	}

	private static void checkUrl(URL url) {
		if (url == null) {
			throw new RuntimeException("url为空");
		}
		if (StringUtils.isEmpty(url.getPath())) {
			throw new RuntimeException("url path 为空");
		}
		if (url.getPath().endsWith("/")) {
			throw new RuntimeException("url不能以/结尾");
		}
	}
}
