package com.tmac2236.spring.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmac2236.spring.dao.CustomerDao;
import com.tmac2236.spring.entity.Customer;

@Controller
public class HomeController {
    @Autowired
	private CustomerDao customerDao;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		System.out.println("Home Page Requested, locale = " + locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/findPk", method = RequestMethod.POST)
	public String user(@RequestParam("customerid") String customerid , ModelMap model) {
		List<Customer> list = customerDao.findAll();
		if(list ==null) {
			model.addAttribute("message", "查無資料");
			return "login";
		}
		Customer customer = customerDao.getOne(Integer.valueOf(customerid));
		if(customer == null) {
			model.addAttribute("message", "查無資料");
			return "login";
		}
		model.addAttribute("list",list);
		model.addAttribute("customer", customer);
		
		return "user";
	}
}
