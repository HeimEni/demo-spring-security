package fr.eni.cave.dal;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.eni.cave.bo.client.Adresse;

public interface AdressRepository extends JpaRepository<Adresse, Integer> {

}
