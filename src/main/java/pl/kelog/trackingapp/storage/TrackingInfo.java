package pl.kelog.trackingapp.storage;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@ToString
public class TrackingInfo {
    public final LocalDateTime timestamp;
    
    public final String url;
    
    public final String remoteAddress;
    
    public final String userAgent;
    
    public final Map<String, String> fullHeaders;
}
