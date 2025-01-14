package org.example;

import com.example.imageprocessing.ImageProcessingProto.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.google.protobuf.ByteString;
import com.example.imageprocessing.ImageProcessingServiceGrpc;

public class ImageProcessingServer {

    private static final Logger logger = LogManager.getLogger(ImageProcessingServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50051)
                .addService(new ImageProcessingServiceImpl())
                .build();

        logger.info("Сервер запущен на порту 50051...");
        server.start();
        server.awaitTermination();
    }

    private static class ImageProcessingServiceImpl extends ImageProcessingServiceGrpc.ImageProcessingServiceImplBase {
        private static final Logger logger = LogManager.getLogger(ImageProcessingServiceImpl.class);

        @Override
        public void processImage(ImageRequest request, StreamObserver<ImageResponse> responseObserver) {
            try {
                if (request.getImageData().isEmpty()) {
                    logger.error("Ошибка валидации: данные изображения отсутствуют");
                    responseObserver.onError(Status.INVALID_ARGUMENT
                            .withDescription("Данные изображения отсутствуют")
                            .asRuntimeException());
                    return;
                }

                BufferedImage inputImage = ImageIO.read(new ByteArrayInputStream(request.getImageData().toByteArray()));
                if (inputImage == null) {
                    logger.error("Ошибка валидации: не удалось прочитать изображение");
                    responseObserver.onError(Status.INVALID_ARGUMENT
                            .withDescription("Не удалось прочитать изображение")
                            .asRuntimeException());
                    return;
                }

                BufferedImage outputImage;

                switch (request.getOperation()) {
                    case GRAYSCALE:
                        logger.info("Обработка операции преобразования в черно-белый");
                        outputImage = applyGrayscale(inputImage);
                        break;
                    case INVERT:
                        logger.info("Обработка операции инверсии");
                        outputImage = applyInvert(inputImage);
                        break;
                    case RESIZE:
                        int width = request.getResizeParams().getWidth();
                        int height = request.getResizeParams().getHeight();
                        if (width <= 0 || height <= 0) {
                            logger.error("Ошибка валидации: некорректные размеры изображения");
                            responseObserver.onError(Status.INVALID_ARGUMENT
                                    .withDescription("Некорректные размеры изображения")
                                    .asRuntimeException());
                            return;
                        }
                        logger.info("Обработка операции изменения размера с шириной: {} и высотой: {}", width, height);
                        outputImage = applyResize(inputImage, width, height);
                        break;
                    case ROTATE:
                        double angle = request.getRotateParams().getAngle();
                        logger.info("Обработка операции поворота с углом: {}", angle);
                        outputImage = applyRotate(inputImage, angle);
                        break;
                    default:
                        logger.error("Ошибка валидации: неизвестная операция");
                        responseObserver.onError(Status.INVALID_ARGUMENT
                                .withDescription("Неизвестная операция: " + request.getOperation())
                                .asRuntimeException());
                        return;
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(outputImage, "png", baos);
                ByteString imageData = ByteString.copyFrom(baos.toByteArray());

                ImageResponse response = ImageResponse.newBuilder().setImageData(imageData).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } catch (Exception e) {
                logger.error("Ошибка при обработке изображения", e);
                responseObserver.onError(Status.INTERNAL
                        .withDescription("Error: " + e.getMessage())
                        .withCause(e)
                        .asRuntimeException());
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
