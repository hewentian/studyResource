package com.hewentian.util;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.sql.Connection;
import java.sql.Statement;

/**
 * <p>
 * <b>DistributedTransactionDemo</b> 是 分布式事务的示例
 * </p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2019-07-10 19:35:19
 * @since JDK 1.8
 */
public class DistributedTransactionDemo {
    public static MysqlXADataSource getDataSource(String connUrl, String userId, String passwd) {
        try {
            MysqlXADataSource ds = new MysqlXADataSource();
            ds.setUrl(connUrl);
            ds.setUser(userId);
            ds.setPassword(passwd);

            return ds;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String connUrl1 = "jdbc:mysql://192.168.1.101:3306/bank_gz?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull";
        String connUrl2 = "jdbc:mysql://192.168.1.102:3306/bank_sz?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull";

        try {
            MysqlXADataSource ds1 = getDataSource(connUrl1, "scott", "abc123");
            MysqlXADataSource ds2 = getDataSource(connUrl2, "tiger", "abc123");

            XAConnection xaConn1 = ds1.getXAConnection();
            XAResource xaRes1 = xaConn1.getXAResource();
            Connection conn1 = xaConn1.getConnection();
            Statement stmt1 = conn1.createStatement();

            XAConnection xaConn2 = ds2.getXAConnection();
            XAResource xaRes2 = xaConn2.getXAResource();
            Connection conn2 = xaConn2.getConnection();
            Statement stmt2 = conn2.createStatement();

            Xid xid1 = new MyXid(100, new byte[]{0x01}, new byte[]{0x02});
            Xid xid2 = new MyXid(100, new byte[]{0x11}, new byte[]{0x12});

            try {
                xaRes1.start(xid1, XAResource.TMNOFLAGS);
                stmt1.execute("UPDATE account SET money = money - 100 WHERE user = 'zhangsan'");
                xaRes1.end(xid1, XAResource.TMSUCCESS);

                xaRes2.start(xid2, XAResource.TMNOFLAGS);
                stmt2.execute("UPDATE account SET money = money + 100 WHERE user = 'lisi'");
                xaRes2.end(xid2, XAResource.TMSUCCESS);

                int ret2 = xaRes2.prepare(xid2);
                int ret1 = xaRes1.prepare(xid1);

                if (ret1 == XAResource.XA_OK && ret2 == XAResource.XA_OK) {
                    xaRes1.commit(xid1, false);
                    xaRes2.commit(xid2, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MyXid implements Xid {
    public int formatId;
    public byte[] globalTransactionId;
    public byte[] branchQualifier;

    public MyXid() {
    }

    public MyXid(int formatId, byte[] globalTransactionId, byte[] branchQualifier) {
        this.formatId = formatId;
        this.globalTransactionId = globalTransactionId;
        this.branchQualifier = branchQualifier;
    }

    @Override
    public int getFormatId() {
        return formatId;
    }

    @Override
    public byte[] getGlobalTransactionId() {
        return globalTransactionId;
    }

    @Override
    public byte[] getBranchQualifier() {
        return branchQualifier;
    }
}
