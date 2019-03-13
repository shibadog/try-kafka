package jp.shibadog.sample.front.web;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.shibadog.sample.front.domain.service.PurchaseService;

@RestController
@RequestMapping("/api/v1")
public class Controller {

    @Autowired
    private PurchaseService service;

    @PostMapping("/purchase")
    public Map<String, Object> purchase(@RequestBody Map<String, Object> body) {
        return Collections.singletonMap("acceptKey", service.purchase(body));
    }
}