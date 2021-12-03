package com.bpaas.doc.framework.base.util;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtil {
	/**
	 * 压缩字符串
	 * 
	 * @param str
	 * @param charest
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] compress(String str, String charset) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		try {
			gzip.write(str.getBytes(charset));
			gzip.close();
			return out.toByteArray();
		} finally {
			if(gzip != null) gzip.close();
			if(out != null) out.close();
		}
	}

	/**
	 * 解压缩字符串
	 * 
	 * @param str
	 * @param charest
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public static byte[] uncompress(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPInputStream gzip = new GZIPInputStream(in);
		try {
			byte[] buffer = new byte[256];
			int n;
			while ((n = gzip.read(buffer)) >= 0) {
				out.write(buffer, 0, n);
			}
			return out.toByteArray();
		} finally {
			if(gzip != null) gzip.close();
			if(out != null) out.close();
			if(in != null) in.close();
		}
	}
	
	public static void main(String[] args) {
//		byte[] _byte=GzipUtil.compress(json,"UTF-8");
//		response.getOutputStream().write(_byte);
//		response.getOutputStream().flush();
//		response.getOutputStream().close();
	}
}
