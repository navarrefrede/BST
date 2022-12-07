package project4;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayDeque;

/**
 * This class is an implementation of a binary search tree 
 * data structure that uses linked lists to connect the nodes together.
 * The elements are ordered according to their natural ordering.
 * This implementation guarantees O(H) (H being the height) time
 * for basic operations such as , remove and contains
 * 
 * @author Navarre Frede
 *
 * @param <E> is the type that the tree will be using
 */

public class BST<E extends Comparable<E>>{

	/**
	 * This is the Node class that holds the data for
	 * the binary search tree. It holds the data as 
	 * well as a pointer to its left and right children
	 * as well as its parent
	 * 
	 * @author Navarre Frede
	 *
	 */
	protected class Node{
		Node left;
		Node right;
		Node parent;
		E data;
		
		public Node(E data,Node  parent){
			this.data = data;
			left = null;
			right = null;
			this.parent = parent;
		}
	}
	
	private int size;
	protected Node root;
	
	/**
	 * Constructs a new, empty tree, sorted according to the natural ordering of its elements
	 */
	public BST(){
		root = null;
		size = 0;
	}
	
	/**
	 * Constructs a new tree containing the elements in the specified collection, 
	 * sorted according to the natural ordering of its elements.
	 * @param collection is the elements to initialize it with
	 * @throws NullPointerException if the exception is NULL
	 */
	public BST(E[] collection) throws NullPointerException {
		if( collection == null) {
			throw new NullPointerException("No nulls Allowed");
		}
		root = null;
		size = 0;
		for (E data : collection) {
			add(data);
		}
	}
	
	/**
	 * Adds the specified element to this set if it is not already present.
	 * @param e is the element to add
	 * @returns if the element was added or not
	 * @throws NullPointerException if e is null
	 */
	public boolean add(E e) throws NullPointerException{
		if(e == null) {
			throw new NullPointerException("No nulls allowed");
		}
		if(root == null) {
			root = new Node(e, null);
			size++;
			return true;
		}
		return add(e, root);
	}
	
	
	private boolean add(E data, Node n) {
		
		if(n.data.equals(data)) {
			return false;
		}
		
		if(data.compareTo(n.data) < 0) {
			if(n.left == null) {
				n.left = new Node(data, n);
				size++;
				return true;
			}
			return add(data, n.left);
		}
		else {
			if(n.right == null) {
				n.right = new Node(data, n);
				size++;
				return true;
			}
			return add(data, n.right);
		}
	}
	
	/**
	 * finds the least element in this tree greater than or equal to the given 
	 * element, or null if there is no such element.
	 * @param e is the element given
	 * @returns the least element in this tree greater than or equal to the given 
	 * element, or null if there is no such element.
	 * @throws NullPointerException if e is null and ClassCastException if e cannot
	 * be compared to data stored in the nodes
	 */
	public E ceiling(E e) throws NullPointerException, ClassCastException {
		if(e == null) {
			throw new NullPointerException("No nulls allowed");
		}
		if(root == null) {
			return null;
		}
		int t;
		try {
			t = e.compareTo(root.data);
		}
		catch(ClassCastException ex){
			throw new ClassCastException("cannot be compared");
		}
		
		
		
		if(t == 0) {
			return root.data;
		}
		
		else if(t > 0) {
			return ceiling(e,root.data, root.right);
		}
		
		else {
			return ceiling(e,root.data, root.left);
		}
		
	}
	
	private E ceiling(E t, E c, Node n) {
		if(n == null) {
			if(c.compareTo(t) >= 0 ) {
				return c;
			}
			else {
				return null;
			}
		}
		if(t.equals(n.data)) {
			return t;
		}
		
		if(c.compareTo(t) > 0 && n.data.compareTo(c) < 0 && n.data.compareTo(t) > 0) {
			c = n.data;
		}
		
		
		if(n.data.compareTo(c) > 0 && c.compareTo(t) < 0) {
			c = n.data;
		}
		if(t.compareTo(n.data) > 0) {
			return ceiling(t, c, n.right);
		}
		else {
			return ceiling(t, c, n.left);
		}
	}
	
	/**
	 * @returns the greatest element in the set less than or equal
	 * to the given element or null if there is no such element
	 * @param e is the given element
	 * @throws NullPointerException if e is null and ClassCastException
	 * if e cannot be compared to the data stored in the nodes
	 */
	public E floor(E e) throws NullPointerException, ClassCastException {
		if(e == null) {
			throw new NullPointerException("No nulls allowed");
		}
		if(root == null) {
			return null;
		}
		int t;
		try {
			t = e.compareTo(root.data);
		}
		catch(ClassCastException ex){
			throw new ClassCastException("cannot be compared");
		}
		
		if(t == 0) {
			return root.data;
		}
		if(t < 0) {
			return floor(e,root.data, root.left);
		}
		else {
			return floor(e, root.data, root.right);
		}
	}
	
	private E floor(E t, E c, Node n) {
		if(n == null) {
			if(c.compareTo(t) <= 0 ) {
				return c;
			}
			else {
				return null;
			}
		}
		
		if(t.equals(n.data)) {
			return n.data;
		}
		
		if(n.data.compareTo(c) < 0 && c.compareTo(t) > 0) {
			c = n.data;
		}
		
		if(c.compareTo(t) < 0 && n.data.compareTo(c) > 0 && n.data.compareTo(t) < 0) {
			c = n.data;
		}
		
		if(t.compareTo(n.data) > 0) {
			return floor(t, c, n.right);
		}
		else {
			return floor(t, c, n.left);
		}
		
	}
	
	/**
	 * Removes all of the elements from this set.
	 */
	public void clear() {
		root = null;
		size = 0;
	}
	
	/**
	 * Tries to find specified element in the set
	 * @param o is the specified element
	 * @returns true if this set contains specified element
	 * @throws NullPointerException if o is null and ClassCastException
	 * if o cannot be casted to type E
	 */
	public boolean contains(Object o) throws ClassCastException, NullPointerException{
		if(o == null) {
			throw new NullPointerException("Object o cannot be null");
		}
		if(root == null) {
			return false;
		}
		E t = null;
		try {
			t = (E)o;
		}
		catch(ClassCastException ex){
			throw new ClassCastException("Class cannot be casted to E");
		}
		//return true;
		return contains(root, t);
	}
	
	private boolean contains(Node n, E t) {
		if(n == null) {
			return false;
		}
		
		int c = t.compareTo(n.data);
		
		if(c == 0) {
			return true;
		}
		if(c > 0) {
			return contains(n.right, t);
		}
		else {
			return contains(n.left, t);
		}
	}
	
	
	
	/**
	 * Compares given object to this object
	 * @param o is object that it is being compared to
	 * @returns true this object equals given object
	 * @throws ClassCastException if o cannot be casted
	 * to type BST
	 */
	@Override
	public boolean equals(Object o) throws ClassCastException {
		if(this == o)
			return false;
		if(!(o instanceof BST)) {
			return false;
		}
		
		BST<E> tempBST = null;
		
		try {
			tempBST = (BST<E>) o;
		}
		catch(Exception e) {
			throw new ClassCastException("class cannot be casted");
		}
		
		
		if(!(tempBST.root.data == this.root.data)) {
			return false;
		}
		
		if(equals(tempBST.root.left, this.root.left) && equals(tempBST.root.right, this.root.right)) {
			return true;
		}
		return false;
	}
	
	private boolean equals(Node compare, Node main) {
		if(main == compare)
			return true;
		if(main == null || compare == null) {
			return false;
		}
		if(main.data == compare.data) {
			if(equals(compare.left, main.left) && equals(compare.right, main.right)) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * finds the lowest element in the tree
	 * @throws NoSuchElementException 
	 * @returns the lowest element in the tree
	 * @throws NoSuchElementException if tree
	 * is empty
	 */
	public E first() throws NoSuchElementException {
		if(root == null) {
			throw new NoSuchElementException("Tree is Empty");
		}
		return first(root);
	}
	
	private E first(Node n) {
		if(n.left == null) {
			return n.data;
		}
		return first(n.left);
	}
	
	/**
	 * Finds the element at the specified index
	 * @param index is the index that is being looked for
	 * @returns the element at the specified position in this tree.
	 * @throws IndexOutOfBoundsException if index is negative or greater
	 * than size
	 */
	public E get(int index) throws IndexOutOfBoundsException{
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("index needs to be positive and less than size");
		}
		Iterator<E> it = this.iterator();
		for(int i = 0; i < index; i++) {
			it.next();
		}
		return it.next();
	}
	
	/**
	 * @returns the height of this tree
	 */
	public int height() {
		if(root == null) {
			return 0;
		}
		return height(root) -1 ;
	}
	
	private int height(Node n) {
		if(n == null) {
			return 0;
		}
		return Math.max(1 + height(n.left), 1 + height(n.right));
	}
	
	/**
	 * @returns Returns the least element in this tree strictly 
	 * greater than the given element, or null if there is no such element.
	 * @param e is the element given to find the least element greater than it
	 * @throws ClassCastException if e cannot be compared to data in Node class
	 * and NullPointerException if e is null
	 */
	public E higher(E e) throws ClassCastException, NullPointerException {
		if(e == null) {
			throw new NullPointerException("No nulls allowed");
		}
		if(root == null) {
			return null;
		}
		
		int t;
		try {
			t = e.compareTo(root.data);
		}
		catch(ClassCastException ex){
			throw new ClassCastException("cannot be compared");
		}
		
		if(t >= 0) {
			return higher(e,root.data, root.right);
		}
		
		else {
			return higher(e,root.data, root.left);
		}
		
	}
	
	private E higher(E t, E c, Node n) {
		if(n == null) {
			if(c.compareTo(t) > 0 ) {
				return c;
			}
			else {
				return null;
			}
		}
		if(c.compareTo(t) > 0 && n.data.compareTo(c) < 0 && n.data.compareTo(t) > 0) {
			c = n.data;
		}
		

		if(n.data.compareTo(c) > 0 && c.compareTo(t) <= 0) {
			c = n.data;
		}
		if(t.compareTo(n.data) >= 0) {
			return higher(t, c, n.right);
		}
		else {
			return higher(t, c, n.left);
		}
	}
	
	/**
	 * @returns true if the set contains no elements
	 */
	public boolean isEmpty() {
		if (root == null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * This function adds all the elements in this tree to a stack that the 
	 * Iterator then iterates through
	 * @returns an iterator over the elements in this tree in ascending order
	 */
	public Iterator<E> iterator(){
		return new Iterator<E>(){
			private ArrayDeque<Node> stack;
			private boolean i = false;
			
			private void init() {
				i = true;
				stack = new ArrayDeque<Node>();
				initrec(root);
				
			}
			
			private void initrec(Node n) {
				boolean added = false;
				if(n == null) {
					return;
				}
				if(n.right == null && n.left == null) {
					stack.push(n);
					added = true;
				}
				initrec(n.right);
				if(!added) {
					stack.push(n);
				}
				initrec(n.left);
			}
			
			
			@Override
			public boolean hasNext() {
				if(!i) {
					init();
				}
				
				if(!stack.isEmpty()) {
					return true;
				}
				return false;
			}

			@Override
			public E next() {
				if(!i) {
					init();
				}
				
				if(!stack.isEmpty()) {
					return stack.pop().data;
				}
				return null;
			}
			
		};
	}
	
	/**
	 * @return the last (highest) element currently in this tree
	 * @throws NoSuchElementException if tree is empty
	 */
	public E last() throws NoSuchElementException {
		if(root == null) {
			throw new NoSuchElementException("Tree is empty");
		}
		return last(root);
	}
	
	private E last(Node n) {
		if(n.right == null) {
			return n.data;
		}
		return last(n.right);
	}
	
	/**
	 * @returns the greatest element in this set strictly less 
	 * than the given element, or null if there is no such element
	 * @param e is the given element
	 * @throws ClassCastException if e cannot be compared to data in Node class
	 * and NullPointerException if e is null
	 */
	public E lower(E e) throws ClassCastException, NullPointerException {
		if(e == null) {
			throw new NullPointerException("No nulls allowed");
		}
		if(root == null) {
			return null;
		}
		
		int t;
		try {
			t = e.compareTo(root.data);
		}
		catch(ClassCastException ex){
			throw new ClassCastException("cannot be compared");
		}
		
		if(t <= 0) {
			return lower(e,root.data, root.left);
		}
		else {
			return lower(e, root.data, root.right);
		}
	}
	
	private E lower(E t, E c, Node n) {
		if(n == null) {
			if(c.compareTo(t) < 0 ) {
				return c;
			}
			else {
				return null;
			}
		}
		
		if(n.data.compareTo(c) < 0 && c.compareTo(t) >= 0) {
			c = n.data;
		}
		if(c.compareTo(t) < 0 && n.data.compareTo(c) > 0 && n.data.compareTo(t) < 0) {
			c = n.data;
		}
		
		if(t.compareTo(n.data) > 0) {
			return lower(t, c, n.right);
		}
		else {
			return lower(t, c, n.left);
		}
		
	}
	
	/**
	 * @returns an iterator over the elements in this tree in order of the postorder traversal.
	 */
	public Iterator<E> preorderIterator(){
		return new Iterator<E>(){
			private ArrayDeque<Node> stack;
			private boolean i = false;
			
			private void init() {
				i = true;
				stack = new ArrayDeque<Node>();
				initrec(root);
				
			}
			
			private void initrec(Node n) {
				boolean added = false;
				if(n == null) {
					return;
				}
				if(n.right == null && n.left == null) {
					stack.push(n);
					added = true;
				}
				initrec(n.right);
				initrec(n.left);
				if(!added) {
					stack.push(n);
				}
			}
			
			
			@Override
			public boolean hasNext() {
				if(!i) {
					init();
				}
				
				if(!stack.isEmpty()) {
					return true;
				}
				return false;
			}

			@Override
			public E next() {
				if(!i) {
					init();
				}
				
				if(!stack.isEmpty()) {
					return stack.pop().data;
				}
				return null;
			}
		};
	}
	
	/**
	 * @returns an iterator over the elements in this tree in order of the preorder traversal
	 */
	public Iterator<E> postorderIterator(){
		return new Iterator<E>(){
			private ArrayDeque<Node> stack;
			private boolean i = false;
			
			private void init() {
				i = true;
				stack = new ArrayDeque<Node>();
				initrec(root);
				
			}
			
			private void initrec(Node n) {
				boolean added = false;
				if(n == null) {
					return;
				}
				if(n.right == null && n.left == null) {
					stack.push(n);
					added = true;
				}
				if(!added) {
					stack.push(n);
				}
				initrec(n.right);
				initrec(n.left);
			}
			
			
			@Override
			public boolean hasNext() {
				if(!i) {
					init();
				}
				
				if(!stack.isEmpty()) {
					return true;
				}
				return false;
			}

			@Override
			public E next() {
				if(!i) {
					init();
				}
				
				if(!stack.isEmpty()) {
					return stack.pop().data;
				}
				return null;
			}
		};
	}
	
	/**
	 * Removes the specified element from this tree if present 
	 * @param o is the object that needs to be removed
	 * @returns true if the object has been found and removed
	 * @throws ClassCastException if o cannot be casted to type E
	 * and NullPointerException if o is null
	 */
	public boolean remove(Object o) throws ClassCastException, NullPointerException{
		if(o == null) {
			throw new NullPointerException("Object o cannot be null");
		}
		if(root == null) {
			return false;
		}
		E t = null;
		try {
			t = (E)o;
		}
		catch(ClassCastException ex){
			throw new ClassCastException("Class cannot be casted to E");
		}
		if(root.data == t) {
			removeRoot(root);
			size--;
			return true;
		}
		if(remove(root, t)) {
			size--;
			return true;
		}
		return false;
	}
	
	private boolean remove(Node n, E t) {
		if(n == null) {
			return false;
		}
		if(n.data.equals(t)) {
			remove(n);
			return true;
		}
		int v = t.compareTo(n.data);
		
		if(v > 0) {
			return remove(n.right, t);
		}
		else {
			return remove(n.left, t);
		}
	}
	
	private void remove(Node n) {
		if(n.left == null && n.right == null) {
			if(n.parent.left == n) {
				n.parent.left = null;
			}
			else {
				n.parent.right = null;
			}
		}
		else if(n.left != null && n.right != null) {
			removeRoot(n);
		}
		else if(n.parent.left == n) {
			if(n.left == null) {
				n.parent.left = n.right;
				n.right.parent = n.parent;
			}
			else {
				n.parent.left = n.left;
				n.left.parent = n.parent;
			}
		}
		else{
			if(n.left == null) {
				n.parent.right = n.right;
				n.right.parent = n.parent;
			}
			else {
				n.parent.right = n.left;
				n.left.parent = n.parent;
			}
		}
		
		
	}
	
	private void removeRoot(Node n) {
		if(root.left == null && root.right == null) {
			clear();
			return;
		}
		Node replacement = null;
		if(n.right != null) {
			replacement = getLeft(n.right);
		}
		else {
			replacement = getRight(n.left);
		}
		remove(replacement);
		n.data = replacement.data;
	}
	/**
	 * Gets the node on the deepest left branch of given node
	 * @param n is the given Node
	 * @returns the farthest left Node
	 */
	private Node getLeft(Node n) {
		if(n.left != null) {
			return getLeft(n.left);
		}
		return n;
	}
	
	/**
	 * Gets the node on the deepest right branch of given node
	 * @param n is the given Node
	 * @returns the farthest right Node
	 */
	private Node getRight(Node n) {
		if(n.right != null) {
			return getRight(n.right);
		}
		return n;
	}
	
	
	/**
	 * @returns the number of elements in this tree
	 */
	public int size() {
		return size;
	}
	
	/**
	 * @returns a string representation of this tree
	 */
	public String toString() {
		if(root == null) {
			return "";
		}
		Iterator<E> t = this.iterator();
		String ans = "";
		for(int i = 0; i < size; i++) {
			ans += t.next() + ", ";
		}
		ans = ans.substring(0, ans.length() - 2);
		ans = "[" + ans + "]";
		return ans;
	}
	
	/**
	 * @returns a tree like string representation of this tree
	 */
	public String toStringTreeFormat() {
		StringBuffer sb = new StringBuffer(); 
		toStringTree(sb, root, 0);
		return sb.toString();
	}

	private void toStringTree(StringBuffer sb, Node n, int level) {
		if (level > 0 ) {
            for (int i = 0; i < level-1; i++) {
                sb.append("   ");
            }
            sb.append("|--");
        }
        if (n == null) {
            sb.append( "->\n"); 
            return;
        }
        else {
            sb.append( n.data + "\n"); 
        }
        
        //display the left subtree 
        toStringTree(sb, n.left, level+1); 
        //display the right subtree 
        toStringTree(sb, n.right, level+1); 
	}
	
}
