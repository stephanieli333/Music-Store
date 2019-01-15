
import java.util.ArrayList;

public class MusicStore {
    private ArrayList<Song> songs;
    private MyHashTable<String, Song> htSong; 
    private MyHashTable<String, ArrayList<Song>> htArtist; 
    private MyHashTable<Integer, ArrayList<Song>> htYear; 
    
    public MusicStore(ArrayList<Song> songs) {
    	this.songs = new ArrayList<Song>();
        htSong = new MyHashTable<String,Song>(songs.size()*2);
        htArtist = new MyHashTable<String,ArrayList<Song>>(songs.size()*2);
        htYear = new MyHashTable<Integer,ArrayList<Song>>(songs.size()*2);

        for(Song song:songs) {
        	this.addSong(song);
        }
    }
    
    
    /**
     * Add Song s to this MusicStore
     */
    public void addSong(Song s) {
        songs.add(s);
        htSong.put(s.getTitle(), s);
        if(this.htArtist.get(s.getArtist())!=null) {
    		htArtist.get(s.getArtist()).add(s);
    	}
    	else {
    		ArrayList<Song> titles = new ArrayList<Song>();
    		titles.add(s);
    		htArtist.put(s.getArtist(), titles);
    	}
    	if(this.htYear.get(s.getYear())!=null) {
    		htYear.get(s.getYear()).add(s);
    	}
    	else {
    		ArrayList<Song> titles = new ArrayList<Song>();
    		titles.add(s);
    		htYear.put(s.getYear(), titles);
    	}
    }
    
    /**
     * Search this MusicStore for Song by title and return any one song 
     * by that title 
     */
    public Song searchByTitle(String title) {
    	return this.htSong.get(title);
    }
    
    /**
     * Search this MusicStore for song by `artist' and return an 
     * ArrayList of all such Songs.
     */
    public ArrayList<Song> searchByArtist(String artist) {
    	if(this.htArtist.get(artist)!=null) {
    		return this.htArtist.get(artist);
    	}
    	return new ArrayList<Song>();
    }
    
    /**
     * Search this MusicSotre for all songs from a `year'
     *  and return an ArrayList of all such  Songs  
     */
    public ArrayList<Song> searchByYear(Integer year) {
    	if(this.htYear.get(year) != null) {
    		return this.htYear.get(year);
    	}
    	return new ArrayList<Song>();    
    }
}
