package pl.kelog.trackingapp.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class TrackingInfoStorage {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd__HH-mm-ss");
    
    private final ObjectMapper mapper;
    private final String storagePath;
    
    public TrackingInfoStorage(
            ObjectMapper mapper,
            @Value("${storagePath:null}") String storagePath
    ) {
        this.mapper = mapper;
        this.storagePath = storagePath;
        
        log.info("Using storage path: " + storagePath);
        ensureStorageIsWritable();
    }
    
    @SneakyThrows
    public void store(TrackingInfo info) {
        String filename = createFilename(info.timestamp);
        File outputFile = Paths.get(storagePath, filename).toFile();
        
        log.info("Writing tracking info into new file " + outputFile.getAbsolutePath());
        mapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, info);
    }
    
    private String createFilename(LocalDateTime timestamp) {
        return FORMATTER.format(timestamp) + ".json";
    }
    
    private void ensureStorageIsWritable() {
        // TODO
    }
}
