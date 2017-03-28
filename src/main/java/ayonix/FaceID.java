
package ayonix;

import java.awt.geom.Point2D;
import java.awt.Rectangle;
import java.util.Vector;

public class FaceID
{
        public static final int PARAM_NTHREADS          = 1;
        public static final int PARAM_MINFACESIZE       = 2;
        public static final int PARAM_MAXFACESIZE       = 3;
        public static final int DETECT_SCLING           = 4;
        public static final int DETECT_MINCR            = 5;
        
        static
        {
        	System.out.println(System.getProperty("java.library.path"));
                System.loadLibrary("AyonixJavaSDK");
                System.loadLibrary("AyonixFaceID");
                System.out.println("bitti");
        }
        
        private long engine;
        
        public native int[] GetVersion();
        
        public FaceID(String engineFolder)
        {
                engine = InitEngine(engineFolder);
        }
        
        public void Close()
        {
                if(engine != 0)
                {
                        FinalizeEngine(engine);
                        engine = 0;
                }
        }
        
        public void SetParam(int param, float value)
        {
                SetEngineParam(engine, param, value);
        }
        
        public float GetParam(int param)
        {
                return GetEngineParam(engine, param);
        }
        
        public native AynxImage LoadImage(String location);
        public native AynxImage LoadImage(byte[] encoded);
        
        public AynxFace[] DetectFaces(AynxImage img)
        {
                return DetectFaces(engine, img, new Rectangle(0, 0, img.getWidth(), img.getHeight()));
        }
        
        public AynxFace[] DetectFaces(AynxImage img, Rectangle roi)
        {
                return DetectFaces(engine, img, roi);
        }
        
        public AynxFace MarkFace(AynxImage img, Point2D.Float lEye, Point2D.Float rEye)
        {
                return MarkFace(engine, img, lEye, rEye);
        } 
        
        public void PreprocessFace(AynxFace face)
        {
                PreprocessFace(engine, face);
        }
        
        public byte[] CreateAfid(AynxFace face)
        {
                return CreateAfid(engine, face);
        }
        
        public byte[] UpdateAfid(byte[] oldAfid, AynxFace face)
        {
                return UpdateAfid(engine, face, oldAfid);
        }
        
        public byte[] MergeAfids(byte[] afid1, byte[] afid2)
        {
                return MergeAfids(afid1, afid2);
        }
        
        public void MatchAfids(byte[] afid_query, Vector<byte[]> afids_db, float[] scores, int[] indexes)
        {
                MatchAfids(engine, afid_query, afids_db, scores, indexes);
        }
        
        private native long InitEngine(String engineFolder);
        private native void FinalizeEngine(long engine);
        private native void SetEngineParam(long engine, int param, float value);
        private native float GetEngineParam(long engine, int param);
        private native AynxFace[] DetectFaces(long engine, AynxImage img, Rectangle roi);
        private native AynxFace MarkFace(long engine, AynxImage img, Point2D.Float lEye, Point2D.Float rEye);
        private native void PreprocessFace(long engine, AynxFace face);
        private native byte[] CreateAfid(long engine, AynxFace face);
        private native byte[] UpdateAfid(long engine, AynxFace face, byte[] oldAfid);
        private native byte[] MergeAfids(long engine, byte[] afid1, byte[] afid2);
        private native void MatchAfids(long engine, byte[] afid, Vector<byte[]> afids, float[] scores, int[] indexes);
}

