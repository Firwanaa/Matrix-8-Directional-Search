package ca.sheridancollege.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import ca.sheridancollege.database.DatabaseAccess;
import ca.sheridancollege.matrixGenerator.squareMatrix;
import ca.sheridancollege.personInfo.PersonInfo;

//import lombok.*;
/**
 * @Author : A Firwana
 * @id : 
 */
@Controller
@SessionAttributes("squareMatrix")

public class controller {

	/**
	 * Map to home.html
	 *
	 * @return
	 */
	@Autowired
	private DatabaseAccess ds;

	@GetMapping("/") // localhost:8080/
	public String submitInfo(Model model) {
		model.addAttribute("PersonInfo", new PersonInfo());// bind object to model attribute
		return "landingPage.html";
	}

	@GetMapping("/home") // localhost:8080/home
	public String goHome(Model model, @ModelAttribute PersonInfo prInfo) {
		model.addAttribute("squareMatrix", new squareMatrix());// bind object to model attribute
		ds.addPerson(prInfo.getId(), prInfo.getName());
		return "home.html";
	}

	@GetMapping("/view") // localhost:8080/view
	public String goView(Model model, @ModelAttribute squareMatrix mtrx) throws Exception {// define object
		model.addAttribute("charMatrixView", mtrx.randomOrWords()); // add attribute
		return "view.html";
	}

	@GetMapping("/searchStr") // localhost:8080/goSearch
	public String goSearch(Model model, @ModelAttribute squareMatrix mtrx) {
		// Adding Attributes to HTML
		model.addAttribute("charMatrixView", mtrx.getStrMatrix());
		model.addAttribute("patternFound",
				mtrx.searchPattern(mtrx.getCharMatrix(), mtrx.getWord().toUpperCase().replaceAll("\\s+", "")));// remove
																												// spaces
																												// between
																												// characters
		model.addAttribute("failedAttempts", mtrx.getFailedAttemptsCounter());
		return "searchStr.html";
	}

}
