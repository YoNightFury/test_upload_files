package com.app.controllers;

import static com.app.CommandLineRunnerImpl.storagePath;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.dao.ResumeRepository;
import com.app.pojos.Resume;

@Controller
@RequestMapping("/")
public class FileController {
	
	// logger for this class
	private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private ResumeRepository repo;

	
	
	@GetMapping("/form")
	public String showFileUploadForm() {
		// log method
		LOG.info("in {} ", Thread.currentThread().getStackTrace()[1].getMethodName());
		return "/form";
	}

	@PostMapping("/form")
	public String processForm(@RequestParam MultipartFile pdf_file, RedirectAttributes flashMap, ServletRequest req) {
		
		
		LOG.info("in {} ", Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			// the multipart file that has been uploaded 
			
			// its name
			String name = pdf_file.getOriginalFilename();
			
			// the default storage folder
			String path = storagePath + "\\" + name;
			
			// change the location to the specified folder (our default storage location)
			pdf_file.transferTo(new File(path));
			
			// save the path and file name in the database
			repo.save(new Resume(path, name));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// redirect attributes
		// will display the name of the file in the redirected method
		flashMap.addFlashAttribute("file", pdf_file);
		LOG.info("Content Type : {}", pdf_file.getContentType());
		LOG.info("File name : {}", pdf_file.getOriginalFilename());
		return "redirect:/show";
	}

	@GetMapping("/show")
	public String show() {
		// redirected attribute the multipartfile will be visible
		LOG.info("in {} ", Thread.currentThread().getStackTrace()[1].getMethodName());
		return "/show";
	}

	@GetMapping("/show_all")
	public String showAll(ModelMap map) {
		
		LOG.info("in {} ", Thread.currentThread().getStackTrace()[1].getMethodName());
		
		
		// list of all names
		List<String> names = repo.findAllAsName();
		// added to requestScope
		map.addAttribute("names", names);
		return "/show_all";
	}

	// path variable filename
	@GetMapping(value = "/show/{name}")
	public @ResponseBody ResponseEntity<?> showResume(@PathVariable String name) throws Exception {
		LOG.info("in {} ", Thread.currentThread().getStackTrace()[1].getMethodName());

		LOG.info("name of requested file : {}", name);
		
		// find the requested file 
		Optional<Resume> name2 = repo.findByName(name);
		
		Resume resume = name2.orElseThrow();
		LOG.info("stored file : {}", resume);
		// get the file
		FileInputStream fis = new FileInputStream(resume.getPath());

		// build response entity with status code ok
		// storing byte[]
		// Content-Type=application/pdf
		
		// method 1 to build response entity
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", "application/pdf");
		ResponseEntity<byte[]> re = new ResponseEntity<byte[]>(fis.readAllBytes(), headers, HttpStatus.OK );
		return re;
		
		// method 2 preferred and easy
		
		
//		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/pdf").body(fis.readAllBytes());

	}
}
