package com.bruce.jobmatchr.document;

import com.bruce.jobmatchr.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_document")
public class UserDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, name = "resume_file", nullable = false, unique = true)
    private String resumeFile;

    @Column(name = "size")
    private long size;

    @Column(name = "upload_time")
    private Date uploadTime;

    @Column(name = "content")
    private byte[] content;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userDocument")
    private User user;

    public UserDocument() {
    }

    public UserDocument(Long id, String resumeFile, long size, Date uploadTime, byte[] content) {
        this.id = id;
        this.resumeFile = resumeFile;
        this.size = size;
        this.uploadTime = uploadTime;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResumeFile() {
        return resumeFile;
    }

    public void setResumeFile(String resumeFile) {
        this.resumeFile = resumeFile;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
