package it.dromedian;

public class JAccession {

	String accessionNumber;
	String url;
	String contentType;
	long size;

	public JAccession(String accessionNumber, String url, String contentType, long size) {

		this.accessionNumber = accessionNumber;
		this.url = url;
		this.contentType = contentType;
		this.size = size;

	}

	public String getAccessionNumber() {
		return accessionNumber;
	}

	public String getUrl() {
		return url;
	}

	public String getcontentType() {
		return contentType;
	}

	public long getSize() {
		return size;
	}

}
