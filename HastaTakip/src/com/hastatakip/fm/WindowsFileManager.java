package com.hastatakip.fm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;

import com.hastatakip.Util;
import com.hastatakip.model.entity.Sube;

public class WindowsFileManager implements FileManagerIntf {

	private final Logger logger = Logger.getLogger(WindowsFileManager.class);

	public void copyFiles(Sube sube, String fileName) throws Exception {
		try {

			String sourcePath = sube.getUrl() + "FisIptal" + fileName + ".txt";
			String destinationPath = Util.getProp("waitingFilesPath")
					+ Util.getProp("directorySeperator")
					+ Util.windowsFilePrefix + sube.getId()
					+ Util.waitingFilePrefix + "FisIptal" + fileName + ".txt";

			logger.debug("Try to copy-" + destinationPath + "-" + sourcePath);

			final FileOutputStream fileOutputStream = new FileOutputStream(
					destinationPath);

			File yourFile = new File(destinationPath);
			if (!yourFile.exists()) {
				yourFile.createNewFile();
				logger.debug("Destination file created" + destinationPath);
			}
			
			final FileInputStream fileInputStream = new FileInputStream(
					new File(sourcePath));

			final byte[] buf = new byte[16 * 1024 * 1024];
			int len;
			while ((len = fileInputStream.read(buf)) > 0) {
				fileOutputStream.write(buf, 0, len);
			}

			sourcePath = sube.getUrl() + "SatirIptal" + fileName + ".txt";
			destinationPath = Util.getProp("waitingFilesPath")
					+ Util.getProp("directorySeperator")
					+ Util.windowsFilePrefix + sube.getId()
					+ Util.waitingFilePrefix + "SatirIptal" + fileName + ".txt";

			logger.debug("Try to copy-" + destinationPath + "-" + sourcePath);
			
			FileInputStream fileInputStream2 = new FileInputStream(new File(
					sourcePath));
			

			File yourFile2 = new File(destinationPath);
			if (!yourFile2.exists()) {
				yourFile2.createNewFile();
				logger.debug("Destination file created" + destinationPath);
			}

			FileOutputStream fileOutputStream2 = new FileOutputStream(
					destinationPath);
			

			final byte[] buf2 = new byte[16 * 1024 * 1024];
			int len2;
			while ((len2 = fileInputStream2.read(buf2)) > 0) {
				fileOutputStream2.write(buf2, 0, len2);
			}

			fileOutputStream2.close();
			fileInputStream2.close();
			fileInputStream.close();
			fileOutputStream.close();

		} catch (Exception e) {
			logger.error("While copy file Sube:" + sube.toString() + "-"
					+ e.getMessage());
			throw e;
		}
	}
}
