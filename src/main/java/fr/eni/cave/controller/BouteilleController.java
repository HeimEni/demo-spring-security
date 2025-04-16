package fr.eni.cave.controller;

import fr.eni.cave.bll.BouteilleService;
import fr.eni.cave.bo.vin.Bouteille;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.List;

@RestController
@RequestMapping("/caveavin/bouteilles")
public class BouteilleController {

    private BouteilleService bouteilleService = null;

    public BouteilleController(BouteilleService bouteilleService) {
        this.bouteilleService = bouteilleService;
    }

    @GetMapping
    public ResponseEntity<List<Bouteille>> getAllBouteilles() {
        var bouteilles = this.bouteilleService.chargerToutesBouteilles();
        if (bouteilles.isEmpty()) {
            return new ResponseEntity<>(bouteilles, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(bouteilles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bouteille> getBouteilleById(@PathVariable() String id) {
        try {
            var bouteille = this.bouteilleService.chargerBouteilleParId(Integer.parseInt(id));
            return new ResponseEntity<>(bouteille, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/region/{idRegion}")
    public ResponseEntity<List<Bouteille>> getBouteillesByRegion(@PathVariable() String idRegion) {
        try {
            List<Bouteille> bouteilles = this.bouteilleService.chargerBouteillesParRegion(Integer.parseInt(idRegion));
            return new ResponseEntity<>(bouteilles, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/couleur/{idCouleur}")
    public ResponseEntity<List<Bouteille>> getBouteillesByCouleur(@PathVariable() String idCouleur) {
        try {
            List<Bouteille> bouteilles = this.bouteilleService.chargerBouteillesParCouleur(Integer.parseInt(idCouleur));
            return new ResponseEntity<>(bouteilles, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Bouteille> saveBouteille(@RequestBody Bouteille bouteille) {

        try {
            bouteille = bouteilleService.saveBouteille(bouteille);
            return new ResponseEntity<>(bouteille, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Bouteille> updateBouteille(@RequestBody Bouteille bouteille) {

        try {
            bouteille = bouteilleService.updateBouteilleById(bouteille.getId());
            return new ResponseEntity<>(bouteille, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
