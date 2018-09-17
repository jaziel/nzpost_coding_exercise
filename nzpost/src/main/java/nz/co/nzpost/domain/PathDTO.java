package nz.co.nzpost.domain;

public class PathDTO {

	private String source;

	private String destination;

	private Integer distance;

	public PathDTO(String source, String destination, Integer distance) {
		super();
		this.source = source;
		this.destination = destination;
		this.distance = distance;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

}
