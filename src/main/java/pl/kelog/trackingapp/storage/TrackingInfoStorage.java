package pl.kelog.trackingapp.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
@Slf4j
public class TrackingInfoStorage {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd__HH-mm-ss");
    private static final String FILE_SEPARATOR = "\n-------------------------------------------------------\n";
    
    private final ObjectMapper mapper;
    private final String storagePath;
    
    public TrackingInfoStorage(
            ObjectMapper mapper,
            @Value("${storagePath:null}") String storagePath
    ) throws IOException {
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
    
    public String listAll() {
        File[] files = new File(storagePath).listFiles((dir, name) -> name.endsWith(".json"));
        requireNonNull(files);
        
        return Arrays.stream(files)
                .sorted(Comparator.comparing(File::getName).reversed())
                .map(Unchecked.function(file -> FileUtils.readFileToString(file, Charset.defaultCharset())))
                .collect(Collectors.joining(FILE_SEPARATOR));
    }
    
    private String createFilename(LocalDateTime timestamp) {
        return FORMATTER.format(timestamp) + ".json";
    }
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void ensureStorageIsWritable() throws IOException {
        log.info("Checking if storage is writable...");
        
        String testFilename = "" + System.currentTimeMillis();
        File testFile = Paths.get(storagePath, testFilename).toFile();
        FileUtils.write(testFile, "testing", Charset.defaultCharset());
        testFile.delete();
        
        log.info("Storage is functioning properly.");
    }
}
