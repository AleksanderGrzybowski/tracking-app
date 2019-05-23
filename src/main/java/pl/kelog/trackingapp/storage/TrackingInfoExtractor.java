package pl.kelog.trackingapp.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TrackingInfoExtractor {
    
    public TrackingInfo extract(HttpServletRequest request) {
        return new TrackingInfo(
                LocalDateTime.now(),
                request.getRequestURL().toString(),
                request.getRemoteHost(),
                request.getHeader("User-Agent"),
                extractHeaders(request)
        );
    }
    
    private Map<String, String> extractHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> e = request.getHeaderNames();
        while (e.hasMoreElements()) {
            String key = e.nextElement();
            headers.put(key, request.getHeader(key));
        }
        return headers;
    }
}
