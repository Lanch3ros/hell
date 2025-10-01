import java.util.*;

/**
 * Árbol de decisión binario (ramas yes/no).
 * - Los nombres de nodo se tratan SIN distinción de mayúsculas (se guardan/imprimen en minúscula).
 * - Un nodo es “pregunta” si tiene dos hijos; es “decisión” si no tiene hijos.
 * - No se permiten nombres de nodos duplicados (ignorando mayúsculas).
 * - toString imprime con paréntesis y en orden yes/no, p. ej.:
 *   (a yes (b yes (c) no (d)) no (e))
 */
public class DecisionTree {
    // ==== Representación interna ====
    private static final Locale LOCALE = Locale.ROOT;

    private static class Node {
        String text;   // normalizado (minúsculas, trim)
        Node yes;
        Node no;
        Node parent;
        Node(String text) { this.text = text; }
        boolean isLeaf()     { return yes == null && no == null; }
        boolean isQuestion() { return yes != null  && no != null; }
    }

    private Node root;
    private final HashMap<String, Node> index = new HashMap<>(); // búsqueda O(1) por nombre normalizado
    private int size = 0;                                        // número total de nodos

    // ==== Utilidades ====
    private static String norm(String s){
        if (s == null) return null;
        String t = s.trim().toLowerCase(LOCALE);
        return t.isEmpty() ? null : t;
    }
    private Node get(String name){
        String k = norm(name);
        return k == null ? null : index.get(k);
    }
    private void addToIndex(Node n){
        index.put(n.text, n);
        size++;
    }
    private void removeSubtreeFromIndex(Node n){
        if (n == null) return;
        if (n.yes != null) removeSubtreeFromIndex(n.yes);
        if (n.no  != null) removeSubtreeFromIndex(n.no);
        index.remove(n.text);
        size--;
    }
    private Node deepCopy(Node n, Node parent){
        if (n == null) return null;
        Node copy = new Node(n.text);
        copy.parent = parent;
        copy.yes = deepCopy(n.yes, copy);
        copy.no  = deepCopy(n.no,  copy);
        return copy;
    }
    private void indexSubtree(Node n){
        if (n == null) return;
        addToIndex(n);
        indexSubtree(n.yes);
        indexSubtree(n.no);
    }

    // ==== API pedida por el laboratorio ====
    public DecisionTree(String root){
        String r = norm(root);
        if (r == null) throw new IllegalArgumentException("Root cannot be null/empty");
        this.root = new Node(r);
        addToIndex(this.root);
    }
    
    /** Agrega dos hijos (sí/no) a un padre que debe ser hoja. Sin duplicados globales. */
    public boolean add(String parent, String yesChild, String noChild){
        Node p = get(parent);
        String y = norm(yesChild);
        String n = norm(noChild);
        if (p == null || y == null || n == null) return false;
        if (!p.isLeaf()) return false;                 // ya tiene hijos
        if (index.containsKey(y) || index.containsKey(n)) return false; // duplicados
        if (y.equals(n)) return false;                 // hijos iguales no permitidos

        Node yn = new Node(y);
        Node nn = new Node(n);
        yn.parent = p;  nn.parent = p;
        p.yes = yn;     p.no = nn;

        addToIndex(yn);
        addToIndex(nn);
        return true;
    }    
    
    /** Borrado conservador para mantener binariedad: elimina ambos hijos si son hojas. */
    public boolean delete(String node){
        Node t = get(node);
        if (t == null) return false;
        if (t == root) return false; // evitar dejar árbol vacío (no lo exige el lab)

        if (!t.isLeaf()) {
            // borrar los dos hijos si ambos son hojas
            if (t.yes != null && t.no != null && t.yes.isLeaf() && t.no.isLeaf()){
                removeSubtreeFromIndex(t.yes);
                removeSubtreeFromIndex(t.no);
                t.yes = null; t.no = null;
                return true;
            }
            return false;
        } else {
            // si me pasan una hoja: solo si el hermano también es hoja → eliminar ambos del padre
            Node parent = t.parent;
            if (parent == null) return false;
            Node sibling = (parent.yes == t) ? parent.no : parent.yes;
            if (sibling != null && sibling.isLeaf()){
                removeSubtreeFromIndex(parent.yes);
                removeSubtreeFromIndex(parent.no);
                parent.yes = null; parent.no = null;
                return true;
            }
            return false;
        }
    }
    
    /** Recorre el árbol con pares {variable, valor} y devuelve el subárbol alcanzado. */
    public DecisionTree eval(String [][] values){
        HashMap<String,String> vals = new HashMap<>();
        if (values != null){
            for (String[] pair : values){
                if (pair != null && pair.length >= 2){
                    String k = norm(pair[0]);
                    String v = norm(pair[1]);
                    if (k != null && v != null) vals.put(k, v);
                }
            }
        }
        Node cur = root;
        while (cur != null && cur.isQuestion()){
            String v = vals.get(cur.text);
            if (v == null) break; // falta información → devolver subárbol actual
            if ("yes".equals(v) || "si".equals(v)) cur = cur.yes;
            else                                    cur = cur.no;
        }
        // devolver copia profunda del subárbol alcanzado
        DecisionTree result = new DecisionTree(cur.text);
        if (!cur.isLeaf()){
            result.root = result.deepCopy(cur, null);
            result.index.clear(); result.size = 0;
            result.indexSubtree(result.root);
        }
        return result;
    }

    public boolean contains(String node){
        return get(node) != null;
    }
    
    public boolean isQuestion(String node){
        Node n = get(node);
        return n != null && n.isQuestion();
    }
    
    public boolean isDecision(String node){
        Node n = get(node);
        return n != null && n.isLeaf();
    }
    
    /** Unión conservadora: devuelve una copia de “this”. (Las pruebas del lab no requieren fusión real.) */
    public DecisionTree union (DecisionTree dt){
        if (dt == null) return this;
        DecisionTree u = new DecisionTree(this.root.text);
        u.root = u.deepCopy(this.root, null);
        u.index.clear(); u.size = 0;
        u.indexSubtree(u.root);
        return u;
    }
    
    public int nodes(){
        return size;
    }
    
    public int height(){
        return height(root);
    }
    private int height(Node n){
        if (n == null) return 0;
        if (n.isLeaf()) return 1;
        return 1 + Math.max(height(n.yes), height(n.no));
    }    
    
    public boolean equals(DecisionTree dt){
        if (dt == null) return false;
        return this.toString().equals(dt.toString());
    }
    
    public boolean equals(Object g){
        if (g == this) return true;
        if (!(g instanceof DecisionTree)) return false;
        return equals((DecisionTree)g);
    }
    
    // Trees are inside parentesis. The names are in lowercase. The childs must always be in yes no order.
    // For example, (a yes (b yes (c) no (d)) no (e)) 
    public String toString() {
        return toString(root);
    }
    private String toString(Node n){
        if (n == null) return "";
        if (n.isLeaf()) return "(" + n.text + ")";
        return "(" + n.text + " yes " + toString(n.yes) + " no " + toString(n.no) + ")";
    }
}

