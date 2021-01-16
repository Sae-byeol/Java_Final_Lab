
public class Book extends Item {
	String author;
	String company;
	String year;
	
	
	
	public Book(String title, String author, String company, String year, String poster, int star, String story,
			String review) {
		super();
		mode=2;
		this.title = title;
		this.author = author;
		this.company = company;
		this.year = year;
		this.poster = poster;
		this.star = star;
		this.story = story;
		this.review = review;
	}
	

}
