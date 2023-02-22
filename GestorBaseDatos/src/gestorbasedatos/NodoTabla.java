/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorbasedatos;

/**
 *
 * @author Gabriela Diaz R
 */
public class NodoTabla {
    String nombrecol;
    String tipoDato;
    String size;
    String pk;
    String notnul;
    
    public NodoTabla(String naame, String type, String num, String key, String nn){
        nombrecol=naame;
        tipoDato=type;
        size=num;
        pk=key;
        notnul=nn;
    }
}
