package com.github.huangp.service.impl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.test.junit.;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.test.hamcrest.ElasticsearchAssertions.assertHitCount;

//@ElasticsearchIntegrationTest.ClusterScope(scope = ElasticsearchIntegrationTest.Scope.SUITE)
public class EJBMediaSearchServiceImplTest
//        extends  ElasticsearchIntegrationTest
{
//    private static final Logger log =
//            LoggerFactory.getLogger(EJBMediaSearchServiceImplTest.class);
//
////    @Override
////    protected Settings nodeSettings(int nodeOrdinal) {
////        return ImmutableSettings.settingsBuilder()
////                .put(super.nodeSettings(nodeOrdinal))
////                .put("number_of_shards", 1)
////                .put("number_of_replicas", 1)
////                .build();
////    }
//
//    // for our test we want to make sure the config path of the cluster actually points
//    // to the test resources that we provide - this is the cluster modification referred
//    // to earlier
//    @Override
//    public Settings nodeSettings(int nodeOrdinal) {
//        String configFolder = Thread.currentThread().getContextClassLoader()
//                .getResource("config").getFile();
//        return Settings.settingsBuilder().put(super.nodeSettings(nodeOrdinal))
//                .put("path.conf", configFolder).build();
//    }
//
//    @Before
//    public void setupData() throws IOException {
//
//        createIndex("media");
//        ensureGreen("media");
//
//        index("media", "test_type", "1",
//                jsonBuilder().startObject().field("key1",
//                        "value1").endObject()
//        );
//
//        refresh();
//    }

//    @Test
//    public void test() {
//        SearchRequest searchRequest = new SearchRequest("media");
//        ActionFuture<SearchResponse> searchResp = client().search(searchRequest);
//        SearchResponse searchResponse =
//                searchResp.actionGet(5, TimeUnit.SECONDS);
//        log.info(">>>>>>>> search result:{}", searchResponse.toString());
//        assertHitCount(searchResponse, 1);
//    }

}
