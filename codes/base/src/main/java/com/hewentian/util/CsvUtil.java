package com.hewentian.util;

import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStream;
import java.net.URLEncoder;

import java.sql.*;
import java.util.Properties;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * <p>
 * <b>CsvUtil</b> 是 Csv工具类
 * </p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016-11-12 16:05:40
 * @since JDK 1.8
 */
public class CsvUtil {
    Logger logger = Logger.getLogger(CsvUtil.class);
    private static String dir = "/home/hewentian/Documents/";

    /**
     * http://csvjdbc.sourceforge.net/
     *
     * @throws Exception
     */
    public static void csvJdbcRead() throws Exception {
        // Load the driver.
        Class.forName("org.relique.jdbc.csv.CsvDriver");

        Properties props = new java.util.Properties();
        props.put("separator", "|");              // separator is a bar
        props.put("suppressHeaders", "true");     // first line contains data
        props.put("fileExtension", ".txt");       // file extension is .txt

        // Create a connection to directory given as first command line
        // parameter. Driver properties are passed in URL format
        // (or alternatively in a java.utils.Properties object).
        //
        // A single connection is thread-safe for use by several threads.
        String url = "jdbc:relique:csv:" + dir + "?separator=,&fileExtension=.csv";
        Connection conn = DriverManager.getConnection(url);
//        Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + dir, props);

        // Create a Statement object to execute the query with.
        // A Statement is not thread-safe.
        Statement stmt = conn.createStatement();

        // Select the ID and NAME columns from sample.csv
        ResultSet rs = stmt.executeQuery("SELECT ID,NAME FROM sample");

        // Dump out the results to a CSV file with the same format
        // using CsvJdbc helper function
//        boolean append = true;
//        CsvDriver.writeToCsv(rs, System.out, append);
        System.out.println("ID,NAME");
        while (rs.next()) {
            System.out.println(rs.getString("ID") + "," + rs.getString("NAME"));
        }

        // Clean up
        rs.close();
        stmt.close();
        conn.close();
    }

    /**
     * http://opencsv.sourceforge.net/
     *
     * @throws Exception
     */
    public static void opencsvRead() throws Exception {
        CSVReader reader = new CSVReader(new FileReader(dir + "sample.csv"), ',');
        String[] nextLine;

        while ((nextLine = reader.readNext()) != null) {
            // nextLine[] is an array of values from the line
            System.out.println(nextLine[0] + "," + nextLine[1]);
        }
    }

    public static void opencsvWrite() throws Exception {
        CSVWriter writer = new CSVWriter(new FileWriter(dir + "sample1.csv"), ',');

        // feed in your array (or convert your data to an array)
        String[] entries = "ID#NAME#SEX".split("#");
        writer.writeNext(entries);
        writer.close();
    }

    public void hostDownStatSumDown(HttpServletResponse response, String startTime, String endTime) throws Exception {
        StringBuilder sb = new StringBuilder("日期,下载数,处理数,修改数,新增数");

        sb.append(System.getProperty("line.separator")); // 换行用这个
        sb.append("1,2,3,4,5");
        sb.append(System.getProperty("line.separator"));
        sb.append("10,20,30,40,50");

        byte[] bytes = sb.toString().getBytes("UTF-8");

        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        int contentLength = bais.available();
        OutputStream os = null;

        try {
            String filename = "数据汇总-20161112.csv";
            response.setContentType("application/force-download;charset=UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            response.setContentLength(contentLength + 3); // bom has 3
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.setCharacterEncoding("UTF-8");
            os = response.getOutputStream();

            byte[] b = new byte[1024];
            int len = 0;
            os.write(0xEF);
            os.write(0xBB);// bom EF BB BF
            os.write(0xBF);
            while ((len = bais.read(b)) != -1) {
                os.write(b, 0, len);
            }
        } catch (Exception e) {
            logger.error("数据汇总", e);
        } finally {
            if (null != bais) {
                try {
                    bais.close();
                } catch (Exception e2) {
                    logger.error("数据汇总", e2);
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (Exception e2) {
                    logger.error("数据汇总", e2);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        csvJdbcRead();
        opencsvRead();
        opencsvWrite();
    }
}
