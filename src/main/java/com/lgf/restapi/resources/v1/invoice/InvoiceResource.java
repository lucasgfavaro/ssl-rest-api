package com.lgf.restapi.resources.v1.invoice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgf.restapi.resources.v1.invoice.model.Invoice;

@RestController
@RequestMapping(value = "/invoices")
public class InvoiceResource {

	@RequestMapping(method = RequestMethod.GET)
	public Invoice get() {

		Invoice invoice = new Invoice();

		invoice.setName("id");
		invoice.setId("123");

		return invoice;
	}

}
