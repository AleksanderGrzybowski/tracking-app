package pl.kelog.trackingapp;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@ResponseBody
@RequestMapping("/")
@AllArgsConstructor
public class TrackingController {
    
    private final TrackingAppService appService;
    
    private static final String FAKE_CSS = ".this-class-doesnt-exist {}";
    
    @RequestMapping(value = "/api/extra.css", method = RequestMethod.GET, produces = "text/css")
    public ResponseEntity<byte[]> store(HttpServletRequest request) {
        appService.store(request);
        return new ResponseEntity<>(FAKE_CSS.getBytes(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/list", method = RequestMethod.GET, produces = "text/plain")
    public ResponseEntity<byte[]> list() {
        return new ResponseEntity<>(appService.listAll().getBytes(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ResponseEntity<Void> health() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
