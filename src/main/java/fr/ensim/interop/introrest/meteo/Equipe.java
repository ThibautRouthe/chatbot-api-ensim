package fr.ensim.interop.introrest.meteo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Equipe {
    private Integer id;
    
    private String name;
    
    private Integer nbJoueurs;

    public Equipe() {}

    public Equipe(String name, Integer nbJoueurs) {
        this.name = name;
        this.nbJoueurs = nbJoueurs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNbJoueurs() {
        return nbJoueurs;
    }

    public void setNbJoueurs(Integer nbJoueurs) {
        this.nbJoueurs = nbJoueurs;
    }

    @Override
    public String toString() {
        return "Equipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nbJoueurs='" + nbJoueurs + '\'' +
                '}';
    }
}
