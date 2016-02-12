package com.github.huangp.media.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.huangp.media.model.Media;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * @author Patrick Huang
 *         <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@RequestScoped
public class EJBMediaSearchServiceImpl implements MediaSearchService {
    private static final Logger log =
            LoggerFactory.getLogger(EJBMediaSearchServiceImpl.class);

    @Inject
    private Client client;
    private static final ObjectMapper objectMapper = new ObjectMapper();


    // TODO need to change wildfly configuration to allow access to folder on local file system http://stackoverflow.com/questions/31545261/wildfly-image-and-http-access-to-show-image-with-websocket
    @Override
    public List<Media> getAllMedia() {
        List<Media> result = Lists.newArrayList();
        searchAll(src -> {
            try {
                Media media = objectMapper.readValue(src, Media.class);
                result.add(media);
            } catch (IOException e) {
                log.debug("error unmarshall json source", e);
                log.warn("can not handle (skipped): {}", src);
            }
            return null;
        });
        return result;
    }

    private <T> void searchAll(Function<String, T> sourceToThing) {
        TimeValue keepAlive = new TimeValue(10, TimeUnit.MINUTES);

        QueryBuilder qb = matchAllQuery();
//        FilterBuilders.termFilter("createdDate")

        String indexName = "media";
        SearchResponse scrollResp = client.prepareSearch(indexName)
                .setSearchType(SearchType.SCAN)
                .setScroll(keepAlive)
                .setQuery(qb)
                .setSize(100).execute().actionGet(); //100 hits per shard will be returned for each scroll
//Scroll until no hits are returned


        while (true) {
            for (SearchHit hit : scrollResp.getHits().getHits()) {
                log.debug("found 1 hit. ID {}", hit.id());
                //Handle the hit...
                String id = hit.getId();
                String sourceAsString = hit.getSourceAsString();
                log.debug("source:{}", sourceAsString);
                sourceToThing.apply(sourceAsString);
            }
            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(keepAlive).execute().actionGet();
            //Break condition: No hits are returned
            if (scrollResp.getHits().getHits().length == 0) {
                break;
            }
        }
    }

    @Override
    public String getAllMediaAsJSON() {
        StringBuilder stringBuilder = new StringBuilder();
        searchAll(stringBuilder::append);
        return stringBuilder.toString();
    }


    @Override
    public void getOne(String id) {
        GetResponse response = client.prepareGet("index(media)", "type", id)
                .execute()
                .actionGet();
        Map<String, Object> source = response.getSource();
    }
}
