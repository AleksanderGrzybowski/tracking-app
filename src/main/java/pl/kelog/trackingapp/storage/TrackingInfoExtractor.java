package pl.kelog.trackingapp.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackingInfoExtractor {
    
    public TrackingInfo extract(HttpServletRequest request) {
        log.info("Extracting information from request...");
        
        TrackingInfo info = new TrackingInfo(
                LocalDateTime.now(),
                request.getRequestURL().toString(),
                request.getRemoteHost(),
                request.getHeader("User-Agent"),
                extractHeaders(request)
        );
        log.info("Extracted: " + info);
        
        return info;
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
