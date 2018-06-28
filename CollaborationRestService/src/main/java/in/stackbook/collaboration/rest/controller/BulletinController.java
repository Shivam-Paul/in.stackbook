package in.stackbook.collaboration.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.stackbook.collaboration.dao.BulletinDAO;
import in.stackbook.collaboration.model.Bulletin;

@RestController
@RequestMapping("/bulletin")
public class BulletinController {
	
	@Autowired Bulletin bulletin;
	
	@Autowired BulletinDAO bulletinDAO;
	
	/*@GetMapping("/list")		
	public ResponseEntity<List<Bulletin>> listAllBulletins() {
		
		List<Integer> bulletin_id = bulletinDAO.listAllID();
		List<String> bulletin_title = bulletinDAO.listAllTitle();
		List<Bulletin> bulletins = new ArrayList<Bulletin>();
		if(bulletin_id.isEmpty()) {
			bulletin = new Bulletin();
			bulletin.setMessage("No bulletins are available");
			bulletins.add(bulletin);
			return new ResponseEntity<List<Bulletin>>(bulletins, HttpStatus.NOT_FOUND);
		}
		Iterator<Integer> id = bulletin_id.iterator();
		Iterator<String> title = bulletin_title.iterator();
		while(id.hasNext() && title.hasNext()) {
			Bulletin tempBulletin = new Bulletin();
			tempBulletin.setBulletin_id((Integer)id.next());
			tempBulletin.setTitle((String)title.next());
			bulletins.add(tempBulletin);
		}
		return new ResponseEntity<List<Bulletin>>(bulletins, HttpStatus.OK);
		
	}*/
	
	@GetMapping("/list")		
	public ResponseEntity<List<Bulletin>> listAllBulletin() {
		
		List<Bulletin> bulletins = bulletinDAO.list();
		
		if(bulletins.isEmpty()) {
			bulletin = new Bulletin();
			bulletin.setMessage("No blogs are available");
			bulletins.add(bulletin);
			return new ResponseEntity<List<Bulletin>>(bulletins, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Bulletin>>(bulletins, HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<Bulletin> saveBulletin(@RequestBody Bulletin bulletin) {
		
		if(bulletinDAO.save(bulletin)) {
			bulletin.setMessage("Bulletin saved successfully");
			return new ResponseEntity<Bulletin>(bulletin, HttpStatus.OK);
		}
		bulletin.setMessage("Could not save the bulletin .. please try after some time");
		return new ResponseEntity<Bulletin>(bulletin, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@PostMapping("/update")
	public ResponseEntity<Bulletin> updateBulletin(@RequestBody Bulletin bulletin) {
		
		if(bulletinDAO.update(bulletin)) {
			bulletin.setMessage("Bulletin updated successfully");
			return new ResponseEntity<Bulletin>(bulletin, HttpStatus.OK);
		}
		bulletin.setMessage("Could not update the bulletin .. please try after some time");
		return new ResponseEntity<Bulletin>(bulletin, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@GetMapping("/get/{bulletin_id}")
	public ResponseEntity<Bulletin> getBulletin(@PathVariable int bulletin_id) {
		
		bulletin = bulletinDAO.get(bulletin_id);
		
		if(bulletin!=null) {
			return new ResponseEntity<Bulletin>(bulletin, HttpStatus.OK);
		}
		return new ResponseEntity<Bulletin>(bulletin, HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/delete/{bulletin_id}")
	public ResponseEntity<Bulletin> deleteBulletin(@PathVariable int bulletin_id) {
		
		if(bulletinDAO.delete(bulletin_id)) {
			bulletin.setMessage("Bulletin deleted successfully");
			return new ResponseEntity<Bulletin>(bulletin, HttpStatus.OK);
		}
		bulletin.setMessage("Could not delete the bulletin .. please try after some time");
		return new ResponseEntity<Bulletin>(bulletin, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

}
