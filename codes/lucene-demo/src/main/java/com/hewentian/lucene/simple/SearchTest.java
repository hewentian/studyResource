package com.hewentian.lucene.simple;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchTest {
	private static IndexSearcher searcher = null;
	
	public static void s1() throws Exception {		
		TopDocs topDocs = searcher.search(new TermQuery(new Term("contents", "creating")), 20);
		ScoreDoc[] scoreDoc = topDocs.scoreDocs;

		for (int i = 0, len = scoreDoc.length; i < len; i++) {
			Document doc = searcher.doc(scoreDoc[i].doc);

			System.out.println("path: " + doc.get("path"));
			System.out.println("contents: " + doc.get("contents"));
			System.out.println("doc:" + scoreDoc[i].doc + ", score:" + scoreDoc[i].score + "\n");
		}
	}
	
	public static void s2() throws Exception {
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_48);
		QueryParser queryParser = new QueryParser(Version.LUCENE_48, "contents", analyzer);
		Query query = queryParser.parse("creating");
		TopDocs topDocs = searcher.search(query, 20);
		ScoreDoc[] scoreDoc = topDocs.scoreDocs;

		for (int i = 0, len = scoreDoc.length; i < len; i++) {
			Document doc = searcher.doc(scoreDoc[i].doc);

			System.out.println("path: " + doc.get("path"));
			System.out.println("contents: " + doc.get("contents"));
			System.out.println("doc:" + scoreDoc[i].doc + ", score:" + scoreDoc[i].score + "\n");
		}
	}
	
	public static void s3() throws Exception {
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_48);
		QueryParser queryParser = new QueryParser(Version.LUCENE_48, "contents", analyzer);
		Query query = queryParser.parse("配置环境变量");
		TopDocs topDocs = searcher.search(query, 20);
		ScoreDoc[] scoreDoc = topDocs.scoreDocs;

		for (int i = 0, len = scoreDoc.length; i < len; i++) {
			Document doc = searcher.doc(scoreDoc[i].doc);

			System.out.println("path: " + doc.get("path"));
			System.out.println("contents: " + doc.get("contents"));
			System.out.println("doc:" + scoreDoc[i].doc + ", score:" + scoreDoc[i].score + "\n");
		}
	}

	public static void main(String[] args) throws Exception {
		String indexPath = "E:\\lucene\\luceneIndex"; // 索引的位置
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
		searcher = new IndexSearcher(reader);
		
//		s1();
//		s2();
		s3();
		
	}

}
