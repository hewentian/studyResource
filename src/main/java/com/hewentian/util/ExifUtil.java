package com.hewentian.util;

import java.io.File;
import java.io.IOException;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectoryBase;

public class ExifUtil {
	public static void main(String[] args) throws Exception {
		File file = new File("f:/ff2.jpg");
		readExif(file);
	}

	public static void readExif(File file) throws Exception {
		Metadata metadata = ImageMetadataReader.readMetadata(file);
//		 Directory directory =
//		 metadata.getFirstDirectoryOfType(ExifDirectoryBase.class
//		 );

		for (Directory directory : metadata.getDirectories()) {
			for (Tag tag : directory.getTags()) {
				System.out.format("[%s] - %s = %s \n", directory.getName(), tag.getTagName(), tag.getDescription());
			}
		}
	}
}
