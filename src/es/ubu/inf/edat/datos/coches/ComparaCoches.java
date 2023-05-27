package es.ubu.inf.edat.datos.coches;

import java.util.Comparator;

public class ComparaCoches<T> implements Comparator<T>{


	/*****************  Metodos de la Interfaz  ************************/
  public int compare(T o1, T o2) throws ClassCastException{
	//public int compare(<? extends Coche> o1, <? extends Coche> o2) throws ClassCastException{
//	 public int compare(Object o1, Object o2) throws ClassCastException{
	  
  	// Comparamos segun el No. de caballos
    return ((Coche)o1).getCaballos()-((Coche)o2).getCaballos();
  }

  public boolean equals(Object o){
    return this.equals(o);
  }

} // Fin clase 