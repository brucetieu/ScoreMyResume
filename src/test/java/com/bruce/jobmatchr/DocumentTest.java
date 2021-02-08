package com.bruce.jobmatchr;

import com.bruce.jobmatchr.document.UserDocument;
import com.bruce.jobmatchr.document.UserDocumentRepository;
import com.bruce.jobmatchr.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DocumentTest {

    @Autowired
    private UserDocumentRepository userDocumentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Rollback(false)
    void testInsertDocument() throws IOException {
        File file = new File("/Users/bruce/Documents/Job-Hunt-2020-2021/BT_SWE_Resume_Edit2_2020.pdf");
        UserDocument userDocument = new UserDocument();
        userDocument.setResumeFile(file.getName());

        byte[] bytes = Files.readAllBytes(file.toPath());
        userDocument.setContent(bytes);
        long fileSize = bytes.length;
        userDocument.setSize(fileSize);
        userDocument.setUploadTime(new Date());

        UserDocument savedDoc = userDocumentRepository.save(userDocument);
        UserDocument existDoc = entityManager.find(UserDocument.class, savedDoc.getId());

        assertThat(existDoc.getSize()).isEqualTo(fileSize);

    }
}
