import javax.swing.JList;

public class Movie extends Item {
	String director;
	String actor;
	String genre;
	String grade;
	String year;
	
	
	
	public Movie(String title, String director, String actor, String gnere, String grade, String year, String poster,
			int star, String story, String review) {
		super();
		mode=1;
		this.title = title;
		this.director = director;
		this.actor = actor;
		this.genre = gnere;
		this.grade = grade;
		this.year = year;
		this.poster = poster;
		this.star = star;
		this.story = story;
		this.review = review;
	}
	
	
	

}
