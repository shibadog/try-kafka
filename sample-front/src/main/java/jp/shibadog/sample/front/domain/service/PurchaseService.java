package jp.shibadog.sample.front.domain.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    public String purchase() {
        return UUID.randomUUID().toString();
    }
}