package at.hacker.shorten;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ShortenController {

	@Autowired
	ShortenRepository shortenRepository;
	
	// Create a new ShortenedUrl
	@PostMapping("/")
	public ShortenedUrl createNote(@RequestBody String url) {
		ShortenedUrl shortenedUrl = new ShortenedUrl();
		shortenedUrl.setUrl(url);
	    return shortenRepository.save(shortenedUrl);
	}
	
	/**
	 * Default error page on root
	 */
	@RequestMapping(
			value = "/",
			method = RequestMethod.GET)
	public String defaultMapping() {
        return "URL Mapping not found!";
    }
	
	// Get a Single ShortenedUrl and redirect to the mapped URL
	@GetMapping("/{id}")
	public ModelAndView getNoteById(ModelMap model, @PathVariable(value = "id") Long urlId) {
		ShortenedUrl shortenedUrl = shortenRepository.findOne(urlId);
	    if(shortenedUrl == null) {
	        return new ModelAndView("", model);
	    }
	    return new ModelAndView("redirect:"+shortenedUrl.getUrl(), model);
	}
	
	// Delete a ShortenedUrl
	@DeleteMapping("/{id}")
	public ResponseEntity<ShortenedUrl> deleteNote(@PathVariable(value = "id") Long urlId) {
		ShortenedUrl shortenedUrl = shortenRepository.findOne(urlId);
	    if(shortenedUrl == null) {
	        return ResponseEntity.notFound().build();
	    }

	    shortenRepository.delete(shortenedUrl);
	    return ResponseEntity.ok().build();
	}
}
