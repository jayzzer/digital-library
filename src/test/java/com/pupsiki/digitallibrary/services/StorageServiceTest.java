package com.pupsiki.digitallibrary.services;

import com.pupsiki.digitallibrary.annotations.StorageException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StorageServiceTest {

    @Value("${upload.path}")
    private String path;

    @Autowired
    private StorageService storageService;

    @Test
    public void uploadFile() throws Exception {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        Mockito.when(file.isEmpty()).thenReturn(false);
        Mockito.when(file.getOriginalFilename()).thenReturn("123");
        InputStream inputStream = new ByteArrayInputStream("123".getBytes());
        Mockito.when(file.getInputStream()).thenReturn(inputStream);
//        Mockito.verify(Files.class, Mockito.times(1)).copy(inputStream, Paths.get(path + "123"), StandardCopyOption.REPLACE_EXISTING);
        storageService.uploadFile(file);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void uploadFile_empty() throws Exception{
        MultipartFile file = Mockito.mock(MultipartFile.class);
        expectedException.expect(StorageException.class);
        expectedException.expectMessage("Failed to store empty file");
        Mockito.when(file.isEmpty()).thenReturn(true);
        storageService.uploadFile(file);
    }

    @Test
    public void uploadFile_error() throws Exception{
        MultipartFile file = new MockMultipartFile("not_empty_file.txt", new FileInputStream(new File("src/main/upload/not_empty_file.txt")));
        expectedException.expect(StorageException.class);
        expectedException.expectMessage(String.format("Failed to store file", file.getName()));
        //Mockito.when(file.isEmpty()).thenReturn(false);
        storageService.uploadFile(file);
    }
}