package com.example.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        FileUploaderGrpc.FileUploaderStub stub = FileUploaderGrpc.newStub(channel);
        StreamObserver<FileChunk> requestObserver = stub.uploadFile(new StreamObserver<UploadStatus>() {
            @Override
            public void onNext(UploadStatus value) {
                System.out.println("Server response: " + value.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("Upload completed.");
            }
        });

        try (InputStream inputStream = file.getInputStream()) {
            byte[] buffer = new byte[4096];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                FileChunk chunk = FileChunk.newBuilder()
                        .setFilename(file.getOriginalFilename())
                        .setContent(com.google.protobuf.ByteString.copyFrom(buffer, 0, read))
                        .build();
                requestObserver.onNext(chunk);
            }
        }

        requestObserver.onCompleted();
        return "File upload initiated";
    }
}
