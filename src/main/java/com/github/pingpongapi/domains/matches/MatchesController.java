package com.github.pingpongapi.domains.matches;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/matches")
@CrossOrigin(origins = "http://localhost:3000")
public class MatchesController {
  private final Logger logger = LoggerFactory.getLogger(MatchesController.class);

  @Autowired
  private MatchesService matchesService;

  @GetMapping
  public ResponseEntity<List<Matches>> getAllMatches() {
    logger.info("Get all matches request received");
    return new ResponseEntity<>(matchesService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/date")
  public ResponseEntity<List<Matches>> getMatchesByDate(@RequestParam String date) {
    logger.info("Get matches by date request received");
    return new ResponseEntity<>(matchesService.getAllMatchesByDate(date), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Matches> getMatchById(@PathVariable Long id) {
    logger.info("Get match by id request received");
    return new ResponseEntity<>(matchesService.getById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Matches> createNewMatch(@Valid @RequestBody Matches match) {
    logger.info("Create new match request received");
    return new ResponseEntity<>(matchesService.createMatch(match), HttpStatus.CREATED);
  }

  @PutMapping("/edit/{id}")
  public ResponseEntity<Matches> updateMatch(@PathVariable Long id, @Valid @RequestBody Matches match)
      throws NoSuchAlgorithmException {
    logger.info("Edit match request received");
    return new ResponseEntity<>(matchesService.updateMatch(id, match), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Matches> deleteMatch(@PathVariable Long id) {
    logger.info("Delete match request received");
    matchesService.deleteMatch(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
