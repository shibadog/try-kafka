package jp.shibadog.sample.merchant.web;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestOperations;

@Controller
public class MainController {

    @Autowired
    private RestOperations restTemplate;

    @Value("${app.front-url}")
    private String frontUrl;

    @GetMapping(value = {"/", ""})
    public String root() {
        return "index";
    }

    @PostMapping(value = {"/purchase"})
    public String purchase(Model model) throws IOException {

        String url = frontUrl + "/purchase";
        @SuppressWarnings("unchecked") Map<String, Object> req = 
            restTemplate.postForObject(url, null, Map.class);
        String acceptKey = req.get("acceptKey").toString();

        model.addAttribute("acceptKey", acceptKey);
        return "accept";
    }

    @GetMapping(value="/status")
    public String status(Model model, @RequestParam String acceptKey) {
        model.addAttribute("acceptKey", acceptKey);
        model.addAttribute("status", "購入処理中");
        return "status";
    }

}