package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.poi.enums.Category;
import com.poi.external.hereMaps.HereMapsClient;
import com.poi.pojo.GeoCoderPojo.Geometry;
import com.poi.pojo.HereMapsPojo;
import com.poi.pojo.HereMapsPojo.Item;
import com.poi.pojo.HereMapsPojo.Results;
import com.poi.util.HttpRequestUtil;
import java.net.URI;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

public class HereMapsClientTest {

    @InjectMocks
    private HereMapsClient hereMapsClient;

    @Mock
    private HttpRequestUtil httpRequestUtil;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetPoiByCategory() {
        Geometry geometry = Geometry.getObject(Arrays.asList(17.3616079, 78.4746286));
        Item item = Item.builder()
            .distance(736)
            .href("https://places.demo.api.here.com/places/v1/places/356t4x7z-78b4458d6ea60203ced5561ce4657c91;context=Zmxvdy1pZD0xOGUyYmI1OC0wYWI0LTU5ODctOTM0NS1hNGIzOWZlMTYyYWRfMTU5NTY3Njc1NTA3OV8wXzIyOTQmcmFuaz0x?app_id=DemoAppId01082013GAL&app_code=AJKnXv84fjrb0KIHawS0T")
            .position(Arrays.asList(17.355109, 78.473301))
            .title("Shah Ghouse Cafe").build();
        HereMapsPojo hereMapsPojo = HereMapsPojo.builder()
                                                .results(
                                                    Results.builder().items(Arrays.asList(item)).build())
                                                .build();
        when(httpRequestUtil.getApiResponse(Mockito.any(URI.class), Mockito.any(HttpHeaders.class), eq(
            HereMapsPojo.class))).thenReturn(hereMapsPojo);
        HereMapsPojo fetchedHereMapsPojo = hereMapsClient.getPoiByCategory(geometry, Category.RESTAURANTS);

        assertNotNull(fetchedHereMapsPojo);
        assertEquals(item.getTitle(), fetchedHereMapsPojo.getResults().getItems().get(0).getTitle());
    }
}
