package objectdetection;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.videoio.VideoCapture;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class Main {
    public static void main(String[] args) {
        // Load OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Load the Haar cascade classifier for full body
        CascadeClassifier fullBodyCascade = new CascadeClassifier("fullbody.xml");

        // Open the default camera
        VideoCapture camera = new VideoCapture(0);

        // Check if camera is opened successfully
        if (!camera.isOpened()) {
            System.out.println("Error opening camera");
            System.exit(1);
        }

        // Create a window to display the camera feed
        String windowName = "Camera Feed";
        HighGui.namedWindow(windowName);

        // Capture and display camera frames
        while (true) {
            // Read a frame from the camera
            Mat frame = new Mat();
            camera.read(frame);

            // Detect fulwrite l body in the frame using the Haar cascade classifier
            Mat grayFrame = new Mat();
            Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
            MatOfRect fullBodyDetections = new MatOfRect();
            fullBodyCascade.detectMultiScale(grayFrame, fullBodyDetections);

            // Draw rectangles around the full body detections
            for (Rect rect : fullBodyDetections.toArray()) {
                Imgproc.rectangle(frame, rect.tl(), rect.br(), new Scalar(0, 255, 0), 2);
            }

            // Display the frame in the window
            HighGui.imshow(windowName, frame);

            // Wait for a key press to exit
            if (HighGui.waitKey(1) == 27) {
                break;
            }
        }

        // Release the camera and destroy the window
        camera.release();
        HighGui.destroyAllWindows();
    }
}