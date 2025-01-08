package org.example;

import com.example.imageprocessing.ImageProcessingProto;
import com.example.imageprocessing.ImageProcessingProto.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.google.protobuf.ByteString;
import com.example.imageprocessing.ImageProcessingServiceGrpc;

public class ImageProcessingServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50051)
                .addService(new ImageProcessingServiceImpl())
                .build();

        System.out.println("Server started on port 50051...");
        server.start();
        server.awaitTermination();
    }

    static class ImageProcessingServiceImpl extends ImageProcessingServiceGrpc.ImageProcessingServiceImplBase {
        @Override
        public void processImage(ImageRequest request, StreamObserver<ImageResponse> responseObserver) {
            try {
                BufferedImage inputImage = ImageIO.read(new ByteArrayInputStream(request.getImageData().toByteArray()));
                BufferedImage outputImage;

                switch (request.getOperation()) {
                    case "grayscale":
                        outputImage = applyGrayscale(inputImage);
                        break;
                    case "invert":
                        outputImage = applyInvert(inputImage);
                        break;
                    case "resize":
                        int width = request.getWidth();
                        int height = request.getHeight();
                        outputImage = applyResize(inputImage, width, height);
                        break;
                    case "rotate":
                        double angle = request.getAngle();
                        outputImage = applyRotate(inputImage, angle);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown operation: " + request.getOperation());
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(outputImage, "png", baos);
                ByteString imageData = ByteString.copyFrom(baos.toByteArray());

                ImageResponse response = ImageResponse.newBuilder().setImageData(imageData).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } catch (Exception e) {
                responseObserver.onError(e);
            }
        }

        private BufferedImage applyGrayscale(BufferedImage image) {
            BufferedImage grayscaleImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            grayscaleImage.getGraphics().drawImage(image, 0, 0, null);
            return grayscaleImage;
        }

        private BufferedImage applyInvert(BufferedImage image) {
            BufferedImage invertedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    int rgba = image.getRGB(x, y);
                    int a = (rgba >> 24) & 0xff;
                    int r = 255 - ((rgba >> 16) & 0xff);
                    int g = 255 - ((rgba >> 8) & 0xff);
                    int b = 255 - (rgba & 0xff);
                    invertedImage.setRGB(x, y, (a << 24) | (r << 16) | (g << 8) | b);
                }
            }
            return invertedImage;
        }

        private BufferedImage applyResize(BufferedImage image, int width, int height) {
            BufferedImage resizedImage = new BufferedImage(width, height, image.getType());
            Graphics2D g = resizedImage.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(image, 0, 0, width, height, null);
            g.dispose();
            return resizedImage;
        }

        private BufferedImage applyRotate(BufferedImage image, double angle) {
            double rads = Math.toRadians(angle);
            double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
            int w = image.getWidth();
            int h = image.getHeight();
            int newWidth = (int) Math.floor(w * cos + h * sin);
            int newHeight = (int) Math.floor(h * cos + w * sin);

            BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, image.getType());
            Graphics2D g = rotatedImage.createGraphics();
            g.translate((newWidth - w) / 2, (newHeight - h) / 2);
            g.rotate(rads, w / 2, h / 2);
            g.drawRenderedImage(image, null);
            g.dispose();
            return rotatedImage;
        }
    }
}
