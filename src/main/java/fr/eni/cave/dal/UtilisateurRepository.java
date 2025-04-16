package fr.eni.cave.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import fr.eni.cave.bo.client.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, String> {
	//Rechercher un utilisateur par son pseudo
	Utilisateur findByPseudo(String pseudo);

	//Rechercher un utilisateur par son pseudo et password
	Utilisateur findByPseudoAndPassword(String pseudo,String password);
}
