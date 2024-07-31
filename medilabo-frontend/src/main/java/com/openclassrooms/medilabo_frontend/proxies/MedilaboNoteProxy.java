package com.openclassrooms.medilabo_frontend.proxies;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "medilabo-note", url = "localhost:9003")
public interface MedilaboNoteProxy {

}
