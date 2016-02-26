package com.github.huangp.media.service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.huangp.media.model.Media;
import com.github.huangp.media.model.MediaFileType;
import com.github.huangp.media.util.JSONObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * @author Patrick Huang
 *         <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@ApplicationScoped
public class EJBMediaSearchServiceImpl implements MediaSearchService {
    private static final Logger log =
            LoggerFactory.getLogger(EJBMediaSearchServiceImpl.class);

    @Inject
    private Client client;
    @Inject
    private JSONObjectMapper objectMapper;

    public static final String INDEX_NAME = "media";

    // 100 hits per shard will be returned for each scroll
    public static final int SIZE = 100;
    public static final TimeValue KEEP_ALIVE =
            new TimeValue(1, TimeUnit.MINUTES);


    @Override
    public List<Media> getAllMedia() {
        QueryBuilder qb = matchAllQuery();
        List<Media> result = Lists.newArrayList();
        query(qb, src -> {
            try {
                Media media = objectMapper.fromJSON(src, Media.class);
                result.add(media);
            } catch (Exception e) {
                log.warn("error unmarshall json source \n{}\n", src, e);
            }
            return null;
        });
        return result;
    }

    private <T> void query(QueryBuilder queryBuilder,
            Function<String, T> sourceToThing) {

//        FilterBuilders.termFilter("createdDate")

        SearchResponse scrollResp = client.prepareSearch(INDEX_NAME)
//                .setSearchType(SearchType.SCAN)
                .setScroll(KEEP_ALIVE)
                .setQuery(queryBuilder)
                .addSort("_doc", SortOrder.DESC)
                .setSize(SIZE)
                .execute().actionGet();

        // Scroll until no hits are returned
        while (true) {
            for (SearchHit hit : scrollResp.getHits().getHits()) {
                log.debug("found 1 hit. ID {}", hit.id());
                // Handle the hit...
                String id = hit.getId();
                String sourceAsString = hit.getSourceAsString();
                log.debug("source:{}", sourceAsString);
                sourceToThing.apply(sourceAsString);
            }
            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId())
                    .setScroll(KEEP_ALIVE).execute().actionGet();
            // Break condition: No hits are returned
            if (scrollResp.getHits().getHits().length == 0) {
                break;
            }
        }
    }

    @Override
    public String getAllMediaAsJSON() {
        QueryBuilder qb = matchAllQuery();
        StringBuilder stringBuilder = new StringBuilder("[");
        query(qb, (str) -> stringBuilder.append(str).append(","));
        // replace last comma
        if (stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
            stringBuilder.setCharAt(stringBuilder.length() - 1, ' ');
        }
        return stringBuilder.append("]").toString();
    }


    @Override
    public String getOneAsJSON(String id) {
        GetResponse response = client.prepareGet(INDEX_NAME, null, id)
                .execute()
                .actionGet();
        return response.getSourceAsString();
    }

    @Override
    public Media getOne(String id) {
        return objectMapper.fromJSON(getOneAsJSON(id), Media.class);
    }

    @Override
    public String search(String fromDate, String toDate, MediaFileType mediaType) {
        RangeQueryBuilder rangeQueryBuilder =
                QueryBuilders.rangeQuery("meta.createdDate").from(fromDate)
                        .to(toDate);
        NestedQueryBuilder qb =
                QueryBuilders.nestedQuery("meta", rangeQueryBuilder);
        List<String> result = Lists.newLinkedList();
        query(qb, result::add);
        return result.toString();
    }
}
