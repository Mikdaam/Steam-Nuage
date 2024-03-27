package fr.side.projects.steamnuage.controllers;

import fr.side.projects.steamnuage.controllers.response.CompanyResponse;
import fr.side.projects.steamnuage.controllers.response.GameSummaryResponse;
import fr.side.projects.steamnuage.services.CompanyService;
import fr.side.projects.steamnuage.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/companies")
public class CompanyController {
  private final GameService gameService;
  private final CompanyService companyService;

  @GetMapping
  public ResponseEntity<List<CompanyResponse>> listCompanies() {
    return ResponseEntity.ok(null);
  }

  @GetMapping("/{name}/games")
  public ResponseEntity<List<GameSummaryResponse>> listCompanyGames(
      @RequestParam(name = "category", required = false) String category,
      @RequestParam(name = "publisher", required = false) String publishedBy,
      @RequestParam(name = "developer", required = false) String developedBy,
      @PathVariable String name
  ) {
    return ResponseEntity.ok(null);
  }
}
