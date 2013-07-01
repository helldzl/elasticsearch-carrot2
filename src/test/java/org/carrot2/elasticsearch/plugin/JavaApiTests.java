package org.carrot2.elasticsearch.plugin;

import java.io.IOException;

import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.testng.annotations.Test;

/**
 * Java API tests.
 */
public class JavaApiTests extends AbstractApiTest {
    @Test
    public void testJavaApiViaLocalClient() throws Exception {
        testApiViaClient(localClient);
    }

    @Test
    public void testJavaApiViaTransportClient() throws Exception {
        testApiViaClient(transportClient);
    }

    private static void testApiViaClient(Client client) throws IOException {
        Carrot2ClusteringActionResponse result = new Carrot2ClusteringActionRequestBuilder(client)
            .setQueryHint("data mining")
            .addFieldMapping("title", LogicalField.TITLE)
            .addHighlightedFieldMapping("content", LogicalField.CONTENT)
            .setSearchRequest(
              client.prepareSearch()
                    .setIndices(INDEX_NAME)
                    .setTypes("test")
                    .setSize(100)
                    .setQuery(QueryBuilders.termQuery("_all", "data"))
                    .setHighlighterPreTags("")
                    .setHighlighterPostTags("")
                    .addField("title")
                    .addHighlightedField("content"))
            .execute().actionGet();
    
        checkValid(result);
        checkJsonSerialization(result);
    }
}