package com.example.springrestapi.products;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    //PostMapping: diese Methode wird aufgerufen wenn wir eine POST anfrage machen?
    //Spring soll in den Body gucken (POST Anfragen haben ja generell irgendwelche Informationen im Body)
    //zum Beispiel in einem Formular (bei REST Schnittstellen (API) ist das im Json Format)
    //Spring geht dann daher, guckt in den RequestBody rein und versucht das dann in ein Product,
    //in eines unserer Java Objekte zu konvertieren
    //und injiziert das ganze in unsere Methode (createProduct?) hier rein
    //und wir speichern das ganze dann. also das resultat von dieser serialisierung
    @PostMapping("")
    public void createProduct(@RequestBody Product product) {
        productRepository.save(product);
    }

    //Wert in brackets GetMapping korrelliert nicht ohne Grund mit
    //Variable in Methode
    //Mit @PathVariable sagen wir Spring, dass es in unserem Pfad nachgucken soll,
    //wo das hier gematched wird mit diesen brackets und was hier quasi in disen brackets
    //für ein Wert drin wäre, der wird dann hier als Wert in diese Variable, in unsere Methode
    //aufgeführt/eingeführt, wenn das ganze aufgerufen wird,
    //dann rufen wir mit unserem productId, das wir dann bekommen, durch unsere URL,
    //das Repository einmal ab, nach dem Produkt, das wir suchen, überprüfen, das es da ist
    //und geben es dann zurück
    //Spring wird das natürlich das ganze dann automatisch im Hintergrund in Json umwandeln
    //und wenn wir das nicht finden, geben wir ganz klassisch ein 404 zurück
    //sagen den Entwicklern die die REST API konsumieren, das wir kein Produkt gefunden haben
    //z.B: http://localhost:8080/products/1
    @GetMapping("/{productId}")
    public Product readProdukt(@PathVariable Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            return product.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this id not found");
    }


    //Wieder suchen wir nach productId in Pfad und in RequestBody wie für PUT üblich UpdateInformation
    //Wenn product in Datenbank gefunden, Update möglich
    //Ich ersetze die Werte der aus der Datenbank geholten Product durch das hier initiierte Produkt
    //Wichtig: was wir hier speichern mit dem save muss auch die productInstanz, die wie hier
    // aus der Datenbank holen weil Hibernate dadurch quasi eine Referenz speichert.
    // und wenn wir das nicht tun (bspw das productUpdate in das save tun),
    // wird Hibernate neue Instanz in Tabelle einfügen, das wollen wir nicht, sondern existierende updaten
    @PutMapping("/{productId}")
    public void updateProduct(@PathVariable Long productId, @RequestBody Product productUpdate) {
        Optional<Product> product = productRepository.findById(productId);
        if(!product.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this id not found");
        }
    Product productInstance = product.get();
    productInstance.setName(productUpdate.getName());
    productInstance.setCostInEuro(productUpdate.getCostInEuro());
    productRepository.save(productInstance);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent()) {
            productRepository.deleteById(productId);
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this id not found");
    }

}
