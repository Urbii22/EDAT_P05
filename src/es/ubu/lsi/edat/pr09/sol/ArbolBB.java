package es.ubu.lsi.edat.pr09.sol;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class ArbolBB<E> extends AbstractSet<E> {
    private Nodo raiz;
    private Comparator<? super E> comparator;
    public ArbolBB() {
        this((Comparator<? super E>) null);
    }

    public ArbolBB(Comparator<? super E> comparator) {
        this.raiz = null;
        this.comparator = comparator;
    }

    public ArbolBB( Collection<? extends E> collection,Comparator<? super E> comparator) {
        this.comparator = comparator;
        if (collection != null) {
            addAll(collection);
        }
    }

    public ArbolBB(Collection<? extends E> collection) {
        if (collection != null) {
            addAll(collection);
        }
    }


    @Override
    public boolean add(E elemento) {
        if (raiz == null) {
            raiz = new Nodo(elemento);
            return true;
        } else {
            return raiz.insertar(elemento, comparator);
        }
    }

    @Override
    public boolean contains(Object objeto) {
        if (raiz == null || objeto == null) {
            return false;
        }
        @SuppressWarnings("unchecked")
        E elemento = (E) objeto;
        return raiz.buscar(elemento, comparator);
    }

    @Override
    public boolean remove(Object objeto) {
        if (raiz == null || objeto == null) {
            return false;
        }
        @SuppressWarnings("unchecked")
        E elemento = (E) objeto;
        if (contains(elemento)) {
            raiz = raiz.eliminar(elemento, comparator);
            return true;
        }
        return false;
    }


    @Override
    public int size() {
        return raiz != null ? raiz.size() : 0;
    }

    public void clear() {
        raiz = null;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArbolIterator();
    }

    public int altura(E elemento) {
        if (raiz == null) {
            return -1;
        }
        return raiz.altura(elemento, comparator);
    }

    public int profundidad(E elemento) {
        if (raiz == null) {
            return -1;
        }
        return raiz.profundidad(elemento, comparator, 0);
    }

    private class Nodo {
        private E dato;
        private Nodo izquierdo;
        private Nodo derecho;

        public Nodo(E dato) {
            this.dato = dato;
            this.izquierdo = null;
            this.derecho = null;
        }

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

        public E minValue() {
            if (izquierdo != null) {
                return izquierdo.minValue();
            }
            return dato;
        }

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

        public int altura() {
            int alturaIzquierdo = izquierdo != null ? izquierdo.altura() : -1;
            int alturaDerecho = derecho != null ? derecho.altura() : -1;
            return Math.max(alturaIzquierdo, alturaDerecho) + 1;
        }

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

    private class ArbolIterator implements Iterator<E> {
        private Nodo siguiente;

        public ArbolIterator() {
            siguiente = findMin(raiz);
        }

        private Nodo findMin(Nodo nodo) {
            if (nodo != null) {
                while (nodo.izquierdo != null) {
                    nodo = nodo.izquierdo;
                }
            }
            return nodo;
        }

        @Override
        public boolean hasNext() {
            return siguiente != null;
        }

        @Override
        public E next() {
            if (siguiente == null) {
                return null;
            }
            E elemento = siguiente.dato;
            siguiente = findSuccessor(siguiente);
            return elemento;
        }

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
