package model;

public abstract class Record {
	 private final String Name;
	 private final Double Total;
	 private final String Date;
	 private final int userId;
	 
	public Record(String name, Double total, String date,int userId) {
		super();
		Name = name;
		Total = total;
		Date = date;
		this.userId=userId;
	}
	public String getName() {
		return Name;
	}
	public Double getTotal() {
		return Total;
	}
	public String getDate() {
		return Date;
	}	
	
	@Override
    public String toString() {
        // Customize the format as per your requirements
        return String.format("Name: %s, Total: %.2f, Date: %s", Name, Total, Date);
    }
	public int getUserId() {
		return userId;
	}

	 

}
