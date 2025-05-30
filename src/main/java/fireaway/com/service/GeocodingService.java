package fireaway.com.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GeocodingService {

    private final RestTemplate restTemplate = new RestTemplate();

    public double[] getLatLonFromCep(String cep) {
        String url = UriComponentsBuilder.fromUriString("https://nominatim.openstreetmap.org/search")
                .queryParam("format", "json")
                .queryParam("postalcode", cep)
                .queryParam("country", "BR")
                .queryParam("limit", 1)
                .build().toUriString();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("User-Agent", "fireaway-app/1.0 (seu-email@exemplo.com)");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<NominatimResponse[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    NominatimResponse[].class
            );

            if (response.getBody() != null && response.getBody().length > 0) {
                double lat = Double.parseDouble(response.getBody()[0].lat);
                double lon = Double.parseDouble(response.getBody()[0].lon);


                Thread.sleep(1000);

                return new double[]{lat, lon};
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    static class NominatimResponse {
        public String lat;
        public String lon;
    }
}

