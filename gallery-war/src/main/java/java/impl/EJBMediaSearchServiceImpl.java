package java.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import com.github.huangp.service.MediaSearchService;
import com.google.common.collect.Lists;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * @author Patrick Huang
 *         <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
public class EJBMediaSearchServiceImpl implements MediaSearchService {

    @Inject
    private Client client;


    // TODO need to change wildfly configuration to allow access to folder on local file system http://stackoverflow.com/questions/31545261/wildfly-image-and-http-access-to-show-image-with-websocket
    public List<String> getAllMedia() {
//        http://stackoverflow.com/a/8831494/345718 how to get all documents using paging

        QueryBuilder qb = termQuery("multi", "test");
//        FilterBuilders.termFilter("createdDate")

        TimeValue keepAlive = new TimeValue(10, TimeUnit.MINUTES);
        SearchResponse scrollResp = client.prepareSearch("index(media)")
                .setSearchType(SearchType.SCAN)
                .setScroll(keepAlive)
                .setQuery(qb)
                .setSize(100).execute().actionGet(); //100 hits per shard will be returned for each scroll
//Scroll until no hits are returned
        while (true) {

            for (SearchHit hit : scrollResp.getHits().getHits()) {
                //Handle the hit...
            }
            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(keepAlive).execute().actionGet();
            //Break condition: No hits are returned
            if (scrollResp.getHits().getHits().length == 0) {
                break;
            }
        }

        return Lists.newArrayList();
    }

    public void getOne(String id) {
        GetResponse response = client.prepareGet("index(media)", "type", id)
                .execute()
                .actionGet();
        Map<String, Object> source = response.getSource();
    }
}
