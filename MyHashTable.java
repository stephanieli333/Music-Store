
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class MyHashTable<K,V> implements Iterable<HashPair<K,V>>{
    // num of entries to the table
    private int numEntries;
    // num of buckets 
    private int numBuckets;
    // load factor needed to check for rehashing 
    private static final double MAX_LOAD_FACTOR = 0.75;
    // ArrayList of buckets. Each bucket is a LinkedList of HashPair
    private ArrayList<LinkedList<HashPair<K,V>>> buckets; 
    
    // constructor
    public MyHashTable(int initialCapacity) {
        // ADD YOUR CODE BELOW THIS
        this.numBuckets = initialCapacity;
        this.numEntries = 0;
        if(initialCapacity <0) {
        	throw new IllegalArgumentException("capacity must be >=0");
        }
        this.buckets = new ArrayList<LinkedList<HashPair<K,V>>>(initialCapacity);
        for(int i = 0; i<initialCapacity; i++) {
        	this.buckets.add(new LinkedList<HashPair<K,V>>());
        }
        //ADD YOUR CODE ABOVE THIS
    }
    
    public int size() {
        return this.numEntries;
    }
    
    public int numBuckets() {
        return this.numBuckets;
    }
    
    /**
     * Returns the buckets vairable. Usefull for testing  purposes.
     */
    public ArrayList<LinkedList< HashPair<K,V> > > getBuckets(){
        return this.buckets;
    }
    /**
     * Given a key, return the bucket position for the key. 
     */
    public int hashFunction(K key) {
        int hashValue = Math.abs(key.hashCode())%this.numBuckets;
        return hashValue;
    }
    /**
     * Takes a key and a value as input and adds the corresponding HashPair
     * to this HashTable. Expected average run time  O(1)
     */
    public V put(K key, V value) {
        //  ADD YOUR CODE BELOW HERE
    	// gets the linked list at the hashValue of the key, and adds the 
    	// hash pair to the linked list
    	if(key== null) {
    		return null;
    	}
    	LinkedList<HashPair<K,V>> list = this.buckets.get(hashFunction(key));
    	if (list != null){
    		for(HashPair<K,V> x:list) {
    			if (x.getKey().equals(key)) {
    				V temp = x.getValue();
    				x.setValue(value);
    				return temp;
    			}
    		}
    	}
    	list.add(new HashPair<K,V>(key, value));
        this.numEntries ++;
    	// if the number of entries exceeeds the max load factor, 
    	// then a new hash table needs to be made with more buckets
    	if ((double)this.numEntries/this.numBuckets > MAX_LOAD_FACTOR) {
    		this.rehash();
    	}
    	return value;
        //  ADD YOUR CODE ABOVE HERE
    }    
    /**
     * Get the value corresponding to key. Expected average runtime = O(1)
     */
    
    public V get(K key) {
        //ADD YOUR CODE BELOW HERE
    	if(key==null) {
    		return null;
    	}
    	LinkedList<HashPair<K,V>> list = this.buckets.get(hashFunction(key));
    	// returns the value if the key exists; if not, returns null
    	for(HashPair<K,V> x:list) {
    		if (x.getKey().equals(key)) {
    			return x.getValue();
    		}
    	}
        return null;
        //ADD YOUR CODE ABOVE HERE
    }
    
    /**
     * Remove the HashPair correspoinding to key . Expected average runtime O(1) 
     */
    public V remove(K key) {
        //ADD YOUR CODE BELOW HERE
    	if(key == null) {
    		return null;
    	}
    	LinkedList<HashPair<K,V>> list = this.buckets.get(hashFunction(key));
    	// returns the value if the key exists; if not, returns null
    	for(HashPair<K,V> x:list) {
    		if(x.getKey().equals(key)) {
    			V temp = x.getValue();
    			list.remove(x);
    			this.numEntries--;
    			return temp;
    		}
    	}
    	return null;
        //ADD YOUR CODE ABOVE HERE
    }
    
    // Method to double the size of the hashtable if load factor increases
    // beyond MAX_LOAD_FACTOR.
    // Made public for ease of testing.
    
    public void rehash() {
        //ADD YOUR CODE BELOW HERE
    	MyHashTable<K,V> temp = new MyHashTable<K,V>(2*this.numBuckets);
		for(LinkedList<HashPair<K, V>> current:this.buckets) {
			for(HashPair<K,V> pair:current) {
    			temp.put(pair.getKey(), pair.getValue());
			}
		}
		this.numBuckets = temp.numBuckets;
		this.buckets = temp.buckets;
        //ADD YOUR CODE ABOVE HERE
    }
    
    
    /**
     * Return a list of all the keys present in this hashtable.
     */
    
    public ArrayList<K> keys() {
        //ADD YOUR CODE BELOW HERE
    	// issue: this is running at O(m) where m is the number of buckets rather
    	// than O(n), where n is the number of entries...
    	ArrayList<K> list = new ArrayList<K>(this.numEntries);
    	for(LinkedList<HashPair<K,V>> l:this.buckets) {
    		if (!l.isEmpty()) {
    			for(HashPair<K,V> pair:l) {
    				list.add(pair.getKey());
    			}
    		}
    	}
        return list;
        //ADD YOUR CODE ABOVE HERE
    }
    
    /**
     * Returns an ArrayList of unique values present in this hashtable.
     * Expected average runtime is O(n)
     */
    public ArrayList<V> values() {
        //ADD CODE BELOW HERE
        ArrayList<V> list = new ArrayList<V>(this.numEntries);
        MyHashTable<V,Integer> table = new MyHashTable<V,Integer>(this.numBuckets);
        for(LinkedList<HashPair<K,V>> l:this.buckets) {
    		for(HashPair<K,V> pair:l) {
    			if(table.get(pair.getValue())==null) {
    				table.put(pair.getValue(), pair.getValue().hashCode());
    				list.add(pair.getValue());
    			}
    		}
    	}
        
        return list;
        //ADD CODE ABOVE HERE
    }
    
    
    @Override
    public MyHashIterator iterator() {
        return new MyHashIterator();
    }

    
    private class MyHashIterator implements Iterator<HashPair<K,V>> {
        private LinkedList<HashPair<K,V>> entries;
        private MyHashIterator() {
            //ADD YOUR CODE BELOW HERE
        	entries = new LinkedList<HashPair<K,V>>();
        	for(LinkedList<HashPair<K,V>> list:buckets) {
        		if(!list.isEmpty()) {
        			for(HashPair<K,V> pair:list) {
        				entries.add(pair);
        			}
        		}
        	}
            //ADD YOUR CODE ABOVE HERE
        }
        
        @Override
        public boolean hasNext() {
            //ADD YOUR CODE BELOW HERE
        	if(entries!=null && !entries.isEmpty()) {
        		return true;
        	}        	
            return false;
            //ADD YOUR CODE ABOVE HERE
        }
        
        @Override
        public HashPair<K,V> next() {
            //ADD YOUR CODE BELOW HERE
        	
        	// needs to throw NoSuchElement exception if you've reached the end and 
        	// there are no elements afterwards
        	if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
        	else {
        		return entries.removeFirst();
          	}
            //ADD YOUR CODE ABOVE HERE
        }
        
    }
}
