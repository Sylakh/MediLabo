package com.openclassrooms.medilabo_frontend.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.openclassrooms.medilabo_frontend.beans.ReportDataDTOBeans;

@FeignClient(name = "medilabo-report", url = "localhost:9102/medilabo-report")
public interface MedilaboReportProxy {

	@GetMapping("/report")
	public List<ReportDataDTOBeans> report();

}
