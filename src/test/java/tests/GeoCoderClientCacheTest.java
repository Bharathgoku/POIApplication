package tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.poi.external.geoCoder.GeoCoderClient;
import com.poi.pojo.GeoCoderPojo;
import com.poi.pojo.GeoCoderPojo.Geometry;
import com.poi.pojo.GeoCoderPojo.Result;
import com.poi.util.HttpRequestUtil;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

public class GeoCoderClientCacheTest {

    @Mock
    private HttpRequestUtil httpRequestUtil;

    @InjectMocks
    private GeoCoderClient geoCoderClient;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetGeometryCache() {
        String cityName = "Hyderabad";
        Geometry geometry = Geometry.getObject(Arrays.asList(17.3616079, 78.4746286));
        GeoCoderPojo geoCoderPojo = GeoCoderPojo.builder()
                                .resultList(Arrays.asList(Result.builder()
                                                                .geometry(geometry).build())).build();
        when(httpRequestUtil.getApiResponse(Mockito.any(URI.class), Mockito.any(HttpHeaders.class), eq(GeoCoderPojo.class))).thenReturn(geoCoderPojo);
        GeoCoderPojo.Geometry geometry1 = geoCoderClient.getGeometry(cityName);
        GeoCoderPojo.Geometry geometry2 = geoCoderClient.getGeometry(cityName);

        assertEquals(geometry1, geometry2);

    }
}
