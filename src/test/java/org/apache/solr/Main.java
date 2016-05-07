package org.apache.solr;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.Date;

/**
 * Created by wuzhong on 2016/5/7.
 */
public class Main {

    public static void main(String[] args) {
        CloudSolrServer solrClient = new CloudSolrServer("master.spark.com:2181/solr");
        solrClient.setDefaultCollection("collection1");
        solrClient.setIdField("id");
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", System.currentTimeMillis());
        document.addField("date_dt", new Date());
        document.addField("title", "dc_title");
        document.addField("author", "author");
        document.addField("dynamic_field_txt", "dynamic_field_txt");
        document.addField("ignored_matching_field", "non_matching_field");

        SolrInputDocument document1 = new SolrInputDocument();
        document1.addField("id", "id_fields_test_val_3");
        document1.addField("date_dt", "2016-05-07T10:11:24Z");
        document1.addField("title", "dc_title_fields_test_val_3");
        document1.addField("author", "Hugo%Miguel%Louro_fields_test_val_3");
        document1.addField("dynamic_field_txt", "dynamic_field_fields_test_val_3_txt");
        document1.addField("ignored_matching_field", "non_matching_field_fields_test_val_3");

        try {
            solrClient.deleteByQuery("*:*");
            //solrClient.add(document1);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                solrClient.commit();
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
