package fr.eni.cave.dal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import fr.eni.cave.bo.vin.*;

public interface BouteilleRepository extends JpaRepository<Bouteille, Integer> {
	// Rechercher des bouteilles par leur région
	List<Bouteille> findByRegion(Region r);
	Bouteille updateBouteilleById(Integer bouteille);

	// Rechercher des bouteilles par leur couleur
	List<Bouteille> findByCouleur(Couleur c);

	// Rechercher une bouteille par son nom
	Bouteille findByNom(@Param("nom") String nom);
}
