package com.example.springrestapi.products;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
