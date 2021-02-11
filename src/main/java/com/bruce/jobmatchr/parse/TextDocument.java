package com.bruce.jobmatchr.parse;

import java.io.File;

public class TextDocument {

    private File resumeFile;
    private String jobText;

    public TextDocument(File resumeFile) {
        this.resumeFile = resumeFile;
    }

    public TextDocument(String jobText) {
        this.jobText = jobText;
    }

    public TextDocument() {
    }
}
