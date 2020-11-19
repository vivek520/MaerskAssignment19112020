package com.maersk.main.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maersk.main.IContainerRepository;
import com.maersk.main.bean.BookingReferenceBean;
import com.maersk.main.bean.ContainerDetails;

@RestController
@RequestMapping("/api/bookings/")
public class ContainerDetailsAndBookingsController {
	
	@Autowired
	IContainerRepository containerRepository;
	
	@Autowired
	CassandraTemplate templateDao;
	
	
	@RequestMapping(value = "containerAvailability", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String,String> isContainerAvailable(){	
		
		
		//=================================Code for external API calling=============================
		/*
		 * Below code is written for calling external rest API which will check if
		 * containers are available or not and return the flag accordingly
		 */
		
		/*
		 * int availableSpace = 0; String available = ""; try {
		 * 
		 * 
		 * URL myURL = new URL("https://maersk.com/api/bookings/checkAvailable");
		 
		 * HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
		 * conn.setRequestProperty("Content-Type", "application/json");
		 * conn.setDoOutput(true); conn.setRequestMethod("PUT"); int code =
		 * conn.getResponseCode(); // 200 = HTTP_OK
		 * System.out.println("Response    (Code):" + code);
		 * System.out.println("Response (Message):" + conn.getResponseMessage());
		 * availableSpace = Integer.parseInt(conn.getResponseMessage().toString()); if
		 * (availableSpace > 0) available = "True"; else available = "false";
		 * 
		 * 
		 * } catch (NumberFormatException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (MalformedURLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch (ProtocolException e)
		 * { // TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 * return available;
		 */
		//=================================Code for external API calling=============================
		
		String available = "True";
		return Collections.singletonMap("available", available.toString());
		
		
	}
	
	
	//===========================API for booking request basis on the availability of container. This will return the booking reference number.==================================
	
	@RequestMapping(value = "bookingRequest",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public BookingReferenceBean addBookingRequest(@RequestBody ContainerDetails containerDetails) {
		
		BookingReferenceBean bookingReferenceBean = new BookingReferenceBean();
		Long bookingRefNumber = null;
		Long unique_id= (Long) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE); 
		
		containerDetails.setId(unique_id);
		
		System.out.println(containerDetails.toString());
		List<ContainerDetails> list = templateDao.select("select *from bookings;", ContainerDetails.class);
		ContainerDetails maxBookingRefCOntainer = list.stream().max(Comparator.
				comparing(ContainerDetails::getBookingRef))
				.orElseThrow(NoSuchElementException::new);
		
		
			bookingRefNumber = maxBookingRefCOntainer.getBookingRef() + 1;
		
		containerDetails.setBookingRef(bookingRefNumber);
		templateDao.insert(containerDetails);
		bookingReferenceBean.setBookingRef(bookingRefNumber);
		return bookingReferenceBean;
	}
}
