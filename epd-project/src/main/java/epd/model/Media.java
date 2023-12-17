/**
 * 
 */
package epd.model;


public class Media {
	
	private String id;
	private String title;
	private String type;
	private String picture;
	private String cast;
	private String year;
	
	public static class Builder {
		
		private String id;
		private String title;
		private String type;
		private String picture;
		private String cast;
		private String year;
		
		public Builder withId (String id) {
			this.id = id;
			
			return this;
		}
		
		public Builder withTitle (String title) {
			this.title = title;
			
			return this;
		}
		
		public Builder withType (String type) {
			this.type = type;
			
			return this;
		}
		
		public Builder withPicture (String picture) {
			this.picture = picture;
			
			return this;
		}
		
		public Builder withCast (String cast) {
			this.cast = cast;
			
			return this;
		}
		
		public Builder withYear (String year) {
			this.year = year;
			
			return this;
		}
		
		public Media build() {
			Media media = new Media();
			
			media.id = this.id;
			media.title = this.title;
			media.type = this.type;
			media.picture = this.picture;
			media.cast = this.cast;
			media.year = this.year;
			
			return media;
		}
		
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getPicture() {
		return picture;
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getCast() {
		return cast;
	}

	public void setCast(String cast) {
		this.cast = cast;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	

}
