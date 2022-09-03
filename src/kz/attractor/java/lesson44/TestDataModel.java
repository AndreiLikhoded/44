package kz.attractor.java.lesson44;

import java.time.LocalDate;

public class TestDataModel {
    private Sampledocument document;


    public TestDataModel() {
        this.document = new Sampledocument(1, LocalDate.now(), "test text");
    }

    public Sampledocument getDocument() {
        return document;
    }

    public void setDocument(Sampledocument document) {
        this.document = document;
    }
}
