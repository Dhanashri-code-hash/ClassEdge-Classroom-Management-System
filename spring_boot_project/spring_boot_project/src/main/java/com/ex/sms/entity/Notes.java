package com.ex.sms.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;
    private String description;
    private LocalDate uploadDate;
    private String filePath; // New: store uploaded file location

    public Notes(Long id, String filename, String description, LocalDate uploadDate) {
        this.id = id;
        this.filename = filename;
        this.description = description;
        this.uploadDate = uploadDate;
    }
    public Notes() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDate uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
