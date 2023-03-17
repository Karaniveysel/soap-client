package com.java.spring.soap.api.clinet;

import com.java.spring.soap.api.generated.CelsiusToFahrenheit;
import com.java.spring.soap.api.generated.CelsiusToFahrenheitResponse;
import com.java.spring.soap.api.generated.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;


import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class SoapClinet {

	@Autowired
	private Jaxb2Marshaller marshaller;

	private WebServiceTemplate template;

	private final ObjectFactory objectFactory=new ObjectFactory();


	public CelsiusToFahrenheitResponse getSoapStatus(CelsiusToFahrenheit request) {
		template = new WebServiceTemplate(marshaller);
		CelsiusToFahrenheitResponse acknowledgement =
				(CelsiusToFahrenheitResponse) template.marshalSendAndReceive("https://www.w3schools.com/xml/tempconvert.asmx",
				request,new WebServiceMessageCallback() {
					@Override
					public void doWithMessage(WebServiceMessage webServiceMessage) throws IOException, TransformerException {
						((SoapMessage)webServiceMessage).setSoapAction("https://www.w3schools.com/xml/CelsiusToFahrenheit");
					}
				});
		return acknowledgement;
	}


	/*
	* Hatalı method çalışmıyor. Boş yere uğraşma . Daha sonra Kontrol edilecek
	*
	 */
	public ResponseEntity<CelsiusToFahrenheitResponse> getSoapStatusExchange(CelsiusToFahrenheit request) {
		 RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.ALL);
		//JAXBElement<String> jaxbElement=objectFactory.createString(request.getCelsius());

		//new JAXBElement<String>(new QName("https://www.w3schools.com/xml/"), CelsiusToFahrenheit.class, request);
		HttpEntity<CelsiusToFahrenheit> entity = new HttpEntity(request, headers);

		URI targetUrl = UriComponentsBuilder.fromUriString("https://www.w3schools.com")
				.path("/xml/tempconvert.asmx")
				.queryParam("celsius", "122")
				.build()
				.toUri();

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
//Add the Jackson Message converter
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

// Note: here we are making this converter to process any kind of response,
// not only application/*json, which is the default behaviour
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);



	//	JAXBElement<CelsiusToFahrenheitResponse> celsius= new JAXBElement<>(new "https://www.w3schools.com/xml/",CelsiusToFahrenheit.class,request);

//		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
//		messageConverters.add(converter);
//		restTemplate.setMessageConverters(messageConverters);

//		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
//		//Add the String Message converter
//		messageConverters.add(new Convert());
//		//Add the message converters to the restTemplate
//		restTemplate.setMessageConverters(messageConverters);

		ResponseEntity exchange =restTemplate.exchange("https://www.w3schools.com/xml/tempconvert.asmx",HttpMethod.GET,entity,String.class);


//        StudentDetailsResponse response = (StudentDetailsResponse) getWebServiceTemplate()
//                .marshalSendAndReceive("http://localhost:8080/ws/countries", request,
//                        new SoapActionCallback(
//                                "http://spring.io/guides/gs-producing-web-service/GetCountryRequest"));

		return exchange;
	}

}
