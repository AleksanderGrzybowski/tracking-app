package pl.kelog.trackingapp;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kelog.trackingapp.storage.TrackingInfo;
import pl.kelog.trackingapp.storage.TrackingInfoExtractor;
import pl.kelog.trackingapp.storage.TrackingInfoStorage;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class TrackingAppService {
    
    private final TrackingInfoExtractor extractor;
    private final TrackingInfoStorage storage;
    
    public void store(HttpServletRequest request) {
    
        TrackingInfo info = extractor.extract(request);
        storage.store(info);
    }
}
