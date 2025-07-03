package com.example.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class FileUploadService extends FileUploaderGrpc.FileUploaderImplBase {

    @Override
    public StreamObserver<FileChunk> uploadFile(StreamObserver<UploadStatus> responseObserver) {
        return new StreamObserver<FileChunk>() {
            private StringBuilder contentBuilder = new StringBuilder();
            private String filename = "";

            @Override
            public void onNext(FileChunk chunk) {
                filename = chunk.getFilename();
                contentBuilder.append(chunk.getContent().toStringUtf8());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("Received file: " + filename);
                System.out.println("Content:" + contentBuilder.toString());

                UploadStatus status = UploadStatus.newBuilder()
                        .setSuccess(true)
                        .setMessage("File received: " + filename)
                        .build();

                responseObserver.onNext(status);
                responseObserver.onCompleted();
            }
        };
    }
}
