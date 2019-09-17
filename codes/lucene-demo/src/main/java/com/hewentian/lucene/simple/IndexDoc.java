package com.hewentian.lucene.simple;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.hewentian.lucene.util.TikaUtil;

/**
 * 
 * <p>
 * <b>IndexDoc</b> 是 索引work文档
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年9月18日 上午11:02:17
 * @since JDK 1.7
 * 
 */
public class IndexDoc {
	public static void main(String[] args) {
		String indexPath = "E:\\lucene\\luceneIndex";
		String docsPath = "E:\\lucene\\docs2\\maven 教程一 入门.doc";

		final File docDir = new File(docsPath);

		try {
			Directory dir = FSDirectory.open(new File(indexPath));
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_48);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_48, analyzer);
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);

			IndexWriter writer = new IndexWriter(dir, iwc);

			Document doc = new Document();

			Field pathField = new StringField("path", docDir.getPath(), Field.Store.YES);
			doc.add(pathField);

			doc.add(new LongField("modified", docDir.lastModified(), Field.Store.NO));

			String content = TikaUtil.extractContent(docDir);
			doc.add(new TextField("contents", content, Field.Store.NO));

			System.out.println("updating " + docDir);
			writer.updateDocument(new Term("path", docDir.getPath()), doc);

			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}