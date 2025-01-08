package org.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.google.protobuf.ByteString;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import com.example.imageprocessing.ImageProcessingServiceGrpc;
import com.example.imageprocessing.ImageProcessingProto.*;

public class ImageProcessingClient {

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.err.println("Не указана операция и картинка");
            System.exit(1);
        }

        String server =  "localhost";
        int port = 50051;
        String operation = args[0];
        String imageFile = args[1];

        ManagedChannel channel = ManagedChannelBuilder.forAddress(server, port).usePlaintext().build();
        ImageProcessingServiceGrpc.ImageProcessingServiceBlockingStub stub = ImageProcessingServiceGrpc.newBlockingStub(channel);

        byte[] imageData = new FileInputStream(new File(imageFile)).readAllBytes();
        ImageRequest.Builder requestBuilder = ImageRequest.newBuilder()
                .setOperation(operation)
                .setImageData(ByteString.copyFrom(imageData));

        if (operation.equals("resize")) {
            if (args.length < 4) {
                System.err.println("Введи размер нового изображения");
                System.exit(1);
            }
            int width = Integer.parseInt(args[2]);
            int height = Integer.parseInt(args[3]);
            requestBuilder.setWidth(width).setHeight(height);
        } else if (operation.equals("rotate")) {
            if (args.length < 3) {
                System.err.println("Введи угол");
                System.exit(1);
            }
            double angle = Double.parseDouble(args[2]);
            requestBuilder.setAngle(angle);
        }

        ImageRequest request = requestBuilder.build();
        ImageResponse response = stub.processImage(request);

        try (FileOutputStream fos = new FileOutputStream("output.png")) {
            fos.write(response.getImageData().toByteArray());
        }

        System.out.println("Сохранено как output.png");

        channel.shutdown();
    }
}
