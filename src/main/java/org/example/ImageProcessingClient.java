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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImageProcessingClient {
    private static final Logger logger = LogManager.getLogger(ImageProcessingClient.class);

    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.err.println("Не указана операция и картинка");
            System.exit(1);
        }

        String server = "localhost";
        int port = 50051;
        String operation = args[0];
        String imageFile = args[1];

        ManagedChannel channel = ManagedChannelBuilder.forAddress(server, port).usePlaintext().build();
        ImageProcessingServiceGrpc.ImageProcessingServiceBlockingStub stub = ImageProcessingServiceGrpc.newBlockingStub(channel);

        byte[] imageData = new FileInputStream(new File(imageFile)).readAllBytes();
        ImageRequest.Builder requestBuilder = ImageRequest.newBuilder()
                .setImageData(ByteString.copyFrom(imageData));

        switch (operation) {
            case "resize":
                if (args.length < 4) {
                    logger.info("Введи размер нового изображения");
                    System.exit(1);
                }
                int width = Integer.parseInt(args[2]);
                int height = Integer.parseInt(args[3]);
                requestBuilder.setResizeParams(ResizeParams.newBuilder().setWidth(width).setHeight(height).build());
                break;
            case "rotate":
                if (args.length < 3) {
                    logger.info("Введи угол");
                    System.exit(1);
                }
                double angle = Double.parseDouble(args[2]);
                requestBuilder.setRotateParams(RotateParams.newBuilder().setAngle(angle).build());
                break;
            case "grayscale":
                requestBuilder.setGrayscaleParams(GrayscaleParams.newBuilder().build());
                break;
            case "invert":
                requestBuilder.setInvertParams(InvertParams.newBuilder().build());
                break;
            default:
                logger.info("Неизвестная операция: " + operation);
                System.exit(1);
        }

        ImageRequest request = requestBuilder.build();
        ImageResponse response = stub.processImage(request);

        try (FileOutputStream fos = new FileOutputStream("output.png")) {
            fos.write(response.getImageData().toByteArray());
        }

        logger.info("Сохранено как output.png");

        channel.shutdown();
    }
}
