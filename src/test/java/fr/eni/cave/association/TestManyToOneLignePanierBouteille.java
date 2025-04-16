package fr.eni.cave.association;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import fr.eni.cave.bo.client.LignePanier;
import fr.eni.cave.bo.vin.*;
import fr.eni.cave.dal.LignePanierRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
public class TestManyToOneLignePanierBouteille {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	LignePanierRepository repository;

	private Bouteille b1;

	@BeforeEach
	public void initDB() {
		final Couleur blanc = Couleur.builder().nom("Blanc").build();

		entityManager.persist(blanc);

		final Region paysDeLaLoire = Region.builder().nom("Pays de la Loire").build();

		entityManager.persist(paysDeLaLoire);
		entityManager.flush();

		b1 = Bouteille.builder().nom("DOMAINE ENI Ecole").millesime("2022").prix(23.95f).quantite(1298)
				.region(paysDeLaLoire).couleur(blanc).build();
		entityManager.persist(b1);
		entityManager.flush();
	}

	@Test
	public void test_LignePanierRepository_save() {
		int qte = 4;
		final LignePanier lp = LignePanier.builder().quantite(qte).prix(qte * b1.getPrix()).build();

		// Association OneToMany
		lp.setBouteille(b1);

		// Appel du comportement
		repository.save(lp);

		// Vérification de l'identifiant
		assertThat(lp.getId()).isGreaterThan(0);

		// Vérification de l'association
		assertThat(lp.getBouteille()).isNotNull();
		assertEquals(lp.getBouteille(), b1);
		log.info(lp.toString());
	}

	@Test
	public void test_delete() {
		int qte = 4;
		final LignePanier lp = LignePanier.builder().quantite(qte).prix(qte * b1.getPrix()).build();

		// Association OneToMany
		lp.setBouteille(b1);

		// Appel du comportement
		final LignePanier lpDB = entityManager.persist(lp);
		entityManager.flush();
		// Vérification de l'identifiant
		assertThat(lpDB.getId()).isGreaterThan(0);

		// Appel du comportement
		repository.delete(lpDB);

		// Vérification que l'entité a été supprimée
		final LignePanier lpDB2 = entityManager.find(LignePanier.class, lpDB.getId());
		assertNull(lpDB2);

		// Vérifier que la bouteille n'a pas été supprimée
		final Bouteille b = entityManager.find(Bouteille.class, b1.getId());
		assertNotNull(b);
	}
}
