package fr.eni.cave.bll;

import java.util.List;

import fr.eni.cave.bo.vin.Bouteille;

public interface BouteilleService {
	List<Bouteille> chargerToutesBouteilles();
	Bouteille updateBouteilleById(Integer bouteille);
	
	Bouteille chargerBouteilleParId(int idBouteille);

	List<Bouteille> chargerBouteillesParRegion(int idRegion);

	List<Bouteille> chargerBouteillesParCouleur(int idCouleur);
	Bouteille saveBouteille(Bouteille bouteille);
}
