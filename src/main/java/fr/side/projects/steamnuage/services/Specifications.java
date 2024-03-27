package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.models.Game;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

public class Specifications {
  public static Specification<Game> filterGames(String developer, String publisher) {
    return (root, query, criteriaBuilder) -> {
      var developerPredicate = criteriaBuilder.like(root.get("developer").get("name"), Strings.isBlank(developer) ? "%%" : developer);
      var publisherPredicate = criteriaBuilder.like(root.get("publisher").get("name"), Strings.isBlank(publisher) ? "%%" : publisher);
      return criteriaBuilder.and(developerPredicate, publisherPredicate);
    };
  }

  public static Specification<Game> searchGames(String search) {
    var lowerSearch = search.toLowerCase();
    return (root, query, criteriaBuilder) -> {
      var titlePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + lowerSearch + "%");
      var descriptionPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + lowerSearch + "%");
      var developerPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("developer").get("name")), "%" + lowerSearch + "%");
      var publisherPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("publisher").get("name")), "%" + lowerSearch + "%");
      return criteriaBuilder.or(titlePredicate, descriptionPredicate, developerPredicate, publisherPredicate);
    };
  }
}
