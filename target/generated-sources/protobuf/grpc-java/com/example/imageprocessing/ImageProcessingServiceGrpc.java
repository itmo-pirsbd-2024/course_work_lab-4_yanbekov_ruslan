package com.example.imageprocessing;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.24.0)",
    comments = "Source: ImageService.proto")
public final class ImageProcessingServiceGrpc {

  private ImageProcessingServiceGrpc() {}

  public static final String SERVICE_NAME = "ImageProcessingService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.example.imageprocessing.ImageProcessingProto.ImageRequest,
      com.example.imageprocessing.ImageProcessingProto.ImageResponse> getProcessImageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "processImage",
      requestType = com.example.imageprocessing.ImageProcessingProto.ImageRequest.class,
      responseType = com.example.imageprocessing.ImageProcessingProto.ImageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.imageprocessing.ImageProcessingProto.ImageRequest,
      com.example.imageprocessing.ImageProcessingProto.ImageResponse> getProcessImageMethod() {
    io.grpc.MethodDescriptor<com.example.imageprocessing.ImageProcessingProto.ImageRequest, com.example.imageprocessing.ImageProcessingProto.ImageResponse> getProcessImageMethod;
    if ((getProcessImageMethod = ImageProcessingServiceGrpc.getProcessImageMethod) == null) {
      synchronized (ImageProcessingServiceGrpc.class) {
        if ((getProcessImageMethod = ImageProcessingServiceGrpc.getProcessImageMethod) == null) {
          ImageProcessingServiceGrpc.getProcessImageMethod = getProcessImageMethod =
              io.grpc.MethodDescriptor.<com.example.imageprocessing.ImageProcessingProto.ImageRequest, com.example.imageprocessing.ImageProcessingProto.ImageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "processImage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.imageprocessing.ImageProcessingProto.ImageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.imageprocessing.ImageProcessingProto.ImageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ImageProcessingServiceMethodDescriptorSupplier("processImage"))
              .build();
        }
      }
    }
    return getProcessImageMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ImageProcessingServiceStub newStub(io.grpc.Channel channel) {
    return new ImageProcessingServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ImageProcessingServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ImageProcessingServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ImageProcessingServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ImageProcessingServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ImageProcessingServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void processImage(com.example.imageprocessing.ImageProcessingProto.ImageRequest request,
        io.grpc.stub.StreamObserver<com.example.imageprocessing.ImageProcessingProto.ImageResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getProcessImageMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getProcessImageMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.example.imageprocessing.ImageProcessingProto.ImageRequest,
                com.example.imageprocessing.ImageProcessingProto.ImageResponse>(
                  this, METHODID_PROCESS_IMAGE)))
          .build();
    }
  }

  /**
   */
  public static final class ImageProcessingServiceStub extends io.grpc.stub.AbstractStub<ImageProcessingServiceStub> {
    private ImageProcessingServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ImageProcessingServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ImageProcessingServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ImageProcessingServiceStub(channel, callOptions);
    }

    /**
     */
    public void processImage(com.example.imageprocessing.ImageProcessingProto.ImageRequest request,
        io.grpc.stub.StreamObserver<com.example.imageprocessing.ImageProcessingProto.ImageResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getProcessImageMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ImageProcessingServiceBlockingStub extends io.grpc.stub.AbstractStub<ImageProcessingServiceBlockingStub> {
    private ImageProcessingServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ImageProcessingServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ImageProcessingServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ImageProcessingServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.example.imageprocessing.ImageProcessingProto.ImageResponse processImage(com.example.imageprocessing.ImageProcessingProto.ImageRequest request) {
      return blockingUnaryCall(
          getChannel(), getProcessImageMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ImageProcessingServiceFutureStub extends io.grpc.stub.AbstractStub<ImageProcessingServiceFutureStub> {
    private ImageProcessingServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ImageProcessingServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ImageProcessingServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ImageProcessingServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.imageprocessing.ImageProcessingProto.ImageResponse> processImage(
        com.example.imageprocessing.ImageProcessingProto.ImageRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getProcessImageMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PROCESS_IMAGE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ImageProcessingServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ImageProcessingServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PROCESS_IMAGE:
          serviceImpl.processImage((com.example.imageprocessing.ImageProcessingProto.ImageRequest) request,
              (io.grpc.stub.StreamObserver<com.example.imageprocessing.ImageProcessingProto.ImageResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ImageProcessingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ImageProcessingServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.imageprocessing.ImageProcessingProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ImageProcessingService");
    }
  }

  private static final class ImageProcessingServiceFileDescriptorSupplier
      extends ImageProcessingServiceBaseDescriptorSupplier {
    ImageProcessingServiceFileDescriptorSupplier() {}
  }

  private static final class ImageProcessingServiceMethodDescriptorSupplier
      extends ImageProcessingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ImageProcessingServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ImageProcessingServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ImageProcessingServiceFileDescriptorSupplier())
              .addMethod(getProcessImageMethod())
              .build();
        }
      }
    }
    return result;
  }
}
