package es.ubu.lsi.edat.pr09.sol;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
/**
 * Clase que implementa un arbol binario de busqueda
 * @author Diego Urbaneja, Victor De Marco
 */
public class ArbolBB<E> extends AbstractSet<E> {
    /**
     * nodo raiz del arbol
     */
    public Nodo raiz;

    /**
     * comparador
     */
    private Comparator<? super E> comparator;

    /**
     * Constructor por defecto
     */
    public ArbolBB() {
        this.raiz = null;
        this.comparator = null;
    }

    /**
     * Constructor con comparador
     * @param comparator comparador que recibe como argumento
     */
    public ArbolBB(Comparator<? super E> comparator) {
        this.raiz = null;
        this.comparator = comparator;
    }

    /**
     * Constructor con coleccion y comparador
     * @param collection colecion de elemntos a añadir al arbol
     * @param comparator comparador que recibe como argumento
     */
    public ArbolBB( Collection<? extends E> collection,Comparator<? super E> comparator) {
        this.comparator = comparator;
        if (collection != null) {
            addAll(collection);
        }
    }

    /**
     * Constructor con coleccion
     * @param collection coleccion de elementos a añadir al arbol
     */
    public ArbolBB(Collection<? extends E> collection) {
        if (collection != null) {
            addAll(collection);
        }
        this.comparator = null;
    }

    /**
     * Método add sobreescrito
     * @param elemento elemnto a añdir
     * @return true si se ha añadido correctamente
     */
    @Override
    public boolean add(E elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("El elemento no puede ser nulo");
        }
        if (raiz == null) {
            raiz = new Nodo(elemento);
            return true;
        } else {
            return raiz.insertar(elemento, comparator);
        }
    }

    /**
     * Metodo para comprobar si el arbol contiene un elemento
     * @param objeto elemento a comprobar
     * @return true si el arbol contiene el elemento
     */
    @Override
    public boolean contains(Object objeto) {
        if (raiz == null || objeto == null) {
            throw new IllegalArgumentException("El elemento no puede ser nulo");
        }
        @SuppressWarnings("unchecked")
        E elemento = (E) objeto;
        return raiz.buscar(elemento, comparator);
    }

    /**
     * Metodo para eliminar un elemento del arbol
     * @param objeto elemento a eliminar
     * @return true si se ha eliminado correctamente
     */
    @Override
    public boolean remove(Object objeto) {
        if (raiz == null || objeto == null) {
            throw new IllegalArgumentException("El elemento no puede ser nulo");
        }
        @SuppressWarnings("unchecked")
        E elemento = (E) objeto;
        if (contains(elemento)) {
            raiz = raiz.eliminar(elemento, comparator);
            return true;
        }
        return false;
    }

    /**
     * Metodo para obtener el tamaño del arbol
     * @return tamaño del arbol
     */
    @Override
    public int size() {
        if(raiz == null){
            return 0;
        } else {
            return raiz.size();
        }
    }

    /**
     * Metodo para vaciar el arbol
     */
    public void clear() {
        raiz = null;
    }

    /**
     * Metodo para obtener un iterador del arbol
     * @return iterador del arbol
     */
    @Override
    public Iterator<E> iterator() {
        return new ArbolIterator();
    }

    /**
     * Metodo para obtener la altura del arbol (distancia desde el elemento a su hoja más lejana)
     * @param elemento elemento del que se quiere obtener la altura
     * @return altura del elemento
     */
    public int altura(E elemento) {
        if (raiz == null) {
            return -1;
        }
        return raiz.altura(elemento, comparator);
    }


    /**
     * Metodo para obtener la profundidad del arbol (distancia desde el elemento a la raiz)
     * @param elemento elemento del que se quiere obtener la profundidad
     * @return profundidad del elemento
     */
    public int profundidad(E elemento) {
        if (raiz == null) {
            return -1;
        }
        return raiz.profundidad(elemento, comparator, 0);
    }

    /**
     * Clase interna Nodo para representar los nodos del arbol
     */
    public class Nodo {
        /**
         * Dato del nodo
         */
        public E dato;
        /**
         * Hijo izq del nodo
         */
        public Nodo izquierdo;

        /**
         * Hijo der del nodo
         */
        public Nodo derecho;

        /**
         * Constructor por defecto
         * @param dato dato del nodo
         */
        public Nodo(E dato) {
            this.dato = dato;
            this.izquierdo = null;
            this.derecho = null;
        }

        /**
         * Metodo para insertar un elemento en el arbol
         * @param elemento elemento a insertar
         * @param comparator comparador que recibe como argumento
         * @return true si se ha insertado correctamente
         */
        public boolean insertar(E elemento, Comparator<? super E> comparator) {
            if (comparator == null) {
                @SuppressWarnings("unchecked")
                Comparable<? super E> comparable = (Comparable<? super E>) elemento;
                if (comparable.compareTo(dato) < 0) {
                    if (izquierdo == null) {
                        izquierdo = new Nodo(elemento);
                        return true;
                    } else {
                        return izquierdo.insertar(elemento, null);
                    }
                } else if (comparable.compareTo(dato) > 0) {
                    if (derecho == null) {
                        derecho = new Nodo(elemento);
                        return true;
                    } else {
                        return derecho.insertar(elemento, null);
                    }
                } else {
                    return false; // Elemento duplicado
                }
            } else {
                if (comparator.compare(elemento, dato) < 0) {
                    if (izquierdo == null) {
                        izquierdo = new Nodo(elemento);
                        return true;
                    } else {
                        return izquierdo.insertar(elemento, comparator);
                    }
                } else if (comparator.compare(elemento, dato) > 0) {
                    if (derecho == null) {
                        derecho = new Nodo(elemento);
                        return true;
                    } else {
                        return derecho.insertar(elemento, comparator);
                    }
                } else {
                    return false; // Elemento duplicado
                }
            }
        }

        /**
         * Método para buscar un elemento en el arbol
         * @param elemento elemento a buscar
         * @param comparator comparador que recibe como argumento
         * @return true si el elemento se encuentra en el arbol
         */
        public boolean buscar(E elemento, Comparator<? super E> comparator) {
            if (comparator == null) {
                @SuppressWarnings("unchecked")
                Comparable<? super E> comparable = (Comparable<? super E>) elemento;
                if (comparable.compareTo(dato) < 0) {
                    return izquierdo != null && izquierdo.buscar(elemento, null);
                } else if (comparable.compareTo(dato) > 0) {
                    return derecho != null && derecho.buscar(elemento, null);
                } else {
                    return true;
                }
            } else {
                if (comparator.compare(elemento, dato) < 0) {
                    return izquierdo != null && izquierdo.buscar(elemento, comparator);
                } else if (comparator.compare(elemento, dato) > 0) {
                    return derecho != null && derecho.buscar(elemento, comparator);
                } else {
                    return true;
                }
            }
        }

        /**
         * Metodo para eliminar un elemento del arbol
         * @param elemento elemento a eliminar
         * @param comparator comparador que recibe como argumento
         * @return nodo eliminado
         */
        public Nodo eliminar(E elemento, Comparator<? super E> comparator) {
            if (comparator == null) {
                @SuppressWarnings("unchecked")
                Comparable<? super E> comparable = (Comparable<? super E>) elemento;
                if (comparable.compareTo(dato) < 0) {
                    if (izquierdo != null) {
                        izquierdo = izquierdo.eliminar(elemento, null);
                    }
                } else if (comparable.compareTo(dato) > 0) {
                    if (derecho != null) {
                        derecho = derecho.eliminar(elemento, null);
                    }
                } else {
                    if (izquierdo == null && derecho == null) {
                        return null;
                    } else if (izquierdo == null) {
                        return derecho;
                    } else if (derecho == null) {
                        return izquierdo;
                    } else {
                        dato = derecho.minValue();
                        derecho = derecho.eliminar(dato, null);
                    }
                }
            } else {
                if (comparator.compare(elemento, dato) < 0) {
                    if (izquierdo != null) {
                        izquierdo = izquierdo.eliminar(elemento, comparator);
                    }
                } else if (comparator.compare(elemento, dato) > 0) {
                    if (derecho != null) {
                        derecho = derecho.eliminar(elemento, comparator);
                    }
                } else {
                    if (izquierdo == null && derecho == null) {
                        return null;
                    } else if (izquierdo == null) {
                        return derecho;
                    } else if (derecho == null) {
                        return izquierdo;
                    } else {
                        dato = derecho.minValue();
                        derecho = derecho.eliminar(dato, comparator);
                    }
                }
            }
            return this;
        }

        /**
         * Metodo para obtener el valor minimo del subarbol del nodo
         * @return valor minimo del subarbol derecho del nodo
         */
        public E minValue() {
            if (izquierdo != null) {
                return izquierdo.minValue();
            }
            return dato;
        }

        /**
         * Metodo para obtener el tamaño del arbol
         * @return tamaño del arbol
         */
        public int size() {
            int size = 1;
            if (izquierdo != null) {
                size += izquierdo.size();
            }
            if (derecho != null) {
                size += derecho.size();
            }
            return size;
        }

        /**
         * Metodo para obtener la altura del arbol
         * @param elemento elemento a buscar
         * @param comparator comparador que recibe como argumento
         * @return altura del arbol
         */
        public int altura(E elemento, Comparator<? super E> comparator) {
            if (comparator == null) {
                @SuppressWarnings("unchecked")
                Comparable<? super E> comparable = (Comparable<? super E>) elemento;
                if (comparable.compareTo(dato) < 0) {
                    return izquierdo != null ? izquierdo.altura(elemento, null) : -1;
                } else if (comparable.compareTo(dato) > 0) {
                    return derecho != null ? derecho.altura(elemento, null) : -1;
                } else {
                    return altura();
                }
            } else {
                if (comparator.compare(elemento, dato) < 0) {
                    return izquierdo != null ? izquierdo.altura(elemento, comparator) : -1;
                } else if (comparator.compare(elemento, dato) > 0) {
                    return derecho != null ? derecho.altura(elemento, comparator) : -1;
                } else {
                    return altura();
                }
            }
        }

        /**
         * Metodo para obtener la altura del arbol
         * @return altura del arbol
         */
        public int altura() {
            int alturaIzquierdo = izquierdo != null ? izquierdo.altura() : -1;
            int alturaDerecho = derecho != null ? derecho.altura() : -1;
            return Math.max(alturaIzquierdo, alturaDerecho) + 1;
        }

        /**
         * Metodo para obtener la profundidad de un elemento
         * @param elemento elemento a buscar
         * @param comparator comparador que recibe como argumento
         * @param profundidadActual profundidad actual del arbol
         * @return profundidad del elemento
         */
        public int profundidad(E elemento, Comparator<? super E> comparator, int profundidadActual) {
            if (comparator == null) {
                @SuppressWarnings("unchecked")
                Comparable<? super E> comparable = (Comparable<? super E>) elemento;
                if (comparable.compareTo(dato) < 0) {
                    return izquierdo != null ? izquierdo.profundidad(elemento, null, profundidadActual + 1) : -1;
                } else if (comparable.compareTo(dato) > 0) {
                    return derecho != null ? derecho.profundidad(elemento, null, profundidadActual + 1) : -1;
                } else {
                    return profundidadActual;
                }
            } else {
                if (comparator.compare(elemento, dato) < 0) {
                    return izquierdo != null ? izquierdo.profundidad(elemento, comparator, profundidadActual + 1) : -1;
                } else if (comparator.compare(elemento, dato) > 0) {
                    return derecho != null ? derecho.profundidad(elemento, comparator, profundidadActual + 1) : -1;
                } else {
                    return profundidadActual;
                }
            }
        }
    }

    /**
     * Clase para iterar el arbol
     */
    private class ArbolIterator implements Iterator<E> {
        /**
         * Nodo siguiente
         */
        private Nodo siguiente;

        /**
         * Constructor del iterador
         */
        public ArbolIterator() {
            siguiente = findMin(raiz);
        }

        /**
         * Metodo para encontrar el valor minimo del arbol
         * @param nodo nodo a buscar
         * @return nodo con el valor minimo
         */
        private Nodo findMin(Nodo nodo) {
            if (nodo != null) {
                while (nodo.izquierdo != null) {
                    nodo = nodo.izquierdo;
                }
            }
            return nodo;
        }

        /**
         * Metodo para saber si hay un siguiente elemento
         * @return true si hay un siguiente elemento, false en otro caso
         */
        @Override
        public boolean hasNext() {
            return siguiente != null;
        }

        /**
         * Metodo para obtener el siguiente elemento
         * @return siguiente elemento
         */
        @Override
        public E next() {
            if (siguiente == null) {
                return null;
            }
            E elemento = siguiente.dato;
            siguiente = findSuccessor(siguiente);
            return elemento;
        }

        /**
         * Metodo para encontrar el sucesor de un nodo
         * @param nodo nodo a buscar
         * @return sucesor del nodo
         */
        private Nodo findSuccessor(Nodo nodo) {
            if (nodo == null) {
                return null;
            }
            if (nodo.derecho != null) {
                return findMin(nodo.derecho);
            } else {
                Nodo sucesor = null;
                Nodo ancestro = raiz;
                while (ancestro != nodo) {
                    if (comparator == null) {
                        @SuppressWarnings("unchecked")
                        Comparable<? super E> comparable = (Comparable<? super E>) nodo.dato;
                        if (comparable.compareTo(ancestro.dato) < 0) {
                            sucesor = ancestro;
                            ancestro = ancestro.izquierdo;
                        } else {
                            ancestro = ancestro.derecho;
                        }
                    } else {
                        if (comparator.compare(nodo.dato, ancestro.dato) < 0) {
                            sucesor = ancestro;
                            ancestro = ancestro.izquierdo;
                        } else {
                            ancestro = ancestro.derecho;
                        }
                    }
                }
                return sucesor;
            }
        }
    }
}